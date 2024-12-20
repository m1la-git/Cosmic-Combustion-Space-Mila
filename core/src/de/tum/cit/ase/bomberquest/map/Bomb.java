package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

public class Bomb extends StationaryObject implements Drawable {
    private float elapsedTime;

    public Bomb(World world, int x, int y) {
        super(world, x, y);
    }

    public void tick(float frameTime) {
        this.elapsedTime += frameTime;

    }

    public Body getHitbox() {
        return hitbox;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Animations.BOMB.getKeyFrame(this.elapsedTime, true);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
