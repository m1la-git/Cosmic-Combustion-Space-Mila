package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.SpriteSheet;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Represents the player character in the game.
 * The player has a hitbox, so it can collide with other objects in the game.
 */
public class Player extends MobileObject implements Drawable {
    /**
     * double queue to manage character movement
     */

    private final Deque<Integer> keyPressOrder = new ArrayDeque<>();


    public Player(World world, float x, float y) {
        super(world, x, y, 2, 0.3f, BodyDef.BodyType.DynamicBody);

    }


    /**
     * Move the player around in a circle by updating the linear velocity of its hitbox every frame.
     * This doesn't actually move the player, but it tells the physics engine how the player should move next frame.
     *
     * @param frameTime the time since the last frame.
     */
    @Override
    public void tick(float frameTime) {
        increaseElapsedTime(frameTime);
        // Make the player move in a circle with radius 2 tiles
        // You can change this to make the player move differently, e.g. in response to user input.
        // See Gdx.input.isKeyPressed() for keyboard input
        if (isAlive()) {
            handleInput();
            if (!keyPressOrder.isEmpty()) {
                switch (keyPressOrder.peekLast()) {
                    case Input.Keys.W, Input.Keys.UP:
                        getHitbox().setLinearVelocity(0, getSpeed());
                        setDirection(DirectionType.UP);
                        break;
                    case Input.Keys.S, Input.Keys.DOWN:
                        getHitbox().setLinearVelocity(0, -getSpeed());
                        setDirection(DirectionType.DOWN);
                        break;
                    case Input.Keys.A, Input.Keys.LEFT:
                        getHitbox().setLinearVelocity(-getSpeed(), 0);
                        setDirection(DirectionType.LEFT);
                        break;
                    case Input.Keys.D, Input.Keys.RIGHT:
                        getHitbox().setLinearVelocity(getSpeed(), 0);
                        setDirection(DirectionType.RIGHT);
                        break;
                }
            } else {
                getHitbox().setLinearVelocity(0, 0);
                setDirection(DirectionType.NONE);
            }
        } else {
            if (getElapsedTime() >= 1.05f && !isDead()) {
                setDead(true);
            }
        }

    }

    /**
     * TBA
     */
    private void handleInput() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.W:
                        keyPressOrder.addLast(Input.Keys.W);
                        break;
                    case Input.Keys.S:
                        keyPressOrder.addLast(Input.Keys.S);
                        break;
                    case Input.Keys.A:
                        keyPressOrder.addLast(Input.Keys.A);
                        break;
                    case Input.Keys.D:
                        keyPressOrder.addLast(Input.Keys.D);
                        break;
                    case Input.Keys.UP:
                        keyPressOrder.addLast(Input.Keys.UP);
                        break;
                    case Input.Keys.DOWN:
                        keyPressOrder.addLast(Input.Keys.DOWN);
                        break;
                    case Input.Keys.LEFT:
                        keyPressOrder.addLast(Input.Keys.LEFT);
                        break;
                    case Input.Keys.RIGHT:
                        keyPressOrder.addLast(Input.Keys.RIGHT);
                        break;
                    default:
                        // Optionally handle other keys or do nothing.
                        break;
                }
                return true; // Indicates the event was processed
            }

            @Override
            public boolean keyUp(int keycode) {
                switch (keycode) {
                    case Input.Keys.W:
                        keyPressOrder.remove(Input.Keys.W);
                        break;
                    case Input.Keys.S:
                        keyPressOrder.remove(Input.Keys.S);
                        break;
                    case Input.Keys.A:
                        keyPressOrder.remove(Input.Keys.A);
                        break;
                    case Input.Keys.D:
                        keyPressOrder.remove(Input.Keys.D);
                        break;
                    case Input.Keys.UP:
                        keyPressOrder.remove(Input.Keys.UP);
                        break;
                    case Input.Keys.DOWN:
                        keyPressOrder.remove(Input.Keys.DOWN);
                        break;
                    case Input.Keys.LEFT:
                        keyPressOrder.remove(Input.Keys.LEFT);
                        break;
                    case Input.Keys.RIGHT:
                        keyPressOrder.remove(Input.Keys.RIGHT);
                        break;
                    default:
                        // Optional: Handle other keys if necessary
                        break;
                }
                return true;
            }
        });
    }



    @Override
    public TextureRegion getCurrentAppearance() {
        if (isAlive()) {
            // Get the frame of the walk down animation that corresponds to the current time.
            return switch (getDirection()) {
                case UP -> Animations.PLAYER_WALK_UP.getKeyFrame(getElapsedTime(), true);
                case DOWN -> Animations.PLAYER_WALK_DOWN.getKeyFrame(getElapsedTime(), true);
                case LEFT -> Animations.PLAYER_WALK_LEFT.getKeyFrame(getElapsedTime(), true);
                case RIGHT -> Animations.PLAYER_WALK_RIGHT.getKeyFrame(getElapsedTime(), true);
                case NONE -> SpriteSheet.CHARACTER.at(1, 1);
            };
        }
        return Animations.PLAYER_DEATH.getKeyFrame(getElapsedTime(), false);
    }


}
