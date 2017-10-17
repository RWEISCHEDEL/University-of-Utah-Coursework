
typedef void (*page_callback)(void *addr);

void pagemap_modify(void *addr, int mapped);
int pagemap_is_mapped(void *addr);
void pagemap_for_each(page_callback f);

/* APAGE_SIZE needs to match the actual page size */
#define LOG_APAGE_SIZE 12
#define APAGE_SIZE (1 << LOG_APAGE_SIZE)
