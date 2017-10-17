CC = gcc -g -O2

run: read.o say.o
	$(CC) -o run read.o say.o

run.o: read.c
	$(CC) -c read.c

say.o: say.c
	$(CC) -c say.c

clean:
	rm *.o
	rm run
