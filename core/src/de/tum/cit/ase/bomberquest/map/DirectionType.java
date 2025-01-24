package de.tum.cit.ase.bomberquest.map;

/**
 * Enumeration of possible movement directions for {@link MobileObject}s in the game.
 * This enum defines the cardinal directions (up, down, left, right) and a NONE state for no direction.
 * It is used to represent the intended movement of mobile entities such as players and enemies.
 */
public enum DirectionType {
    /**
     * Represents the upward direction.
     */
    UP,
    /**
     * Represents the downward direction.
     */
    DOWN,
    /**
     * Represents the rightward direction.
     */
    RIGHT,
    /**
     * Represents the leftward direction.
     */
    LEFT,
    /**
     * Represents no direction, or a state of not moving.
     */
    NONE;

    /**
     * Gets the opposite direction of a given {@link DirectionType}.
     * For example, the opposite of {@link #UP} is {@link #DOWN}, and vice versa.
     * The opposite of {@link #NONE} is also {@link #NONE}.
     *
     * @param direction The {@link DirectionType} to get the opposite of.
     * @return The opposite {@link DirectionType}.
     */
    public static DirectionType getOppositeDirection(DirectionType direction) {
        return switch (direction) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            default -> NONE;
        };
    }
}