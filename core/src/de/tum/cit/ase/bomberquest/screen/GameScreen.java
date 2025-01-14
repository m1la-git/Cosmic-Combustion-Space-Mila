package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.audio.BackgroundTrack;
import de.tum.cit.ase.bomberquest.map.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    /**
     * The size of a grid cell in pixels.
     * This allows us to think of coordinates in terms of square grid tiles
     * (e.g. x=1, y=1 is the bottom left corner of the map)
     * rather than absolute pixel coordinates.
     */
    public static final int TILE_SIZE_PX = 16;

    /**
     * The scale of the game.
     * This is used to make everything in the game look bigger or smaller.
     */
    public static final int SCALE = 4;

    private final BomberQuestGame game;
    private final Stage stage; // New stage for dialog
    private final SpriteBatch spriteBatch;
    private final GameMap map;
    private final Hud hud;
    private final OrthographicCamera mapCamera;
    private final Actor overlay; // For the darkened background
    private boolean isPaused = false; // Flag to track pause state


    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(BomberQuestGame game) {
        this.game = game;
        this.spriteBatch = game.getSpriteBatch();
        this.stage = new Stage(new ScreenViewport());
        this.map = game.getMap();
        this.hud = new Hud(spriteBatch, game.getSkin().getFont("font"), game.getSkin().getFont("bold"));
        // Create and configure the camera for the game view
        this.mapCamera = new OrthographicCamera();
        this.mapCamera.setToOrtho(false);
        //play background music
        BackgroundTrack.BACKGROUND.play();

        // Create a semi-transparent overlay
        overlay = new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                Color oldColor = batch.getColor();
                batch.setColor(0, 0, 0, 0.5f); // Black with 50% transparency
                batch.draw(game.getSkin().getRegion("white"), 0, 0, hud.getCamera().viewportWidth, hud.getCamera().viewportHeight);
                batch.setColor(oldColor);
            }
        };
        overlay.setVisible(false); // Hide it initially
        stage.addActor(overlay);
    }

    /**
     * Draws this object on the screen.
     * The texture will be scaled by the game scale and the tile size.
     * This should only be called between spriteBatch.begin() and spriteBatch.end(), e.g. in the renderMap() method.
     *
     * @param spriteBatch The SpriteBatch to draw with.
     */
    private static void draw(SpriteBatch spriteBatch, Drawable drawable) {
        TextureRegion texture = drawable.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = drawable.getX() * TILE_SIZE_PX * SCALE;
        float y = drawable.getY() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }

    /**
     * The render method is called every frame to render the game.
     * @param deltaTime The time in seconds since the last render.
     */
    @Override
    public void render(float deltaTime) {

        // Check for escape key press to go back to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            BackgroundTrack.BACKGROUND.pause();
            game.goToMenu();
        }


        // Clear the previous frame from the screen, or else the picture smears
        ScreenUtils.clear(Color.BLACK);

        // Cap frame time to 250ms to prevent spiral of death
        float frameTime = Math.min(deltaTime, 0.250f);
        if (!isPaused) {
            map.tick(frameTime); // Update the map state
        }
        updateCamera(); // Update the camera



        OrthographicCamera hudCamera = hud.getCamera();
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        spriteBatch.begin();

        spriteBatch.draw(Textures.BACKGROUND, 0, 0, hudCamera.viewportWidth, hudCamera.viewportHeight);
        spriteBatch.end();

        // Render the map on the screen
        renderMap();

        // Render the HUD_BACKGROUND on the screen
        hud.render(map.getPlayer1(), map.getPlayer2(), map.getTimer(), map.getNumberOfEnemies(), map.isExitOpen(), frameTime);

        if (!isPaused && map.isPlayerDead() || map.isVictory()) {
            isPaused = true; // Pause the game after showing the dialog
            BackgroundTrack.BACKGROUND.stop();
            showGameOverDialog(map.isVictory());
        }

        stage.act(deltaTime); // Update the stage
        stage.draw();         // Draw the stage (for dialog)
    }


    /**
     * Updates the camera to follow the player while keeping it within map boundaries.
     * If the map is smaller than the screen, the camera is centered on the map.
     */
    private void updateCamera() {
        float viewportHalfWidth = mapCamera.viewportWidth / 2f;  // Half-width of the viewport
        float viewportHalfHeight = mapCamera.viewportHeight / 2f; // Half-height of the viewport

        float mapWidthPx = (map.getMAX_X() + 1) * TILE_SIZE_PX * SCALE;
        float mapHeightPx = (map.getMAX_Y() + 1) * TILE_SIZE_PX * SCALE;

        // Calculate the 15% screen margin
        float screenMarginX = mapCamera.viewportWidth * 0.1f;
        float screenMarginY = mapCamera.viewportHeight * 0.1f;

        // Calculate max and min camera bounds with the margin in mind
        float maxCameraX = Math.max(viewportHalfWidth, mapWidthPx - viewportHalfWidth + screenMarginX);
        float maxCameraY = Math.max(viewportHalfHeight, mapHeightPx - viewportHalfHeight + screenMarginY);

        float minCameraX = Math.min(viewportHalfWidth - screenMarginX, mapWidthPx - viewportHalfWidth);
        float minCameraY = Math.min(viewportHalfHeight - screenMarginY, mapHeightPx - viewportHalfHeight);

        // Get player's position
        float playerX = map.getPlayer1().getX() * TILE_SIZE_PX * SCALE;
        float playerY = map.getPlayer1().getY() * TILE_SIZE_PX * SCALE;

        // Clamp the camera position to ensure it stays within the new map bounds
        float cameraX = (mapWidthPx <= mapCamera.viewportWidth * 0.8 || map.getPlayer2() != null) ? mapWidthPx / 2f : Math.max(minCameraX, Math.min(playerX, maxCameraX));
        float cameraY = (mapHeightPx <= mapCamera.viewportHeight * 0.8 || map.getPlayer2() != null) ? mapHeightPx / 2f : Math.max(minCameraY, Math.min(playerY, maxCameraY));

        // Update camera properties
        mapCamera.setToOrtho(false);
        mapCamera.position.set(cameraX, cameraY, 0);
        mapCamera.update(); // Apply the changes
    }

    /**
     * Renders the map with all the objects
     */
    private void renderMap() {
        // This configures the spriteBatch to use the camera's perspective when rendering
        spriteBatch.setProjectionMatrix(mapCamera.combined);

        // Start drawing
        spriteBatch.begin();

        // Render everything in the map here, in order from lowest to highest (later things appear on top)
        // You may want to add a method to GameMap to return all the drawables in the correct order

        //ground first
        for (int i = 0; i < map.getMAX_X(); i++) {
            for (int j = 0; j < map.getMAX_Y(); j++) {
                draw(spriteBatch, new Ground(i, j));
            }
        }
        if (map.getExit() != null) draw(spriteBatch, map.getExit());

        //walls (destructible and indestructible)
        for (StationaryObject obj : map.getWalls().values()) {
            draw(spriteBatch, obj);
        }
        //bombs
        for (Bomb bomb : map.getBombs()) {
            draw(spriteBatch, bomb);
        }
        //blasts
        for (Blast blast : map.getBlasts()) {
            draw(spriteBatch, blast);
        }


        List<MobileObject> entities = new ArrayList<>(map.getEnemies());
        entities.add(map.getPlayer1());
        if (map.getPlayer2() != null) entities.add(map.getPlayer2());
        entities.sort(Comparator.comparing(MobileObject::getY).reversed());
        for (MobileObject object : entities) {
            draw(spriteBatch, object);
        }

        // Finish drawing, i.e. send the drawn items to the graphics card
        spriteBatch.end();
    }

    private void showGameOverDialog(boolean victory) {
        Dialog dialog = new Dialog("", game.getSkin()) {
            @Override
            protected void result(Object object) {
                if (object.equals(false)) game.finishGame();  // Go to menu when button is clicked
                else {
                    game.restartGame();
                }
            }
        };
        Label messageLabel = new Label(victory ? "VICTORY" : "GAME OVER", game.getSkin(), "bold");
        messageLabel.setFontScale(1.6f);
        messageLabel.setAlignment(Align.center);  // Center the text
        dialog.getContentTable().add(messageLabel).pad(20f).row(); // Add padding
        if (map.getPlayer2() != null) {
            Label player1Label = new Label("Player1 points: " + map.getPlayer1().getPoints(), game.getSkin());
            Label player2Label = new Label("Player2 points: " + map.getPlayer2().getPoints(), game.getSkin());
            dialog.getContentTable().add(player1Label).pad(20f).row();
            dialog.getContentTable().add(player2Label).pad(20f).row();
        }
        else {
            Label player1Label = new Label("Player points: " + map.getPlayer1().getPoints(), game.getSkin());
            dialog.getContentTable().add(player1Label).pad(20f).row();

        }


        TextButton restartButton = new TextButton("Restart", game.getSkin(), "mini");
        TextButton menuButton = new TextButton("Go to Menu", game.getSkin(), "mini");

        // Set button result values
        dialog.setObject(restartButton, true);   // "Yes" button sends true
        dialog.setObject(menuButton, false);   // "No" button sends false
        // Add buttons with spacing between them
        dialog.getButtonTable().add(restartButton).pad(10f).size(200f, 60f); // Set size and padding
        dialog.getButtonTable().add(menuButton).pad(10f).size(200f, 60f);  // Set size and padding
        // Allow keyboard shortcuts
        dialog.key(com.badlogic.gdx.Input.Keys.ENTER, true);
        overlay.setVisible(true);
        dialog.show(stage);
    }

    /**
     * Called when the window is resized.
     * This is where the camera is updated to match the new window size.
     *
     * @param width  The new window width.
     * @param height The new window height.
     */
    @Override
    public void resize(int width, int height) {
        mapCamera.setToOrtho(false);
        hud.resize(width, height);
        stage.getViewport().update(width, height, true);



    }

    // Unused methods from the Screen interface
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Set input processor in show()
        System.out.println(Gdx.input.getInputProcessor());
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
