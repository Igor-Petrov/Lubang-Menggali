Lubang Menggali game Java implementation

Author: Igor Petrov, igorpetrov342@gmail.com

GAME RULES:
Board Setup
Each of the two players has his six pits in front of him. To the right of the six pits, each player has a larger pit,
his Lubang Menggali. In each of the six round pits are put six stones when the game starts.

Lubang Menggali Rules
Game Play
The player who begins with the first move picks up all the stones in anyone of his own six pits, and sows the stones
on to the right, one in each of the following pits, including his own Lubang Menggali. No stones are put in the 
opponents' Lubang Menggali. If the player's last stone lands in his own Lubang Menggali, he gets another turn.
This can be repeated several times before it's the other player's turn.

Capturing Stones
During the game the pits are emptied on both sides. Always when the last stone lands in an own empty pit, the player
captures his own stone and all stones in the opposite pit (the other players' pit) and puts them in his own
Lubang Menggali.


The Game Ends
The game is over as soon as one of the sides run out of stones. The player who still has stones in his pits keeps them
and puts them in his/hers Lubang Menggali. Winner of the game is the player who has the most stones in his 
Lubang Menggali.


Application is packaged with Maven
To package:
$ mvn package
Than deploy target WAR to the WEB server

Software used during development:
* Java 1.8.0_40
* Maven 3.2.1
* Apache Tomcat 7.0.54

To run the application your browser must support Web Socket technology

Tested with browsers:
* Google Chrome 36
* Firefox 31
* Internet Explorer 11
