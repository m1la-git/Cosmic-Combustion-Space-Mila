package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

/**
 * Represents bombs
 * Bombs are Stationary Objects with their own hitbox
 */
public class Bomb extends StationaryObject implements Drawable {
    private float elapsedTime;
    private boolean exploded;
    private final MobileObject owner;

    public Bomb(World world, int x, int y, MobileObject owner) {
        super(world, x, y, true);
        exploded = false;
        this.owner = owner;
    }

    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
        if (this.elapsedTime >= 3.0f && !exploded) {
            exploded = true;
            owner.returnBomb();
        }
    }


    public boolean isExploded() {
        return exploded;
    }


    public void explodeNow() {
        exploded = true;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Animations.BOMB.getKeyFrame(this.elapsedTime, true);
    }

    public MobileObject getOwner() {
        return owner;
    }

}
