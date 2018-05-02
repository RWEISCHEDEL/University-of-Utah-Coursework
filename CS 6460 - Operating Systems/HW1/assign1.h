#ifndef ASSIGN1_H_
#define ASSIGN1_H_


enum format_t {
    OCT = 66, BIN, HEX
};

struct elt {
    char val;
    struct elt *link;
};

unsigned long byte_sort(unsigned long arg);

unsigned long nibble_sort(unsigned long arg);

void convert(enum format_t mode, unsigned long value);

struct elt *name_list(void);

void draw_me(void);

#endif
