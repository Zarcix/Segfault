package edu.pacific.comp55.main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;

public class ControlPane extends GraphicsPane {
	

	private MainApplication program; // use program to get access to all of the GraphicsProgram calls

	private GRect background;
	private GRoundRect menu, up, down, left, right;
	private GRoundRect back;
	private boolean controlClicked = false;
	private GRoundRect previousPressed = null; 
	
	private ArrayList<GRoundRect> buttons = new ArrayList<GRoundRect>();
	
	public ControlPane(MainApplication app) {
		super();
		double buttonSize = MainApplication.WINDOW_WIDTH / 9.5;
		
		program = app;
		
		background = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		background.setFilled(true);
		background.setColor(Color.black);
		
		menu = new GRoundRect(150, 150);
		menu.setColor(Color.white);
		menu.setFilled(true);
		
		
		up = new GRoundRect((MainApplication.WINDOW_WIDTH / 2), (MainApplication.WINDOW_HEIGHT / 2) - MainApplication.WINDOW_WIDTH / 12, buttonSize, buttonSize);
		
		down = new GRoundRect((MainApplication.WINDOW_WIDTH / 2), (MainApplication.WINDOW_HEIGHT / 2), buttonSize, buttonSize);

		left = new GRoundRect((MainApplication.WINDOW_WIDTH / 2) - 150, (MainApplication.WINDOW_HEIGHT / 2), buttonSize, buttonSize);

		right = new GRoundRect((MainApplication.WINDOW_WIDTH / 2) + 150, (MainApplication.WINDOW_HEIGHT / 2), buttonSize, buttonSize);

		
		buttons.add(up);
		buttons.add(down);
		buttons.add(left);
		buttons.add(right);
		buttons.add(menu);
		
		for (GRoundRect button : buttons) {
			button.setColor(Color.white);
			button.setFilled(true);
			button.scale(.7 / MainApplication.WINDOW_SCALE);
		}
		
		back = new GRoundRect(MainApplication.WINDOW_WIDTH - 200, 0, 200, 200);
		back.setColor(Color.red);
		back.setFilled(true);
		back.scale(.9 / MainApplication.WINDOW_SCALE);
	}

	@Override
	public void showContents() {
		program.add(background);
		
		for(GRoundRect button : buttons) {
			program.add(button);
		}
		
		program.add(back);
		
	}

	@Override
	public void hideContents() {
		program.remove(background);
		
		for(GRoundRect button : buttons) {
			program.remove(button);
		}
		
		program.remove(back);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj.getClass().getName() == GRoundRect.class.getName() ) {
			controlClicked = true;
			previousPressed = (GRoundRect) obj;
			previousPressed.setColor(Color.gray);
		}
		
		if (obj == back) {
			program.switchToMenu();
			previousPressed.setColor(Color.red);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int pressedChar = e.getKeyCode();
		String control = "";
		
		
		if (controlClicked == false) {
			return;
		}
		
		if (previousPressed == up) {
			control = "up";
		} else if (previousPressed == down) {
			control = "down";
		} else if (previousPressed == left) {
			control = "left";
		} else if (previousPressed == right) {
			control = "right";
		} else {
			control = "menu";
		}
		
		program.getGamePane().setControls(control, pressedChar);
		previousPressed.setColor(Color.white);
		
		controlClicked = false;
	}
}