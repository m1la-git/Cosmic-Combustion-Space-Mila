package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.map.Player;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * A Heads-Up Display (HUD) that displays information on the screen.
 * It uses a separate camera so that it is always fixed on the screen.
 */
public class Hud {

    /**
     * The SpriteBatch used to draw the HUD. This is the same as the one used in the GameScreen.
     */
    private final SpriteBatch spriteBatch;
    /**
     * The font used to draw text on the screen.
     */
    private final BitmapFont font;
    /**
     * The camera used to render the HUD.
     */
    private final OrthographicCamera camera;

    private final float hudX;
    private final float hudY;
    private float elapsedTime;

    public Hud(SpriteBatch spriteBatch, BitmapFont font) {
        this.spriteBatch = spriteBatch;
        this.font = font;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false);
        hudX = 16*3*4.5f;
        hudY = 16*6*4.5f;
        elapsedTime = 0;
    }

    /**
     * Draws this object on the screen.
     * The texture will be scaled by the game scale and the tile size.
     * This should only be called between spriteBatch.begin() and spriteBatch.end(), e.g. in the renderMap() method.
     *
     * @param spriteBatch The SpriteBatch to draw with.
     */
    private static void draw(SpriteBatch spriteBatch, TextureRegion texture, float x, float y, float scale) {
        // Scale everything
        float width = texture.getRegionWidth() * scale;
        float height = texture.getRegionHeight() * scale;
        spriteBatch.draw(texture, x, y, width, height);
    }

    /**
     * Renders the HUD on the screen.
     * This uses a different OrthographicCamera so that the HUD is always fixed on the screen.
     */
    public void render(Player player, int timer, int enemies, float frameTime) {
        elapsedTime += frameTime;
        // Render from the camera's perspective
        spriteBatch.setProjectionMatrix(camera.combined);
        // Start drawing
        spriteBatch.begin();
        // Draw the HUD elements
        draw(spriteBatch, Textures.HUD, 0, 0, 4.5f);
        draw(spriteBatch, Textures.TIMER, 15, (float) (hudY * 0.8) + 15, 3.5f);
        draw(spriteBatch, Textures.BLAST_HUD, 15, (float) (hudY * 0.6) + 15, 3.5f);
        draw(spriteBatch, Textures.BOMB_HUD, 15, (float) (hudY * 0.4) + 15, 3.5f);
        draw(spriteBatch, Textures.ENEMY_HUD, 15, (float) (hudY * 0.2) + 15, 3.5f);
        draw(spriteBatch, Animations.EXIT_CLOSED.getKeyFrame(elapsedTime, true), 15, 15, 3.5f);
        font.draw(spriteBatch, "" + timer, 48, hudY - 25);
        font.draw(spriteBatch, "" + player.getBlastRadius(), 48, (float) (hudY * 0.8) - 25);
        font.draw(spriteBatch, "" + player.getConcurrentBombs(), 48, (float) (hudY * 0.6) - 25);
        font.draw(spriteBatch, "" + enemies, 48, (float) (hudY * 0.4) - 25);
        font.draw(spriteBatch, "Press Esc to Pause!", 0, camera.viewportHeight - 10);
        font.draw(spriteBatch, "" + player.getPoints(), 0, camera.viewportHeight - 20);

        // Finish drawing
        spriteBatch.end();
    }

    /**
     * Resizes the HUD when the screen size changes.
     * This is called when the window is resized.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    /**
     * Hud will take 10% of the screen on the bottom
     *
     * @return the height
     */
    public float getHudX() {
        return hudX;
    }

    public float getHudY() {
        return hudY;
    }
    public OrthographicCamera getCamera() {
        return camera;
    }

}
