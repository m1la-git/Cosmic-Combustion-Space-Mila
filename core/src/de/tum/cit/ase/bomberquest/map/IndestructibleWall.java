package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * Represents Indestructible walls.
 * Indestructible walls are Stationary and have a hitbox.
 */
public class IndestructibleWall extends StationaryObject implements Drawable {

    public IndestructibleWall(World world, int x, int y) {
        super(world, x, y, true);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.INDESTRUCTIBLE_WALL;
    }
}
