#include <stdio.h>

int main(){

    // 1 Word = 16 Byte Aligned
    int byteAligned = 16;
    int byteSizes[] = {1008, 1024, 1028};

    int i;
    for(i = 0; i < 3; i++){
        int size = byteSizes[i] - 1;
        printf("Size Minus 1:  %d\n", size);
        int pageSize = (1 + ((size/16)) + ((size%16) / 8)) * 16;
        printf("Page Size In Bytes: %d\n", pageSize);

    }

    return 0;
}
