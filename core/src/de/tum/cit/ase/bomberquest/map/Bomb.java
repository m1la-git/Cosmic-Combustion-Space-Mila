package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

public class Bomb extends StationaryObject implements Drawable {
    private float elapsedTime;
    private boolean exploded;

    public Bomb(World world, int x, int y) {
        super(world, x, y, true);
        exploded = false;
    }

    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
        if (this.elapsedTime >= 3.0f && !exploded) {
            exploded = true;
        }
    }


    public boolean isExploded() {
        return exploded;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Animations.BOMB.getKeyFrame(this.elapsedTime, true);
    }

}
