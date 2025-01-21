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
    private final int blastRadius;

    public Bomb(World world, int x, int y, MobileObject owner) {
        super(world, x, y, true);
        exploded = false;
        this.owner = owner;
        blastRadius = owner.getBlastRadius();
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
        owner.returnBomb();
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if (owner instanceof Player player) {
            if (player.isPlayer1()) return Animations.BOMB_P1.getKeyFrame(elapsedTime, true);
            return Animations.BOMB_P2.getKeyFrame(elapsedTime, true);
        }
        return Animations.BOMB_ENEMY.getKeyFrame(elapsedTime, true);
    }

    public MobileObject getOwner() {
        return owner;
    }
    public int getBlastRadius() {
        return blastRadius;
    }

}
