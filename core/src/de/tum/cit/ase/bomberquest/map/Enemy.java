package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the Enemies in the game.
 * Extends MobileObject, has a hitbox and can collide with other objects in-game
 * Kinematic body type
 */
public class Enemy extends MobileObject implements Drawable { // Change direction every 2 seconds
    /**
     * Random for choosing an arbitrary directions
     */
    private final Random random = new Random();
    /**
     * The map of the game to see the surroundings
     */
    private final GameMap map;
    /**
     * Smooth moving from cell to cell
     */
    private boolean reachedCell;
    private int targetX;
    private int targetY;
    /**
     * Remember the lastDirection the enemy went to
     */
    private DirectionType lastDirection = DirectionType.NONE;
    /**
     * For a proper direction choice when there are no free spaces around
     */
    private boolean trapped;

    public Enemy(World world, float x, float y, GameMap map) {
        super(world, x, y, 1, 0.5f, BodyDef.BodyType.KinematicBody);
        this.map = map;
        reachedCell = true;
        trapped = false;
    }

    /**
     * Move the enemy in the free directions
     * @param frameTime the time since the last frame.
     */
    @Override
    public void tick(float frameTime) {
        increaseElapsedTime(frameTime);
        if (isAlive()) {
            //Check iff it is practically on the reached cell put the enemy on it
            if (Math.abs(getX() - targetX) < 0.03 && Math.abs(getY() - targetY) < 0.03) {
                reachedCell = true;
                getHitbox().setTransform(getCellX(), getCellY(), getHitbox().getAngle());
            }
            //if it reached the target cell - change the directions again
            if (reachedCell) {
                setDirection(selectFreeDirection());
                if (!trapped) {
                    reachedCell = false;
                }
            }
            moveInDirection();
        } else if (!isAlive() && getElapsedTime() >= 1.05f && !isDead()) setDead();


    }

    /**
     * Select the direction out of all possible ones
     * If none - enemy is trapped until it dies or other possible direction frees up
     */
    private DirectionType selectFreeDirection() {
        // Randomly pick one of the four directions
        DirectionType[] directions = {DirectionType.UP, DirectionType.DOWN, DirectionType.LEFT, DirectionType.RIGHT};
        List<DirectionType> freeDirections = new ArrayList<>();
        trapped = false;

        for (DirectionType direction : directions) {
            if (isDirectionFree(direction) && direction != DirectionType.getOppositeDirection(lastDirection)) {
                freeDirections.add(direction);
            }
        }

        // If there are free directions, pick one at random; otherwise, allow going in the opposite direction
        if (!freeDirections.isEmpty()) {
            DirectionType direction = freeDirections.get(random.nextInt(freeDirections.size()));
            updateTargetCell(direction);
            lastDirection = direction;
            return direction;
        }

        // If no alternative, choose the opposite direction as a fallback
        DirectionType fallbackDirection = DirectionType.getOppositeDirection(lastDirection);
        if (isDirectionFree(fallbackDirection)) {
            updateTargetCell(fallbackDirection);
            lastDirection = fallbackDirection;
            return fallbackDirection;
        }

        trapped = true;
        return fallbackDirection;
    }

    /**
     * updates the target cell depending on the direction the enemy chose
     * @param direction type of the direction
     */
    private void updateTargetCell(DirectionType direction) {
        int[] targetCellCoords = coordsPossibleTargetCell(direction);
        targetX = targetCellCoords[0];
        targetY = targetCellCoords[1];
    }

    /**
     * checks whether the target cell is free or not
     * @param direction type of the direction
     * @return whether it is free or not
     */
    private boolean isDirectionFree(DirectionType direction) {
        int[] targetCellCoords = coordsPossibleTargetCell(direction);
        // Check if the target cell is free of StationaryObjects and Bombs
        return map.isCellFree(targetCellCoords[0], targetCellCoords[1]);
    }

    /**
     * Find the coords of the possible target cell
     * @param direction type of the direction
     * @return the coords
     */
    private int[] coordsPossibleTargetCell(DirectionType direction) {
        int targetCellX = getCellX();
        int targetCellY = getCellY();
        switch (direction) {
            case UP:
                targetCellY += 1;
                break;
            case DOWN:
                targetCellY -= 1;
                break;
            case LEFT:
                targetCellX -= 1;
                break;
            case RIGHT:
                targetCellX += 1;
                break;
        }
        return new int[]{targetCellX, targetCellY};

    }




    @Override
    public TextureRegion getCurrentAppearance() {
        if (isAlive()) {
            return switch (getDirection()) {
                case UP, RIGHT -> Animations.ENEMY_WALK_UP_OR_RIGHT.getKeyFrame(getElapsedTime(), true);
                case DOWN, LEFT -> Animations.ENEMY_WALK_DOWN_OR_LEFT.getKeyFrame(getElapsedTime(), true);
                case NONE -> Textures.ENEMY;
            };
        }
        return Animations.ENEMY_DEATH.getKeyFrame(getElapsedTime(), false);

    }
}
