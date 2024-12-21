package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;

public class Enemy extends MobileObject implements Drawable {
    public Enemy(World world, float x, float y) {
        super(world, x, y, 1);
    }

    @Override
    public void tick(float frameTime) {

    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return null;
    }
}
