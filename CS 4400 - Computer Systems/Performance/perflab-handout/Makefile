CC = gcc
CFLAGS = -Wall -O2
LIBS = -lm

OBJS = driver.o kernels.o fcyc.o clock.o

all: driver

driver: $(OBJS) config.h defs.h fcyc.h
	$(CC) $(CFLAGS) $(OBJS) $(LIBS) -o driver

###########################################################
# Use these rules to switch back and forth between versions
# of kernels.c. E.g., "make naive" installs the naive solution 
# that is handed out to students; "make solution" installs
# some optimized solutions.
###########################################################

# Naive baseline version handed out to students
naive:
	rm -f kernels.{c,o}; ln -s kernels-naive.c kernels.c

# Example solutions
solution:
	rm -f kernels.{c,o}; ln -s kernels-solution.c kernels.c

clean: 
	-rm -f $(OBJS) driver core *~ *.o
