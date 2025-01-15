package de.tum.cit.ase.bomberquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.tum.cit.ase.bomberquest.audio.BackgroundTrack;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.screen.GameScreen;
import de.tum.cit.ase.bomberquest.screen.MenuScreen;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;

import java.io.File;

/**
 * The BomberQuestGame class represents the core of the Bomber Quest game.
 * It manages the screens and global resources like SpriteBatch and Skin.
 */
public class BomberQuestGame extends Game {

    /**
     * The file chooser for loading map files from the user's computer.
     * This will give you access to a {@link com.badlogic.gdx.files.FileHandle} object,
     * which you can use to read the contents of the map file as a String, and then parse it into a {@link GameMap}.
     */
    private final NativeFileChooser fileChooser;
    /**
     * Sprite Batch for rendering game elements.
     * This eats a lot of memory, so we only want one of these.
     */
    private SpriteBatch spriteBatch;
    /**
     * The game's UI skin. This is used to style the game's UI elements.
     */
    private Skin skin;
    /**
     * The map. This is where all the game objects are stored.
     * This is owned by {@link BomberQuestGame} and not by {@link GameScreen}
     * because the map should not be destroyed if we temporarily switch to another screen.
     */
    private GameMap map;

    private String mapFilePath;
    private final String defaultPath = "maps/map-1.properties";

    /**
     * Constructor for BomberQuestGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public BomberQuestGame(NativeFileChooser fileChooser) {
        this.fileChooser = fileChooser;
        mapFilePath = defaultPath;
    }

    /**
     * Called when the game is created. Initializes the SpriteBatch and Skin.
     * During the class constructor, libGDX is not fully initialized yet.
     * Therefore, this method serves as a second constructor for the game,
     * and we can use libGDX resources here.
     */
    @Override
    public void create() {
        this.spriteBatch = new SpriteBatch(); // Create SpriteBatch for rendering
        this.skin = new Skin(Gdx.files.internal("skin/UI skin.json")); // Load UI skin

         // Play some background music
        goToMenu(); // Navigate to the menu screen
        createNewMap();
    }

    /**
     * Switches to the menu screen.
     */
    public void goToMenu() {
        this.setScreen(new MenuScreen(this)); // Set the current screen to MenuScreen
    }

    /**
     * Switches to the game screen.
     */
    public void goToGame() {
        this.setScreen(new GameScreen(this)); // Set the current screen to GameScreen
    }
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
     * Returns the skin for UI elements.
     */
    public Skin getSkin() {
        return skin;
    }

    /**
     * Returns the main SpriteBatch for rendering.
     */
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    /**
     * Returns the current map, if there is one.
     */
    public GameMap getMap() {
        return map;
    }

    /**
     * Switches to the given screen and disposes of the previous screen.
     *
     * @param screen the new screen
     */
    @Override
    public void setScreen(Screen screen) {
        Screen previousScreen = super.screen;
        super.setScreen(screen);
        if (previousScreen != null) {
            previousScreen.dispose();
        }
    }
    public void finishGame() {
        createNewMap();
        goToMenu();

    }
    public void restartGame() {
        String player1name = map.getPlayer1().getName();
        String player2name = (map.getPlayer2() != null) ? map.getPlayer2().getName(): "";
        createNewMap();
        if (player2name.isEmpty()) renamePlayer(player1name);
        else renamePlayers(player1name, player2name);
        goToGame();
    }
    public void errorLoadingMap(String error) {
        if (super.getScreen() instanceof MenuScreen menuScreen) {
            menuScreen.showErrorMapDialog(this, error);
        }
    }
    public void renamePlayers(String player1, String player2) {
        System.out.println("Player1: " + map.getPlayer1().getName());
        if (map != null) {
            map.getPlayer1().setName(player1);

            if (map.getPlayer2() != null) {
                map.getPlayer2().setName(player2);
            }
        }
        else {
            System.out.println("map is null");
        }
    }
    public void renamePlayer(String player1) {

        if (map != null) {
            map.getPlayer1().setName(player1);
        }
        else {
            System.out.println("map is null");
        }
    }

    /**
     * Cleans up resources when the game is disposed.
     */
    @Override
    public void dispose() {
        getScreen().hide(); // Hide the current screen
        getScreen().dispose(); // Dispose the current screen
        spriteBatch.dispose(); // Dispose the spriteBatch
        skin.dispose(); // Dispose the skin
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public boolean createNewMap() {
        setMap(new GameMap(this, mapFilePath));
        return map != null && map.getMAX_X() >= 0;
    }

    public String getMapFilePath() {
        return mapFilePath;
    }
    public void setMapFilePath(String mapFilePath) {
        this.mapFilePath = mapFilePath;
    }
}
