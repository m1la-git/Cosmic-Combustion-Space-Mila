package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

/**
 * Represents Power-up items in the game.
 * <p>
 * Power-ups are {@link StationaryObject}s that players can collect to gain temporary or permanent abilities.
 * Unlike walls or bombs, power-ups do not have a hitbox and are purely interactive items that modify player stats or abilities when collected.
 * Each power-up has a {@link WallContentType} which defines the type of power-up it grants.
 */
public class PowerUp extends StationaryObject implements Drawable {
    /**
     * The type of power-up, defining what effect it has when collected by a player.
     * This is represented by a {@link WallContentType} enum value, excluding {@link WallContentType#EMPTY} and {@link WallContentType#EXIT}.
     */
    private final WallContentType type;
    /**
     * Elapsed time since the power-up was created.
     * Used to animate the power-up's visual appearance.
     */
    private float elapsedTime;

    /**
     * Constructs a new PowerUp object.
     *
     * @param world The Box2D world (not used for power-ups as they have no body, but included for consistent object creation).
     * @param x     The x-coordinate of the power-up in game units.
     * @param y     The y-coordinate of the power-up in game units.
     * @param type  The {@link WallContentType} of this power-up, determining its effect.
     */
    public PowerUp(World world, int x, int y, WallContentType type) {
        super(world, x, y, false);
        this.type = type;
        elapsedTime = 0;
    }

    /**
     * Updates the power-up's state every frame.
     * Currently, it only updates the elapsed time, which is used for animation purposes.
     *
     * @param frameTime The time elapsed since the last frame in seconds.
     */
    public void tick(float frameTime) {
        elapsedTime += frameTime;
    }

    /**
     * Gets the {@link WallContentType} of this power-up.
     *
     * @return The {@link WallContentType} representing the type of power-up.
     */
    public WallContentType getType() {
        return type;
    }

    /**
     * Gets the current texture region representing the power-up's appearance.
     * <p>
     * The appearance is determined by the {@link #type} of the power-up and animated based on {@link #elapsedTime}.
     *
     * @return The current {@link TextureRegion} for rendering the power-up.
     * Returns different animations based on the power-up type.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        return switch (type) {
            case BOMBS_POWER_UP ->
                    Animations.BOMBS_POWER_UP.getKeyFrame(elapsedTime, true); // Animation for bombs power-up
            case FLAMES_POWER_UP ->
                    Animations.FLAMES_POWER_UP.getKeyFrame(elapsedTime, true); // Animation for flames power-up
            case SPEED_POWER_UP ->
                    Animations.SPEED_POWER_UP.getKeyFrame(elapsedTime, true); // Animation for speed power-up
            case WALLPASS_POWER_UP ->
                    Animations.WALLPASS_POWER_UP.getKeyFrame(elapsedTime, true); // Animation for wallpass power-up
            case BOMBPASS_POWER_UP ->
                    Animations.BOMBPASS_POWER_UP.getKeyFrame(elapsedTime, true); // Animation for bombpass power-up
            case FLAMEPASS_POWER_UP ->
                    Animations.FLAMEPASS_POWER_UP.getKeyFrame(elapsedTime, true); // Animation for flamepass power-up
            default -> null; // Returns null for unknown or unsupported power-up types
        };
    }
}