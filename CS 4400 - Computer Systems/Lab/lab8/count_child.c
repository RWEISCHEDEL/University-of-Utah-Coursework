#include "csapp.h"

int main() {
  char buffer[256];
  int n, got = 0;
  
  while (1) {
    n = Read(0, buffer, sizeof(buffer));
    if (!n)
      break;
    got += n;
  }

  printf("got %d\n", got);

  return 0;
}
