This lab is about shells, file descriptors, signals, and process
groups.

Ctl-C and Ctl-D
---------------

Compile and run the program "count.c".

You can type input, and it just gets conumed by the program...

How do you make the program stop?

 Option 1: Ctl-C --- interrupts the program, so the count doesn't
           print.

 Option 2: Ctl-D (on its own line) --- triggers an EOF on the input
           stream, so Read() returns 0 and the count prints.


>> Exercise 1: Make the accumulated count print even even if you type
   Ctl-C (but still exit in that case).


Fork and Ctl-C and Ctl-D
------------------------

Compile and run the program "count2.c".

>> Exercise 2: Make "count2.c" similarly print a count on Ctl-C. Make
   sure that only the child process tries to print a count.



Why does the child process even get a Ctl-C from the shell?


Handling and Sending Signals
----------------------------

The program "count_child.c" is the same as the original "count.c".
Compile it as `count_child`.

The program "count3.c" is similar to "count2.c", but it runs
`count_child`.

By changing only "count3.c", reverse the behavior of Ctl-C and Ctl-D
for this program. That is, Ctl-D should terminate the counting program
immediately without printing a count, while Ctl-C should cause
`count_child` to print a count and then stop.

