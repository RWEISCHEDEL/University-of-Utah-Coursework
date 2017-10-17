#include <stdio.h>
#include "h.h"

int main(int argc, char const *argv[])
{
	if(argc < 2)
	{
		printf("%s\n", "This program requires an integer");
		return -1;
	}
	
	int arg = atoi(argv[1]);
	h(arg);

	return 0;
}
