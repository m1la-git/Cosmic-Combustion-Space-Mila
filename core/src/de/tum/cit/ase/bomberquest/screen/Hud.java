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

    private static final float HUD_X = 180;
    private static final float HUD_Y = 240;
    private static final float SCALE = 3.5f;
    private float elapsedTime;

    public Hud(SpriteBatch spriteBatch, BitmapFont font) {
        this.spriteBatch = spriteBatch;
        this.font = font;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false);
        elapsedTime = 0;
    }



    /**
     * Draws this object on the screen.
     * The texture will be scaled by the game scale.
     * This should only be called between spriteBatch.begin() and spriteBatch.end()
     * @param spriteBatch The SpriteBatch to draw with.
     */
    private static void draw(SpriteBatch spriteBatch, TextureRegion texture, float x, float y) {
        // Scale everything
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }

    private void drawHudPlayer(SpriteBatch spriteBatch, Player player, float startX) {
        draw(spriteBatch, Textures.STAR, startX, ((HUD_Y - 9) / 3 * 2) + 15);
        draw(spriteBatch, Textures.BLAST_HUD, startX, ((HUD_Y - 9) / 3) + 15);
        draw(spriteBatch, Textures.BOMB_HUD, startX, 15);
        font.draw(spriteBatch, player.getPoints() + "", startX + 16 * SCALE + 15, ((HUD_Y - 9) / 3 * 2) + 57);
        font.draw(spriteBatch, player.getBlastRadius() + "", startX + 16 * SCALE + 15, ((HUD_Y - 9) / 3) + 57);
        font.draw(spriteBatch, player.getConcurrentBombs() + "", startX + 16 * SCALE + 15,  57);
        String playerText;
        if (player.isPlayer1()) playerText = "Player 1";
        else playerText = "Player 2";
        font.draw(spriteBatch, playerText, startX + 10,  HUD_Y + 30);

    }
    private void drawHudMain(SpriteBatch spriteBatch, float startX) {

    }

    /**
     * Renders the HUD_BACKGROUND on the screen.
     * This uses a different OrthographicCamera so that the HUD_BACKGROUND is always fixed on the screen.
     */
    public void render(Player player1, Player player2, int timer, int enemies, boolean exitOpen, float frameTime) {
        elapsedTime += frameTime;
        // Render from the camera's perspective
        spriteBatch.setProjectionMatrix(camera.combined);
        // Start drawing
        spriteBatch.begin();

        TextureRegion texture = Textures.HUD;
        float maxX = camera.viewportWidth;
        float maxY = camera.viewportHeight;

        //player1 hud
        spriteBatch.draw(texture, 0, 0, HUD_X, HUD_Y);
        drawHudPlayer(spriteBatch, player1, 17);

        //player2 hud
        if (player2 != null) {
            spriteBatch.draw(texture, maxX - HUD_X, 0, HUD_X, HUD_Y);
            drawHudPlayer(spriteBatch, player2, maxX - HUD_X + 17);
        }

        //main hud
        spriteBatch.draw(texture, 0, maxY - HUD_Y, HUD_X, HUD_Y);
        draw(spriteBatch, Textures.TIMER, 17, ((HUD_Y - 9) / 3 * 2) + maxY - HUD_Y + 15);
        draw(spriteBatch, Textures.ENEMY_HUD, 17, ((HUD_Y - 9) / 3) + maxY - HUD_Y + 15);
        font.draw(spriteBatch, timer + "", 17 + 16 * SCALE + 15, ((HUD_Y - 9) / 3 * 2) + maxY - HUD_Y + 57);
        font.draw(spriteBatch, enemies + "", 17 + 16 * SCALE + 15, ((HUD_Y - 9) / 3) + maxY - HUD_Y + 57);
        if (exitOpen) {
            draw(spriteBatch, Animations.EXIT_OPENED.getKeyFrame(elapsedTime, true), 18, 15 + maxY - HUD_Y);
            draw(spriteBatch, Textures.CHECK_MARK, 18 + 16 * SCALE + 12, 15 + maxY - HUD_Y);
        }
        else {
            draw(spriteBatch, Animations.EXIT_CLOSED.getKeyFrame(elapsedTime, true), 18, 15 + maxY - HUD_Y);
            draw(spriteBatch, Textures.CROSS_MARK, 18 + 16 * SCALE + 12, 15 + maxY - HUD_Y);
        }






        // Finish drawing
        spriteBatch.end();
    }

    /**
     * Resizes the HUD_BACKGROUND when the screen size changes.
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
        return HUD_X;
    }

    public float getHudY() {
        return HUD_Y;
    }
    public OrthographicCamera getCamera() {
        return camera;
    }

}
