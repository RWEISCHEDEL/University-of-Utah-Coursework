#include "assign1.h"
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>

int main(int argc, char* argv[]){

	unsigned long input = 0x0403deadbeef0201;

	unsigned long output = 0;

	unsigned long output1 = 0;

	output = byte_sort(input);

	printf("%llx\n", output);

	output1 = nibble_sort(input);

	printf("%llx\n", output1);

	struct elt * list = name_list();

	while(list != NULL){
		printf("%c\n", list->val);
		list = list->link;
	}

	unsigned long input1 = 0xdeadbeef;

	convert(BIN, input1);

	draw_me();

}
