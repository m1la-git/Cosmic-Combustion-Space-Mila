package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

/**
 * Represents the blasts created by exploding bombs in the game.
 * <p>
 * Blasts are stationary visual effects rendered after a bomb detonates.
 * They do not have a physical body and are purely for visual representation of the explosion.
 * Each blast has a {@link BlastType} which determines its animation and shape.
 */
public class Blast extends StationaryObject implements Drawable {
    /**
     * The type of blast, determining its animation and visual appearance.
     */
    private final BlastType type;
    /**
     * The {@link MobileObject} that placed the bomb which caused this blast. Used to determine player-specific blast animations.
     */
    private final MobileObject owner;
    /**
     * Flag indicating whether the blast animation has finished playing.
     */
    private boolean finished = false;
    /**
     * Time elapsed since the blast was created, used to control the animation frames.
     */
    private float elapsedTime;

    /**
     * Constructs a new Blast object.
     *
     * @param world The Box2D world (not used for blasts as they are stationary and have no body, but included for consistent object creation).
     * @param x     The x-coordinate of the blast in game units.
     * @param y     The y-coordinate of the blast in game units.
     * @param type  The {@link BlastType} of this blast, defining its visual representation.
     * @param owner The {@link MobileObject} that owns this blast (the bomber).
     */
    public Blast(World world, int x, int y, BlastType type, MobileObject owner) {
        super(world, x, y, type == BlastType.WALL);
        this.type = type;
        this.owner = owner;
    }

    /**
     * Updates the blast animation and checks if it has finished.
     * This method should be called every frame to advance the animation.
     *
     * @param frameTime The time elapsed since the last frame in seconds.
     */
    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
        if (this.elapsedTime >= 0.4f && !finished) {
            finished = true;
        }
    }

    /**
     * Checks if the blast animation has finished playing.
     *
     * @return {@code true} if the animation is finished, {@code false} otherwise.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Gets the {@link BlastType} of this blast.
     *
     * @return The {@link BlastType} of the blast.
     */
    public BlastType getType() {
        return type;
    }

    /**
     * Gets the {@link MobileObject} that is the owner of this blast (the bomber).
     *
     * @return The {@link MobileObject} owner of the blast.
     */
    public MobileObject getOwner() {
        return owner;
    }

    /**
     * Gets the current texture region representing the blast's appearance based on its type and animation progress.
     * Chooses the animation based on the blast type and the owner of the blast (for player-specific colors).
     *
     * @return The current {@link TextureRegion} for rendering the blast.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        if (owner instanceof Player player) {
            if (player.isPlayer1()) {
                return switch (type) {
                    case CENTER -> Animations.BLAST_CENTER_P1.getKeyFrame(this.elapsedTime, false);
                    case HORIZONTAL -> Animations.BLAST_HORIZONTAL_P1.getKeyFrame(this.elapsedTime, false);
                    case VERTICAL -> Animations.BLAST_VERTICAL_P1.getKeyFrame(this.elapsedTime, false);
                    case UP -> Animations.BLAST_UP_P1.getKeyFrame(this.elapsedTime, false);
                    case DOWN -> Animations.BLAST_DOWN_P1.getKeyFrame(this.elapsedTime, false);
                    case LEFT -> Animations.BLAST_LEFT_P1.getKeyFrame(this.elapsedTime, false);
                    case RIGHT -> Animations.BLAST_RIGHT_P1.getKeyFrame(this.elapsedTime, false);
                    case WALL -> Animations.BLAST_WALL.getKeyFrame(this.elapsedTime, false);
                };
            }
            return switch (type) {
                case CENTER -> Animations.BLAST_CENTER_P2.getKeyFrame(this.elapsedTime, false);
                case HORIZONTAL -> Animations.BLAST_HORIZONTAL_P2.getKeyFrame(this.elapsedTime, false);
                case VERTICAL -> Animations.BLAST_VERTICAL_P2.getKeyFrame(this.elapsedTime, false);
                case UP -> Animations.BLAST_UP_P2.getKeyFrame(this.elapsedTime, false);
                case DOWN -> Animations.BLAST_DOWN_P2.getKeyFrame(this.elapsedTime, false);
                case LEFT -> Animations.BLAST_LEFT_P2.getKeyFrame(this.elapsedTime, false);
                case RIGHT -> Animations.BLAST_RIGHT_P2.getKeyFrame(this.elapsedTime, false);
                case WALL -> Animations.BLAST_WALL.getKeyFrame(this.elapsedTime, false);
            };
        }
        return switch (type) {
            case CENTER -> Animations.BLAST_CENTER.getKeyFrame(this.elapsedTime, false);
            case HORIZONTAL -> Animations.BLAST_HORIZONTAL.getKeyFrame(this.elapsedTime, false);
            case VERTICAL -> Animations.BLAST_VERTICAL.getKeyFrame(this.elapsedTime, false);
            case UP -> Animations.BLAST_UP.getKeyFrame(this.elapsedTime, false);
            case DOWN -> Animations.BLAST_DOWN.getKeyFrame(this.elapsedTime, false);
            case LEFT -> Animations.BLAST_LEFT.getKeyFrame(this.elapsedTime, false);
            case RIGHT -> Animations.BLAST_RIGHT.getKeyFrame(this.elapsedTime, false);
            case WALL -> Animations.BLAST_WALL.getKeyFrame(this.elapsedTime, false);
        };

    }
}