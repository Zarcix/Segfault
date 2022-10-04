# Customer Statement of Requirement

The motivation for creating this project is to make a game that is fun and challenging to
play. The game is a fun game where a square is moved around a grid by the player to win the
game. This game aims to cure the boredom of the children of the planet Earth.

# Functional Requirements

The people who will directly interact with the system will be the players. This game will
specifically be designed for young children. The main goal of the game will be accomplishing
the levels step by step and reaching to the end of the game. In order to make the game more
interesting for the players, the difficulty of the level will increase each time when the player
moves to the next level by making the game more and more complicated.

To finish the level, players are supposed to move the particular block through the special
path in order to reach the finish state. There will be multiple paths that the user will be able to
choose with all of them supposedly leading to the end tile. The goal for the player is to move to
the end with as little movements as possible.

## UI Design:

Home Screen:
![image](https://user-images.githubusercontent.com/101155569/166123158-a80c057f-6770-4607-928e-f609309b64e8.png)
Pause Menu:
![image](https://user-images.githubusercontent.com/101155569/166123165-dd3bfa1d-b9c2-4764-ab24-1f00ccbaf265.png)
Level Select:
![image](https://user-images.githubusercontent.com/101155569/166123266-c9a617fd-74ba-4dd9-a3f9-62c64ed4567c.png)

# Class Diagrams and Interface Specifications

![image](https://user-images.githubusercontent.com/101155569/166133703-d6a457ad-2377-49bf-bfec-4c060a4f2f20.png)

### Contracts
- Preconditions
  - Before calling setLevel in Levels, the function checkValidLevel must be passed first. If not, the game will try to load a non-existent level, leading to a fail
  - Before calling FileReader's readFile, the readDir function must be finished first in order to read all the levels in the folder. Without this reading, the program will not know when to stop trying to read the levels.
- Postconditions
  - After calling movePlayer, the movement area the player wants to move in must have a valid object already created at that location. If the location is null, the player will not move. 

# Interaction Diagrams

### Start Playing Interaction:

![image](https://user-images.githubusercontent.com/101155569/166123411-736b1936-46c7-41cc-ba6f-3c4e0fad2312.png)

In this interaction diagram, we displayed the initial loading after pressing the play button. In the diagram above, we showed that the program will create instances of the tiles that will be reused per level load to reduce the amount of memory we use overall. 

### Single Movement Interaction:

![image](https://user-images.githubusercontent.com/101155569/166123728-5ad4b6c9-e1c8-49c9-8a8b-52d88b02f9e8.png)

In this interaction diagram, we simply replaced the old tiles in the current level with the new tile that the player currently wants to take over. If the player hits an invalid board area like a nulltile, then the player will not move.

### Movement into a Win Tile:

![image](https://user-images.githubusercontent.com/101155569/166123794-79c86a91-aa93-4ffe-ab4c-41ebef9311f6.png)

In this interaction diagram, the player has been detected as winning the level. In that case, Board then calls Level's nextLevel for loading. Board then takes that result and places it into the currentLevel and regenerates the board, essentially rewriting the board with the new level.

### Loading a level from Level Select:

![image](https://user-images.githubusercontent.com/101155569/166124007-5b5d3222-fc83-4b7c-b0fd-c8e05f70382b.png)

In this interaction diagram, the player clicks on the level select and chooses a level. The level is then loaded through board and regenerates the board to apply the level change. Then the gamepane is updated and shown. 

# References with Annotations

- ACM Library: [ACM](https://cs.stanford.edu/people/eroberts/jtf/javadoc/student/)
- Color Picker: [Canva](https://www.canva.com/colors/color-wheel/)
- File Reading: [GeeksForGeeks](https://www.geeksforgeeks.org/different-ways-reading-text-file-java/)
# Segfault
