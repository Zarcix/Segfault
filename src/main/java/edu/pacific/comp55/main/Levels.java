package edu.pacific.comp55.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import sun.misc.Unsafe;

public class Levels {
    public static final int LEVEL_WIDTH = 16;
    public static final int LEVEL_HEIGHT = 9;
	
	private Tiles[][] board;
	
	private int currentLevel;
	
    private OpenTile opentile = new OpenTile();
    private PlayerTile playertile = new PlayerTile();
    private KillTile killtile = new KillTile();
    private WinTile wintile = new WinTile();
    
    public static ArrayList<Integer> levels = new ArrayList<Integer>();
    
    private HashMap<String, Tiles> availableTiles;
	
    public Levels() {
    	board = new Tiles[LEVEL_HEIGHT][LEVEL_WIDTH];
    	availableTiles = new HashMap<String, Tiles>();
    	currentLevel = 1;
    	
    	loadAllTiles();
    	
    	genBoard();
    }
    
    private void loadAllTiles() {
    	availableTiles.put(opentile.getChar(), opentile);
    	availableTiles.put(playertile.getChar(), playertile);
    	availableTiles.put(killtile.getChar(), killtile);
    	availableTiles.put(wintile.getChar(), wintile);
    }
    
    public Tiles[][] getBoard() {
    	return board;
    }
    
    public void genBoard() {
    	levels.clear();
    	FileReader reader = new FileReader();
    	String[][] levelData = reader.readLevel(currentLevel);
    	for (String level : reader.getLevels()) {
    		levels.add(Integer.parseInt(level));
    	}
    	
    	Collections.sort(levels);
    	
    	for (int height = 0; height < LEVEL_HEIGHT; height++) {
    		for (int width = 0; width < LEVEL_WIDTH; width++) {
    			String data = levelData[height][width];
    			if (data.contains("t") && availableTiles.get(data) == null) {
    				Tiles teleport = new TeleportTile();
    				availableTiles.put(data, teleport);
    			}
    			Tiles tile = availableTiles.get(data);
    			board[height][width] = tile;
    		}
    	}
    	
    }
    
    public void nextLevel() { // Levels
    	currentLevel++;
    	if (checkValidLevel(currentLevel)) {
			System.out.println("GET SEGFAULTED!!!!!");
			forceSeg();
		}
    	genBoard();
    	loadAllTiles();
    }
    
    public boolean checkValidLevel(int level) {
		return Levels.levels.size() < level;
	}
    
    public void forceSeg() {
    	try {
			// Get unsafe field
			Field Segfaulter = Unsafe.class.getDeclaredField("theUnsafe");
			Segfaulter.setAccessible(true);
		    Unsafe Segfault = (Unsafe) Segfaulter.get(null);

		    // Allocation number and size
		     long ten = 10;
		     byte size = 1;
		     
		     //Allocate Memory
		     long mem = Segfault.allocateMemory(size);
		     Segfault.putAddress(1, ten);
		     
		     //Read bad memory
		     long readValue = Segfault.getAddress(mem);
		     System.out.println("result: " + readValue);
			
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
    }
    
    public int getLevel() {
    	return currentLevel;
    }
    
    public void setLevel(int level) {
    	currentLevel = level;
    	genBoard();
    	loadAllTiles();
    }
    
    public void printBoard() {
    	for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 16; j++) {
                if (getBoard()[i][j] == null) {
                    System.out.print("x ");
                } else {
                    System.out.print(getBoard()[i][j].getChar() + " ");
                }
            }
            System.out.println("");
        }
    }
    
    public static void main(String[] args) {
    	Levels level = new Levels();
    	level.setLevel(2);
    	level.printBoard();
    }
}