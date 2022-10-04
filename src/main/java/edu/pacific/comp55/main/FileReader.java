package edu.pacific.comp55.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
	private ArrayList<String> levels = new ArrayList<String>();
	
	public String[][] readLevel(int levelNum) {
		
		String level = levelNum + "";
		
		File folder = new File("src/main/resources/levels");
		File Level = readDir(folder, level);
		return readFile(Level);
	}
	
	public ArrayList<String> getLevels() {
		return levels;
	}
	
	private File readDir(File folder, String levelNum) {
		File level = null;
    	for (File fileEntry : folder.listFiles()) {
    		if (fileEntry.getName().matches("\\d+")) { // Get files with numbers https://stackoverflow.com/questions/16183467/difference-between-d-and-d-in-java-regex
        		if (fileEntry.getName().equals(levelNum)) {
        			level = new File(fileEntry.getPath());
        		}
        		levels.add(fileEntry.getName());
    		}
    	}
    	
    	return level;
	}
	
	private String[][] readFile(File level) {
		String[][] levelData = new String[9][16];
		
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(level);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found: " + level);
		}
		
		fileScanner.nextLine(); // skip first line
		
		
		int height = 0;
		int width = 0;
		while (fileScanner.hasNext()) {
			fileScanner.next();
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			
			while (lineScanner.hasNext()) {
				String data = lineScanner.next();
				levelData[height][width] = data;
				width++;
			}
			
			lineScanner.close();
			height++;
			width = 0;
		}
    	fileScanner.close();
    	
    	return levelData;
	}
	
	public static void main(String[] args) {
		FileReader read = new FileReader();
		
		read.readLevel(1);
	}
}
