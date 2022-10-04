package edu.pacific.comp55.main;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class MainApplication extends GraphicsApplication {
	public static int WINDOW_WIDTH = 1280;
	public static int WINDOW_HEIGHT = 720;
	public static double WINDOW_SCALE = 0;
	public static final String MUSIC_FOLDER = "sounds";

	private GamePane somePane;
	private MenuPane menuPane;
	private PausePane pausePane;
	private LevelsPane levelsPane;
	private ControlPane controlPane;

	public void init() {
		// Get Display Scaling
		GraphicsConfiguration displayConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		AffineTransform scales = displayConfig.getDefaultTransform();
		double scaleX = scales.getScaleX();
		double scaleY = scales.getScaleY();
		WINDOW_SCALE = (scaleX + scaleY) / 2;
		
		
		// Get Display Size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		WINDOW_WIDTH = (int)(screenSize.getWidth() / 1.1);
		WINDOW_HEIGHT = (int)(screenSize.getHeight() / 1.1); 
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		System.out.println("Program Launched!");
		somePane = new GamePane(this);
		pausePane = new PausePane(this);
		levelsPane = new LevelsPane(this);
		menuPane = new MenuPane(this);
		controlPane = new ControlPane(this);
		
		setupInteractions();
		switchToMenu();
	}

	public void switchToMenu() {
		switchToScreen(menuPane);
	}

	public void switchToSome() {
		switchToScreen(somePane);
	}
	
	public void switchToLevelsPane() {
		switchToScreen(levelsPane);
	}
	
	public void switchToPausePane() {
		switchToScreen(pausePane);
	}
	
	public void switchToControls() {
		switchToScreen(controlPane);
	}
	
	public GamePane getGamePane() {
		return somePane;
	}
	
	public PausePane getPausePane() {
		return pausePane;
	}
	
	public static void main(String[] args) {
		new MainApplication().start();
	}
}

