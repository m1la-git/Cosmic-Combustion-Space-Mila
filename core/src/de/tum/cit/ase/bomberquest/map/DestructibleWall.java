package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * Represents destructible wall tiles in the game map.
 * Destructible walls are a type of {@link StationaryObject} that can be destroyed by explosions.
 * They have a hitbox for collision detection and can optionally contain a {@link WallContentType}
 * which determines what item, if any, is revealed when the wall is destroyed.
 */
public class DestructibleWall extends StationaryObject implements Drawable {
    /**
     * The type of content that this destructible wall may contain when destroyed.
     * Defaults to {@link WallContentType#EMPTY} if no content is specified.
     */
    private final WallContentType wallContentType;
    /**
     * The Box2D body representing the hitbox of the destructible wall.
     * Inherited from {@link StationaryObject}.
     */
    private Body hitbox; // While this is declared, it's actually managed by StationaryObject

    /**
     * Constructs a new DestructibleWall object with no content.
     *
     * @param world The Box2D world where the wall's hitbox will be created.
     * @param x     The x-coordinate of the wall in game units.
     * @param y     The y-coordinate of the wall in game units.
     */
    public DestructibleWall(World world, int x, int y) {
        super(world, x, y, true);
        wallContentType = WallContentType.EMPTY;
    }

    /**
     * Constructs a new DestructibleWall object with specified content.
     *
     * @param world           The Box2D world where the wall's hitbox will be created.
     * @param x               The x-coordinate of the wall in game units.
     * @param y               The y-coordinate of the wall in game units.
     * @param wallContentType The type of content to be contained within this wall.
     */
    public DestructibleWall(World world, int x, int y, WallContentType wallContentType) {
        super(world, x, y, true);
        this.wallContentType = wallContentType;
    }

    /**
     * Gets the content type of this destructible wall.
     *
     * @return The {@link WallContentType} of the wall.
     */
    public WallContentType getWallContentType() {
        return wallContentType;
    }

    /**
     * Gets the current texture region representing the visual appearance of the destructible wall.
     *
     * @return The {@link TextureRegion} the standard destructible wall texture.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.DESTRUCTIBLE_WALL;
    }
}