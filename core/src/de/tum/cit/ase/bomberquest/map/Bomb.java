package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

/**
 * Represents a bomb placed in the game.
 * Bombs are stationary objects that have a hitbox and will explode after a certain time.
 * They are placed by {@link MobileObject}s and cause blasts upon explosion.
 */
public class Bomb extends StationaryObject implements Drawable {
    /**
     * The {@link MobileObject} that placed this bomb.
     */
    private final MobileObject owner;
    /**
     * The blast radius of this bomb, determining the size of the explosion.
     */
    private final int blastRadius;
    /**
     * Time elapsed since the bomb was placed. Used to determine when the bomb explodes.
     */
    private float elapsedTime;
    /**
     * Flag indicating whether the bomb has exploded.
     */
    private boolean exploded;

    /**
     * Constructs a new Bomb object.
     *
     * @param world The Box2D world where the bomb's hitbox will be created.
     * @param x     The x-coordinate of the bomb in game units.
     * @param y     The y-coordinate of the bomb in game units.
     * @param owner The {@link MobileObject} that placed this bomb.
     */
    public Bomb(World world, int x, int y, MobileObject owner) {
        super(world, x, y, true);
        exploded = false;
        this.owner = owner;
        blastRadius = owner.getBlastRadius();
    }

    /**
     * Updates the bomb's state every frame.
     * This method increments the elapsed time and checks if the bomb should explode based on the elapsed time.
     * If the bomb explodes, it sets the {@code exploded} flag to true and notifies the owner to return a bomb to their inventory.
     *
     * @param frameTime The time elapsed since the last frame in seconds.
     */
    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
        if (this.elapsedTime >= 3.0f && !exploded) {
            exploded = true;
            owner.returnBomb();
        }
    }

    /**
     * Checks if the bomb has exploded.
     *
     * @return {@code true} if the bomb has exploded, {@code false} otherwise.
     */
    public boolean isExploded() {
        return exploded;
    }

    /**
     * Forces the bomb to explode immediately.
     * Sets the {@code exploded} flag to true and notifies the owner to return a bomb to their inventory.
     */
    public void explodeNow() {
        exploded = true;
        owner.returnBomb();
    }

    /**
     * Gets the current texture region representing the bomb's appearance.
     * The appearance can vary based on the owner of the bomb (e.g., player 1, player 2, or enemy).
     *
     * @return The current {@link TextureRegion} for rendering the bomb.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        if (owner instanceof Player player) {
            if (player.isPlayer1()) return Animations.BOMB_P1.getKeyFrame(elapsedTime, true);
            return Animations.BOMB_P2.getKeyFrame(elapsedTime, true);
        }
        return Animations.BOMB_ENEMY.getKeyFrame(elapsedTime, true);
    }

    /**
     * Gets the {@link MobileObject} that placed this bomb.
     *
     * @return The {@link MobileObject} owner of the bomb.
     */
    public MobileObject getOwner() {
        return owner;
    }

    /**
     * Gets the blast radius of this bomb.
     *
     * @return The blast radius.
     */
    public int getBlastRadius() {
        return blastRadius;
    }
}