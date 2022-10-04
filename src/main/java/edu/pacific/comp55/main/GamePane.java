package edu.pacific.comp55.main;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class GamePane extends GraphicsPane implements ActionListener {
	private static final String FONT = "Arial-bold-24";
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private AudioPlayer player = AudioPlayer.getInstance();
	private GRect background;
	private GLabel moveCounter, deathCounter;
	private GButton esc;
	public Board board;
	boolean cancelDouble = false;
	private WinPane hopepane;
	Timer paneTimer;
	private ArrayList<GObject> gObjects = new ArrayList <GObject>(); 
	private ArrayList<GImage> objects = new ArrayList<GImage>();
	private HashMap<String, Integer> controls = new HashMap<String, Integer>();

	public GamePane(MainApplication app) {
		this.program = app;
		
		board = new Board();
		
		controls.put("up", 87);
		controls.put("left", 65);
		controls.put("down", 83);
		controls.put("right", 68);
		controls.put("menu", 27);
		
		background = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		background.setFilled(true);
		background.setColor(Color.black);
		gObjects.add(background);
		
		hopepane = new WinPane(program);
		paneTimer = new Timer(50, this);
		
		moveCounter = createGLabel("0", 110, 30);
		deathCounter = createGLabel("0", 110, 60);
		createGLabel("MOVES: ", 10, 30);
		createGLabel("DEAD: ", 10, 60);
		
		esc = new GButton("ESC", MainApplication.WINDOW_WIDTH - 120, 10, 50, 50, Color.gray);
		gObjects.add(esc);
		
		generateScreen();
	}

	private GLabel createGLabel(String s, int x, int y) {
		GLabel label = new GLabel(s, x, y);
		label.setColor(Color.white);
		label.setFont(FONT);
		gObjects.add(label);
		return label;
	}
	
	public void setBoard(Board b) {
		board = b;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public HashMap<String, Integer> getControls() {
		return controls;
	}
	
	public void setControls(String direction, int key) {
		controls.replace(direction, key);
	}
	
	public void generateScreen() {
		objects.clear();
		for (int i = 0; i < Levels.LEVEL_HEIGHT; i++) {
			for (int j = 0; j < Levels.LEVEL_WIDTH; j++) {
				if (board.getLevel().getBoard()[i][j] != null) {
					double width = j * (MainApplication.WINDOW_WIDTH / 17);
					double widthPadding = (MainApplication.WINDOW_WIDTH - 16 * (MainApplication.WINDOW_WIDTH / 17)) / 2;
					
					double height = i * (MainApplication.WINDOW_HEIGHT / 10);
					double heightPadding = (MainApplication.WINDOW_HEIGHT - 9 * (MainApplication.WINDOW_HEIGHT / 10)) / 2.5;
					
					GImage newImage = new GImage("media/" + board.getLevel().getBoard()[i][j].getType() + ".png", widthPadding + width, heightPadding + height);
					newImage.scale(.7 / MainApplication.WINDOW_SCALE);
					objects.add(newImage);
				}
			}
		}
	}
	
	@Override
	public void showContents() {
		for (GObject obj : gObjects) {
			program.add(obj);
		}
		for (GImage image : objects) {
			program.add(image);
		}
	}

	@Override
	public void hideContents() {
		for (GObject obj : gObjects) {
			program.remove(obj);
		}
		for (GImage image : objects) {
			program.remove(image);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int up = controls.get("up");
		int down = controls.get("down");
		int left = controls.get("left");
		int right = controls.get("right");
		int escape = controls.get("menu");
		
		cancelDouble = !cancelDouble;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		int tempChar = e.getKeyCode();
		updateMoveDead();
		if (tempChar == up || tempChar == 38) { // W Up
			temp.add(1);
			temp.add(0);
		} else if (tempChar == left || tempChar == 37) { // A Left
			temp.add(0);
			temp.add(-1);
		} else if (tempChar == down || tempChar == 40) { // S Down
			temp.add(-1);
			temp.add(0);
		} else if (tempChar == right || tempChar == 39) { // D Right
			temp.add(0);
			temp.add(1);
		} else if (tempChar == escape) {
			program.toggleScreen(program.getPausePane());
			return;
		} else {
			return;
		}
		
		hideContents();
		
		String[] sound = board.movePlayer(temp.get(0), temp.get(1));
		player.playSound(MainApplication.MUSIC_FOLDER, sound[0]);
		
		
		hideContents();
		paneTimer.start();
		this.generateScreen();
		
		if (sound[1] != null) {
			hopepane.setActivate(sound[1]);
			return;
		}
		this.showContents();
		
	}

	private void updateMoveDead() {
		String counterStr = Integer.toString(board.getMoveNum()); 
		moveCounter.setLabel(counterStr);
		String deathCountStr = Integer.toString(board.getDeathNum());
		deathCounter.setLabel(deathCountStr);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		hopepane.hideContents();
		this.showContents();
		paneTimer.stop();
	}
	
	@Override
	public void  mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == esc) {
			program.toggleScreen(program.getPausePane());
		}
	}
}
