package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;

/**
 * Represents all Mobile Objects.
 * Abstract superclass for a player and enemies.
 */
public abstract class MobileObject implements Drawable {
    /**
     * The Box2D hitbox of the player, used for position and collision detection.
     */
    private Body hitbox;
    /**
     * Speed of the object
     */
    private int speed;
    /**
     * Total time elapsed since the game started. We use this for calculating the player movement and animating it.
     */
    private float elapsedTime;
    /**
     * Type of the direction the object is going to
     */
    private DirectionType direction;
    /**
     * If object is alive it is still moving
     * If not object is dying, doesn't have a hitbox and has respective animation
     * If the object finished the animation of death - dead.
     */
    private boolean alive;
    private boolean dead;
    /**
     * Coords to store after object's death
     */
    private float deathX;
    private float deathY;

    private int concurrentBombs;
    private int blastRadius;
    private int bombsNow;

    public MobileObject(World world, float x, float y, int speed, float radius) {
        this.hitbox = createHitbox(world, x, y, radius);
        this.speed = speed;
        direction = DirectionType.NONE;
        alive = true;
        dead = false;
        concurrentBombs = 1;
        blastRadius = 1;
        bombsNow = 1;
    }

    /**
     * Creates a Box2D body for the player.
     * This is what the physics engine uses to move the player around and detect collisions with other bodies.
     *
     * @param world  The Box2D world to add the body to.
     * @param startX The initial X position.
     * @param startY The initial Y position.
     * @param radius The radius of the hitbox.
     * @return The created body.
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
     * abstract method to update the object.
     * @param frameTime the time since the last frame.
     */
    public abstract void tick(float frameTime);

    /**
     * set the velocity according to the direction.
     */
    protected void moveInDirection() {

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
     * update the elapsed time
     * @param frameTime the time since the last frame.
     */
    public void increaseElapsedTime(float frameTime) {
        elapsedTime += frameTime;
    }

    /**
     * Death of the object: not alive anymore
     * Saves the coords
     * Destroys the hitbox
     * @param world is the world where the hitbox is stored
     */
    public void death(World world) {
        deathX = getX();
        deathY = getY();
        alive = false;
        System.out.println("death: " + deathX + "," + deathY);
        destroy(world);
        setElapsedTime(0);
    }

    /**
     * destroys the hitbox
     * @param world is the world where the hitbox is stored
     */
    private void destroy(World world) {
        if (hitbox != null) {
            world.destroyBody(hitbox);
            hitbox = null; // Set the body to null
        }
    }
    /**
     * destroys the hitbox after the collision of the enemy and the player
     * @param world is the world where the hitbox is stored
     * @param contactListener the ContactListener of the game that handles collisions
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

    public void setDead() {
        this.dead = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDead() {
        return dead;
    }

    public int getConcurrentBombs() {
        return concurrentBombs;
    }
    public int getBlastRadius() {
        return blastRadius;
    }
    public int getSpeed() {
        return speed;
    }
    public void increaseConcurrentBombs() {
        if (concurrentBombs < 8) {
            concurrentBombs++;
            bombsNow++;
        }
    }
    public void placedBomb() {
        bombsNow--;
    }
    public void returnBomb() {
        bombsNow++;
    }
    public void increaseBlastRadius() {
        if (blastRadius < 8) blastRadius++;
    }
    public void increaseSpeed() {
        speed++;
    }

    public int getBombsNow() {
        return bombsNow;
    }




    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Body getHitbox() {
        if (!alive) {
            System.out.println("hitbox doesn't exist");
            return null;
        }
        return hitbox;
    }




    public DirectionType getDirection() {
        return direction;
    }

    public void setDirection(DirectionType direction) {
        this.direction = direction;
    }

    /**
     * get round coords
     * @return int x
     */
    public int getCellX() {
        return Math.round(getX());
    }

    /**
     * get round coords
     * @return int y
     */
    public int getCellY() {
        return Math.round(getY());
    }

    @Override
    public abstract TextureRegion getCurrentAppearance();

    @Override
    public float getX() {
        // The x-coordinate of the player is the x-coordinate of the hitbox (this can change every frame).
        if (alive) {
            return hitbox.getPosition().x;
        }
        return deathX;
    }

    @Override
    public float getY() {
        // The y-coordinate of the player is the y-coordinate of the hitbox (this can change every frame).
        if (alive) {
            return hitbox.getPosition().y;
        }
        return deathY;
    }


}
