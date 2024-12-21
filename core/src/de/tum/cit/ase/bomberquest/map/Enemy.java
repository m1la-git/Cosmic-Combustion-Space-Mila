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

public class Enemy extends MobileObject implements Drawable { // Change direction every 2 seconds
    private final Random random = new Random();
    private final GameMap map;
    private boolean reachedCell;
    private int targetCellX;
    private int targetCellY;
    private DirectionType lastDirection = DirectionType.NONE;

    public Enemy(World world, float x, float y, GameMap map) {
        super(world, x, y, 2, 0.5f, BodyDef.BodyType.KinematicBody);
        this.map = map;
        reachedCell = true;
    }

    @Override
    public void tick(float frameTime) {
        increaseElapsedTime(frameTime);
        if (Math.abs(getX() - targetCellX) < 0.1 && Math.abs(getY() - targetCellY) < 0.1) {
            reachedCell = true;
            getHitbox().setTransform(getCellX(), getCellY(), getHitbox().getAngle());
        }
        if (reachedCell) {
            setDirection(selectFreeDirection());
            reachedCell = false;
        }
        moveInDirection(getDirection(), frameTime);

    }

    private DirectionType selectFreeDirection() {
        // Randomly pick one of the four directions
        DirectionType[] directions = {DirectionType.UP, DirectionType.DOWN, DirectionType.LEFT, DirectionType.RIGHT};
        List<DirectionType> freeDirections = new ArrayList<>();

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

        return DirectionType.NONE;
    }

    private void updateTargetCell(DirectionType direction) {
        targetCellX = getCellX();
        targetCellY = getCellY();
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
    }

    private boolean isDirectionFree(DirectionType direction) {
        int currentX = getCellX();
        int currentY = getCellY();
        int targetX = currentX;
        int targetY = currentY;

        switch (direction) {
            case UP:
                targetY += 1;
                break;
            case DOWN:
                targetY -= 1;
                break;
            case LEFT:
                targetX -= 1;
                break;
            case RIGHT:
                targetX += 1;
                break;
        }

        // Check if the target cell is free of StationaryObjects
        return map.isCellFree(targetX, targetY);
    }

    private void moveInDirection(DirectionType direction, float frameTime) {

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


    @Override
    public TextureRegion getCurrentAppearance() {
        return switch (getDirection()) {
            case UP, RIGHT -> Animations.ENEMY_WALK_UP_OR_RIGHT.getKeyFrame(getElapsedTime(), true);
            case DOWN, LEFT -> Animations.ENEMY_WALK_DOWN_OR_LEFT.getKeyFrame(getElapsedTime(), true);
            case NONE -> Textures.ENEMY;
        };
    }
}
