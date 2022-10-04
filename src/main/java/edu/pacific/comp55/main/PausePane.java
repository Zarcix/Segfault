package edu.pacific.comp55.main;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GObject;
import acm.graphics.GRect;

public class PausePane extends GraphicsPane {
	private static final int SIZE_Y = 90;
	private static final int SIZE_X = 190;
	private static final int LOCATION_Y = 100;
	private static final int LOCATION_X = 500;
	private MainApplication program; 
	private GButton level, menu, exit;
	private GRect back;
	private GButton prevLevel, nextLevel, esc;
	private boolean visible = false;
	private ArrayList<GObject> gObjects = new ArrayList <GObject>(); 

	public PausePane(MainApplication app) {
		Color custom = new Color(0, 0, 0, 140);
		this.program = app;
		back = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		back.setFilled(true);
		back.setColor(custom);
		gObjects.add(back);
		
		level = new GButton("LEVELS", LOCATION_X, LOCATION_Y, SIZE_X, SIZE_Y, Color.blue);
		gObjects.add(level);

		menu = new GButton("MENU", LOCATION_X, 2 * LOCATION_Y, SIZE_X, SIZE_Y, Color.green);
		gObjects.add(menu);

		exit = new GButton("EXIT", LOCATION_X, 3 * LOCATION_Y, SIZE_X, SIZE_Y, Color.red);
		gObjects.add(exit);

		
		prevLevel = new GButton("" + (char) 8592, 950, 30, 70, 70, 216, 31, 42); 
		gObjects.add(prevLevel);

		nextLevel = new GButton("" + (char) 8594, 950, 110, 70, 70, 26, 149, 49);
		gObjects.add(nextLevel);

		
		esc = new GButton("ESC", MainApplication.WINDOW_WIDTH - 120, 10, 50, 50, Color.gray);
		gObjects.add(esc);

	}

	@Override
	public void showContents() {
		visible = true;
		for(GObject obj : gObjects) {
			program.add(obj);
		}
	}

	@Override
	public void hideContents() {
		visible = false;
		for(GObject obj : gObjects) {
			program.remove(obj);
		}
	}
	
	public void toggleContents() {
		if (visible) {
			hideContents();
			return;
		}
		showContents();
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == level) {
			program.switchToLevelsPane();
			resetMoveDeath();
		}
		if(obj == menu) {
			program.switchToMenu();
		}
		if(obj == esc) {
			program.switchToSome();
		}
		if(obj == exit) {
			System.exit(0);
		}
		if(obj == prevLevel) {
			int previousLevel = getNextLevel(-1);
			changeLevel(previousLevel);
			resetMoveDeath();
			showContents();
		}
		if(obj == nextLevel) {
			int nextLevel = getNextLevel(1);
			changeLevel(nextLevel);
			resetMoveDeath();
			showContents();
		}
	}

	private int getNextLevel(int i) {
		int someLevel = program.getGamePane().getBoard().getLevel().getLevel() + i;
		return someLevel;
	}

	private void changeLevel(int someLevel) {
		if (program.getGamePane().getBoard().getLevel().checkValidLevel(someLevel)) {
			return;
		}
		program.getGamePane().getBoard().setLevel(someLevel);
		program.getGamePane().getBoard().generateBoard();
		program.getGamePane().hideContents();
		program.getGamePane().generateScreen();
		program.getGamePane().showContents();
	}

	private void resetMoveDeath() {
		Board.setMoveNum(1);
		Board.setDeathNum(0);
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == program.getGamePane().getControls().get("menu")) {
			program.switchToSome();
		}
	}
}
