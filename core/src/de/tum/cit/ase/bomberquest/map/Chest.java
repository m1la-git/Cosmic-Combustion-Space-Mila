package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * A chest is a static object with a hitbox, so the player cannot walk through it.
 */
public class Chest extends StationaryObjects  implements Drawable {
    
    // We would normally get the position from the hitbox, but since we don't need to move the chest, we can store the position directly.
    
    /**
     * Create a chest at the given position.
     * @param world The Box2D world to add the chest's hitbox to.
     * @param x The X position.
     * @param y The Y position.
     */
    public Chest(World world, float x, float y) {
        super(world, x, y);
    }
    


    
    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.CHEST;
    }


}
