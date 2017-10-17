#include <stdio.h>

// GDB Help
// http://darkdust.net/files/GDB%20Cheat%20Sheet.pdf
// https://betterexplained.com/articles/debugging-with-gdb/

int fib(int n)
{
	/*	if(n == 0)
		return 0;

	if(n == 1)
		return 1;

		return fib(n - 1) + fib(n - 2);*/

	int i, prev = 1, curr = 1, ans = 0;

	if(n == 0){
		return 0;
	}

	if(n == 1){
		return 1;
	}

	for(int i = 0; i < n; i++){
		ans = prev + curr;
		prev = curr;
		curr = ans;
	}

	return ans;
}

int main(int argc, char const *argv[])
{
	if(argc < 2)
	{
		printf("%s\n", "Program requires an integer.");
		return -1;
	}

		printf("%d\n", fib(atoi(argv[1])));
	
	return 0;
}
