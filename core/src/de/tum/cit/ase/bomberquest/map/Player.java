package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.SpriteSheet;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Represents the player character in the game.
 * The player has a hitbox, so it can collide with other objects in the game.
 */
public class Player implements Drawable {

    /**
     * The Box2D hitbox of the player, used for position and collision detection.
     */
    private final Body hitbox;
    /**
     * double queue to manage character movement
     */
    private final Deque<Integer> keyPressOrder = new ArrayDeque<>();
    /**
     * Total time elapsed since the game started. We use this for calculating the player movement and animating it.
     */
    private float elapsedTime;

    public Player(World world, float x, float y) {
        this.hitbox = createHitbox(world, x, y);
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
    private Body createHitbox(World world, float startX, float startY) {
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
        // Give the circle a radius of 0.3 tiles (the player is 0.6 tiles wide).
        circle.setRadius(0.3f);
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

    /**
     * Move the player around in a circle by updating the linear velocity of its hitbox every frame.
     * This doesn't actually move the player, but it tells the physics engine how the player should move next frame.
     *
     * @param frameTime the time since the last frame.
     */
    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
        // Make the player move in a circle with radius 2 tiles
        // You can change this to make the player move differently, e.g. in response to user input.
        // See Gdx.input.isKeyPressed() for keyboard input
        handleInput();
        if (!keyPressOrder.isEmpty()) {
            switch (keyPressOrder.peekLast()) {
                case Input.Keys.W, Input.Keys.UP:
                    this.hitbox.setLinearVelocity(0, 2);
                    break;
                case Input.Keys.S, Input.Keys.DOWN:
                    this.hitbox.setLinearVelocity(0, -2);
                    break;
                case Input.Keys.A, Input.Keys.LEFT:
                    this.hitbox.setLinearVelocity(-2, 0);
                    break;
                case Input.Keys.D, Input.Keys.RIGHT:
                    this.hitbox.setLinearVelocity(2, 0);
                    break;
            }
        } else {
            this.hitbox.setLinearVelocity(0, 0);
        }
    }

    /**
     * TBA
     */
    private void handleInput() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.W) keyPressOrder.addLast(Input.Keys.W);
                if (keycode == Input.Keys.S) keyPressOrder.addLast(Input.Keys.S);
                if (keycode == Input.Keys.A) keyPressOrder.addLast(Input.Keys.A);
                if (keycode == Input.Keys.D) keyPressOrder.addLast(Input.Keys.D);
                if (keycode == Input.Keys.UP) keyPressOrder.addLast(Input.Keys.UP);
                if (keycode == Input.Keys.DOWN) keyPressOrder.addLast(Input.Keys.DOWN);
                if (keycode == Input.Keys.LEFT) keyPressOrder.addLast(Input.Keys.LEFT);
                if (keycode == Input.Keys.RIGHT) keyPressOrder.addLast(Input.Keys.RIGHT);
                return true; // Indicates the event was processed
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.W) keyPressOrder.remove(Input.Keys.W);
                if (keycode == Input.Keys.S) keyPressOrder.remove(Input.Keys.S);
                if (keycode == Input.Keys.A) keyPressOrder.remove(Input.Keys.A);
                if (keycode == Input.Keys.D) keyPressOrder.remove(Input.Keys.D);
                if (keycode == Input.Keys.UP) keyPressOrder.remove(Input.Keys.UP);
                if (keycode == Input.Keys.DOWN) keyPressOrder.remove(Input.Keys.DOWN);
                if (keycode == Input.Keys.LEFT) keyPressOrder.remove(Input.Keys.LEFT);
                if (keycode == Input.Keys.RIGHT) keyPressOrder.remove(Input.Keys.RIGHT);
                return true;
            }
        });
    }

    public Body getHitbox() {
        return hitbox;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        // Get the frame of the walk down animation that corresponds to the current time.

        if (!keyPressOrder.isEmpty()) {
            switch (keyPressOrder.peekLast()) {
                case Input.Keys.W, Input.Keys.UP:
                    return Animations.CHARACTER_WALK_UP.getKeyFrame(this.elapsedTime, true);
                case Input.Keys.S, Input.Keys.DOWN:
                    return Animations.CHARACTER_WALK_DOWN.getKeyFrame(this.elapsedTime, true);
                case Input.Keys.A, Input.Keys.LEFT:
                    return Animations.CHARACTER_WALK_LEFT.getKeyFrame(this.elapsedTime, true);
                case Input.Keys.D, Input.Keys.RIGHT:
                    return Animations.CHARACTER_WALK_RIGHT.getKeyFrame(this.elapsedTime, true);
            }
        }
        return SpriteSheet.CHARACTER.at(1, 1);
    }

    public int getCellX() {
        return Math.round(getX());
    }
    public int getCellY() {
        return Math.round(getY());
    }

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
