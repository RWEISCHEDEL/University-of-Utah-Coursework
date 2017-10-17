/* The `fail` function is like printf() for reporting errors and
   exiting. A newline is added to the output. */

#include <stdlib.h>
#include <stdio.h>
#include <stdarg.h>
#include "fail.h"

void fail(const char *format, ...) {
  va_list ap;
  va_start(ap, format);
  vfprintf(stderr, format, ap);
  fprintf(stderr, "\n");
  exit(1);
}
