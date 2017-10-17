PA3 - A How To GUIDE

How to Run My Program:

I have provided two .jar files in order to run the program: alicetalk.jar and boblisten.jar

Run the program in the command line just like this :

ALICE

java -jar alicetalk.jar [the command tags]

Example: java -jar alicetalk.jar -a localhost -p 6060 -m helloworld

BOB

java -jar boblisten.jar [the command tags]

Example: java -jar boblisten.jar -p 6060

Note use the -h tag to gain more information about the tags.

NOTE:
I have programmed the key path to automatically detect your filepath, but if it bugs out or thinks your in a different directory, just use -v mode and it will print out the filepath the program thinks its in.

Just change the keypath variable name at the top of the class. 

Included in the Tar
pa3_alice.tar
pa3_bob.tar
PA3Keys - folder containing all necessary keys
PA3ReadMe
The .java files of the program
