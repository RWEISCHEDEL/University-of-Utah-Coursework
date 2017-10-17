/*
 * memlib.c - bridge to mmap
 */
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <unistd.h>
#include <sys/mman.h>
#include <string.h>
#include <inttypes.h>
#include <errno.h>

#include "memlib.h"
#include "pagemap.h"

/* private variables */
static int activity_counter = 0; /* to simulate other processes */

static int page_count;

/* 
 * mem_init - initialize the memory system model
 */
void mem_init(void)
{
  size_t page_size = (size_t)getpagesize();
  if (APAGE_SIZE != page_size) {
    fprintf(stderr, "configuration error: APAGE_SIZE does not match %ld\n",
            page_size);
    abort();
  }
}

static void unmap(void *p)
{
  if (munmap(p, APAGE_SIZE) < 0) {
    fprintf(stderr, "unexpected error in munmap: %s (%d)\n",
            strerror(errno), errno);
    abort();
  }
}

/* 
 * mem_deinit - free the storage used by the memory system model
 */
void mem_reset(void)
{
  pagemap_for_each(unmap);
  page_count = 0;
  activity_counter = 0;
}

/*
 * mem_pagesize() - returns the page size of the system
 */
size_t mem_pagesize()
{
  return APAGE_SIZE;
}

size_t mem_heapsize(void)
{
  return APAGE_SIZE * page_count;
}


void *mem_map(size_t sz)
{
  void *p;
  size_t i;
  
  if (sz & (APAGE_SIZE - 1)) {
    fprintf(stderr, "mem_map: requested size is not a multiple of %d: %ld\n",
            APAGE_SIZE, sz);
    abort();
  }

  activity_counter++;
  if ((activity_counter & (activity_counter - 1)) == 0) {
    /* allocate a page to ensure that mem_map results are not
       always sequential */
    mmap(0, APAGE_SIZE, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANON, -1, 0);
  }

  p = mmap(0, sz, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANON, -1, 0);
  if (p == MAP_FAILED) {
    fprintf(stderr, "mmap failed: %s (%d)\n",
            strerror(errno), errno);
    abort();
  }

  for (i = 0; i < sz; i += APAGE_SIZE) {
    pagemap_modify(p + i, 1);
    page_count++;
  }
  
  return p;
}

void mem_unmap(void *p, size_t sz)
{
  size_t i;
  
  if (((uintptr_t)p) & (APAGE_SIZE - 1)) {
    fprintf(stderr, "mem_unmap: given address is not page-aligned: %p\n",
            p);
    abort();
  }

  if (sz & (APAGE_SIZE - 1)) {
    fprintf(stderr, "mem_unmap: given size is not a multiple of %d: %ld\n",
            APAGE_SIZE, sz);
    abort();
  }
  
  for (i = 0; i < sz; i += APAGE_SIZE) {
    if (!pagemap_is_mapped(p+i)) {
      fprintf(stderr, "mem_unmap: given page is not mapped: %p (in %p:%p)\n",
              p + i, p, p + sz);
      abort();
    }      

    pagemap_modify(p + i, 0);
    
    --page_count;
  }

  if (munmap(p, sz) < 0) {
    fprintf(stderr, "munmap failed: %s (%d)\n",
            strerror(errno), errno);
    abort();
  }
}
