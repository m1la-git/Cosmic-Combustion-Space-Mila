package de.tum.cit.ase.bomberquest.map;

/**
 * Enumeration of content types that a {@link DestructibleWall} can contain.
 * This enum defines what, if anything, is revealed when a destructible wall is destroyed.
 * It can be empty, contain a game exit, or various power-up items.
 */
public enum WallContentType {
    /**
     * Represents an empty wall, containing no special content when destroyed.
     */
    EMPTY,
    /**
     * Represents a wall that, when destroyed, reveals the level exit.
     */
    EXIT,
    /**
     * Represents a wall that, when destroyed, reveals a power-up that increases the player's bomb capacity.
     */
    BOMBS_POWER_UP,
    /**
     * Represents a wall that, when destroyed, reveals a power-up that increases the blast radius of the player's bombs.
     */
    FLAMES_POWER_UP,
    /**
     * Represents a wall that, when destroyed, reveals a power-up that increases the player's movement speed.
     */
    SPEED_POWER_UP,
    /**
     * Represents a wall that, when destroyed, reveals a power-up allowing the player to pass through walls.
     */
    WALLPASS_POWER_UP,
    /**
     * Represents a wall that, when destroyed, reveals a power-up allowing the player to pass through bombs.
     */
    BOMBPASS_POWER_UP,
    /**
     * Represents a wall that, when destroyed, reveals a power-up allowing the player to pass through flames.
     */
    FLAMEPASS_POWER_UP
}