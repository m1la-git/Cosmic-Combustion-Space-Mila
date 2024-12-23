package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.audio.BackgroundTrack;
import de.tum.cit.ase.bomberquest.map.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;

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
    private final SpriteBatch spriteBatch;
    private final GameMap map;
    private final Hud hud;
    private final OrthographicCamera mapCamera;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(BomberQuestGame game) {
        this.game = game;
        this.spriteBatch = game.getSpriteBatch();
        this.map = game.getMap();
        this.hud = new Hud(spriteBatch, game.getSkin().getFont("font"));
        // Create and configure the camera for the game view
        this.mapCamera = new OrthographicCamera();
        this.mapCamera.setToOrtho(false);
        //play background music
        BackgroundTrack.BACKGROUND.play();
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
     *
     * @param deltaTime The time in seconds since the last render.
     */
    @Override
    public void render(float deltaTime) {
        // Check for escape key press to go back to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || map.getPlayer().isDead() || map.isVictory()) {
            game.goToMenu();
        }

        // Clear the previous frame from the screen, or else the picture smears
        ScreenUtils.clear(Color.BLACK);

        // Cap frame time to 250ms to prevent spiral of death
        float frameTime = Math.min(deltaTime, 0.250f);

        // Update the map state
        map.tick(frameTime);

        // Update the camera
        updateCamera();

        // Render the map on the screen
        renderMap();

        // Render the HUD on the screen
        hud.render(map.getBlastRadius(), map.getConcurrentBombs(), map.getTimer(), map.getNumberOfEnemies());
    }


    /**
     * Updates the camera to follow the player while keeping it within map boundaries.
     * If the map is smaller than the screen, the camera is centered on the map.
     */
    private void updateCamera() {
        float viewportWidth = mapCamera.viewportWidth / 2f;  // Half-width of the viewport
        float viewportHeight = mapCamera.viewportHeight / 2f; // Half-height of the viewport

        float mapWidthPx = (map.getMAX_X() + 1) * TILE_SIZE_PX * SCALE;
        float mapHeightPx = (map.getMAX_Y() + 1) * TILE_SIZE_PX * SCALE;

        // Determine camera boundaries
        float maxCameraX = Math.max(viewportWidth, mapWidthPx - viewportWidth); // Max X position camera can move to
        float maxCameraY = Math.max(viewportHeight, mapHeightPx - viewportHeight); // Max Y position camera can move to

        float minCameraX = Math.max(viewportWidth, mapWidthPx / 2f) - hud.getHudX(); // Min X position camera can move to
        float minCameraY = viewportHeight - hud.getHudY(); // Min Y position camera can move to

        // Get player's position
        float playerX = map.getPlayer().getX() * TILE_SIZE_PX * SCALE;
        float playerY = map.getPlayer().getY() * TILE_SIZE_PX * SCALE;

        // Clamp the camera position to ensure it stays within the map bounds or centers for small maps
        float cameraX = (mapWidthPx <= mapCamera.viewportWidth - hud.getHudX()) ? mapWidthPx / 2f : Math.max(minCameraX, Math.min(playerX, maxCameraX));
        float cameraY = (mapHeightPx <= mapCamera.viewportHeight - hud.getHudY()) ? mapHeightPx / 2f : Math.max(minCameraY, Math.min(playerY, maxCameraY));

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
        //enemies
        for (Enemy enemy: map.getEnemies()) {
            draw(spriteBatch, enemy);
        }

        //player
        draw(spriteBatch, map.getPlayer());

        // Finish drawing, i.e. send the drawn items to the graphics card
        spriteBatch.end();
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

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}
