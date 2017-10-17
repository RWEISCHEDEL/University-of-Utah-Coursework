#include "csapp.h"
#include <stdio.h>

/* Define a check_heap function that can be called after malloc or
   free to check invariants of the heap structure for the allocator
   with coalescing. */

void check_heap()
{
    // Part 1:
    // Start at the first block payload
    // Traverse the list until we reach the terminator (0-sized allocated)
    // Make sure the footer size of each block matches the header size
    // If we detect an error, exit with status -1

    void *bp = 0;
    while (GET_SIZE(HDRP(bp)) != 0) {
        int Hsize = GET_SIZE(HDRP(bp));
        int Fsize = GET_SIZE(FTRP(bp));
        if (Hsize != Fsize) {
            exit(-1);
        }

        printf("%d\n", Hsize);
        printf("%d\n", Fsize);

        // Part 2:
        // In addition to the above, enforce the free list coalescing invariant
        // (No two consecutive blocks can be free)

        int isAlloc = GET_ALLOC(HDRP(bp));
        int isNextAlloc = GET_ALLOC(HDRP(NEXT_BLKP(bp)));
        if((isAlloc == isNextAlloc) && isAlloc == 0){
            exit(-1);
        }

        bp = NEXT_BLKP(bp);
    }

}

int main(){
    check_heap();
}
