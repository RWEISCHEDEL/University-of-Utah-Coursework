#include <stdio.h>
#include <stdint.h>
#include "mm.h"

int main(){
    mm_init();
    mm_malloc(8);
    mm_malloc(64);
    mm_malloc(256);
    mm_malloc(16);
    mm_malloc(3);
    mm_malloc(350);
    mm_malloc(3300);
    mm_malloc(16);
    mm_malloc(3000);
    mm_malloc(272);
    mm_malloc(720);
    mm_malloc(5);
    mmPrintPages();

}
