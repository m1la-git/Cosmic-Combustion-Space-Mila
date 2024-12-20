package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

public class DestructibleWall extends StationaryObject implements Drawable {
    private boolean isDestroyed = false;
    private Body hitbox;

    public DestructibleWall(World world, int x, int y) {
        super(world, x, y);
    }

    /**
     * Method to destroy the wall.
     * @param world The Box2D world to remove the body from.
     */
    public void destroyWall(World world) {
        if (!isDestroyed) {
            destroy(world);
            isDestroyed = true;
        }
    }
    /**
     * Method to destroy the body.
     * @param world The Box2D world to remove the body from
     */
    public void destroy(World world){
        if(hitbox != null) {
            world.destroyBody(hitbox);
            hitbox = null; // Set the body to null
        }
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if (!isDestroyed) {
            return Textures.DESTRUCTIBLE_WALL;
        }
        return Textures.FLOWERS;
    }
}
