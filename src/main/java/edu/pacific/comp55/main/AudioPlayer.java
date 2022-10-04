package edu.pacific.comp55.main;


import java.io.IOException;
/*
 * Typical usage of the AudioPlayer.java
 * ----------------------------------
 * AudioPlayer myAudio = AudioPlayer.getInstance()
 * myAudio.playSound("music", "funk.mp3")
 * 
 * AudioPlayer supports mp3 files and is based on the javafx MediaPlayer class
 * Questions can be sent to ojimenez@pacific.edu
 */
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;

public final class AudioPlayer {
	private final Map<String, MediaPlayer> players;

	private static class AudioPlayerInit {
		public static final AudioPlayer INSTANCE = new AudioPlayer();
	}

	private AudioPlayer() {
		final JFXPanel fxPanel = new JFXPanel();
		players = new HashMap<String, MediaPlayer>();
	}

	/**
	 * Think of this like the constructor for getting the audioplayer Usage:
	 * AudioPlayer myPlayer = AudioPlayer.getInstance();
	 * 
	 * @return instance of the AudioPlayer
	 */
	public static AudioPlayer getInstance() {
		return AudioPlayerInit.INSTANCE;
	}

	/**
	 * Plays a sound based on the foldername and filename given in the
	 * parameters Will only play the sound once. If the sound isn't finished and
	 * the exact same sound is played again, playSound will restart the sound.
	 * 
	 * @param folder
	 *            folder where the sound is inside of media, leave as empty
	 *            string if in the main media folder
	 * @param filename
	 *            filename for the sound, make sure to include the extension
	 */
	public void playSound(String folder, String filename) {
		playSound(folder, filename, false);
	}

	/**
	 * same as the original play sound, but has the option to loop the sound
	 * 
	 * @param folder
	 *            folder where the sound is inside of media, leave as empty
	 *            string if in the main media folder
	 * @param filename
	 *            filename for the sound, make sure to include the extension
	 * @param shouldLoop
	 *            true will loop the sound.
	 */
	public void playSound(String folder, String filename, boolean shouldLoop) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				playSoundWithOptions(folder, filename, shouldLoop);
			}
		});
	}

	private void playSoundWithOptions(String folder, String filename, boolean shouldLoop) {
		URL key = buildResourcePath(folder, filename);
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream instream = AudioSystem.getAudioInputStream(key);
			clip.open(instream);
			clip.start();
			LineListener listener = new LineListener() {
				@Override
				public void update(LineEvent event) {
					if (event.getType() != Type.STOP) {
						return;
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					clip.stop();
					clip.close();
					
				}
				
			};
			clip.addLineListener(listener);
		} catch (IOException e) {
			System.out.println("Speaker? What Speaker?");
		} catch (LineUnavailableException e) {
			System.out.println("Unable to play: Line is Unavailable");
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Unable to play: Audio File is unsupported");
		}
	}

	/*
	 * Currently only supports default package or one sub-package, have not
	 * updated the code for the latest package
	 */
	private URL buildResourcePath(String folder, String filename) {
		if (folder != null && folder.length() > 0) {
			folder += "/";
		}
		final URL resource = getClass().getClassLoader().getResource(folder + filename);
		try {
			return resource;
		} catch (NullPointerException ex) {
			try {
				final URL newResource = getClass().getClassLoader().getResource("../" + folder + filename);
				return newResource;
			} catch (NullPointerException ex1) {
				ex.printStackTrace();
				System.out.println("MEDIA FILE NOT FOUND: " + folder + filename);
				System.out.println("Also tried: ../" + folder + filename + "...Exiting");
				System.exit(0);
			}

		}
		return resource;
	}

	private MediaPlayer findSound(String folder, String filename) {
		return players.get(folder + filename);
	}

	/**
	 * Stops the sound when the media is playing, does nothing otherwise Calling
	 * playSound after stopping the sound will cause the sound to start from the
	 * beginning
	 * 
	 * @param folder
	 *            folder where the sound is inside of media, leave as empty
	 *            string if in the main media folder
	 * @param filename
	 *            filename for the sound, make sure to include the extension
	 */
	public void stopSound(String folder, String filename) {
		Platform.runLater(new Runnable() {
			public void run() {
				MediaPlayer sound = findSound(folder, filename);
				if (sound != null) {
					sound.stop();
				}
			}
		});
	}

	/**
	 * Pauses the sound when the media is playing, does nothing otherwise
	 * Calling playSound after pausing the sound will cause the sound to play
	 * where it left off
	 * 
	 * @param folder
	 *            folder where the sound is inside of media, leave as empty
	 *            string if in the main media folder
	 * @param filename
	 *            filename for the sound, make sure to include the extension
	 */
	public void pauseSound(String folder, String filename) {
		Platform.runLater(new Runnable() {
			public void run() {
				MediaPlayer sound = findSound(folder, filename);
				if (sound != null) {
					sound.pause();
				}
			}
		});
	}
}