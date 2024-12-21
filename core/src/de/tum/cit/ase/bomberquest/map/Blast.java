package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

public class Blast extends StationaryObject implements Drawable {
    private boolean finished = false;
    private float elapsedTime;
    private final BlastType type;

    public Blast(World world, int x, int y, BlastType type) {
        super(world, x, y, type == BlastType.WALL);
        this.type = type;
    }

    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
        if (this.elapsedTime >= 0.6f && !finished) {
            finished = true;
        }
    }
    public boolean isFinished() {
        return finished;
    }

    public BlastType getType() {
        return type;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
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
