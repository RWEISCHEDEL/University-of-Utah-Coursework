
/* A parsed whoosh script: */
typedef struct {
  int num_groups;                 /* length of the `groups` array */
  struct script_group *groups;    /* sequence of groups to run */
} script;

/* A group within a script: */
typedef struct script_group {
  int mode;                        /* GROUP_SINGLE, GROUP_AND, or GROUP_OR */
  int repeats;                     /* number of copies of the commands to run */
  struct script_var *result_to;    /* NULL or a variable to receive result status */
  int num_commands;                /* length of the `commands` array; GROUP_SINGLE => 1 */
  struct script_command *commands; /* commands to run in the group */
} script_group;

/* Values for `mode`: */
#define GROUP_SINGLE 0
#define GROUP_AND    1
#define GROUP_OR     2

/* A single command within a group: */
typedef struct script_command {
  const char *program;             /* program to run */
  int num_arguments;               /* length of the `arguments` array */
  struct script_argument *arguments; /* arguments to provide to `program` */
  struct script_var *pid_to;       /* NULL or a variable */
} script_command;

/* An argument within a command: */
typedef struct script_argument {
  int kind;                 /* ARGUMENT_LITERAL or ARGUMENT_VARIABLE */
  union {
    const char *literal;    /* if ARGUMENT_LITERAL */
    struct script_var *var; /* if ARGUMENT_VARIABLE */
  } u;
} script_argument;

#define ARGUMENT_LITERAL  0
#define ARGUMENT_VARIABLE 1

/* A variable: */
typedef struct script_var {
  const char *name;  /* for parsing and debugging */
  const char *value; /* current value; script interpreters can change this */
} script_var;

/* Parser: */
script *parse_script_file(const char *file);

/* Printing a script back out is mostly useful for testing the
   parser: */
void print_script(script *scr);
