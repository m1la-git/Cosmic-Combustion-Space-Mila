package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

public class DestructibleWall extends StationaryObject implements Drawable {
    private boolean isDestroyed = false;
    private Body hitbox;
    private WallContentType wallContentType;


    public DestructibleWall(World world, int x, int y) {
        super(world, x, y, true);
        wallContentType = WallContentType.EMPTY;
    }

    public DestructibleWall(World world, int x, int y, WallContentType wallContentType) {
        super(world, x, y, true);
        this.wallContentType = wallContentType;
    }


    public WallContentType getWallContentType() {
        return wallContentType;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.DESTRUCTIBLE_WALL;
    }
}
