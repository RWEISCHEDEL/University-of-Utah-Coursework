/* This file implements the parser for `whoosh` scripts. You should
   not modify it. */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <errno.h>
#include "ast.h"
#include "fail.h"

#define MAX_LINE_LENGTH 1024

/* Table of variables */
static int alloc_vars = 0;
static int num_vars = 0;
static script_var **vars = NULL;

/* Parsing functions: */
static void parse_group(const char *buffer, const char *buffer_start, script_group *group);
static const char *parse_variable(const char *buffer, const char *buffer_start, script_var **dest);
static const char *parse_command(const char *buffer, const char *buffer_start, script_command *command);
static int parse_literal(const char *buffer, const char *buffer_start, const char **literal);
static const char *skip_space(const char *buffer);

#define issep(c) ((c) == '|')

script *parse_script_file(const char *file) {
  FILE *f = (file ? fopen(file, "r") : stdin);
  script *scr;
  int alloc_groups;

  if (!f)
    fail("error opening script file\n path: %s\n error: %s",
         file,
         strerror(errno));

  scr = malloc(sizeof(script));
  scr->num_groups = 0;
  alloc_groups = 1;
  scr->groups = malloc(sizeof(script_group) * alloc_groups);
  
  while (1) {
    char buffer_start[MAX_LINE_LENGTH];
    const char *buffer;
    int i;

    if (!fgets(buffer_start, MAX_LINE_LENGTH, f))
      break;

    buffer = buffer_start;
          
    for (i = 0; buffer[i]; i++) {
      if (buffer[i] < 0)
        fail("script line contains non-ASCII character\n in: %s",
             buffer);
    }
    if (i >= (MAX_LINE_LENGTH - 1))
      fail("script line too long");

    if ((i > 0) && (buffer_start[i-1] == '\n'))
      buffer_start[i-1] = 0;

    buffer = skip_space(buffer);

    if (!buffer[0] || (buffer[0] == '#')) {
      /* skip a comment or empty line */
    } else {
      if (scr->num_groups == alloc_groups) {
        alloc_groups *= 2;
        scr->groups = realloc(scr->groups, sizeof(script_group) * alloc_groups);
      }

      parse_group(buffer, buffer_start, &scr->groups[scr->num_groups]);
      scr->num_groups++;
    }
  }

  if (file) fclose(f);

  return scr;
}

static void parse_group(const char *buffer, const char *buffer_start, script_group *group) {
  int alloc_commands;

  buffer = skip_space(buffer);
  
  if (!strncmp(buffer, "repeat ", 7)) {
    buffer += 7;
    group->repeats = 0;

    buffer = skip_space(buffer);
    if (!isdigit(buffer[0]))
      fail("not a valid number for repeat\n in: %s", buffer_start);
    
    while (isdigit(buffer[0])) {
      group->repeats *= 10;
      group->repeats += (buffer[0] - '0');
      buffer++;
    }

    if (!isspace(buffer[0]))
      fail("not a valid number for repeat\n in: %s", buffer_start);
    buffer = skip_space(buffer);
  } else if (!strcmp(buffer, "repeat"))
    fail("need a number after repeat\n in: %s");
  else
    group->repeats = 1;

  if (buffer[0] == '$') {
    buffer = parse_variable(buffer, buffer_start, &group->result_to);
    buffer = skip_space(buffer);
    if (buffer[0] != '=')
      fail("expected an equal sign\n after: $%s\n in: %s",
           group->result_to->name,
           buffer_start);
    buffer = skip_space(buffer+1);
  } else {
    group->result_to = NULL;
  }

  if (!buffer[0])
    fail("missing a command\n in: %s", buffer_start);

  group->mode = GROUP_SINGLE;
  alloc_commands = 1;
  group->num_commands = 0;
  group->commands = malloc(sizeof(script_command));
  
  while (1) {
    if (group->num_commands == alloc_commands) {
      alloc_commands *= 2;
      group->commands = realloc(group->commands, sizeof(script_command) * alloc_commands);
    }
    
    buffer = parse_command(buffer, buffer_start, &group->commands[group->num_commands]);
    group->num_commands++;

    buffer = skip_space(buffer);
    if (!buffer[0])
      break;

    if (buffer[0] == '|') {
      if (buffer[1] == '|') {
        if (group->mode == GROUP_SINGLE)
          group->mode = GROUP_OR;
        else if (group->mode != GROUP_OR)
          fail("mixed command combiners\n at: %s\n in: %s",
               buffer,
               buffer_start);
        buffer += 2;
      } else {
        if (group->mode == GROUP_SINGLE)
          group->mode = GROUP_AND;
        else if (group->mode != GROUP_AND)
          fail("mixed command combiners\n at: %s\n in: %s",
               buffer,
               buffer_start);
        buffer++;
      }
      buffer = skip_space(buffer);

      if (!buffer[0])
        fail("missing command after last combiner\n in: %s", buffer_start);
    } else {
      fail("expected a combiner %s\n at: %s\n in: %s",
           ((group->mode == GROUP_AND)
            ? "|"
            : ((group->mode == GROUP_OR)
               ? "||"
               : "| or ||")),
           buffer,
           buffer_start);
    }
  }
}

static const char *parse_variable(const char *buffer, const char *buffer_start, script_var **dest) {
  int len = 0;
  int i;
  script_var *var;

  if (buffer[0] != '$')
    fail("internal error: expected $ for a variable");

  while (isalpha(buffer[len+1]) || isdigit(buffer[len+1]))
    len++;

  if ((len == 0) || (buffer[len+1]
                     && !isspace(buffer[len+1])
                     && !issep(buffer[len+1])))
    fail("bad variable name\n at: %s\n in: %s",
         buffer,
         buffer_start);

  for (i = 0; i < num_vars; i++) {
    if (!strncmp(buffer+1, vars[i]->name, len)) {
      *dest = vars[i];
      return buffer + len + 1;
    }
  }
  
  var = malloc(sizeof(script_var));
  var->name = strndup(buffer + 1, len);
  var->value = strdup("0");

  if (num_vars == alloc_vars) {
    if (alloc_vars > 0)
      alloc_vars *= 2;
    else
      alloc_vars = 8;
    vars = realloc(vars, sizeof(script_var *) * alloc_vars);
  }
  vars[num_vars++] = var;

  *dest = var;
  
  return buffer + len + 1;
}

static const char *parse_command(const char *buffer, const char *buffer_start, script_command *command) {
  int len;
  int alloc_arguments;
  const char *literal;

  len = parse_literal(buffer, buffer_start, &literal);
  if (!len)
    fail("bad command\n at: %s, in: %s", buffer, buffer_start);

  command->program = literal;

  command->num_arguments = 0;
  alloc_arguments = 1;
  command->arguments = malloc(sizeof(script_argument) * alloc_arguments);
  command->pid_to = NULL;

  buffer += len;

  while (1) {
    script_var *var = NULL;

    buffer = skip_space(buffer);

    if (buffer[0] == '$') {
      buffer = parse_variable(buffer, buffer_start, &var);
      len = 0;
    } else if (buffer[0] == '@') {
      buffer = skip_space(buffer+1);
      if (buffer[0] == '$') {
        buffer = parse_variable(buffer, buffer_start, &var);
        command->pid_to = var;
        buffer = skip_space(buffer);
        
        if (!buffer[0]
            || isspace(buffer[0])
            || issep(buffer[0]))
          return buffer;

        fail("command didn't end after \"@\" variable\n at: %s\n in: %s",
             buffer,
             buffer_start);
      } else
        fail("expected a variable after \"@\"\n at: %s\n in: %s",
             buffer,
             buffer_start);
    } else {
      len = parse_literal(buffer, buffer_start, &literal);
      if (!len)
        break;
      buffer += len;
    }

    if (command->num_arguments == alloc_arguments) {
      alloc_arguments *= 2;
      command->arguments = realloc(command->arguments, sizeof(script_argument) * alloc_arguments);
    }

    if (var) {
      command->arguments[command->num_arguments].kind = ARGUMENT_VARIABLE;
      command->arguments[command->num_arguments].u.var = var;
    } else {
      command->arguments[command->num_arguments].kind = ARGUMENT_LITERAL;
      command->arguments[command->num_arguments].u.literal = literal;
    }
    command->num_arguments++;
  }

  return buffer;
}

static int parse_literal(const char *buffer, const char *buffer_start, const char **literal) {
  int len = 0, lit_len = 0;
  char lit_buffer[MAX_LINE_LENGTH];

  while (isalpha(buffer[len])
         || isdigit(buffer[len])
         || (buffer[len] == '.')
         || (buffer[len] == ':')
         || (buffer[len] == '_')
         || (buffer[len] == '-')
         || (buffer[len] == '=')
         || (buffer[len] == '/')
         || (buffer[len] == '"')) {
    if (buffer[len] == '"') {
      /* Find matching " */
      len++;
      while (buffer[len] != '"') {
        if (!buffer[len])
          fail("unterminated quote\n at: %s\n in %s",
               buffer,
               buffer_start);
        lit_buffer[lit_len++] = buffer[len++];
      }
      len++;
    } else {
      lit_buffer[lit_len++] = buffer[len++];
    }
  }

  lit_buffer[lit_len] = 0;

  if (!buffer[len]
      || isspace(buffer[len])
      || issep(buffer[len])) {
    *literal = strdup(lit_buffer);
    return len;
  }

  fail("bad literal\n at: %s\n in: %s",
       buffer + len,
       buffer_start);
  return 0;
}

static const char *skip_space(const char *buffer) {
  while (isspace(*buffer))
    buffer++;
  return buffer;
}

/* ************************************************************ */

/* To help test and debug the parser: */

void print_script(script *scr) {
  int i, j, k;
  script_group *group;
  script_command *command;
  script_argument *argument;

  for (i = 0; i < scr->num_groups; i++) {
    group = &scr->groups[i];

    if (group->repeats != 1) {
      printf("repeat %d ", group->repeats);
    }
    if (group->result_to != NULL) {
      printf("$%s = ", group->result_to->name);
    }

    for (j = 0; j < group->num_commands; j++) {
      if (j > 0)
        printf(" %s ", ((group->mode == GROUP_AND)
                        ? "|"
                        : "||"));

      command = &group->commands[j];
      printf("\"%s\"", command->program);

      for (k = 0; k < command->num_arguments; k++) {
        argument = &command->arguments[k];
        if (argument->kind == ARGUMENT_LITERAL)
          printf(" \"%s\"", argument->u.literal);
        else
          printf(" $%s", argument->u.var->name);
      }

      if (command->pid_to)
        printf(" @ $%s", command->pid_to->name);
    }

    printf("\n");
  }
}
