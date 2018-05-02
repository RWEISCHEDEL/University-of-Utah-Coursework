#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <sys/syscall.h> 
#include <unistd.h>
#include <sys/types.h>
#include <pthread.h>
#include <time.h>
#include <assert.h>

int n_cats;
int n_dogs;
int n_birds;

int catPlayCount;
int dogPlayCount;
int birdPlayCount;

volatile int cats;
volatile int dogs;
volatile int birds;

long start;

pthread_cond_t cond = PTHREAD_COND_INITIALIZER;
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

void play(void){

	for (int i = 0; i < 10; i++){

		assert(cats >= 0 && cats <= n_cats);
		assert(dogs >= 0 && dogs <= n_dogs);
		assert(birds >= 0 && birds <= n_birds);
		assert(cats == 0 || dogs == 0);
		assert(cats == 0 || birds == 0);

	}

	return;
}

void cat_enter(void){

	int resultVal = pthread_mutex_lock(&lock);

	if(resultVal != 0){
		printf("Problem With Lock.\n");
	}

	while(birds > 0 || dogs > 0){

		resultVal = pthread_cond_wait(&cond, &lock);

		if(resultVal != 0){
			printf("Wait Condition Failed.\n");
		}
			
	}

	cats++;
	catPlayCount++;
	
	resultVal = pthread_mutex_unlock(&lock);

	if(resultVal != 0){
		printf("Problem With Unlock.\n");
	}

	return;
}

void cat_exit(void){

	int resultVal = pthread_mutex_lock(&lock);

	if(resultVal != 0){
		printf("Problem With Lock.\n");
	}

	cats--;
	
	resultVal = pthread_cond_signal(&cond);

	if(resultVal != 0){
		printf("Signal Condition Failed.\n");
	}

	resultVal = pthread_mutex_unlock(&lock);

	if(resultVal != 0){
		printf("Problem With Unlock.\n");
	}

	return;
}

void dog_enter(void){

	int resultVal = pthread_mutex_lock(&lock);

	if(resultVal != 0){
		printf("Problem With Lock.\n");
	}

	while(cats > 0){

		resultVal = pthread_cond_wait(&cond, &lock);

		if(resultVal != 0){
			printf("Wait Condition Failed.\n");
		}

	}

	dogs++;
	dogPlayCount++;
	
	resultVal = pthread_mutex_unlock(&lock);

	if(resultVal != 0){
		printf("Problem With Unlock.\n");
	}

	return;
}

void dog_exit(void){

	int resultVal = pthread_mutex_lock(&lock);

	if(resultVal != 0){
		printf("Problem With Lock.\n");
	}

	dogs--;

	resultVal = pthread_cond_signal(&cond);

	if(resultVal != 0){
		printf("Signal Condition Failed.\n");
	}


	resultVal = pthread_mutex_unlock(&lock);

	if(resultVal != 0){
		printf("Problem With Unlock.\n");
	}

	return;
}

void bird_enter(void){

	int resultVal = pthread_mutex_lock(&lock);

	if(resultVal != 0){
		printf("Problem With Lock.\n");
	}

	while(cats > 0) {

		resultVal = pthread_cond_wait(&cond, &lock);

		if(resultVal != 0){
			printf("Wait Condition Failed.\n");
		}

	}

	birds++;
	birdPlayCount++;
	
	resultVal = pthread_mutex_unlock(&lock);

	if(resultVal != 0){
		printf("Problem With Unlock.\n");

	}

	return;
}

void bird_exit(void){

	int resultVal = pthread_mutex_lock(&lock);

	if(resultVal != 0){
		printf("Problem With Lock.\n");
	}

	birds--;

	resultVal = pthread_cond_signal(&cond);

	if(resultVal != 0){
		printf("Signal Condition Failed.\n");
	}

	resultVal = pthread_mutex_unlock(&lock);

	if(resultVal != 0){
		printf("Problem With Unlock.\n");
	}

	return;
}

void* runCatEnterPlayExit(void* arg){

	while((time(NULL) - start) < 10){

		cat_enter();
		play();
		cat_exit();
	}

	return NULL;
}

void* runDogEnterPlayExit(void* arg){

	while((time(NULL) - start) < 10){

		dog_enter();
		play();
		dog_exit();
	}

	return NULL;
}

void* runBirdEnterPlayExit(void* arg){

	while((time(NULL) - start) < 10){

		bird_enter();
		play();
		bird_exit();
	}

	return NULL;
}

void petMotel(void){

	pthread_t catThreadsCount[n_cats];
	pthread_t dogThreadsCount[n_dogs];
	pthread_t birdThreadsCount[n_birds];

	start = time(NULL);

	int i;

	for(i = 0; i < n_cats; i++){

		int resultVal = pthread_create(&catThreadsCount[i], NULL, &runCatEnterPlayExit, (void*)(long)i);

		if(resultVal != 0){
			printf("Create Thread Failed.\n");
		}

	}

	for(i = 0; i < n_dogs; i++){

		int resultVal = pthread_create(&dogThreadsCount[i], NULL, &runDogEnterPlayExit, (void*)(long)i);

		if(resultVal != 0){
			printf("Create Thread Failed.\n");
		}

	}

	for(i = 0; i < n_birds; i++){

		int resultVal = pthread_create(&birdThreadsCount[i], NULL, &runBirdEnterPlayExit, (void*)(long)i);

		if(resultVal != 0){
			printf("Create Thread Failed.\n");
		}

	}

	for(i = 0; i < n_cats; i++){

		int resultVal = pthread_join(catThreadsCount[i], NULL);

		if(resultVal != 0){
			printf("Create Thread Failed.\n");
		}

	}

	for(i = 0; i < n_dogs; i++){

		int resultVal = pthread_join(dogThreadsCount[i], NULL);

		if(resultVal != 0){
			printf("Create Thread Failed.\n");
		}

	}

	for(i = 0; i < n_birds; i++){

		int resultVal = pthread_join(birdThreadsCount[i], NULL);

		if(resultVal != 0){
			printf("Create Thread Failed.\n");
		}

	}

	return;
}



int main(int argc, char* argv[]){

	if(argc != 4){

		printf("Incorrect Number of Input Arguments.\n");
		return 0;

	}

	n_cats = atoi(argv[1]);
	n_dogs = atoi(argv[2]);
	n_birds = atoi(argv[3]);

	if(n_cats < 0 || n_cats > 99 || n_dogs < 0 || n_dogs > 99 || n_birds < 0 || n_birds > 99){

		printf("All Numbers For Cats, Dogs, and Birds must be between 0 and 99.\n");
		return 0;

	}

	catPlayCount = 0;
	dogPlayCount = 0;
	birdPlayCount = 0;

	cats = 0;
	dogs = 0;
	birds = 0;

	petMotel();

	printf("cat play = %d, dog play = %d, bird play = %d\n", catPlayCount, dogPlayCount, birdPlayCount);

	return 0;
}
