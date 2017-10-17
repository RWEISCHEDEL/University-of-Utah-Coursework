lab2: lab2.o h.o print_number.o
	gcc -o lab2 lab2.o h.o print_number.o

lab2.o: lab2.c
	gcc -g -c lab2.c

print_number.o: print_number.c
	gcc -g -c print_number.c

h.o: h.c
	gcc -c -O2 h.c

clean:
	-rm lab2
	-rm *.o
