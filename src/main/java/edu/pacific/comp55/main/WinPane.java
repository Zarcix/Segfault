package edu.pacific.comp55.main;

import java.awt.Color;

import acm.graphics.GRect;


public class WinPane extends GraphicsPane {
	private MainApplication program;
	private GRect chosenScreen;
	
	public WinPane(MainApplication app) {
		this.program = app;
		
		chosenScreen = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		chosenScreen.setFilled(true);
		
	}
	
	public void setActivate(String passed) {
		Color screenColor = Color.green;
		if (passed.equals("false")) { // Passed
			screenColor = Color.red;
		}
		chosenScreen.setColor(screenColor);
		showContents();
	}
	
	@Override
	public void showContents() {
		program.add(chosenScreen);
	}

	@Override
	public void hideContents() {
		program.remove(chosenScreen);
	}

}
