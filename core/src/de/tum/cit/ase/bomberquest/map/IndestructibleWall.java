package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * Represents indestructible wall tiles in the game map.
 * Indestructible walls are a type of {@link StationaryObject} that cannot be destroyed by explosions or any other game mechanic.
 * They serve as permanent obstacles within the game environment, defining level boundaries and creating strategic pathways.
 * These walls have a hitbox, meaning they will block movement and trigger collision events.
 */
public class IndestructibleWall extends StationaryObject implements Drawable {

    /**
     * Constructs a new IndestructibleWall object.
     *
     * @param world The Box2D world where the wall's hitbox will be created.
     * @param x     The x-coordinate of the wall in game units.
     * @param y     The y-coordinate of the wall in game units.
     */
    public IndestructibleWall(World world, int x, int y) {
        super(world, x, y, true);
    }

    /**
     * Gets the current texture region representing the visual appearance of the indestructible wall.
     * This method always returns the standard indestructible wall texture, defined in {@link Textures#INDESTRUCTIBLE_WALL}.
     *
     * @return The {@link TextureRegion} for rendering the indestructible wall.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.INDESTRUCTIBLE_WALL;
    }
}