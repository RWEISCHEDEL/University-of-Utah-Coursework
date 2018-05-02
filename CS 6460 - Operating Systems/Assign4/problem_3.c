#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <assert.h>

volatile int * ticketList;
volatile int * choosingList;
volatile int threadCount;
int * inCSCount;
volatile int in_cs;
int stop;


void critical_section(void * tid);
void lock (int tid);
void unlock(int tid);
int getTicketMax();
void mfence (void) { __asm volatile ("mfence" : : : "memory"); }

int main(int argc, char* argv[]){

	// Ensure correct number of inputs
	if(argc != 3){
		fprintf(stderr, "Incorrect number of input args.\n");
		return 1;
	}
	
	threadCount = atoi(argv[1]);
	int secondsCount = atoi(argv[2]);
	
	in_cs = 0;
	stop = 0;

	ticketList = malloc(sizeof(int) * threadCount);
	choosingList = malloc(sizeof(int) * threadCount);
	inCSCount = malloc(sizeof(int) * threadCount);
	
	int createFlag = -1;
	
	pthread_t * threadList = malloc(sizeof(pthread_t) * threadCount);


	for(int i = 0; i < threadCount; i ++){
		
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

	for(int i = 0; i < threadCount; i ++){

		joinFlag = pthread_join(threadList[i], NULL);

		if(joinFlag != 0){
			fprintf(stderr, "Failed to join threads.\n");
			exit (1);
		}
	}

	for(int i = 0; i < threadCount; i++){
		printf("Thread Number: %d entered Critical Section %d times.\n", i, inCSCount[i]);
	}

	free((void*) ticketList);
	free((void*) choosingList);
	free((void*) inCSCount);
	free((void*) threadList);

	return 0;
}

void critical_section(void * tid){

	int id = *((int *) tid);

	while(!stop){

		lock(id);
		
		inCSCount[id]++;	

		assert(in_cs == 0);
		in_cs++;
		assert(in_cs == 1);
		in_cs++;
		assert(in_cs == 2);
		in_cs++;
		assert(in_cs == 3);
		in_cs = 0;

		unlock(id);
	}
}

void lock(int tid){

	choosingList[tid] = 1;

	mfence();
	
	ticketList[tid] = getTicketMax() + 1;
	choosingList[tid] = 0;

	mfence();

	for(int i = 0; i < threadCount; i++){

		while(choosingList[i]);

		while((ticketList[i] != 0) && ((ticketList[i] < ticketList[tid]) || (ticketList[i] == ticketList[tid] && i < tid)));
	}
}

void unlock(int tid){

	ticketList[tid] = 0;
	
	mfence();
}

int getTicketMax(){

	int maxVal = 0;

	for(int i = 0; i < threadCount; i++)
	{
		if(maxVal < ticketList[i])
			maxVal = ticketList[i];
	}

	return maxVal;
}
