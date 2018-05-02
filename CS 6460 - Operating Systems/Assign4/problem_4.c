#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <assert.h>

int * inCSCount;
volatile int in_cs;
int stop;

void critical_section(void * tid);
static inline int atomic_cmpxchg (volatile int *ptr, int old, int new);

struct spin_lock_t {

	volatile int lock;

};

struct spin_lock_t s;

void spin_lock (struct spin_lock_t *s);
void spin_unlock (struct spin_lock_t *s);


int main(int argc, char* argv[]){

	
	// Ensure correct number of inputs
	if(argc != 3){
		fprintf(stderr, "Incorrect number of input args.\n");
		return 1;
	}
	
	int	threadsCount = atoi(argv[1]);
  	int secondsCount = atoi(argv[2]);

	in_cs = 0;
	stop = 0;

	inCSCount = malloc(sizeof(int) * threadsCount);
	
	int createFlag = -1;

	pthread_t * threadList = malloc(sizeof(pthread_t) * threadsCount);

	for(int i = 0; i < threadsCount; i ++){
		
		int *arg = malloc(sizeof(*arg));
		*arg = i;

		createFlag = pthread_create(&threadList[i], NULL, (void *) &critical_section, arg);

		if(createFlag != 0){
			fprintf(stderr, "Failed to create thread.\n");
			exit(1);
		}
	}
	
	sleep(secondsCount);
	
	stop = 1;

	int joinFlag = -1;

	for(int i = 0; i < threadsCount; i ++)
	{
		joinFlag = pthread_join(threadList[i], NULL);

		if(joinFlag != 0){
			fprintf(stderr, "Failed to join threads.\n");
			exit(1);
		}
	}

	for(int i = 0; i < threadsCount; i++){
		printf("Thread Number: %d entered Critical Section %d times.\n", i, inCSCount[i]);
	}

	free((void*) inCSCount);
	free((void*) threadList);
	
	return 0;
}

void critical_section(void * tid){

	int id = *((int *) tid);

	while(!stop){
	
		spin_lock(&(s));

		inCSCount[id]++;	
		
		assert(in_cs == 0);
		in_cs++;
		assert(in_cs == 1);
		in_cs++;
		assert(in_cs == 2);
		in_cs++;
		assert(in_cs == 3);
		in_cs = 0;

		spin_unlock(&(s));
	}
}

void spin_lock(struct spin_lock_t *s){
	while(atomic_cmpxchg(&(s->lock), 0, 1));		
}

void spin_unlock(struct spin_lock_t *s){
	atomic_cmpxchg(&(s->lock), 1, 0);
}

/*
 * atomic_cmpxchg
 * 
 * equivalent to atomic execution of this code:
 *
 * if (*ptr == old) {
 *   *ptr = new;
 *   return old;
 * } else {
 *   return *ptr;
 * }
 *
 */
static inline int atomic_cmpxchg (volatile int *ptr, int old, int new){
	  int ret;
		
		__asm volatile ("lock cmpxchgl %2,%1"
			: "=a" (ret), "+m" (*ptr)     
			: "r" (new), "0" (old)      
			: "memory");         
	
		return ret;                            
}
