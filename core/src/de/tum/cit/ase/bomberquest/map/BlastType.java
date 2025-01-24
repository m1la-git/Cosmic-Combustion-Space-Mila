package de.tum.cit.ase.bomberquest.map;

/**
 * Enumeration of different blast types in the game.
 * These types define the shape and direction of the explosion visual effect.
 * Each type corresponds to a specific part of the bomb blast pattern.
 */
public enum BlastType {
    /**
     * Represents the central point of the explosion.
     */
    CENTER,
    /**
     * Represents a horizontal blast segment.
     */
    HORIZONTAL,
    /**
     * Represents a vertical blast segment.
     */
    VERTICAL,
    /**
     * Represents a blast segment extending upwards from the center.
     */
    UP,
    /**
     * Represents a blast segment extending to the left from the center.
     */
    LEFT,
    /**
     * Represents a blast segment extending to the right from the center.
     */
    RIGHT,
    /**
     * Represents a blast segment extending downwards from the center.
     */
    DOWN,
    /**
     * Represents a blast that hits a wall or obstacle, potentially having a different visual.
     */
    WALL
}