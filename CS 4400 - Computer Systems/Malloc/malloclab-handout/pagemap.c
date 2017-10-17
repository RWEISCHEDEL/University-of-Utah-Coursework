#include <stdlib.h>
#include <stdio.h>
#include <inttypes.h>
#include "pagemap.h"

/* Keep track of all mapped pages so that we can easily get a list of
   all of them --- but also efficiently add and remove from the list. */

typedef struct mpage {
  void *addr;
  struct mpage *prev, *next;
} mpage;

static mpage *all_mapped_pages;

static mpage ***page_maps1;

#define PAGEMAP64_LEVEL1_SIZE (1 << 16)
#define PAGEMAP64_LEVEL2_SIZE (1 << 16)
#define PAGEMAP64_LEVEL3_SIZE (1 << (32 - LOG_APAGE_SIZE))
#define PAGEMAP64_LEVEL1_BITS(p) (((uintptr_t)(p)) >> 48)
#define PAGEMAP64_LEVEL2_BITS(p) ((((uintptr_t)(p)) >> 32) & ((PAGEMAP64_LEVEL2_SIZE) - 1))
#define PAGEMAP64_LEVEL3_BITS(p) ((((uintptr_t)(p)) >> LOG_APAGE_SIZE) & ((PAGEMAP64_LEVEL3_SIZE) - 1))

void pagemap_modify(void *p, int mapped) {
  uintptr_t pos;
  mpage **page_maps2;
  mpage *page_maps3;
  mpage *page;

  if (!page_maps1) {
    page_maps1 = calloc(PAGEMAP64_LEVEL1_SIZE, sizeof(mpage **));
  }

  pos = PAGEMAP64_LEVEL1_BITS(p);
  page_maps2 = page_maps1[pos];
  if (!page_maps2) {
    page_maps2 = calloc(PAGEMAP64_LEVEL2_SIZE, sizeof(mpage *));
    page_maps1[pos] = page_maps2;
  }
  
  pos = PAGEMAP64_LEVEL2_BITS(p);
  page_maps3 = page_maps2[pos];
  if (!page_maps3) {
    page_maps3 = calloc(PAGEMAP64_LEVEL3_SIZE, sizeof(mpage));
    page_maps2[pos] = page_maps3;
  }

  page = &page_maps3[PAGEMAP64_LEVEL3_BITS(p)];

  if (mapped) {
    if (page->addr) {
      fprintf(stderr, "internal error: page is already mapped\n");
      abort();
    }
    if (page == all_mapped_pages)
      abort();
    page->addr = p;
    page->prev = NULL;
    page->next = all_mapped_pages;
    if (all_mapped_pages)
      all_mapped_pages->prev = page;
    all_mapped_pages = page;
  } else {
    if (!page->addr) {
      fprintf(stderr, "internal error: not currently mapped\n");
      abort();
    }
    page->addr = NULL;
    if (page->prev)
      page->prev->next = page->next;
    else
      all_mapped_pages = page->next;
    if (page->next)
      page->next->prev = page->prev;
  }
}

int pagemap_is_mapped(void *p) {
  mpage **page_maps2;
  mpage *page_maps3;

  if (!page_maps1) return 0;
  page_maps2 = page_maps1[PAGEMAP64_LEVEL1_BITS(p)];
  if (!page_maps2) return 0;
  page_maps3 = page_maps2[PAGEMAP64_LEVEL2_BITS(p)];
  if (!page_maps3) return 0;
  return !!page_maps3[PAGEMAP64_LEVEL3_BITS(p)].addr;
}

void pagemap_for_each(page_callback f) {
  mpage *p, *next;
  p = all_mapped_pages;
  while (p) {
    next = p->next;
    f(p->addr);
    pagemap_modify(p->addr, 0);
    p = next;
  }
  all_mapped_pages = NULL;
}
