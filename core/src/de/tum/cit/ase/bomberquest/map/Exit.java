package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

/**
 * Represents the exit tile in the game level.
 * The exit is a {@link StationaryObject} that does not have a physical hitbox, meaning it does not block movement or collisions.
 * It can be in either an open or closed state, visually represented by different animations.
 * The exit is typically the goal of each level, which players need to reach to proceed.
 */
public class Exit extends StationaryObject implements Drawable {
    /**
     * Flag indicating whether the exit is currently open.
     * When open, players can interact with it to complete the level.
     */
    private boolean open;
    /**
     * Elapsed time since the exit was opened, used to animate the opening sequence.
     */
    private float elapsedTime;

    /**
     * Constructs a new Exit object.
     *
     * @param world The Box2D world (not used for Exit as it has no Body, but included for consistent object creation).
     * @param x     The x-coordinate of the exit in game units.
     * @param y     The y-coordinate of the exit in game units.
     */
    public Exit(World world, int x, int y) {
        super(world, x, y, false);
        open = false; // Initially, the exit is closed
    }

    /**
     * Updates the exit's state every frame.
     * Currently, it only updates the elapsed time, which is used for animation purposes.
     *
     * @param frameTime The time elapsed since the last frame in seconds.
     */
    public void tick(float frameTime) {
        elapsedTime += frameTime;
    }

    /**
     * Sets the exit to the open state.
     * This typically happens when all enemies are defeated or level conditions are met.
     * It also resets the elapsed time to start the opening animation from the beginning.
     */
    public void open() {
        open = true;
        elapsedTime = 0; // Reset elapsed time to start opening animation
    }

    /**
     * Checks if the exit is currently open.
     *
     * @return {@code true} if the exit is open, {@code false} otherwise.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Gets the current texture region representing the exit's appearance.
     * The appearance changes based on whether the exit is open or closed, and the animation frame.
     *
     * @return The current {@link TextureRegion} for rendering the exit.
     * Returns the opened animation if {@link #open} is true, otherwise the closed animation.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        if (open) return Animations.EXIT_OPENED.getKeyFrame(elapsedTime, true); // Open exit animation
        return Animations.EXIT_CLOSED.getKeyFrame(elapsedTime, true); // Closed exit animation
    }
}