LIBHARU = ~/cs3505/A2/libharu-RELEASE_2_3_0
ENDS = -lhpdfs -lz
CC = g++
CFLAGS = -Wall -std=c++11

spiralPDF: Spiral.o HaruPDF.o spiralPDF.cpp
	$(CC) $(CFLAGS) Spiral.o HaruPDF.o spiralPDF.cpp -o spiralPDF -L $(LIBHARU)/src -I $(LIBHARU)/include $(ENDS)

Spiral.o: Spiral.cpp
	$(CC) $(CFLAGS) -c Spiral.cpp

HaruPDF.o: HaruPDF.cpp
	$(CC) $(CFLAGS) -c HaruPDF.cpp -L $(LIBHARU)/src -I $(LIBHARU)/include $(ENDS)

clean:
	rm *.o
	rm spiralPDF

test:
	./spiralPDF "I go to encounter for the millionth time the reality of experience and to forge in the smithy of my soul the uncreated conscience of my race."
