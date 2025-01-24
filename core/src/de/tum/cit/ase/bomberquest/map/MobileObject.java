package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents all Mobile Objects.
 * Abstract superclass for players and enemies.
 * Mobile objects are entities in the game that can move and interact with the game world.
 */
public abstract class MobileObject implements Drawable {
    /**
     * Set of Box2D bodies representing bombs that this object is currently ignoring collisions with.
     * This is used to prevent immediate self-destruction when placing a bomb.
     */
    private final Set<Body> ignoredBombs;
    /**
     * The Box2D hitbox of the mobile object, used for position, movement and collision detection.
     */
    private Body hitbox;
    /**
     * Movement speed of the object in Box2D units per second.
     */
    private int speed;
    /**
     * Total time elapsed in seconds since the game started.
     * Used for animation timing and movement calculations.
     */
    private float elapsedTime;
    /**
     * The current direction the object is attempting to move in.
     */
    private DirectionType direction;
    /**
     * Flag indicating if the object is currently alive and active in the game.
     * If false, the object is in its death animation or considered dead.
     */
    private boolean alive;
    /**
     * Flag indicating if the object has finished its death animation and is completely dead.
     * Once dead, the object is generally removed from the game world.
     */
    private boolean dead;
    /**
     * X-coordinate where the object died, used for rendering death animation at the correct location.
     */
    private float deathX;
    /**
     * Y-coordinate where the object died, used for rendering death animation at the correct location.
     */
    private float deathY;
    /**
     * The maximum number of bombs this object can place concurrently.
     */
    private int concurrentBombs;
    /**
     * The blast radius of bombs placed by this object, determining the area of effect of the explosion.
     */
    private int blastRadius;
    /**
     * The number of bombs the object currently has available to place.
     * This number decreases when a bomb is placed and increases when a placed bomb explodes.
     */
    private int bombsNow;
    /**
     * Stores the last direction the object was moving in.
     * This is used for animation purposes, especially when the object stops moving.
     */
    private DirectionType lastDirection = DirectionType.NONE;

    /**
     * Constructor for a MobileObject.
     * Initializes the mobile object with a Box2D body, speed, and initial stats.
     *
     * @param world  The Box2D world to which the hitbox will be added.
     * @param x      The starting x-coordinate of the object in world units.
     * @param y      The starting y-coordinate of the object in world units.
     * @param speed  The movement speed of the object.
     * @param radius The radius of the object's hitbox.
     */
    public MobileObject(World world, float x, float y, int speed, float radius) {
        this.hitbox = createHitbox(world, x, y, radius);
        this.speed = speed;
        direction = DirectionType.NONE;
        alive = true;
        dead = false;
        concurrentBombs = 1;
        blastRadius = 1;
        bombsNow = 1;
        ignoredBombs = new HashSet<>();
    }

    /**
     * Creates a Box2D body for the mobile object.
     * This body is used by the physics engine for movement and collision detection.
     *
     * @param world  The Box2D world to add the body to.
     * @param startX The initial X position of the body.
     * @param startY The initial Y position of the body.
     * @param radius The radius of the circular hitbox.
     * @return The created Box2D body.
     */
    private Body createHitbox(World world, float startX, float startY, float radius) {
        // BodyDef is like a blueprint for the movement properties of the body.
        BodyDef bodyDef = new BodyDef();
        // Dynamic bodies are affected by forces and collisions.
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set the initial position of the body.
        bodyDef.position.set(startX, startY);
        // Create the body in the world using the body definition.
        Body body = world.createBody(bodyDef);
        // Now we need to give the body a shape so the physics engine knows how to collide with it.
        // We'll use a circle shape for the player.
        CircleShape circle = new CircleShape();
        // Give the circle a radius.
        circle.setRadius(radius);
        // Attach the shape to the body as a fixture.
        // Bodies can have multiple fixtures, but we only need one for the player or the enemy.
        Fixture fixture = body.createFixture(circle, 1.0f);
        fixture.setFilterData(new Filter());
        // We're done with the shape, so we should dispose of it to free up memory.
        circle.dispose();
        // Set the object as the user data of the body, so we can look up the object from the body later.
        body.setUserData(this);
        return body;
    }

    /**
     * Abstract method to update the object's state every frame.
     * Subclasses must implement this to define specific update logic (e.g., AI, animation).
     *
     * @param frameTime The time elapsed since the last frame in seconds.
     */
    public abstract void tick(float frameTime);

    /**
     * Sets the linear velocity of the hitbox based on the current direction and speed of the object.
     * This method is called in the {@link #tick(float)} method to move the object.
     */
    protected void moveInDirection() {
        if (direction != DirectionType.NONE) setLastDirection(direction);
        // Update the hitbox's position based on direction and speed
        switch (direction) {
            case UP:
                getHitbox().setLinearVelocity(0, getSpeed());
                break;
            case DOWN:
                getHitbox().setLinearVelocity(0, -getSpeed());
                break;
            case LEFT:
                getHitbox().setLinearVelocity(-getSpeed(), 0);
                break;
            case RIGHT:
                getHitbox().setLinearVelocity(getSpeed(), 0);
                break;
            case NONE:
                getHitbox().setLinearVelocity(0, 0);
                break;
        }
    }

    /**
     * Increases the elapsed game time.
     *
     * @param frameTime The time elapsed since the last frame in seconds.
     */
    public void increaseElapsedTime(float frameTime) {
        elapsedTime += frameTime;
    }

    /**
     * Handles the death of the object.
     * Sets the object to not alive, saves death coordinates, destroys the hitbox immediately and resets elapsed time.
     *
     * @param world The Box2D world where the hitbox is stored.
     */
    public void death(World world) {
        deathX = getX();
        deathY = getY();
        alive = false;
        destroy(world);
        setElapsedTime(0);
    }

    /**
     * Destroys the Box2D hitbox of the object immediately.
     *
     * @param world The Box2D world where the hitbox is stored.
     */
    private void destroy(World world) {
        if (hitbox != null) {
            world.destroyBody(hitbox);
            hitbox = null; // Set the body to null
        }
    }

    /**
     * Marks the object for death, queuing the hitbox for destruction by the {@link GameContactListener}.
     * This is used for handling destruction during collision events to avoid Box2D lockstep issues.
     *
     * @param world           The Box2D world where the hitbox is stored.
     * @param contactListener The GameContactListener responsible for handling contact events and body destruction.
     */
    public void markForDeath(World world, GameContactListener contactListener) {
        deathX = getX();
        deathY = getY();
        alive = false;
        System.out.println("death: " + deathX + "," + deathY);

        // Queue the hitbox for destruction
        if (hitbox != null) {
            contactListener.queueBodyForDestruction(hitbox);
            hitbox = null; // Set to null to prevent further interactions
        }

        setElapsedTime(0);
    }

    /**
     * Sets the object as dead, indicating that its death animation is finished.
     */
    public void setDead() {
        this.dead = true;
    }

    /**
     * Checks if the object is currently alive.
     *
     * @return {@code true} if the object is alive, {@code false} otherwise.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Checks if the object is dead (death animation finished).
     *
     * @return {@code true} if the object is dead, {@code false} otherwise.
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Gets the maximum number of concurrent bombs this object can place.
     *
     * @return The number of concurrent bombs.
     */
    public int getConcurrentBombs() {
        return concurrentBombs;
    }

    /**
     * Gets the blast radius of bombs placed by this object.
     *
     * @return The blast radius.
     */
    public int getBlastRadius() {
        return blastRadius;
    }

    /**
     * Gets the movement speed of the object.
     *
     * @return The movement speed.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Increases the maximum number of concurrent bombs the object can place, up to a maximum of 8.
     * Also increases the number of bombs currently available to place.
     */
    public void increaseConcurrentBombs() {
        if (concurrentBombs < 8) {
            concurrentBombs++;
            bombsNow++;
        }
    }

    /**
     * Decreases the number of bombs currently available to place, called when a bomb is placed.
     */
    public void placedBomb() {
        bombsNow--;
    }

    /**
     * Increases the number of bombs currently available to place, called when a placed bomb explodes.
     */
    public void returnBomb() {
        bombsNow++;
    }

    /**
     * Increases the blast radius of bombs placed by this object, up to a maximum of 8.
     */
    public void increaseBlastRadius() {
        if (blastRadius < 8) blastRadius++;
    }

    /**
     * Increases the movement speed of the object.
     */
    public void increaseSpeed() {
        speed++;
    }

    /**
     * Gets the number of bombs the object currently has available to place.
     *
     * @return The number of bombs available.
     */
    public int getBombsNow() {
        return bombsNow;
    }

    /**
     * Gets the set of Box2D bodies representing bombs this object is ignoring collisions with.
     *
     * @return The set of ignored bomb bodies.
     */
    public Set<Body> getIgnoredBombs() {
        return ignoredBombs;
    }

    /**
     * Adds a Box2D body of a bomb to the set of ignored bombs.
     *
     * @param body The Box2D body of the bomb to ignore.
     */
    public void addIgnoredBomb(Body body) {
        ignoredBombs.add(body);
    }

    /**
     * Removes a Box2D body of a bomb from the set of ignored bombs.
     *
     * @param body The Box2D body of the bomb to stop ignoring.
     */
    public void removeIgnoredBomb(Body body) {
        ignoredBombs.remove(body);
    }

    /**
     * Gets the elapsed game time in seconds.
     *
     * @return The elapsed time.
     */
    public float getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Sets the elapsed game time in seconds.
     *
     * @param elapsedTime The new elapsed time.
     */
    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * Gets the Box2D hitbox of the object.
     *
     * @return The Box2D hitbox, or {@code null} if the object is not alive or hitbox is destroyed.
     */
    public Body getHitbox() {
        if (!alive) {
            System.out.println("hitbox doesn't exist");
            return null;
        }
        return hitbox;
    }

    /**
     * Gets the current movement direction of the object.
     *
     * @return The current direction.
     */
    public DirectionType getDirection() {
        return direction;
    }

    /**
     * Sets the current movement direction of the object.
     *
     * @param direction The new direction to set.
     */
    public void setDirection(DirectionType direction) {
        this.direction = direction;
    }

    /**
     * Gets the last direction the object was moving in.
     *
     * @return The last direction.
     */
    public DirectionType getLastDirection() {
        return lastDirection;
    }

    /**
     * Sets the last direction the object was moving in.
     *
     * @param lastDirection The new last direction to set.
     */
    public void setLastDirection(DirectionType lastDirection) {
        this.lastDirection = lastDirection;
    }

    /**
     * Gets the rounded x-coordinate of the object's cell in the game map.
     *
     * @return The rounded x-coordinate.
     */
    public int getCellX() {
        return Math.round(getX());
    }

    /**
     * Gets the rounded y-coordinate of the object's cell in the game map.
     *
     * @return The rounded y-coordinate.
     */
    public int getCellY() {
        return Math.round(getY());
    }

    /**
     * Abstract method to get the current texture region representing the appearance of the mobile object.
     * Subclasses must implement this to provide specific textures based on animation state, direction, etc.
     *
     * @return The current texture region.
     */
    @Override
    public abstract TextureRegion getCurrentAppearance();

    /**
     * Gets the current x-coordinate of the object in world units.
     * Returns the deathX coordinate if the object is not alive.
     *
     * @return The x-coordinate.
     */
    @Override
    public float getX() {
        // The x-coordinate of the player is the x-coordinate of the hitbox (this can change every frame).
        if (alive) {
            return hitbox.getPosition().x;
        }
        return deathX;
    }

    /**
     * Gets the current y-coordinate of the object in world units.
     * Returns the deathY coordinate if the object is not alive.
     *
     * @return The y-coordinate.
     */
    @Override
    public float getY() {
        // The y-coordinate of the player is the y-coordinate of the hitbox (this can change every frame).
        if (alive) {
            return hitbox.getPosition().y;
        }
        return deathY;
    }
}