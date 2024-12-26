package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

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

    private final boolean players2;
    private final boolean player1;

    public Player(World world, float x, float y, boolean player1) {
        super(world, x, y, 2, 0.3f);
        players2 = true;
        this.player1 = player1;
    }

    public Player(World world, float x, float y) {
        super(world, x, y, 2, 0.3f);
        players2 = false;
        this.player1 = true;
    }

    /**
     * Move the player in the set direction according do the queue
     * Uses handleInput() to process keyboard input
     *
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


    public void addKeys(int keycode) {
        keyPressOrder.addLast(keycode);
    }
    public void removeKeys(int keycode) {
        keyPressOrder.remove(keycode);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if (isAlive()) {
            // Get the frame of the walk down animation that corresponds to the current time.
            if (player1) {
                return switch (getDirection()) {
                    case UP -> Animations.PLAYER1_WALK_UP.getKeyFrame(getElapsedTime(), true);
                    case DOWN -> Animations.PLAYER1_WALK_DOWN.getKeyFrame(getElapsedTime(), true);
                    case LEFT -> Animations.PLAYER1_WALK_LEFT.getKeyFrame(getElapsedTime(), true);
                    case RIGHT -> Animations.PLAYER1_WALK_RIGHT.getKeyFrame(getElapsedTime(), true);
                    case NONE -> Textures.PLAYER1;
                };
            } else {
                return switch (getDirection()) {
                    case UP -> Animations.PLAYER2_WALK_UP.getKeyFrame(getElapsedTime(), true);
                    case DOWN -> Animations.PLAYER2_WALK_DOWN.getKeyFrame(getElapsedTime(), true);
                    case LEFT -> Animations.PLAYER2_WALK_LEFT.getKeyFrame(getElapsedTime(), true);
                    case RIGHT -> Animations.PLAYER2_WALK_RIGHT.getKeyFrame(getElapsedTime(), true);
                    case NONE -> Textures.PLAYER2;
                };
            }


        }
        return Animations.PLAYER_DEATH.getKeyFrame(getElapsedTime(), false);
    }


}
