package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

/**
 * Represents the blasts after the exploded bombs.
 * Blasts are Stationary objects without body
 * They have a blast type and a respective animation
 */
public class Blast extends StationaryObject implements Drawable {
    private final BlastType type;
    private boolean finished = false;
    private float elapsedTime;
    private final MobileObject owner;

    public Blast(World world, int x, int y, BlastType type, MobileObject owner) {
        super(world, x, y, type == BlastType.WALL);
        this.type = type;
        this.owner = owner;
    }

    /**
     * method to update the object and animation.
     * @param frameTime the time since the last frame.
     */
    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
        if (this.elapsedTime >= 0.4f && !finished) {
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public BlastType getType() {
        return type;
    }

    public MobileObject getOwner() {
        return owner;
    }

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
