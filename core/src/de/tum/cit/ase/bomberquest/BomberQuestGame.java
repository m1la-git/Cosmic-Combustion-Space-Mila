package de.tum.cit.ase.bomberquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.map.Settings;
import de.tum.cit.ase.bomberquest.screen.GameScreen;
import de.tum.cit.ase.bomberquest.screen.MenuScreen;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

/**
 * The BomberQuestGame class represents the core of the Bomber Quest game.
 * <p>
 * It extends {@link Game} and is responsible for managing game screens,
 * global resources like {@link SpriteBatch} and {@link Skin}, game settings,
 * and map loading functionalities. This class acts as the central orchestrator
 * for the entire game application.
 */
public class BomberQuestGame extends Game {

    /**
     * The file chooser for loading map files from the user's computer.
     * <p>
     * This native file chooser allows users to select custom map files,
     * providing flexibility in game content. It is platform-dependent and
     * typically used in desktop environments.
     *
     * @see NativeFileChooser For file selection API.
     * @see FileHandle For accessing file contents.
     * @see GameMap For map data structure.
     */
    private final NativeFileChooser fileChooser;
    /**
     * Default map file path, used if no custom map is loaded or loading fails.
     */
    private final String defaultPath = "maps/map-1.properties";
    /**
     * Sprite Batch for rendering game elements.
     * <p>
     * A single instance of SpriteBatch is used throughout the game for efficient
     * rendering of 2D sprites and textures. This spriteBatch is passed to screens and HUDs
     * to ensure all rendering operations are batched together for performance.
     */
    private SpriteBatch spriteBatch;
    /**
     * The game's UI skin.
     * <p>
     * This skin defines the visual style of UI components (like buttons, labels, etc.)
     * used in the game menus and dialogs. It is loaded from a JSON file and managed centrally.
     */
    private Skin skin;
    /**
     * The game map.
     * <p>
     * Holds all the game objects and the current state of the game world.
     * It is managed by {@code BomberQuestGame} to persist map data across screen switches.
     * The GameMap is loaded from a file and updated every frame during gameplay.
     *
     * @see GameMap
     * @see GameScreen
     */
    private GameMap map;
    /**
     * Path to the currently loaded map file.
     */
    private String mapFilePath;
    /**
     * Game settings, encapsulating various game configurations like AI and timer settings.
     */
    private Settings settings;


    /**
     * Constructor for BomberQuestGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public BomberQuestGame(NativeFileChooser fileChooser) {
        this.fileChooser = fileChooser;
        mapFilePath = defaultPath; // Initialize map file path to default
        settings = new Settings(); // Initialize game settings with default values
    }

    /**
     * Called when the game is created. Initializes the SpriteBatch and Skin.
     * <p>
     * This method is part of the LibGDX game lifecycle and is called upon application start.
     * It sets up essential game resources like the SpriteBatch for rendering and the Skin for UI styling.
     * It also navigates the game to the main menu and creates a new game map.
     */
    @Override
    public void create() {
        this.spriteBatch = new SpriteBatch(); // Create SpriteBatch for rendering
        this.skin = new Skin(Gdx.files.internal("skin/UI skin.json")); // Load UI skin

        // Play some background music
        goToMenu(); // Navigate to the menu screen
        createNewMap(); // Create a new game map
    }

    /**
     * Switches the current screen to the menu screen.
     * <p>
     * This method sets the active screen to {@link MenuScreen}, which displays the game's main menu.
     * It's used to navigate users to the menu for options like starting a new game, settings, etc.
     */
    public void goToMenu() {
        this.setScreen(new MenuScreen(this)); // Set the current screen to MenuScreen
    }

    /**
     * Switches the current screen to the game screen.
     * <p>
     * This method sets the active screen to {@link GameScreen}, initiating the gameplay.
     * It's called when the user starts or continues a game, transitioning from the menu to the active game world.
     */
    public void goToGame() {
        this.setScreen(new GameScreen(this)); // Set the current screen to GameScreen
    }

    /**
     * Opens a native file chooser dialog to allow the user to select a map file.
     * <p>
     * Uses {@link NativeFileChooser} to present a file selection dialog to the user,
     * specifically filtering for .properties files, which are used for game maps.
     * Upon file selection, it attempts to load the chosen map and handles potential errors.
     *
     * @return {code true} if a new map was chosen by the player, {@code false} otherwise
     */
    public boolean chooseMap() {
        final boolean[] choseNewMap = {false};
        NativeFileChooserConfiguration config = new NativeFileChooserConfiguration();
        config.mimeFilter = "text/x-java-properties"; // choose only .properties file
        config.title = "Select a Properties File";
        config.directory = Gdx.files.external("");
        fileChooser.chooseFile(config, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle fileHandle) {
                choseNewMap[0] = true;
                setMapFilePath(fileHandle.path()); // Store the absolute path
                if (!createNewMap()) {
                    setMapFilePath(defaultPath);
                    createNewMap();
                }
            }

            @Override
            public void onCancellation() {
                System.out.println("File cancelled");
            }

            @Override
            public void onError(Exception e) {
                System.out.println("FileChooser Error: " + e.getMessage());
            }
        });
        return choseNewMap[0];

    }

    /**
     * Gets the game's UI skin.
     *
     * @return The {@link Skin} object used for styling UI elements.
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * Gets the main SpriteBatch used for rendering.
     *
     * @return The {@link SpriteBatch} object.
     */
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    /**
     * Gets the current game map.
     *
     * @return The {@link GameMap} object representing the current game world.
     */
    public GameMap getMap() {
        return map;
    }

    /**
     * Sets the game map to a new map instance.
     *
     * @param map The new {@link GameMap} object to use for the game.
     */
    public void setMap(GameMap map) {
        this.map = map;
    }

    /**
     * Sets the current screen, disposing of the previous screen if it exists.
     * <p>
     * This method overrides {@link Game#setScreen(Screen)} to include screen disposal logic,
     * ensuring proper resource management when screens are changed.
     *
     * @param screen The new screen to set as the current screen.
     */
    @Override
    public void setScreen(Screen screen) {
        Screen previousScreen = super.screen; // Get the current screen
        super.setScreen(screen); // Set the new screen
        if (previousScreen != null) {
            previousScreen.dispose(); // Dispose of the previous screen to free resources
        }
    }

    /**
     * Finishes the current game and navigates back to the menu screen.
     * <p>
     * This method is called when the game concludes (e.g., game over, level completion).
     * It resets the game map by creating a new one and then transitions the user back to the main menu.
     */
    public void finishGame() {
        createNewMap(); // Create a new game map, effectively resetting the game state
        goToMenu(); // Navigate to the menu screen
    }

    /**
     * Restarts the current game, keeping player names but resetting the game state.
     * <p>
     * This method is called when the player chooses to restart the game from within a game session.
     * It preserves player names, creates a new game map, and then starts a new game on the game screen.
     */
    public void restartGame() {
        String player1name = map.getPlayer1().getName(); // Get player 1's name
        String player2name = (map.getPlayer2() != null) ? map.getPlayer2().getName() : ""; // Get player 2's name if exists
        createNewMap(); // Create a new game map, resetting the game state
        if (player2name.isEmpty()) renamePlayer(player1name); // Rename player 1 with the preserved name
        else renamePlayers(player1name, player2name); // Rename both players with preserved names
        goToGame(); // Navigate to the game screen to start a new game
    }

    /**
     * Handles map loading errors by displaying an error dialog in the menu screen.
     * <p>
     * When map loading fails, this method is called to inform the user about the error
     * through a dialog presented on the {@link MenuScreen}. It ensures that error messages
     * are displayed within the menu context.
     *
     * @param error The error message to display to the user.
     */
    public void errorLoadingMap(String error) {
        if (super.getScreen() instanceof MenuScreen menuScreen) {
            menuScreen.showErrorMapDialog(this, error); // Show error dialog on the menu screen
        }
    }

    /**
     * Renames both players in a multiplayer game.
     *
     * @param player1 The new name for player 1.
     * @param player2 The new name for player 2.
     */
    public void renamePlayers(String player1, String player2) {
        System.out.println("Player1: " + map.getPlayer1().getName());
        if (map != null) {
            map.getPlayer1().setName(player1); // Set player 1's name

            if (map.getPlayer2() != null) {
                map.getPlayer2().setName(player2); // Set player 2's name if exists
            }
        } else {
            System.out.println("map is null"); // Log if map is null
        }
    }

    /**
     * Renames player 1 in a single-player game.
     *
     * @param player1 The new name for player 1.
     */
    public void renamePlayer(String player1) {

        if (map != null) {
            map.getPlayer1().setName(player1); // Set player 1's name
        } else {
            System.out.println("map is null"); // Log if map is null
        }
    }

    /**
     * Cleans up resources when the game is disposed.
     * <p>
     * This method is part of the LibGDX game lifecycle and is called when the application is shutting down.
     * It's crucial for releasing resources like screens, sprite batch, and skin to prevent memory leaks.
     */
    @Override
    public void dispose() {
        getScreen().hide(); // Hide the current screen
        getScreen().dispose(); // Dispose the current screen
        spriteBatch.dispose(); // Dispose the spriteBatch
        skin.dispose(); // Dispose the skin
    }

    /**
     * Creates a new game map using the current map file path and game settings.
     * <p>
     * This method instantiates a new {@link GameMap} object, effectively resetting the game world.
     * It uses the currently set map file path and game settings to initialize the new map.
     *
     * @return {@code true} if the map was successfully created and loaded, {@code false} otherwise.
     */
    public boolean createNewMap() {
        setMap(new GameMap(this, mapFilePath, settings)); // Create a new GameMap instance
        return map != null && map.getMAX_X() >= 0; // Check if map creation was successful
    }

    /**
     * Sets the file path for the game map.
     *
     * @param mapFilePath The file path to the map file.
     */
    public void setMapFilePath(String mapFilePath) {
        this.mapFilePath = mapFilePath;
    }

    /**
     * Gets the current game settings.
     *
     * @return The {@link Settings} object containing game configurations.
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * Sets the game settings.
     *
     * @param settings The new {@link Settings} object to apply to the game.
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }


}