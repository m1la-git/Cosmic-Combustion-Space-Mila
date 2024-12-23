package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * Represents the exit.
 * Exit is a Stationary object without a hitbox.
 */
public class Exit extends StationaryObject implements Drawable {
    public Exit(World world, int x, int y) {
        super(world, x, y, false);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.EXIT;
    }
}
