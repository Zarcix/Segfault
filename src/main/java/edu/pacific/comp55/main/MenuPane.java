package edu.pacific.comp55.main;
 
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.*;  

public class MenuPane extends GraphicsPane {
	private static final String FONT = "Arial-Bold-80";
	
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GButton rect1, rect2, rect3, rect4;
	private GRect background;
	private GLabel segfault;

	public MenuPane(MainApplication app) {
		super();
		program = app;
		background = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		background.setFilled(true);
		background.setColor(Color.black);
		
		rect1 = new GButton("", (MainApplication.WINDOW_WIDTH / 2) - (MainApplication.WINDOW_WIDTH / 10) * 2, (MainApplication.WINDOW_HEIGHT / 2) - (MainApplication.WINDOW_HEIGHT / 10), (MainApplication.WINDOW_WIDTH / 10) * 4, 128);
		rect1.setFillColor(Color.green);
		
		rect3 = new GButton("", (MainApplication.WINDOW_WIDTH / 2) - (MainApplication.WINDOW_WIDTH / 10), (MainApplication.WINDOW_HEIGHT / 2) + (MainApplication.WINDOW_HEIGHT / 10), (MainApplication.WINDOW_WIDTH / 10) * 2, MainApplication.WINDOW_HEIGHT / 10);
		rect3.setFillColor(Color.yellow);
		
		rect2 = new GButton("", (MainApplication.WINDOW_WIDTH / 2) - (MainApplication.WINDOW_WIDTH / 10), (MainApplication.WINDOW_HEIGHT / 2) + (MainApplication.WINDOW_HEIGHT / 10)*2 + 10, (MainApplication.WINDOW_WIDTH / 10) * 2, MainApplication.WINDOW_HEIGHT / 10);
		rect2.setFillColor(Color.red);
		
		rect4 = new GButton("", 0, MainApplication.WINDOW_HEIGHT - 120, 80, 80);
		rect4.setFillColor(Color.gray);
		
		segfault = new GLabel("S E G F A U L T");
		double width = segfault.getWidth();
		segfault.setLocation((double)(MainApplication.WINDOW_WIDTH / 2) - (double)(width * 3.645), (double)(MainApplication.WINDOW_HEIGHT / 4));
		
		segfault.setColor(Color.white);
		segfault.setFont(FONT);
		
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(rect1);
		program.add(segfault);
		program.add(rect2);
		program.add(rect3);
		program.add(rect4);
	}

	@Override
	public void hideContents() {
		program.remove(rect1);
		program.remove(background);
		program.remove(rect2);
		program.remove(rect3);
		program.remove(rect4);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect1) {
			/* Reload Level */
			program.getGamePane().getBoard().generateBoard();
			program.getGamePane().generateScreen();
			
			program.switchToSome();
		}
		if(obj == rect2) {
			System.exit(0);
		}
		if(obj == rect3) {
			program.switchToLevelsPane();
		}
		if (obj == rect4) {
			program.switchToControls();
		}
	}
}