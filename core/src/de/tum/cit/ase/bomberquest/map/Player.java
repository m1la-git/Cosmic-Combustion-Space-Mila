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
 * Extends MobileObject, has a hitbox and can collide with other objects in-game
 * Dynamic body type
 */
public class Player extends MobileObject implements Drawable {
    /**
     * double queue to manage character movement
     */
    private final Deque<Integer> keyPressOrder = new ArrayDeque<>();

    public Player(World world, float x, float y) {
        super(world, x, y, 2, 0.3f);
    }

    /**
     * Move the player in the set direction according do the queue
     * Uses handleInput() to process keyboard input
     * @param frameTime the time since the last frame.
     */
    @Override
    public void tick(float frameTime) {
        increaseElapsedTime(frameTime);
        if (isAlive()) {
            handleInput();
            moveInDirection();
        } else {
            if (!isAlive() && getElapsedTime() >= 1.05f && !isDead()) setDead();
        }
    }

    /**
     * handling keyboard input with new InputAdapter()
     * adds keys to the queue for appropriate direction change
     * sets the direction according to the queue
     */
    private void handleInput() {
        // check the keyboard input
        Gdx.input.setInputProcessor(new InputAdapter() {
            //key is just pressed
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

            //key is just released
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

        // set the direction
        if (!keyPressOrder.isEmpty()) {
            switch (keyPressOrder.peekLast()) {
                case Input.Keys.W, Input.Keys.UP:
                    setDirection(DirectionType.UP);
                    break;
                case Input.Keys.S, Input.Keys.DOWN:
                    setDirection(DirectionType.DOWN);
                    break;
                case Input.Keys.A, Input.Keys.LEFT:
                    setDirection(DirectionType.LEFT);
                    break;
                case Input.Keys.D, Input.Keys.RIGHT:
                    setDirection(DirectionType.RIGHT);
                    break;
            }
        } else {
            setDirection(DirectionType.NONE);
        }
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
