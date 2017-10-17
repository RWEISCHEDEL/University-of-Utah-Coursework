
TINYCHAT_C = tinychat.c
CFLAGS = -O2 -g -Wall

tinychat: $(TINYCHAT_C) dictionary.c dictionary.h csapp.c csapp.h more_string.c more_string.h
	$(CC) $(CFLAGS) -o tinychat $(TINYCHAT_C) dictionary.c more_string.c csapp.c -pthread

clean:
	rm tinychat
