package de.tum.cit.ase.bomberquest;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import games.spooky.gdx.nativefilechooser.desktop.DesktopFileChooser;

import java.util.List;

/**
 * The DesktopLauncher class is the entry point for the desktop version of the Bomber Quest game.
 * It sets up the game window and launches the game using LibGDX framework.
 */
public class DesktopLauncher {
	/**
	 * The main method sets up the configuration for the game window and starts the application.
	 *
	 * @param arg Command line arguments (not used in this application)
	 */
	public static void main(String[] arg) {
		// Configuration for the game window
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Cosmic combustion: Space Mila"); // Set the window title
		config.setWindowIcon("assets/texture/Icon.png");

		// Get the display mode of the current monitor
		Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
		// Set the window size to 80% of the screen width and height
		config.setWindowedMode(
				Math.round(0.8f * displayMode.width),
				Math.round(0.8f * displayMode.height)
		);
		config.useVsync(true); // Enable vertical sync
		config.setForegroundFPS(60); // Set the foreground frames per second

		// Launch the game
		new Lwjgl3Application(new BomberQuestGame(new DesktopFileChooser()), config);
	}
}
