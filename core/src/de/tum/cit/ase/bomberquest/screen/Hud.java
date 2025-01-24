package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.map.Player;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * A Heads-Up Display (HUD) that displays game information on the screen.
 * The HUD is a fixed screen element that overlays the game world and presents
 * essential information to the player, such as score, timer, number of enemies,
 * player stats (like bomb count and blast radius), and game status indicators.
 * It utilizes a separate {@link OrthographicCamera} to remain static on the screen,
 * regardless of the game world camera's position.
 */
public class Hud {

    /**
     * The fixed x-coordinate for the HUD background dimensions.
     */
    private static final float HUD_X = 180;
    /**
     * The fixed y-coordinate for the HUD background dimensions.
     */
    private static final float HUD_Y = 240;
    /**
     * Scale factor applied to HUD elements for visual consistency.
     */
    private static final float SCALE = 3.5f;
    /**
     * The SpriteBatch used to draw the HUD elements.
     * This SpriteBatch is shared with the {@link GameScreen} to ensure efficient rendering
     * and avoid unnecessary resource creation.
     */
    private final SpriteBatch spriteBatch;
    /**
     * The font used to render standard text on the HUD.
     * This font is used for displaying scores, timers, and other numerical information.
     */
    private final BitmapFont font;
    /**
     * A bold version of the font used for emphasizing text on the HUD.
     * Typically used for player names or headings to make them stand out.
     */
    private final BitmapFont bold;
    /**
     * The OrthographicCamera dedicated to rendering the HUD.
     * This camera is configured to cover the entire screen and is not affected by the game world camera,
     * ensuring the HUD stays fixed in place.
     */
    private final OrthographicCamera camera;
    /**
     * Elapsed time for animations within the HUD, such as the exit sign.
     */
    private float elapsedTime;

    /**
     * Constructor for the Hud.
     * Initializes the HUD with a SpriteBatch, fonts, and sets up the HUD camera.
     *
     * @param spriteBatch The SpriteBatch to be used for drawing HUD elements.
     * @param font        The BitmapFont for standard text in the HUD.
     * @param bold        The BitmapFont for bold text in the HUD.
     */
    public Hud(SpriteBatch spriteBatch, BitmapFont font, BitmapFont bold) {
        this.spriteBatch = spriteBatch;
        this.font = font;
        this.bold = bold;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false); // Sets the camera to orthographic projection, covering the entire screen
        elapsedTime = 0;
    }


    /**
     * Draws a {@link TextureRegion} on the screen using the HUD's {@link SpriteBatch}.
     * The texture will be scaled by the HUD's scale factor.
     * This method is intended for internal use within the Hud class, to draw individual HUD elements.
     *
     * @param spriteBatch The SpriteBatch to draw with (should be the HUD's spriteBatch).
     * @param texture     The TextureRegion to be drawn.
     * @param x           The x-coordinate to draw the texture at.
     * @param y           The y-coordinate to draw the texture at.
     */
    private static void draw(SpriteBatch spriteBatch, TextureRegion texture, float x, float y) {
        // Scale everything by the HUD scale factor for consistent size
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }

    /**
     * Draws the HUD elements specific to a player (score, blast radius, bomb count).
     * This method is used for rendering the player-specific section of the HUD,
     * including icons and numerical values representing player stats.
     *
     * @param spriteBatch The SpriteBatch to draw with (should be the HUD's spriteBatch).
     * @param player      The Player object whose stats are to be displayed.
     * @param startX      The starting x-coordinate for drawing the player's HUD elements.
     */
    private void drawHudPlayer(SpriteBatch spriteBatch, Player player, float startX) {
        draw(spriteBatch, Textures.STAR, startX, ((HUD_Y - 9) / 3 * 2) + 15); // Score icon
        draw(spriteBatch, Textures.BLAST_HUD, startX, ((HUD_Y - 9) / 3) + 15); // Blast radius icon
        draw(spriteBatch, Textures.BOMB_HUD, startX, 15); // Bomb count icon
        font.draw(spriteBatch, player.getPoints() + "", startX + 16 * SCALE + 15, ((HUD_Y - 9) / 3 * 2) + 57); // Player score value
        font.draw(spriteBatch, player.getBlastRadius() + "", startX + 16 * SCALE + 15, ((HUD_Y - 9) / 3) + 57); // Player blast radius value
        font.draw(spriteBatch, player.getConcurrentBombs() + "", startX + 16 * SCALE + 15, 57); // Player bomb count value
        bold.draw(spriteBatch, player.getName(), startX - 5, HUD_Y + 30); // Player name in bold
    }

    /**
     * Renders the entire HUD on the screen.
     * This method draws the background, player-specific HUDs, and the main HUD elements
     * such as timer, enemy count, and exit status.
     *
     * @param player1   The Player object for player 1, whose HUD will be rendered.
     * @param player2   The Player object for player 2, whose HUD will be rendered (can be null for single player).
     * @param timer     The current game timer value to be displayed.
     * @param enemies   The number of enemies remaining in the game to be displayed.
     * @param exitOpen  A boolean indicating if the exit is open, to display the correct exit status icon.
     * @param frameTime The time elapsed since the last frame, used for animating HUD elements.
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
        spriteBatch.draw(texture, 0, 0, HUD_X, HUD_Y); // Draw HUD background for player 1
        drawHudPlayer(spriteBatch, player1, 17); // Draw player 1 specific HUD elements

        //player2 hud
        if (player2 != null) {
            spriteBatch.draw(texture, maxX - HUD_X, 0, HUD_X, HUD_Y); // Draw HUD background for player 2 if exists
            drawHudPlayer(spriteBatch, player2, maxX - HUD_X + 17); // Draw player 2 specific HUD elements
        }

        //main hud
        spriteBatch.draw(texture, 0, maxY - HUD_Y, HUD_X, HUD_Y); // Draw main HUD background
        draw(spriteBatch, Textures.TIMER, 17, ((HUD_Y - 9) / 3 * 2) + maxY - HUD_Y + 15); // Timer icon
        draw(spriteBatch, Textures.ENEMY_HUD, 17, ((HUD_Y - 9) / 3) + maxY - HUD_Y + 15); // Enemy count icon
        font.draw(spriteBatch, timer + "", 17 + 16 * SCALE + 15, ((HUD_Y - 9) / 3 * 2) + maxY - HUD_Y + 57); // Timer value
        font.draw(spriteBatch, enemies + "", 17 + 16 * SCALE + 15, ((HUD_Y - 9) / 3) + maxY - HUD_Y + 57); // Enemy count value
        if (exitOpen) {
            draw(spriteBatch, Animations.EXIT_OPENED.getKeyFrame(elapsedTime, true), 18, 15 + maxY - HUD_Y); // Animated open exit icon
            draw(spriteBatch, Textures.CHECK_MARK, 18 + 16 * SCALE + 12, 15 + maxY - HUD_Y); // Check mark icon for open exit
        } else {
            draw(spriteBatch, Animations.EXIT_CLOSED.getKeyFrame(elapsedTime, true), 18, 15 + maxY - HUD_Y); // Animated closed exit icon
            draw(spriteBatch, Textures.CROSS_MARK, 18 + 16 * SCALE + 12, 15 + maxY - HUD_Y); // Cross mark icon for closed exit
        }


        // Finish drawing
        spriteBatch.end();
    }

    /**
     * Resizes the HUD when the screen size changes.
     * This method updates the HUD's camera viewport to match the new screen dimensions,
     * ensuring the HUD scales correctly and remains fixed on the screen.
     *
     * @param width  The new width of the screen in pixels.
     * @param height The new height of the screen in pixels.
     */
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height); // Update camera to new viewport dimensions
    }

    /**
     * Gets the width of the HUD background.
     *
     * @return The width of the HUD in pixels.
     */
    public float getHudX() {
        return HUD_X;
    }

    /**
     * Gets the height of the HUD background.
     *
     * @return The height of the HUD in pixels.
     */
    public float getHudY() {
        return HUD_Y;
    }

    /**
     * Gets the OrthographicCamera used for rendering the HUD.
     *
     * @return The {@link OrthographicCamera} for the HUD.
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

}