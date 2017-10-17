PS9 READ ME
Partners 

Robert Weischedel : 00887905

Jackson Murphy : 00647107

Repository 

https://github.com/uofu-cs3500-fall15/00647107

Notes To TA/Graders:
Overall we are very pleased with how the assignment turned out. An issue still remains from PS8 where sometimes an orginal player cube is eaten and the uid isn't transfered to a teammate.
But, all the PS9 mechanics appear to be working well. And it should be noted that we completed the whole assignment as well, up and beyond the required 70% done that was required. We 
fufilled all excelent solution requirements except one, where  all the eaten players are linked to their player info pages.

What Works:
1. Successfully track game stats for a players team and update the database tables upon player death.
2. Successfully send database information to a web browser in a dynamic html page.
3. Included links in html to individual player information screens and to the main scores page.

Database Description:
For our database we decided to implement a 2 table structure. Each table is linked by the unique session number that is generated for each player. The first table contains most of the data 
including the length of time the player was alive, the maximum mass, total number of cubes eaten, the number of food cubes eaten, and the number of player cubes eaten. The second table 
contains the list of all players eaten by each player during that session. We decided that it would be best to keep the list of eaten players in a single string separated by commas in the 
database instead of giving each player name its own column, because we felt it helped keep the database cleaner by not having as many rows and columns. Overall the reasons why we decided to 
break up the design into two tables was to make it easier to know where that data was being stored. By having fewer tables it easier to keep track of where to look for specific information.  

SQL Code
For “SCORES”
"select Name, Maximum_Mass, Rank, Number_Of_Cubes_Eaten, Lifespan from Player_Games"

For “PLAYER”
“select Maximum_Mass, Rank, Number_Of_Cubes_Eaten, Number_Of_Player_Cubes_Eaten, Lifespan, Time_Of_Death from Player_Games where Name = \"" + player + "\""

For “EATEN CUBES”
“select Maximum_Mass, Rank, Number_Of_Cubes_Eaten, Number_Of_Player_Cubes_Eaten, Lifespan, Time_Of_Death from Player_Games"

Changes to AgCubio;
In order to accommodate all the new changes needed for the database, we create a new class in the model called PlayerSessionStats. This class contains all the required information that the 
database requires for each cube. At first we thought about just adding all the extra variables to the cube class, but we felt that this was too messy and it was much cleaner to just create 
another class. And by implementing PS9 this way made our jobs a lot easier and made the implementation a whole lot cleaner. We also added several helper methods that enabled us to retrieve 
and process the database information. And finally we added a UpdateDbWithDeadPlayerStats method which sends all information about a player who was just eaten to the database. This method is 
called from Update() in order to ensure that the players information will be sent as quickly as possible. 

***************************************************************************************************************************************************************
README
README PS8 – AgCubio

Partners 

Robert Weischedel : 00887905

Jackson Murphy : 00647107

Repository 

https://github.com/uofu-cs3500-fall15/00647107

Notes To TA/Grader

THINGS THAT ARE WORKING WELL:

1. Player movement: We can move our player cube(s) smoothly and without any problem. The player cube is
restricted from leaving the world.

2. Splitting: We can split player cubes and then re-merge them after a timer goes off. 
We use a dictionary of timers to keep track
of when each team's cubes can re-merge. Our splitting works in a similar manner to that of Agar,io's:
any player cube can split as long as it has the minimum mass and the team has not reached the max number
of player cubes. When we run our server with the example Client, the values we chose make it so that the
cubes split with a nice momentum.

3.  Keeping player cubes separated until after the merge timer goes off: The strategy we use for this is
that if a move would cause a player to bump into a teammate, we try to move the player only in the
x-direction towards the mouse. If that also bumps into a teammate, we try to move only in the y-direction
towards the mouse. If that too bumps into a teammate, we don't move the player.
This strategy works well most of the time, but we do occasionally get a few cubes that freeze up after 
we've split several times.

4: Virus explosions: When the player is larger than a virus, it breaks into 4 smaller cubes and these
cubes shoot off in all 4 directions. We use a timer to prevent the cubes from re-merging until a 
set time has passed.

5: Transferring the main player's uid to a teammate when it dies: We were successful in implementing this.


THINGS THAT ARE NOT WORKING WELL: 

1. If you test our server using our Client, you'll see that the mouse tracking is slightly off when moving the cube.
This is an issue with our client, not our server.

2. When there's only 1 client connected, the game play experience is very smooth. But when we try to run multiple clients
at the same time on our computer (the same computer that's running the server), the game lags a bit. 

3. We weren't able to disconnect from clients as gracefully as we would have liked. When a client disconnects, the 
server does not crash, so that's good. But we weren't sure about best practices for closing sockets.

4. We noticed early on that since all of our game logic is based off of “real cube sizes” and not the 
exploded extra scale factor found in the Client this can cause our absorb to look like it is 
not functioning as it should. But to test this run our Client View and turn the scaling down to 1 
and you will see that it works perfectly. A issue of your clients Math.pow(Mass, 0.65) vs our 
Math.sqrt(Mass) calculations.
 
5. We had some hard coded values in our View that caused some issues and as well as how we 
handled some of the exceptions in the View instead of in the Network caused some issues as 
well.

6. In our Client View when the player cube stops moving, the whole window shakes a bit. But in 
the sample client it doesn’t do this. And as the cube gets bigger, it stops doing this as well.
Note: Our client does work and does well, but its just not as refined as we wanted/hoped it 
would be.

7: We added an update timer to control how often we send an updated version of the world to clients.
However, we get can't go beyond ~ 20 updates per second without causing a memory leak.

NOTE ABOUT OUR CLIENT:
1. If you add more than three clients the first time it will crash all the clients, but the server is still running. If you readd the clients
and if you go over three after the initial crash all the clients perform as expected.

2. Mouse is a little off in our client.

3. The read in value from the XML make our client appear slow, just bump up the cube move and split speeds when you want to use ours and it runs 
great.


NOTE ABOUT UNIT TESTING:
We tried to thoroughly unit test our model (World and Cube classes). We were able to reach over 80%
code coverage on our World class, but getting beyond that proved difficult. To make sure this unreached
code was working ok, we did visual tests by running the game and simulating the various conditions. 



Initial Design Thoughts
Since Jackson and I were partners for PS7 we had a good an solid understanding of the code going into 
PS8. Since we built the backbone together we felt confident going forward that we wouldn’t encounter 
too many issues and that we would quickly be able to finish this assignment. 

Design Decisions :

World Class – We made several and many changes to the world class. We added several methods and 
helper methods to handle player movement, splitting, creatation of all types of cubes (player, food, 
virus), generating unique uids and team_ids, and the absorbing of other cubes.

Viruses : Our design implementation followed closely to what Agar.io does. That player cubes that are 
smaller than the virus cannot eat the virus cube, but larger ones can. The cube that eats the virus will 
gain the viruses mass, but it will explode in the process. NOTE : Food still has a possibility of spawning 
under the virus, we felt like this wasn’t that much of an issue since the player penalty for eating the cube 
was exploding was going to take care of any additional gains from a few more food cube pieces.

Spliting : 
Absorbing : Our design uses the Rectangles class to create two squares one of the player cube and the 
other of the cube that the player could be absorbing. It then sees if the two squares intersect with the 
correct absorb delta value. We had to implement another HashSet in this method, that is only contained 
in this method to keep track of all the cubes that have changed for each step of the move.

Moving: Implemented functionality to be able to move the player cube, and that the cube won’t be able 
to escape the game world. NOTE : For some reason in our Client View the mouse is slightly off from what 
it should be, and the camera shakes a bit when you stop moving. 

Food: Food is implemented to be generated randomly in the world, from a selection of 16 different 
colors. We choose 16 colors because we felt it was a good enough spread to make the world look 
colorful and unique. The number of food generated in the world is told to the world by the XML. And as 
well the number of food to generate at each update is also easily updatable as well.



EXTRA FEATURES: 

1. When adding a new player to the world, the GetPlayerSpawnLoc generates a random Point that 
is ensured that it will not run into another player upon spawning. 

2. The constructor is set up to take in a XML file to read in all the various constants that define the 
world, making it easier to change and update them and change the game in an instant.

3. All the constants, we added a ton of constant values in order to make the process of fine tuning 
and tweaking the game to a particular playing style a breeze. We added all the constants found 
in the PS8 instructions and also a few more of our own like, constants for mass of viruses, 
number of max viruses in the world, the virus green color, attrition rates for different cube sizes, 
array of the different colors for players and food, and constants to handle all the aspects of 
splitting.

4. There are three different attrition values, two for smaller and larger cubes that are all 
changeable and updatable from the XML.

5. All types of cubes are generated to not go outside the world.
Cube Class – Not much was added to the Cube class, except the has momentum and has momentum 
methods that were suggested in the PS8 implementation.  

Network_Controller Class – We added the required methods in the Network class and 

Server Class – Implemented methods exactly as the specifications dictate. We also added a few member 
variables to keep track of how many 

Client/View – We had some up and downs with our Client. Some of our design decisions from PS7, like 
how we handled exceptions, mouse tracking, scaling, and some hard coded values made testing our new 
methods difficult at times. Not saying that it doesn’t work or anything, it does and it does work well, but 
its not as smooth as we like.









*****************************************************************************************************************************************************************************************************************
Assignment 7 : AgCubio
Partners : Jackson Murphy and Robert Weischedel

******TA README INFO******
We designed our game to freeze when the serve unexpectly ends while the game is being played. No dialog box is displayed, just simply close the window and the start menu window will open and allow you to reconnect.
In addition we were confused about which scaling method to use, either a constant scaling or by calcualting by player mass. We did it both ways and they can be activated from Form2 if the boolean var 
useContantScaling is set to true/false. We think the constant value looks better, but the assignment appearently required it calcuated by mass. 

Initial Design Thoughts : 
Before the beginning of this assignment we were excited to get started on making and using networks and servers. We believed that the assignment would be difficult and time intensive so we decided to begin working 
early and to meet up almost every day for about 4 or so hours in order to knockout this assignment on time. 

Design Plan : 

Model :

World Class :
Contains all world info including all cubes and their information.

Cube Class :
Contains all info about a cube, size, color, position, etc.

View :
The GUI for the game.

*UPDATE 11/6/15
Form1 – Opening screen to allow players to enter their player and server names.
Form 2 – The gameplay window

*UPDATE 11/11/15
Add and update call backs via the brought in preserved state obj. Use these saved call backs to bring us back to the Form 2 from the network class methods.

Network Controller :
All the network code, methods and Preserved State class

Preserved State Class :
Contains socket, and other info?

*UPDATE 11/11/15
Use callbacks to call to the other methods in network, only store the callbacks from the Form 2 in the preserved state obj. 

Design Updates : 

*UPDATE 11/6/15
Began with making the Model class which was pretty easy and then being unsure of how to go about the network portion we began on the GUI elements. Decided we should have two different Forms, one for entering player 
name and server and the second being the game world window. Got the first Form done, and then got stuck on the second form. 

*UPDATE 11/7/15
Decided that in order to fully understand how the GUI was going to pull information from the network we should start on it first. Didn’t make really any progress today, spinning tires all day… And scouring examples 
online and from the course to help us. 

*UPDATE 11/9/15
Finally found a good MSDN example online that helped us make tremendous progress today, not 100% positive that this is correct or the right direction we should go in but we are going with it. Got a lot of the 
network code done today.

*UPDATE 11/10/15
Learned in class that the example we found was a good one and one the professor suggested. Spent most of the day debugging the network code and trying to get it to print to the output window. Finally began 
understanding all the different callbacks and their specific tasks they are trying to do.

*UPDATE 11/11/15
Continued debugging the network class and hooked up all the callbacks correctly in the network class and Form2 of the GUI. By the end of the day the network was printing all the de-serialized JSON cube information 
to the output window. 

*UPDATE 11/12/15
Tried to get cubes to print to screen, finally got them to appear but it is very slow. Implemented mouse controls. Finally realized the Console.WriteLine() statements were slowing things down dramatically, once 
removed the GUI sped up immediately. 

*UPDATE 11/13/15
Began working on scaling/resizing of the GUI window. Made some progress. 

*UPDATE 11/16/15
Continued working on scaling/resizing of the GUI window, worked on handling server errors.

*UPDATE 11/17/15
Finished adding comments and test cases. 

Take Away/Lessons Learned
Drawing the assignment out before starting on a new section of the code really helped! It allowed us to eventually figure out how all the network and GUI callbacks work. 
