package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;

public abstract class RemovableStationaryObject implements Drawable {
    protected final int x;
    protected final int y;
    protected Body hitbox;
    protected Fixture hitboxFixture;

    public RemovableStationaryObject(World world, int x, int y) {
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
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = 0x0002;
        fixtureDef.filter.maskBits = 0x0001;
        // Attach the shape to the body as a fixture.
        hitboxFixture = body.createFixture(fixtureDef);
        // We're done with the shape, so we should dispose of it to free up memory.
        box.dispose();
        // Set the chest as the user data of the body, so we can look up the chest from the body later.
        body.setUserData(this);
        return body;
    }

    public Fixture getHitboxFixture() {
        return hitboxFixture;
    }


    @Override
    public abstract TextureRegion getCurrentAppearance();

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
