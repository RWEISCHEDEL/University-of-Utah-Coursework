/* This is the main file for the `whoosh` interpreter and the part
   that you modify. */

// Robert Weischedel
// u0887905
// CS 4400 - Shell Lab

#include <stdlib.h>
#include <stdio.h>
#include "csapp.h"
#include "ast.h"
#include "fail.h"

pid_t *procedure;
int procedureCount;

static void run_script(script *scr);
static void run_group(script_group *group);
static void run_command(script_command *command);
static void set_var(script_var *var, int new_value);
static void setState(script_var *script, int state);
static void endProcedure(int pid);

/* You probably shouldn't change main at all. */

int main(int argc, char **argv) {
  script *scr;

  if ((argc != 1) && (argc != 2)) {
    fprintf(stderr, "usage: %s [<script-file>]\n", argv[0]);
    exit(1);
  }

  scr = parse_script_file((argc > 1) ? argv[1] : NULL);

  run_script(scr);

  return 0;
}

static void run_script(script *scr) {
  int i;
  for(i = 0; i < scr->num_groups; i++){
    run_group(&scr->groups[i]);
  }
}

static void run_group(script_group *group) {

  sigset_t mask;
  Sigemptyset(&mask);
  Sigaddset(&mask,SIGINT);
  Signal(SIGINT, endProcedure);

  int commandID[group->num_commands];

  int i;
  for(i = 0; i < group->repeats; i++){
    int pid;
    int state;

    if(group->mode == 0){

      Sigprocmask(SIG_BLOCK,&mask,NULL);

      pid = Fork();
      if(pid == 0){
        Setpgid(0,0);
        run_command(&group->commands[0]);
      }

      if(group->commands[0].pid_to != NULL){
        set_var(group->commands[0].pid_to,pid);
      }

      commandID[0] = pid;
      procedure = commandID;
      procedureCount = group->num_commands;
      Sigprocmask(SIG_UNBLOCK,&mask,NULL);
      Waitpid(pid,&state,0);

      if(group->result_to != NULL){
        setState(group->result_to, state);
      }

    }
    else if(group->mode == 2){

      Sigprocmask(SIG_BLOCK,&mask,NULL);
      int j;
      for(j = 0; j < group->num_commands; j++){
        pid = Fork();
        if(pid == 0){
          Sigprocmask(SIG_BLOCK,&mask,NULL);
          Setpgid(0,0);
          run_command(&group->commands[j]);
        }

        commandID[j] = pid;
        if(group->commands[j].pid_to != NULL){
          set_var(group->commands[j].pid_to,pid);
        }
      }

      procedure = commandID;
      procedureCount = group->num_commands;
      Sigprocmask(SIG_UNBLOCK,&mask,NULL);

      pid = Waitpid(-1, &state, 0);

      if(group->result_to != NULL){
        setState(group->result_to, state);
      }

      int k;
      for(k = 0; k < group->num_commands; k++){
        if(pid != commandID[k]){
          Kill(commandID[k],SIGTERM);
          Waitpid(commandID[k],&state,0);
        }
      }

    }
    else if(group->mode == 1){
      int fds[2];
      int input = 0;

      Sigprocmask(SIG_BLOCK,&mask,NULL);

      int l;
      for(l = 0; l < group->num_commands - 1; l++){
        Pipe(fds);

        pid = Fork();
        if(pid == 0){
          Setpgid(0,0);

          if(input != 0){
            Dup2(input, 0);
            Close(input);
          }

          if(fds[1] != 1){
            Dup2(fds[1],1);
            Close(fds[1]);
          }
          run_command(&group->commands[l]);
        }

        commandID[l] = pid;
        if(group->commands[l].pid_to != NULL){
          set_var(group->commands[l].pid_to,pid);
        }

        Close(fds[1]);
        input = fds[0];
      }

      pid = Fork();

      if(pid == 0){
        Setpgid(0,0);
        if(input != 0){
          Dup2(input,0);
        }
        run_command(&group->commands[l]);
      }

      commandID[l] = pid;
      if(group->commands[l].pid_to != NULL){
        set_var(group->commands[l].pid_to,pid);
      }

      procedure = commandID;
      procedureCount = group->num_commands;
      Sigprocmask(SIG_UNBLOCK,&mask,NULL);

      int m;
      for(m = 0; m < group->num_commands; m++){
        Waitpid(commandID[m],&state,0);
        if(group->result_to != NULL){
          setState(group->result_to, state);
        }
      }
    }
  }
}

static void setState(script_var *script, int state){
  if (WIFSIGNALED(state)){
    set_var(script, -WTERMSIG(state));
  }
  else if(WIFEXITED(state)){
    set_var(script, WEXITSTATUS(state));
  }
}

static void endProcedure(int pid){
  int i;
  if(procedureCount > 0 && procedure != NULL){
    for(i = 0; i < procedureCount; i++){
      Kill(procedure[i], SIGTERM);
    }
  }
}


/* This run_command function is a good start, but note that it runs
   the command as a replacement for the `whoosh` script, instead of
   creating a new process. */

static void run_command(script_command *command) {
  const char **argv;
  int i;

  argv = malloc(sizeof(char *) * (command->num_arguments + 2));
  argv[0] = command->program;
  
  for (i = 0; i < command->num_arguments; i++) {
    if (command->arguments[i].kind == ARGUMENT_LITERAL)
      argv[i+1] = command->arguments[i].u.literal;
    else
      argv[i+1] = command->arguments[i].u.var->value;
  }
  
  argv[command->num_arguments + 1] = NULL;

  Execve(argv[0], (char * const *)argv, environ);
}

/* You'll likely want to use this set_var function for converting a
   numeric value to a string and installing it as a variable's
   value: */

static void set_var(script_var *var, int new_value) {
  char buffer[32];
  free((void *)var->value);
  snprintf(buffer, sizeof(buffer), "%d", new_value);
  var->value = strdup(buffer);
}
