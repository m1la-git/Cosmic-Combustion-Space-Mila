package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;

/**
 * Represents all Stationary Objects in the game.
 * Abstract superclass for all game objects that remain static in the game world, such as walls, power-ups, and bombs.
 * Stationary objects, if they require collision detection, are represented by a Box2D {@link Body} of type {@link BodyDef.BodyType#StaticBody}.
 * This class implements the {@link Drawable} interface, allowing stationary objects to be rendered in the game.
 */
public abstract class StationaryObject implements Drawable {
    /**
     * The x-coordinate of the stationary object in the game world.
     * Represents the object's position on the horizontal axis.
     */
    private final int x;
    /**
     * The y-coordinate of the stationary object in the game world.
     * Represents the object's position on the vertical axis.
     */
    private final int y;
    /**
     * The Box2D body of the stationary object, used for collision detection.
     * Initialized only if the object {@link #needsHitbox} is set to true in the constructor.
     * If null, the object has no physical presence in the Box2D world.
     */
    private Body hitbox;

    /**
     * Constructor for a StationaryObject.
     * Initializes a stationary object with its position and optionally creates a Box2D hitbox.
     *
     * @param world       The Box2D world to which the hitbox, if needed, will be added.
     * @param x           The x-coordinate of the object in the game world.
     * @param y           The y-coordinate of the object in the game world.
     * @param needsHitbox A boolean flag indicating whether this stationary object requires a Box2D hitbox for collision detection.
     *                    If {@code true}, a static body hitbox will be created; otherwise, {@code hitbox} will remain null.
     */
    public StationaryObject(World world, int x, int y, boolean needsHitbox) {
        this.x = x;
        this.y = y;
        if (needsHitbox) {
            hitbox = createHitbox(world);
        }
    }

    /**
     * Creates a Box2D static body as a hitbox for the stationary object.
     * This method sets up a {@link BodyDef} for a static body, positions it at the object's coordinates,
     * creates the body in the given Box2D world, and attaches a square {@link PolygonShape} as its fixture.
     *
     * @param world The Box2D world to add the body to.
     * @return The created Box2D {@link Body} representing the hitbox.
     */
    private Body createHitbox(World world) {
        // BodyDef is like a blueprint for the movement properties of the body.
        BodyDef bodyDef = new BodyDef();
        // Static bodies never move, but other bodies can collide with them.
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set the initial position of the body to the object's coordinates.
        bodyDef.position.set(this.x, this.y);
        // Create the body in the world using the body definition.
        Body body = world.createBody(bodyDef);
        // Now we need to give the body a shape so the physics engine knows how to collide with it.
        // We'll use a polygon shape to represent a square tile.
        PolygonShape box = new PolygonShape();
        // Make the polygon a square with a side length of 1 tile (0.5f extends in each direction from the center).
        box.setAsBox(0.5f, 0.5f);
        // Attach the shape to the body as a fixture with default density and friction.
        body.createFixture(box, 1.0f);
        // We're done with the shape, so we should dispose of it to free up memory.
        box.dispose();
        // Set the StationaryObject instance as the user data of the body, allowing retrieval of this object from the Box2D body later.
        body.setUserData(this);
        return body;
    }

    /**
     * Destroys the Box2D hitbox of the stationary object.
     * Removes the associated {@link #hitbox} from the Box2D world and sets the {@link #hitbox} reference to null.
     * This is typically called when the stationary object is no longer needed or is being removed from the game.
     *
     * @param world The Box2D world to remove the body from.
     */
    public void destroy(World world) {
        if (hitbox != null) {
            world.destroyBody(hitbox);
            hitbox = null; // Set the body reference to null after destruction
        }
    }

    /**
     * Gets the Box2D hitbox of the stationary object.
     *
     * @return The Box2D {@link Body} representing the hitbox, or {@code null} if the object does not have a hitbox.
     */
    public Body getHitbox() {
        return hitbox;
    }

    /**
     * Abstract method to get the current texture region for rendering the stationary object.
     * Subclasses must implement this to provide the specific texture for their visual representation.
     *
     * @return The current {@link TextureRegion} to be rendered for this stationary object.
     */
    @Override
    public abstract TextureRegion getCurrentAppearance();

    /**
     * Gets the x-coordinate of the stationary object.
     *
     * @return The x-coordinate as a float.
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the stationary object.
     *
     * @return The y-coordinate as a float.
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     * Gets the x-coordinate of the stationary object as an integer, representing its cell position in the game grid.
     *
     * @return The integer x-coordinate.
     */
    public int getCellX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the stationary object as an integer, representing its cell position in the game grid.
     *
     * @return The integer y-coordinate.
     */
    public int getCellY() {
        return y;
    }
}