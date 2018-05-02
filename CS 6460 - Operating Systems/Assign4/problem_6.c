#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <assert.h>

volatile int in_cs;
volatile double total = 0;
volatile double hits = 0;
int stop;
pthread_mutex_t lock;

void critical_section();
double rand_d(double l, double h); 


int main(int argc, char* argv[]){

	// Ensure correct number of inputs
	if(argc != 3){
		fprintf(stderr, "Incorrect number of input args.\n");
		return 1;
	}
	
	int threadsCount = atoi(argv[1]);
  	int secondsCount = atoi(argv[2]);

	in_cs = 0;
	stop = 0;
	
	int createFlag = -1;
	
	pthread_t * threadList = malloc(sizeof(pthread_t) * threadsCount);

	for(int i = 0; i < threadsCount; i ++){
		
		createFlag = pthread_create(&threadList[i], NULL, (void *) &critical_section, NULL);

		if(createFlag != 0){
			fprintf(stderr, "Failed to create thread.\n");
			exit(1);
		}
	}
	
	sleep(secondsCount);
	
	stop = 1;

	int joinFlag = -1;

	for(int i = 0; i < threadsCount; i ++){

		joinFlag = pthread_join(threadList[i], NULL);

		if(joinFlag != 0){
			fprintf(stderr, "Failed to join threads.\n");
			exit(1);
		}
	}

	printf("Number of Points: %f \n", total);
	printf("Number of points within the circle: %f \n", hits);
	printf("Pi is roughly equal to: %f \n", 4.0 * hits / total); 

	free((void*) threadList);
	
	return 0;
}

void critical_section(){

	while(!stop){

		pthread_mutex_lock(&lock);

		in_cs++;
		assert(in_cs == 1);
		in_cs++;
		assert(in_cs == 2);
		in_cs++;
		assert(in_cs == 3);
		in_cs = 0;
		
		// Create The Random Point
		double x = rand_d(-1, 1);
		double y = rand_d(-1, 1);
		
		total++;

		if(((x * x) + (y * y)) < 1){
			hits++;
		}

		pthread_mutex_unlock(&lock);
	}
}

double rand_d(double l, double h){
	return ((double)rand() * (h - l)) / (double)RAND_MAX + l;
}
