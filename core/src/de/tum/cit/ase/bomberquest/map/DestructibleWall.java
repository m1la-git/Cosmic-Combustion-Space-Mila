package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

public class DestructibleWall implements Drawable {

    private final int x;
    private final int y;
    private boolean isDestroyed = false;
    private Body hitbox;

    public DestructibleWall(World world, int x, int y) {
        this.x = x;
        this.y = y;
        hitbox = createHitbox(world);
    }

    /**
     * Create a Box2D body for the chest.
     * @param world The Box2D world to add the body to.
     */
    private Body createHitbox(World world) {
        // BodyDef is like a blueprint for the movement properties of the body.
        BodyDef bodyDef = new BodyDef();
        // Static bodies never move, but static bodies can collide with them.
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set the initial position of the body.
        bodyDef.position.set(this.x, this.y);
        // Create the body in the world using the body definition.
        Body body = world.createBody(bodyDef);
        // Now we need to give the body a shape so the physics engine knows how to collide with it.
        // We'll use a polygon shape for the chest.
        PolygonShape box = new PolygonShape();
        // Make the polygon a square with a side length of 1 tile.
        box.setAsBox(0.5f, 0.5f);
        // Attach the shape to the body as a fixture.
        body.createFixture(box, 1.0f);
        // We're done with the shape, so we should dispose of it to free up memory.
        box.dispose();
        // Set the wall as the user data of the body, so we can look up the wall from the body later.
        body.setUserData(this);
        return body;
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
    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

}
