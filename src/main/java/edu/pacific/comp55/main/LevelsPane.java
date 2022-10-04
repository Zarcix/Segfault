package edu.pacific.comp55.main;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import acm.graphics.GObject;
import acm.graphics.GRect;

public class LevelsPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private ArrayList<GButton> buttons = new ArrayList<GButton>();
	GRect back;
	Board board;
	GButton pause, esc;
	
	public LevelsPane(MainApplication app) {
		this.program = app;
		back = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		back.setFilled(true);
		back.setColor(Color.BLACK);
		
		esc = new GButton("ESC", MainApplication.WINDOW_WIDTH - 120, 10, 50, 50, Color.gray);
		
		generateLevels();
	}
	
	public void generateLevels() {
		board = program.getGamePane().getBoard();
		HashMap<Integer, Levels> passed = board.getPassedLevels();
		buttons.clear();
		int tempWidth = 0;
		int tempHeight = 1;
		for (int i = 0; i < Levels.levels.size(); i++) {
			String s = Integer.toString(i + 1);
			
			tempWidth *= ((tempWidth + 1)*100 > MainApplication.WINDOW_WIDTH? 0 : 1);
			tempHeight += ((tempWidth + 1)*100 > MainApplication.WINDOW_WIDTH? 1 : 0);
			
			GButton button = new GButton("Level" + s, tempWidth*100, tempHeight * 100, 80, 80, Color.gray);
			buttons.add(button);
			
			Color color = new Color(144, 144, 230);
			if (passed.get(i + 1) != null) {
				button.setFillColor(color);
			}
			tempWidth++;
		}
	}

	@Override
	public void showContents() {
		generateLevels();
		program.add(back);
		program.add(esc);
		
		for(GButton b : buttons) {
			program.add(b);
		}
	}

	@Override
	public void hideContents() {
		program.remove(back);
		program.remove(esc);
		for(GButton b : buttons) {
			program.remove(b);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		for(int i = 0; i < buttons.size(); i++) {
			if(obj == buttons.get(i)) {
				board.setLevel(i + 1);
				board.getLevel().genBoard();
				board.generateBoard();
				program.getGamePane().generateScreen();
				hideContents();
				program.switchToSome();
			}
		}
		if(obj == esc) {
			program.toggleScreen(program.getPausePane());
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int tempChar = e.getKeyCode();
		if(tempChar == program.getGamePane().getControls().get("menu")) {
			program.toggleScreen(program.getPausePane());
		}
	}
}
