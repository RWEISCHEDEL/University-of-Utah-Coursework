#include "csapp.h"

const char * const cc_args[] = { "./count_child", NULL };

void handle(int sig){
  sio_puts("\n");
  _exit(1);
}

int main() {
  pid_t pid, fds[2];

  Pipe(fds);

  pid = Fork();
  if (pid == 0) {
    Execve(cc_args[0], cc_args, environ);
  } else {
    Waitpid(pid, NULL, 0);
  }

  while(1){
    int n;
    char buffer[256];

    n = Read(0,)
  }

  return 0;
}
