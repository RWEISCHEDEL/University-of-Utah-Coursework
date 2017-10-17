Part 1
------

The TA has started a tick server that receives UDP packets and ignores
the packet content, but responds with the current time in seconds
(since the start of 1970) at a 3-second boundary.

Your first job is to write a client that sends the server a packet and
gets back a time as a little-endian `long` value.  Print the received
value; during the week of November 14, that value will be bigger than
1479100000 and less than 14798000000.

Your client should loop, so that it issues a new request after
receiving each 3-second tick from the sever.

The files "udp_send.c" and "udp_recvfrom.c" can help you remember how
to use library and system calls for UDP (but those files also have
some pieces that you don't need, and you shouldn't include those
pieces in your solution).


Part 2
------

Write your own tick server. At first, though, make it work only for a
single client.

The starting file "server.c" provides the functionality of waiting for
3-second boundaries by using nanosleep().

You should be able to run your client from part 1 with part 2. What
happens if you point two copies of the client at your server? What
happens if you start clients before starting the server?


Part 3
------

Make your tick server work with any number of clients.

Hint: You could have a process for each client.


