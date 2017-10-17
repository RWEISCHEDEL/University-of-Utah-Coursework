
CC = gcc -O2 -Wall

WHOOSH_C = whoosh.c

OBJS = whoosh.o parse.o fail.o csapp.o
HEADERS = ast.h fail.h csapp.h

whoosh: $(OBJS)
	$(CC) -o whoosh $(OBJS)

whoosh.o: $(WHOOSH_C) $(HEADERS)
	$(CC) -c -o whoosh.o $(WHOOSH_C)

parse.o: parse.c $(HEADERS)
	$(CC) -c parse.c

fail.o: fail.c $(HEADERS)
	$(CC) -c fail.c

csapp.o: csapp.c $(HEADERS)
	$(CC) -c csapp.c

clean:
	rm -f *.o whoosh
