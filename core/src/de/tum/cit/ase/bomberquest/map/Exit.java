package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * Represents the exit.
 * Exit is a Stationary object without a hitbox.
 */
public class Exit extends StationaryObject implements Drawable {
    private boolean open;
    private float elapsedTime;
    public Exit(World world, int x, int y) {
        super(world, x, y, false);
        open = false;
    }

    public void tick(float frameTime) {
        elapsedTime += frameTime;
    }

    public void open() {
        open = true;
        elapsedTime = 0;
    }
    public boolean isOpen() {
        return open;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if (open) return Animations.EXIT_OPENED.getKeyFrame(elapsedTime, true);
        return Animations.EXIT_CLOSED.getKeyFrame(elapsedTime, true);
    }
}
