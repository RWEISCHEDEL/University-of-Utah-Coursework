/*
 * mm.c
 *
 * For my implementation I attempted to do all the performance enhancing techniques taught to us by using a explicit free
 * doubly linked list for my free list, coalescing free blocks, and freed unused pages.Unfortunatley it does work as
 * planned it does and pass all the  tests but for some reason it is not as effcient as it should be. My best guess is
 * that this is because we are not relesasing the free pages in all situations and that when I free space that I might not
 * be doing that as effiecnently as possible.
 *
 * Robert Weischedel
 */
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <assert.h>
#include <unistd.h>
#include <string.h>
#include "mm.h"
#include "memlib.h"

#define PAGECOST 16

#define GETALLOC(p) (*(int *)((char *)(p - 4)) & 0x1)
#define GETHDR(p) (*(int *)((char *)(p - 4)) & ~0xF)
#define GETFTR(p) (*(int *)((char *)(p - 8)))
#define PUTHDR(p, value) (*(int *)((char *)(p - 4)) = (value))
#define PUTFTR(p, value) (*(int *)(char *)(p - 8) = (value))

#define GET(p) (*(uintptr_t *)(p))
#define PUT(p, val) (*(uintptr_t *)(p) = (val))
#define PACK(size, alloc) ((size) | (alloc))
#define WORDSIZE(size) ((1+((size-1)>>4)+(((size-1)%16)>>3))<<4)

#define PAGE_ALIGN(size) (((size) + (mem_pagesize()-1)) & ~(mem_pagesize()-1))

int pageCount;
void *freeListStart = NULL;

/*
 * mm_init - initialize the malloc package.
 */
int mm_init(void)
{
  pageCount = 0;
  freeListStart = NULL;
  return 0;
}

/*
 * mm_malloc - Allocate a block by using bytes from current_avail,
 *     grabbing a new page if necessary.
 */
void *mm_malloc(size_t size)
{
  void *p;
  void *last = NULL;
  void *free = freeListStart;

  int identifyValue = 0;

  while (1) {

    if (free == NULL){
      break;
    }
    else if ((int)GETHDR(free) >= (int)WORDSIZE(size) + 32) {

      if (last != NULL){
        PUT(last, *(uintptr_t *)(free));
      }
      else{
        freeListStart = (void *)(*(uintptr_t *)(free));
      }

      int hdrLength = (int)GETHDR(free);

      p = free;
      PUTHDR(p,PACK(WORDSIZE(size),1));
      PUTFTR((void *)((char *)(p + WORDSIZE(size))),WORDSIZE(size));

      if ((int)WORDSIZE(size) < hdrLength) {
        PUTHDR((char *)(p + WORDSIZE(size)),PACK(hdrLength - (int)WORDSIZE(size),0));
        PUTFTR((void *)((char *)(p + hdrLength)),hdrLength - (int)WORDSIZE(size));
        *(uintptr_t *)((char *)(p + WORDSIZE(size))) = (uintptr_t)freeListStart;
        freeListStart = (void *)((char *)(p + WORDSIZE(size)));
      }
      identifyValue++;
      break;
    }
    else {
      last = free;
      free = (void *)(*(uintptr_t *)(free));
    }
  }

  if (!identifyValue) {

    int openSize = PAGE_ALIGN(WORDSIZE(size) + PAGECOST);
    void *open = mem_map(openSize);

    pageCount++;

    if (open == NULL){
      return NULL;
    }

    p = (char *)(open + PAGECOST);
    PUTFTR(p,0);
    PUTHDR((void *)((char *)(open + openSize)), 0);
    PUTHDR(p,PACK(WORDSIZE(size), 1));
    PUTFTR((void *)((char *)(p + WORDSIZE(size))),WORDSIZE(size));

    if ((int)WORDSIZE(size) + PAGECOST < (int)openSize) {
      PUTHDR((char *)(p + WORDSIZE(size)),PACK(openSize - WORDSIZE(size) - PAGECOST,0));
      PUT((char *)(p + WORDSIZE(size)), (uintptr_t)freeListStart);
      freeListStart = (void *)((char *)(p + WORDSIZE(size)));
      PUTFTR((void *)((char *)(open + openSize)), openSize - WORDSIZE(size) - PAGECOST);
    }
  }

  return p;
}

/*
 * mm_free - Free the block and coalesce with other unnallocated space
 */
void mm_free(void *ptr)
{
  int start = 0;
  int end = 0;
  int size = (int)(GETHDR(ptr));
  uintptr_t linkDirections[2] = {0,0};

  if ((int)GETHDR((void *)((char *)(ptr + ((int)(GETHDR(ptr)))))) == 0){
    end = 1;
  }

  if ((int)GETFTR(ptr) == 0){
    start = 1;
  }

  if (start == 0 && (int)GETALLOC((void *)((char *)(ptr - ((int)GETFTR(ptr))))) == 0) {

    linkDirections[0] = (uintptr_t)((char *)(ptr - ((int)GETFTR(ptr))));
    size += (int)GETFTR(ptr);

    if ((int)GETFTR((void *)((char *)(ptr - ((int)GETFTR(ptr))))) == 0){
      start = 1;
    }

  }

  if (end == 0 && (int)GETALLOC((void *)((char *)(ptr + ((int)GETHDR(ptr))))) == 0) {

    linkDirections[1] = (uintptr_t)((char *)(ptr + ((int)GETHDR(ptr))));
    size += (int)GETHDR((void *)((char *)(ptr + ((int)GETHDR(ptr)))));

    if (GETHDR((void *)((char *)(ptr + GETHDR(ptr) + (GETHDR((void *)((char *)ptr + GETHDR(ptr))))))) == 0){
      end = 1;
    }

  }

  if (linkDirections[0] == 0 && linkDirections[1] == 0) {
    PUTHDR(ptr,PACK(size,0));
  }

  if (freeListStart != NULL) {

    int leftPtr;
    int rightPtr;
    void *free = freeListStart;
    void *last = NULL;

    if(linkDirections[0]){
      leftPtr = 0;
    }
    else{
      leftPtr = 1;
    }

    if(linkDirections[1]){
      rightPtr = 0;
    }
    else{
      rightPtr = 1;
    }

    while (1) {
      if (linkDirections[0] != 0 && (uintptr_t)free == linkDirections[0]) {

        if (last != NULL){
          PUT(last, *(uintptr_t *)(free));
        }
        else{
          freeListStart = (void *)(*(uintptr_t *)(free));
        }

        leftPtr = 1;
      }
      else if (linkDirections[1] != 0 && (uintptr_t)free == linkDirections[1]) {

        if (*(uintptr_t *)(free) == 0) {

          if (last != NULL){
            PUT(last, 0);
          }
          else{
            freeListStart = NULL;
          }

        }
        else {

          if (last != NULL){
            PUT(last, *(uintptr_t *)(free));
          }
          else{
            freeListStart = (void *)(*(uintptr_t *)(free));
          }

        }

        rightPtr = 1;
      }

      if (leftPtr && rightPtr){
        break;
      }

      if ((uintptr_t)free != linkDirections[0] && (uintptr_t)free != linkDirections[1]){
        last = free;
      }

      free = (void *)(*(uintptr_t *)(free));
    }
  }
  else {
    if (start || end){
      freeListStart = NULL;
    }
    else{
      freeListStart = ptr;
    }
  }

  if (!start || !end) {
    if (!linkDirections[0]) {
      *(uintptr_t *)(ptr) = (uintptr_t)freeListStart;
      freeListStart = (void *)ptr;
      if (!linkDirections[0]) {
        PUTHDR(ptr, PACK(size, 0));
        PUTFTR((void *)((char *)(ptr + size)),size);
      }
    }
    else {
      *(uintptr_t *)(linkDirections[0]) = (uintptr_t)freeListStart;
      freeListStart = (void *)linkDirections[0];
      PUTHDR(linkDirections[0], PACK(size, 0));
      PUTFTR((void *)((char *)(linkDirections[0] + size)), size);
    }
  }

  if (start == 1 && end == 1) {
    if (pageCount > 1) {
      if (linkDirections[0]){
        ptr = (uintptr_t *)linkDirections[0];
      }

      mem_unmap((void *)((char *)(ptr - 16)),size + 16);

      pageCount--;

    }
    else {
      if (!linkDirections[0]){
        freeListStart = ptr;
      }
      else{
        freeListStart = (void *)linkDirections[0];
      }
      *(uintptr_t *)freeListStart = 0;
      PUTHDR(freeListStart, size);
      PUTFTR((void *)((char *)(freeListStart + size)),size);
    }
  }
}
