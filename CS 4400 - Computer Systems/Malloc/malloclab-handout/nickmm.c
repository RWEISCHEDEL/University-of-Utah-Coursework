/*
 * mm-naive.c - The least memory-efficient malloc package.
 *
 * In this naive approach, a block is allocated by allocating a
 * new page as needed.  A block is pure payload. There are no headers or
 * footers.  Blocks are never coalesced or reused.
 *
 * NOTE TO STUDENTS: Replace this header comment with your own header
 * comment that gives a high level description of your solution.
 */
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <unistd.h>
#include <string.h>
#include <stdint.h>

#include "mm.h"
//#include "memlib.h"

/* always use 16-byte alignment */
#define ALIGNMENT 16
#define PAGECOST 16 // PAGEOVERHEAD

/* rounds up to the nearest multiple of ALIGNMENT */
#define ALIGN(size) (((size) + (ALIGNMENT-1)) & ~(ALIGNMENT-1))

/* rounds up to the nearest multiple of mem_pagesize() */
//#define PAGE_ALIGN(size) (((size) + (mem_pagesize()-1)) & ~(mem_pagesize()-1))
#define PAGE_ALIGN(size) (((size) + (4096-1)) & ~(4096 - 1))

#define PACK(size, alloc) ((size) | (alloc))
#define GET(p) (*(uintptr_t *)(p))
#define PUT(p, val) (*(uintptr_t *)(p) = (val))
#define WORDSIZE(size) ((1+((size-1)/16)+(((size-1)%16)/8))*16) // WORDBYTES

#define PUTHDR(p, value) (*(uintptr_t *)((char *)(p - 4)) = (value))
#define PUTFTR(p, size, value) (*(uintptr_t *)((char *)(p + size - 8)) = (value))
//#define PUTFTR(p, value) (*(uintptr_t *)((char *)(p - 8)) = (value))
#define GETHDR(p) (*(uintptr_t *)((char *)(p - 4)) & ~0xF)
//#define GETFTR(p, size) (*(uintptr_t *)((char *)(p + size - 8)))
//#define GETFTR(p) (*(uintptr_t *)((char *)(p - 8)))
#define GETFTR(p) (*(uintptr_t *)((char *)(p + ((int)GETHDR(p))- 8)))
#define GETHDRALLOC(p) (*(uintptr_t *)((char *)(p - 4)) & 0x1)

void *freeListStart = NULL;
int availSize = 0;

/*FOR TESTING*/
void *pages[50];
int pageCount;

/*
 * mm_init - initialize the malloc package.
 */
int mm_init(void)
{
  freeListStart = NULL;
  availSize = 0;

  pageCount = 0;

  return 0;
}

/*
 * mm_malloc - Allocate a block by using bytes from current_avail,
 *     grabbing a new page if necessary.
 */
void *mm_malloc(size_t size)
{
  void *p;
  void *sizePtr = NULL;

  if((int)availSize < (int)WORDSIZE(size)){
    int currentAvailSize = PAGE_ALIGN(WORDSIZE(size) + PAGECOST);
    // void *currentAvail = mem_map(currentAvailsize);
    void *currentAvail = malloc(currentAvailSize);

    // FOR TESTING PURPOSES
    pages[pageCount] = currentAvail;
    pageCount++;

    if(currentAvail == NULL){
      return NULL;
    }

    p = (char *)(currentAvail + PAGECOST);
    PUTFTR(p, 0, 0);
    PUTFTR(p, WORDSIZE(size), WORDSIZE(size));
    PUTHDR((void *)((char *)(currentAvail + currentAvailSize)),0);
    PUTHDR(p,PACK(WORDSIZE(size), 1));

    if((int)WORDSIZE(size) + PAGECOST < (int)currentAvailSize){
      PUTHDR((char *)(p + WORDSIZE(size)),PACK(currentAvailSize - WORDSIZE(size) - PAGECOST,0));
      sizePtr = (char *)(p + WORDSIZE(size));
      PUTFTR(currentAvail, currentAvailSize, currentAvailSize - WORDSIZE(size) - PAGECOST);
      if(currentAvailSize - WORDSIZE(size) - 16 > availSize){
        availSize = currentAvailSize - (int)WORDSIZE(size) - PAGECOST;
      }
    }

  }
  else{
    void *free = freeListStart;
    void *last = NULL;

    while(1){

      if((int)GETHDR(free) >= (int)WORDSIZE(size)){

        if(last == NULL){
          freeListStart = (void *)(GET(free));
        }
        else{
          *(uintptr_t *)(last) = (GET(free));
        }

        p = free;
        PUTHDR(p, PACK(WORDSIZE(size),1));
        PUTFTR(p, WORDSIZE(size), (int)WORDSIZE(size));

        if((int)WORDSIZE(size) < (int)GETHDR(free)){
          PUTHDR(((char *)p + WORDSIZE(size)), PACK(GET(free) - (int)WORDSIZE(size),0));
          PUTFTR(p, (int)GETHDR(free), (int)GETHDR(free) - (int)WORDSIZE(size));
          sizePtr = (void *)((char *)(p + ((int)WORDSIZE(size))));
        }

        break;
      }
      else{
        last = free;
        free = (void *)(GET(free));
      }

    }

  }

  if(sizePtr != NULL){
    void *free = freeListStart;
    void *last = NULL;

    while(1){
      if(free == NULL){
        freeListStart = sizePtr;
        *(uintptr_t *)(sizePtr) = 0;
        break;
      }
      else if((int)GETHDR(free) >= (int)GETHDR(sizePtr)){
        if(last != NULL){
          *(uintptr_t *)(last) = (uintptr_t)(sizePtr);
        }
        else{
          freeListStart = sizePtr;
        }
        *(uintptr_t *)(sizePtr) = (uintptr_t)(free);
        break;
      }
      else{
        last = free;
        free = (void *)(*(uintptr_t *)(free));
      }
    }
  }

  if(freeListStart != NULL){
    void *free = freeListStart;

    while(1){
      printf("%d (%d) ", GETHDR(free), GET(free));
      if((int)(*(uintptr_t *)(free)) == 0){
        availSize = GETHDR(free);
        break;
      }
      else{
        free = (void *)(*(uintptr_t *)(free));
      }
    }
  }
  else{
    availSize = 0;
  }

  printf("largest: %d\n\n", availSize);


  return p;
}

void mmPrintPages(){
  printf("\n\n");
  int i;
  for(i = 0; i < pageCount; i++){
    int offset = 16;
    printf("Page %d: ", i + 1);
    while(1){
      int header = GETHDR((void *)((char *)(pages[i] + offset)));
      int alloc = GETHDRALLOC((void *)((char *)(pages[i] + offset)));
      int footer = GETFTR((char *)(pages[i] + offset + header));

      printf("{%d,%d,%d | %d} ", header, alloc, footer, offset);
      if(header != 0){
        offset += header;
      }
      else{
        printf("\n\n");
        break;
      }

    }
  }
}

/*
 * mm_free - Freeing a block does nothing.
 */
void mm_free(void *ptr)
{
}
