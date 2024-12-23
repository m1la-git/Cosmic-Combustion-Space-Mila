package de.tum.cit.ase.bomberquest.map;

/**
 * Represents the type of direction for Mobile Objects
 */
public enum DirectionType {
    UP,
    DOWN,
    RIGHT,
    LEFT,
    NONE;
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
