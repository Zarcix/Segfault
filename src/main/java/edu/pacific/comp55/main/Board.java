package edu.pacific.comp55.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class Board {
	private Levels currentLevel; 
	private ArrayList<ArrayList<Integer>> players; // Store all players
	private HashMap<Integer, Levels> passedLevels;
	private static int moveCount = 1;
	private static int deathCount = 0;
	
	public Board() {
		players = new ArrayList<ArrayList<Integer>>();
		passedLevels = new HashMap<Integer, Levels>(); // Use index of level to get if level has been passed
		
		currentLevel = new Levels();
		
		generateBoard();
	}
	
	public HashMap<Integer, Levels> getPassedLevels() {
		return passedLevels;
	}
	
	public Levels getLevel() {
		return currentLevel;
	}
	
	public int getMoveNum() {
		return moveCount;
	}
	
	public int getDeathNum() {
		return deathCount;
	}
	
	public static void setDeathNum(int num) {
		deathCount = num;
	}
	
	public static void setMoveNum(int num) {
		moveCount = num;
	}
	
	public void setLevel(int level) {
		/* Boundary Checks */
		
		
		level = (level < 1) ? 1 : level;
		
		currentLevel.setLevel(level);
	}
	
	
	
	public void generateBoard() {
		currentLevel.genBoard();
		findPlayers();
	}

	private void findPlayers() {
		/* Clear arraylist before refill */
		players.clear();
		
		// Loop over board and reset player locations
		for (int i = 0; i < Levels.LEVEL_HEIGHT; i++) {
			for (int j = 0; j < Levels.LEVEL_WIDTH; j++) {
				if (currentLevel.getBoard()[i][j] != null) {
					if (currentLevel.getBoard()[i][j].getType() == "PlayerTile") {
						ArrayList<Integer> location = new ArrayList<Integer>();
						
						location.add(i);
						location.add(j);
						
						players.add(location);
					}
				}
				
			}
		}
	}
	
	private int[] teleportPlayer(int newPlayerX, int newPlayerY, int up, int down) {
		int potentialX = 0;
		int potentialY = 0;
		int[] returner = new int[2];
		
		for (int i = 0; i < Levels.LEVEL_HEIGHT; i++) {
			for (int j = 0; j < Levels.LEVEL_WIDTH; j++) {
				
				// Skip over the same Levels tile
				if (i == newPlayerX && j == newPlayerY) {
					continue;
				}
				
				// Skip over null tiles
				if (currentLevel.getBoard()[i][j] == null) {
					continue;
				}
				
				if (currentLevel.getBoard()[i][j] == currentLevel.getBoard()[newPlayerX][newPlayerY]) {
					potentialX = i - up;
					potentialY = j + down;
				}
			}
		}
		
		returner[0] = potentialX;
		returner[1] = potentialY;
		
		return returner;
	}
	


	public String[] movePlayer(int vertical, int horizontal) {
		moveCount++;
		
		int playerCounter = 0;
		boolean hitWall = false;
		
		String[] returner = new String[2];
		returner[0] = "click.wav";
		returner[1] = null;
		
		while (!players.isEmpty()) {
			ArrayList<Integer> player = players.get(0);
			
			int oldX = player.get(0);
			int oldY = player.get(1);
			
			int newX = oldX - vertical;
			int newY = oldY + horizontal;
			
			if (vertical == 0 && horizontal == 0) {
				players.clear();
				break;
			}
			
			if (newX < 0 || newX > Levels.LEVEL_HEIGHT - 1 || newY < 0 || newY > Levels.LEVEL_WIDTH - 1) {
				players.remove(0);
				hitWall = true;
				continue;
			}
			
			if (currentLevel.getBoard()[newX][newY] == null) {
				players.remove(0);
				hitWall = true;
				continue;
			}
			
			if (currentLevel.getBoard()[newX][newY].getType() == "PlayerTile" && (vertical < 0 || horizontal > 0)) {
				ArrayList<Integer> tempPlayer = player;
				if (!hitWall) {
					players.add(tempPlayer);
				}
				players.remove(0);
				continue;
			}
			
			if (currentLevel.getBoard()[newX][newY].getType().equals("TeleportTile")) {
				int[] coords = teleportPlayer(newX, newY, vertical, horizontal);
				
				if (currentLevel.getBoard()[coords[0]][coords[1]] == null) {
					players.remove(0);
					continue;
				}
				
				newX = coords[0];
				newY = coords[1];
				returner[0] = "TpSound.wav";
			}
			
			if (currentLevel.getBoard()[newX][newY].getType().equals("KillTile")) {
				deathCount++;
				generateBoard();
				returner[0] = "nothing.wav"; // Death
				returner[1] = "false";
				break;
			}
			
			if (currentLevel.getBoard()[newX][newY].getType().equals("WinTile")) {
				hitWall = true;
				playerCounter++;
				players.remove(0);
				continue;
			}
			
			Tiles oldTile = currentLevel.getBoard()[newX][newY];
			currentLevel.getBoard()[newX][newY] = currentLevel.getBoard()[oldX][oldY];
			currentLevel.getBoard()[oldX][oldY] = oldTile;
			players.remove(0);
			
			
		}
		findPlayers();

		if (playerCounter > 0 && playerCounter == players.size()) {
			int levelNumber = currentLevel.getLevel();
			if (passedLevels.get(levelNumber) == null) {
				passedLevels.put(levelNumber, currentLevel);
			}
			
			
			currentLevel.nextLevel();
			moveCount = 1;
			deathCount = 0;
			
			generateBoard();
			returner[0] = "nothing.wav"; // win sound
			returner[1] = "true";
			
		} else if (playerCounter > 0) {
			returner[0] = "nothing.wav"; // kill sound
			returner[1] = "false"; // fails to win board
			deathCount++;
			generateBoard();
		}
		
		
		return returner;
	}

	public void printBoard() {
		/**
		 * @TESTING
		 */
		for (int i = 0; i < Levels.LEVEL_HEIGHT; i++) {
			for (int j = 0; j < Levels.LEVEL_WIDTH; j++) {
				if (currentLevel.getBoard()[i][j] == null) {
					System.out.print(" x ");
				} else {
					System.out.print(" " + currentLevel.getBoard()[i][j].getChar() + " ");
				}
			}
			System.out.println("");
		}
	}
	
	public static void main(String args[]) {
		/**
		 * @TESTING
		 */
		Board board = new Board();
		Scanner reader = new Scanner(System.in);
		board.printBoard();
		while (true) {
			String response = reader.nextLine();
			if (response.equals("a")) {
				board.movePlayer(0, -1);
			}
			if (response.equals("d")) {
				board.movePlayer(0,  1);
			}
			if (response.equals("w")) {
				board.movePlayer(1,  0);
			}
			if (response.equals("s")) {
				board.movePlayer(-1, 0);
			}
			board.printBoard();
			System.out.println(moveCount);
			System.out.println(deathCount);
		}
	}
}