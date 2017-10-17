#include <unistd.h>

void mem_init(void);               
void mem_reset(void);

size_t mem_pagesize(void);
void *mem_map(size_t);
void mem_unmap(void *, size_t);

size_t mem_heapsize(void);
