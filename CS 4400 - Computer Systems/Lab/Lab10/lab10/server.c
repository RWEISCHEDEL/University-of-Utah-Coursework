#include "csapp.h"

#define TICK_EVERY 3

int main() {
  printf("Ticking at %d-second boundaries\n", TICK_EVERY);
  while (1) {
    struct timeval now;
    struct timespec delay;
    long now_s;
    
    gettimeofday(&now, NULL);
    if ((now.tv_usec == 0)
	&& ((now.tv_sec % TICK_EVERY) == 0)) {
      delay.tv_sec = TICK_EVERY;
      delay.tv_nsec = 0;
    } else {
      delay.tv_sec = (TICK_EVERY - 1 - (now.tv_sec % TICK_EVERY));
      delay.tv_nsec = (1000000 - now.tv_usec) * 1000;
    }
    
    while (nanosleep(&delay, &delay)) { }
    
    gettimeofday(&now, NULL);
    now_s = now.tv_sec;
    printf("tick %ld\n", now_s);
  }
}
