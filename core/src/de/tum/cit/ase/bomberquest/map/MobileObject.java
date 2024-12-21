package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;

public abstract class MobileObject implements Drawable {
    /**
     * The Box2D hitbox of the player, used for position and collision detection.
     */
    private final Body hitbox;
    private int speed;
    /**
     * Total time elapsed since the game started. We use this for calculating the player movement and animating it.
     */
    private float elapsedTime;
    private DirectionType direction;

    public MobileObject(World world, float x, float y, int speed, float radius, BodyDef.BodyType bodyType) {
        this.hitbox = createHitbox(world, x, y, radius, bodyType);
        this.speed = speed;
        direction = DirectionType.NONE;
    }

    /**
     * Creates a Box2D body for the player.
     * This is what the physics engine uses to move the player around and detect collisions with other bodies.
     *
     * @param world  The Box2D world to add the body to.
     * @param startX The initial X position.
     * @param startY The initial Y position.
     * @return The created body.
     */
    private Body createHitbox(World world, float startX, float startY, float radius, BodyDef.BodyType bodyType) {
        // BodyDef is like a blueprint for the movement properties of the body.
        BodyDef bodyDef = new BodyDef();
        // Dynamic bodies are affected by forces and collisions.
        bodyDef.type = bodyType;
        // Set the initial position of the body.
        bodyDef.position.set(startX, startY);
        // Create the body in the world using the body definition.
        Body body = world.createBody(bodyDef);
        // Now we need to give the body a shape so the physics engine knows how to collide with it.
        // We'll use a circle shape for the player.
        CircleShape circle = new CircleShape();
        // Give the circle a radius of 0.3 tiles (the player is 0.6 tiles wide).
        circle.setRadius(radius);
        // Attach the shape to the body as a fixture.
        // Bodies can have multiple fixtures, but we only need one for the player.
        Fixture fixture = body.createFixture(circle, 1.0f);
        fixture.setFilterData(new Filter());
        // We're done with the shape, so we should dispose of it to free up memory.
        circle.dispose();
        // Set the player as the user data of the body, so we can look up the player from the body later.
        body.setUserData(this);
        return body;
    }

    public abstract void tick(float frameTime);

    public void increaseElapsedTime(float frameTime) {
        elapsedTime += frameTime;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }


    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Body getHitbox() {
        return hitbox;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public DirectionType getDirection() {
        return direction;
    }

    public void setDirection(DirectionType direction) {
        this.direction = direction;
    }

    public int getCellX() {
        return Math.round(getX());
    }

    public int getCellY() {
        return Math.round(getY());
    }

    @Override
    public abstract TextureRegion getCurrentAppearance();

    @Override
    public float getX() {
        // The x-coordinate of the player is the x-coordinate of the hitbox (this can change every frame).
        return hitbox.getPosition().x;
    }

    @Override
    public float getY() {
        // The y-coordinate of the player is the y-coordinate of the hitbox (this can change every frame).
        return hitbox.getPosition().y;
    }

}
