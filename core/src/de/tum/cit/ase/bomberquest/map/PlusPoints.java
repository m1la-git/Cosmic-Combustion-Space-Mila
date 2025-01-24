package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

/**
 * Represents a visual effect for displaying points gained by a player.
 * This class is used to show a "+100" points animation when a player defeats an enemy.
 * It is a short-lived visual element that appears at the enemy's death location and then fades away.
 * This provides visual feedback to the player that they have earned points.
 */
public class PlusPoints implements Drawable {
    /**
     * The x-coordinate in world units where the "+100" effect is displayed.
     */
    private final float x;
    /**
     * The y-coordinate in world units where the "+100" effect is displayed.
     */
    private final float y;
    /**
     * Flag to determine which player's color scheme to use for the "+100" effect.
     * {@code true} for Player 1's colors, {@code false} for Player 2's colors.
     */
    private final boolean player1;
    /**
     * Elapsed time since the effect started, used to control the animation and lifespan of the effect.
     */
    private float elapsedTime = 0;
    /**
     * Flag indicating whether the "+100" effect animation has finished and the effect should be removed.
     */
    private boolean finished = false;

    /**
     * Constructs a new PlusPoints effect.
     *
     * @param x       The x-coordinate in world units for the effect's position.
     * @param y       The y-coordinate in world units for the effect's position.
     * @param player1 {@code true} if the points are for Player 1, {@code false} for Player 2.
     *                This parameter determines the color of the points display.
     */
    public PlusPoints(float x, float y, boolean player1) {
        this.x = x;
        this.y = y;
        this.player1 = player1;
    }

    /**
     * Updates the state of the PlusPoints effect every frame.
     * This method increments the elapsed time and checks if the effect's lifespan has exceeded 1 second.
     * If 1 second has passed, it sets the {@code finished} flag to true, indicating that the effect should be removed.
     *
     * @param frameTime The time elapsed since the last frame in seconds.
     */
    public void tick(float frameTime) {
        elapsedTime += frameTime;
        if (elapsedTime >= 1f) {
            finished = true;
        }
    }

    /**
     * Gets the current texture region representing the "+100" points effect.
     * The texture region is determined by the player who earned the points (Player 1 or Player 2)
     * and the current animation frame based on the elapsed time.
     *
     * @return The current {@link TextureRegion} for rendering the "+100" effect.
     * Uses different animations based on whether it's for Player 1 or Player 2.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        if (player1) return Animations.PLUS_POINTS_P1.getKeyFrame(elapsedTime); // Animation for Player 1 points
        return Animations.PLUS_POINTS_P2.getKeyFrame(elapsedTime); // Animation for Player 2 points
    }

    /**
     * Gets the x-coordinate of the PlusPoints effect.
     *
     * @return The x-coordinate in world units.
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the PlusPoints effect.
     *
     * @return The y-coordinate in world units.
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     * Checks if the PlusPoints effect animation has finished.
     *
     * @return {@code true} if the effect is finished and should be removed, {@code false} otherwise.
     */
    public boolean isFinished() {
        return finished;
    }

}