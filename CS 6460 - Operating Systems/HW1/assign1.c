#define _GNU_SOURCE
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <sys/syscall.h> 
#include <unistd.h>
#include <fcntl.h>

/*********************************************************************
 *
 * These C functions use patterns and functionality often found in
 * operating system code. Your job is to implement them. Of course you
 * should write test cases. However, do not hand in your test cases
 * and (especially) do not hand in a main() function since it will
 * interfere with our tester.
 *
 * Additional requirements on all functions you write:
 *
 * - you may not refer to any global variables
 *
 * - you may not call any functions except those specifically
 *   permitted in the specification
 *
 * - your code must compile successfully on CADE lab Linux
 *   machines when using:
 *
 * /usr/bin/gcc -O2 -fmessage-length=0 -pedantic-errors -std=c99 -Werror -Wall -Wextra -Wwrite-strings -Winit-self -Wcast-align -Wcast-qual -Wpointer-arith -Wstrict-aliasing -Wformat=2 -Wmissing-include-dirs -Wno-unused-parameter -Wshadow -Wuninitialized -Wold-style-definition -c assign1.c 
 *
 * NOTE 1: Some of the specifications below are specific to 64-bit
 * machines, such as those found in the CADE lab.  If you choose to
 * develop on 32-bit machines, some quantities (the size of an
 * unsigned long and the size of a pointer) will change. Since we will
 * be grading on 64-bit machines, you must make sure your code works
 * there.
 *
 * NOTE 2: You should not need to include any additional header files,
 * but you may do so if you feel that it helps.
 *
 * HANDIN: submit your finished file, still called assign1.c, in Canvas
 *
 *
 *********************************************************************/

/*********************************************************************
 *
 * byte_sort()
 *
 * specification: byte_sort() treats its argument as a sequence of
 * 8 bytes, and returns a new unsigned long integer containing the
 * same bytes, sorted numerically, with the smaller-valued bytes in
 * the lower-order byte positions of the return value
 * 
 * EXAMPLE: byte_sort (0x0403deadbeef0201) returns 0xefdebead04030201
 *
 *********************************************************************/

unsigned long byte_sort (unsigned long arg)
{
  unsigned long bytes [8];
  unsigned long temp = 0x00000000000000ff;

  int b1, b2;

  for(b1 = 0; b1 < 8; b1++){
    bytes[b1] = arg & temp;
    arg = arg >> 8;
  }

  for(b1 = 0; b1 < 8; b1++){
    int currMinIndex = b1;

    for(b2 = b1; b2 < 8; b2++){

      if(bytes[b2] <= bytes[currMinIndex]){
        currMinIndex = b2;
      }

    }

    int holdByte = bytes[b1];
    bytes[b1] = bytes[currMinIndex];
    bytes[currMinIndex] = holdByte;

  }

  unsigned long answer = 0;

  for(b1 = 0; b1 < 8; b1++){
    answer = answer | (bytes[b1] << 8*b1);
  }

  return answer;

}

/*********************************************************************
 *
 * nibble_sort()
 *
 * specification: nibble_sort() treats its argument as a sequence of 16 4-bit
 * numbers, and returns a new unsigned long integer containing the same nibbles,
 * sorted numerically, with smaller-valued nibbles towards the "small end" of
 * the unsigned long value that you return
 *
 * the fact that nibbles and hex digits correspond should make it easy to
 * verify that your code is working correctly
 * 
 * EXAMPLE: nibble_sort (0x0403deadbeef0201) returns 0xfeeeddba43210000
 *
 *********************************************************************/

unsigned long nibble_sort (unsigned long arg)
{
  unsigned long bytes[16];
  unsigned long temp = 0x000000000000000f;

  int b1, b2;

  for(b1 = 0; b1 < 16; b1++){
    bytes[b1] = arg & temp;
    arg = arg >> 4;
  }

  for(b1 = 0; b1 < 16; b1++){
    int currMinIndex = b1;

    for(b2 = b1; b2 < 16; b2++){

      if(bytes[b2] <= bytes[currMinIndex]){
        currMinIndex = b2;
      }

    }

    int holdByte = bytes[b1];
    bytes[b1] = bytes[currMinIndex];
    bytes[currMinIndex] = holdByte;

  }

  unsigned long answer = 0;

  for(b1 = 0; b1 < 16; b1++){
    answer = answer | (bytes[b1] << 4*b1);
  }

  return answer;
}

/*********************************************************************
 *
 * name_list()
 *
 * specification: allocate and return a pointer to a linked list of
 * struct elts
 *
 * - the first element in the list should contain in its "val" field the first
 *   letter of your first name; the second element the second letter, etc.;
 *
 * - the last element of the linked list should contain in its "val" field
 *   the last letter of your first name and its "link" field should be a null
 *   pointer
 *
 * - each element must be dynamically allocated using a malloc() call
 *
 * - if any call to malloc() fails, your function must return NULL and must also
 *   free any heap memory that has been allocated so far; that is, it must not
 *   leak memory when allocation fails
 *  
 *********************************************************************/

struct elt {
  char val;
  struct elt *link;
};

static void delete_list(struct elt *head){

  struct elt *next;

  while(head != NULL){
    next = head->link;
    free(head);
    head = next;
  }

}

struct elt *name_list (void)
{

  char firstName[] = "Robert";

  struct elt *head = NULL;
  struct elt *curr = NULL;
  struct elt *prev = NULL;

  head = (struct elt*) malloc(sizeof(struct elt));

  if(head == NULL){
    return NULL;
  }

  head->val = firstName[0];
  prev = head;

  int l;

  for(l = 1; l < 6; l++){

    curr = (struct elt*) malloc(sizeof(struct elt));

    if(curr == NULL){

      delete_list(head);
      return NULL;

    }

    curr->val = firstName[l];

    if(prev != NULL){
      prev->link = curr;
    }

    prev = curr;

  }

  return head;

}

/*********************************************************************
 *
 * convert()
 *
 * specification: depending on the value of "mode", print "value" as
 * octal, binary, or hexidecimal to stdout, followed by a newline
 * character
 *
 * extra requirement 1: output must be via putc()
 *
 * extra requirement 2: print nothing if "mode" is not one of OCT,
 * BIN, or HEX
 *
 * extra requirement 3: all leading/trailing zeros should be printed
 *
 * EXAMPLE: convert (HEX, 0xdeadbeef) should print
 * "00000000deadbeef\n" (including the newline character but not
 * including the quotes)
 *
 *********************************************************************/

enum format_t {
  OCT = 66, BIN, HEX
};

void convert (enum format_t mode, unsigned long value)
{
  if(mode == OCT){

    unsigned long ans;
    int i;

    for(i = 21; i >= 0; i--){

      ans = 0x0000000000000007 & (value >> i*3);

      switch(ans){

      case 0:
        putc('0', stdout);
        break;

      case 1:
        putc('1', stdout);
        break;

      case 2:
        putc('2', stdout);
        break;

      case 3:
        putc('3', stdout);
        break;

      case 4:
        putc('4', stdout);
        break;

      case 5:
        putc('5', stdout);
        break;

      case 6:
        putc('6', stdout);
        break;

      case 7:
        putc('7', stdout);
        break;

      }

    }

    putc('\n', stdout);

  }
  else if(mode == BIN){

    unsigned long ans;

    int i;

    for(i = 63; i >= 0; i--){

      ans = 0x0000000000000001 & (value >> i);

      switch(ans){

      case 0:
        putc('0', stdout);
        break;

      case 1:
        putc('1', stdout);
        break;

      }

    }

    putc('\n', stdout);

  }
  else if(mode == HEX){

    unsigned long ans;

    int i;

    for(i = 15; i >= 0; i--){

      ans = 0x000000000000000F & (value >> 4*i);

      switch(ans){

      case 0:
        putc('0', stdout);
        break;

      case 1:
        putc('1', stdout);
        break;

      case 2:
        putc('2', stdout);
        break;

      case 3:
        putc('3', stdout);
        break;

      case 4:
        putc('4', stdout);
        break;

      case 5:
        putc('5', stdout);
        break;

      case 6:
        putc('6', stdout);
        break;

      case 7:
        putc('7', stdout);
        break;

      case 8:
        putc('8', stdout);
        break;

      case 9:
        putc('9', stdout);
        break;

      case 10:
        putc('a', stdout);
        break;

      case 11:
        putc('b', stdout);
        break;

      case 12:
        putc('c', stdout);
        break;

      case 13:
        putc('d', stdout);
        break;

      case 14:
        putc('e', stdout);
        break;

      case 15:
        putc('f', stdout);
        break;

      }

    }

    putc('\n', stdout);

  }
}

/*********************************************************************
 *
 * draw_me()
 *
 * this function creates a file called me.txt which contains an ASCII-art
 * picture of you (it does not need to be very big). the file must (pointlessly,
 * since it does not contain executable content) be marked as executable.
 * 
 * extra requirement 1: you may only call the function syscall() (type "man
 * syscall" to see what this does)
 *
 * extra requirement 2: you must ensure that every system call succeeds; if any
 * fails, you must clean up the system state (closing any open files, deleting
 * any files created in the file system, etc.) such that no trash is left
 * sitting around
 *
 * you might be wondering how to learn what system calls to use and what
 * arguments they expect. one way is to look at a web page like this one:
 * http://blog.rchapman.org/post/36801038863/linux-system-call-table-for-x86-64
 * another thing you can do is write some C code that uses standard I/O
 * functions to draw the picture and mark it as executable, compile the program
 * statically (e.g. "gcc foo.c -O -static -o foo"), and then disassemble it
 * ("objdump -d foo") and look at how the system calls are invoked, then do
 * the same thing manually using syscall()
 *
 *********************************************************************/

void draw_me (void)
{

  int fileInfo = syscall(SYS_open, "./me.txt", O_WRONLY|O_CREAT, 0777);

  if(fileInfo < 0){
    return;
  }

  char image[] = "_\\|/^ \n (_oo \n  |     Robert \n /|\\ \n  | \n  LL";

  int writeInfo = syscall(SYS_write, fileInfo, image, sizeof(image)-1);

  if(writeInfo < 0){

    syscall(SYS_close, fileInfo);
    syscall(SYS_unlink, "./me.txt");
    return;
  }

  int closeInfo = syscall(SYS_close, fileInfo);

  if(closeInfo < 0){
    return;
  }


}
