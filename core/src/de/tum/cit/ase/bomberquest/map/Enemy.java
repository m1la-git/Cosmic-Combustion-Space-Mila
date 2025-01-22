package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
public class Enemy extends MobileObject implements Drawable {
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
    private float nothingChangedTime = 0;
    private float previousX;
    private float previousY;

    /**
     * For a proper direction choice when there are no free spaces around
     */
    private boolean trapped;

    /**
     * Radius within which the enemy starts pathfinding to the player.
     */
    private final float DETECTION_RADIUS = 5f; // Adjust as needed
    /**
     * The path to the player, calculated by the pathfinder.
     */
    private List<Tile> pathToPlayer;
    /**
     * Index of the next tile in the path to follow.
     */
    private int pathIndex;
    private final float reachedCellThreshold = 0.03f;
    private final boolean canFindPlayer;
    private final boolean canPlaceBombs;

    public Enemy(World world, float x, float y, GameMap map, boolean canFindPlayer, boolean canPlaceBombs) {
        super(world, x, y, 1, 0.45f);
        this.map = map;
        reachedCell = true;
        trapped = false;
        this.pathToPlayer = null;
        this.pathIndex = 0;
        previousX = x;
        previousY = y;
        this.canFindPlayer = canFindPlayer;
        this.canPlaceBombs = canPlaceBombs;
    }

    /**
     * Move the enemy. If the player is within the detection radius, try to find a path to them.
     * Otherwise, continue with the random movement.
     *
     * @param frameTime the time since the last frame.
     */
    @Override
    public void tick(float frameTime) {
        increaseElapsedTime(frameTime);
        if (isAlive()) {
            nothingChangedTime += frameTime;
            if (Math.abs(getX() - previousX) > reachedCellThreshold || Math.abs(getY() - previousY) > reachedCellThreshold) {
                nothingChangedTime = 0;
                previousX = getX();
                previousY = getY();
            }
            if (nothingChangedTime >= 0.2f) { // prevent sticking on 1 place
                nothingChangedTime = 0;
                reachedCell = true;
                pathToPlayer = null;
            }
            if (!map.isCellFree(targetX, targetY) || !map.isCellFree(getCellX(), getCellY())) {
                pathToPlayer = null;
                reachedCell = true;
            }
            if (Math.abs(getX() - targetX) > 1f - reachedCellThreshold && Math.abs(getY() - targetY) > 1f - reachedCellThreshold && nothingChangedTime > 0.3){
                reachedCell = true;
                pathToPlayer = null;
            }


            if (Math.abs(getX() - targetX) < reachedCellThreshold && Math.abs(getY() - targetY) < reachedCellThreshold) {
                reachedCell = true;
                getHitbox().setTransform(getCellX(), getCellY(), getHitbox().getAngle());
            }

            float distanceToPlayer1 = Vector2.dst(getX(), getY(), map.getPlayer1().getX(), map.getPlayer1().getY());
            float distanceToPlayer2 = (map.getPlayer2() != null) ? Vector2.dst(getX(), getY(), map.getPlayer2().getX(), map.getPlayer2().getY()): -1;

            if ((distanceToPlayer1 < DETECTION_RADIUS || distanceToPlayer2 < DETECTION_RADIUS && distanceToPlayer2 >= 0) && canFindPlayer) {
                if (reachedCell) { // Only recalculate path or choose new direction if reached the cell
                    if (pathToPlayer == null || pathToPlayer.isEmpty()) {
                        if (distanceToPlayer1 < DETECTION_RADIUS) findPathToPlayer(map.getPlayer1());
                        else findPathToPlayer(map.getPlayer2());
                    }
                    if (pathToPlayer != null && !pathToPlayer.isEmpty()) {
                        followPath();
                    } else {
                        // If no path found, fall back to random movement
                        setDirection(selectFreeDirection());
                        if (!trapped) {
                            reachedCell = false; // Allow movement in the new random direction
                        }
                    }
                }
                moveInDirection();
            } else {
                pathToPlayer = null;
                // Player is out of range, resume random movement
                if (reachedCell) {
                    setDirection(selectFreeDirection());
                    if (!trapped) {
                        reachedCell = false; // Allow movement in the new random direction
                    }
                }
                moveInDirection();
            }
        } else if (!isAlive() && getElapsedTime() >= 1.05f && !isDead()) setDead();
    }

    private void findPathToPlayer(Player player) {
        Tile startTile = new Tile(getCellX(), getCellY());
        Tile targetTile = new Tile(player.getCellX(), player.getCellY());
        this.pathToPlayer = Pathfinder.findPath(startTile, targetTile, map);
        this.pathIndex = 0; // Reset path index when a new path is found
    }

    private void followPath() {
        if (pathToPlayer != null && pathIndex < pathToPlayer.size()) {
            Tile nextTile = pathToPlayer.get(pathIndex);
            int nextTargetX = nextTile.x();
            int nextTargetY = nextTile.y();

            // Check if the next tile in the path is now blocked (e.g., by a bomb)
            if (!map.isCellFree(nextTargetX, nextTargetY)) {
                pathToPlayer = null; // Invalidate the current path
                return; // Recalculate the path in the next tick
            }

            if (reachedCell) {
                DirectionType nextDirection = getDirectionToTarget(nextTargetX, nextTargetY);
                setDirection(nextDirection);
                updateTargetCellFromCoords(nextTargetX, nextTargetY);
                reachedCell = false; // Moved, so not at the new target yet
            }

            // Continue moving if not yet reached the target of the current step
            if (getDirection() != DirectionType.NONE) {
                moveInDirection();
            }

            // Check if the current target cell for this step is reached
            if (Math.abs(getX() - targetX) < reachedCellThreshold && Math.abs(getY() - targetY) < reachedCellThreshold) {
                getHitbox().setTransform(getCellX(), getCellY(), getHitbox().getAngle());
                reachedCell = true;
                pathIndex++; // Move to the next step in the path
            }

        } else {
            pathToPlayer = null;
        }
    }

    private DirectionType getDirectionToTarget(int targetCellX, int targetCellY) {
        int currentCellX = getCellX();
        int currentCellY = getCellY();

        if (targetCellX > currentCellX) {
            return DirectionType.RIGHT;
        } else if (targetCellX < currentCellX) {
            return DirectionType.LEFT;
        } else if (targetCellY > currentCellY) {
            return DirectionType.UP;
        } else if (targetCellY < currentCellY) {
            return DirectionType.DOWN;
        } else {
            return DirectionType.NONE; // Already at the target
        }
    }

    /**
     * Select the direction out of all possible ones, avoiding repeating paths.
     * If none - enemy is trapped until it dies or other possible direction frees up
     */
    private DirectionType selectFreeDirection() {
        DirectionType[] directions = {DirectionType.UP, DirectionType.DOWN, DirectionType.LEFT, DirectionType.RIGHT};
        List<DirectionType> freeDirections = new ArrayList<>();

        DirectionType fallbackDirection = DirectionType.getOppositeDirection(getDirection());

        for (DirectionType direction : directions) {
            if (isDirectionFree(direction) && direction != fallbackDirection) {
                freeDirections.add(direction);
            }
        }

        if (!freeDirections.isEmpty()) {
            DirectionType direction = freeDirections.get(random.nextInt(freeDirections.size()));
            updateTargetCell(direction);
            setDirection(direction);
            trapped = false;
            return direction;
        }

        if (isDirectionFree(fallbackDirection)) {
            updateTargetCell(fallbackDirection);
            setDirection(fallbackDirection);
            if (!trapped && canPlaceBombs) map.placeBomb(this);
            trapped = false;
            return fallbackDirection;
        }

        trapped = true;
        return getDirection();
    }

    /**
     * Updates the target cell based on direction.
     *
     * @param direction type of the direction
     */
    private void updateTargetCell(DirectionType direction) {
        int[] targetCellCoords = coordsPossibleTargetCell(direction);
        targetX = targetCellCoords[0];
        targetY = targetCellCoords[1];
    }

    /**
     * Updates the target cell directly from coordinates.
     *
     * @param x The x-coordinate of the target cell.
     * @param y The y-coordinate of the target cell.
     */
    private void updateTargetCellFromCoords(int x, int y) {
        targetX = x;
        targetY = y;
    }

    /**
     * Checks whether the target cell is free or not.
     *
     * @param direction type of the direction
     * @return whether it is free or not
     */
    private boolean isDirectionFree(DirectionType direction) {
        int[] targetCellCoords = coordsPossibleTargetCell(direction);
        return map.isCellFree(targetCellCoords[0], targetCellCoords[1]);
    }

    /**
     * Find the coords of the possible target cell.
     *
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
            return switch (getLastDirection()) {
                case UP -> Animations.ENEMY_WALK_UP.getKeyFrame(getElapsedTime(), true);
                case RIGHT -> Animations.ENEMY_WALK_RIGHT.getKeyFrame(getElapsedTime(), true);
                case LEFT -> Animations.ENEMY_WALK_LEFT.getKeyFrame(getElapsedTime(), true);
                case DOWN -> Animations.ENEMY_WALK_DOWN.getKeyFrame(getElapsedTime(), true);
                case NONE -> Textures.ENEMY;
            };
        }
        return Animations.ENEMY_DEATH.getKeyFrame(getElapsedTime(), false);
    }
}
