#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

int main(int argc, char* argv[]){

    int inputValue = 0;

    pid_t pid;

    while(inputValue <= 0){
        printf("Please enter a non-zero positive integer: \n");
        scanf("%d", &inputValue);
    }

    pid = fork();

    if(pid == 0){
        printf("%d\n", inputValue);

        while(inputValue != 1){

            if(inputValue % 2 == 0){
                inputValue = inputValue / 2;
            }
            else if(inputValue % 2 == 1){
                inputValue = 3 * inputValue + 1;
            }

            printf("%d\n", inputValue);


        }
    }
    else{
        printf("Everything is finished - Calling Wait() command\n");

        int status;

        wait(&status);

        if(status != 0){
            printf("Error!\n");
        }
    }


    return 0;


}
