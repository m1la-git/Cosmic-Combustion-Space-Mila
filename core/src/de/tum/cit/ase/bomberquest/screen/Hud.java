package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Drawable;
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

    private float hudX;
    private float hudY;

    public Hud(SpriteBatch spriteBatch, BitmapFont font) {
        this.spriteBatch = spriteBatch;
        this.font = font;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false);
        hudX = 256;
        hudY = 192;
    }

    /**
     * Renders the HUD on the screen.
     * This uses a different OrthographicCamera so that the HUD is always fixed on the screen.
     */
    public void render(int blastRadius, int concurrentBombs, int timer, int enemies) {
        // Render from the camera's perspective
        spriteBatch.setProjectionMatrix(camera.combined);
        // Start drawing
        spriteBatch.begin();
        // Draw the HUD elements
        font.draw(spriteBatch, "Press Esc to Pause!" + timer, 0, 25);

        draw(spriteBatch, Textures.HUD, 0, 1);
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
     * Draws this object on the screen.
     * The texture will be scaled by the game scale and the tile size.
     * This should only be called between spriteBatch.begin() and spriteBatch.end(), e.g. in the renderMap() method.
     *
     * @param spriteBatch The SpriteBatch to draw with.
     */
    private static void draw(SpriteBatch spriteBatch, TextureRegion texture, float x, float y) {
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * 4;
        float height = texture.getRegionHeight() * 4;
        spriteBatch.draw(texture, x, y, width, height);
    }

    /**
     * Hud will take 10% of the screen on the bottom
     * @return the height
     */
    public float getHudX() {
        return hudX;
    }
    public float getHudY() {
        return hudY;
    }

}
