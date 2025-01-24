package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Contains all animation constants used in the game.
 * It is good practice to keep all textures and animations in constants to avoid loading them multiple times.
 * These constants can be referenced anywhere animations are needed throughout the game.
 * All animations defined here are loaded from the {@link SpriteSheet} class.
 */
public class Animations {
    /**
     * Animation for player 1 walking downwards.
     * Consists of frames to create a walking animation when the player moves down.
     */
    public static final Animation<TextureRegion> PLAYER1_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(1, 1),
            SpriteSheet.MOBILE_OBJECTS.at(1, 2),
            SpriteSheet.MOBILE_OBJECTS.at(1, 1),
            SpriteSheet.MOBILE_OBJECTS.at(1, 3)
    );
    /**
     * Animation for player 1 walking upwards.
     * Consists of frames to create a walking animation when the player moves up.
     */
    public static final Animation<TextureRegion> PLAYER1_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(1, 4),
            SpriteSheet.MOBILE_OBJECTS.at(1, 5),
            SpriteSheet.MOBILE_OBJECTS.at(1, 4),
            SpriteSheet.MOBILE_OBJECTS.at(1, 6)
    );
    /**
     * Animation for player 1 walking to the right.
     * Consists of frames to create a walking animation when the player moves right.
     */
    public static final Animation<TextureRegion> PLAYER1_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(2, 1),
            SpriteSheet.MOBILE_OBJECTS.at(2, 2),
            SpriteSheet.MOBILE_OBJECTS.at(2, 1),
            SpriteSheet.MOBILE_OBJECTS.at(2, 3)
    );
    /**
     * Animation for player 1 walking to the left.
     * Consists of frames to create a walking animation when the player moves left.
     */
    public static final Animation<TextureRegion> PLAYER1_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(2, 4),
            SpriteSheet.MOBILE_OBJECTS.at(2, 5),
            SpriteSheet.MOBILE_OBJECTS.at(2, 4),
            SpriteSheet.MOBILE_OBJECTS.at(2, 6)
    );
    /**
     * Animation for player 2 walking downwards.
     * Consists of frames to create a walking animation when player 2 moves down.
     */
    public static final Animation<TextureRegion> PLAYER2_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(1, 7),
            SpriteSheet.MOBILE_OBJECTS.at(1, 8),
            SpriteSheet.MOBILE_OBJECTS.at(1, 7),
            SpriteSheet.MOBILE_OBJECTS.at(1, 9)
    );
    /**
     * Animation for player 2 walking upwards.
     * Consists of frames to create a walking animation when player 2 moves up.
     */
    public static final Animation<TextureRegion> PLAYER2_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(1, 10),
            SpriteSheet.MOBILE_OBJECTS.at(1, 11),
            SpriteSheet.MOBILE_OBJECTS.at(1, 10),
            SpriteSheet.MOBILE_OBJECTS.at(1, 12)
    );
    /**
     * Animation for player 2 walking to the right.
     * Consists of frames to create a walking animation when player 2 moves right.
     */
    public static final Animation<TextureRegion> PLAYER2_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(2, 7),
            SpriteSheet.MOBILE_OBJECTS.at(2, 8),
            SpriteSheet.MOBILE_OBJECTS.at(2, 7),
            SpriteSheet.MOBILE_OBJECTS.at(2, 9)
    );
    /**
     * Animation for player 2 walking to the left.
     * Consists of frames to create a walking animation when player 2 moves left.
     */
    public static final Animation<TextureRegion> PLAYER2_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(2, 10),
            SpriteSheet.MOBILE_OBJECTS.at(2, 11),
            SpriteSheet.MOBILE_OBJECTS.at(2, 10),
            SpriteSheet.MOBILE_OBJECTS.at(2, 12)
    );

    /**
     * Animation for player death.
     * Displays a sequence of frames showing the player character dying.
     */
    public static final Animation<TextureRegion> PLAYER_DEATH = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(3, 7),
            SpriteSheet.MOBILE_OBJECTS.at(3, 7),
            SpriteSheet.MOBILE_OBJECTS.at(3, 7),
            SpriteSheet.MOBILE_OBJECTS.at(3, 7),
            SpriteSheet.MOBILE_OBJECTS.at(3, 8),
            SpriteSheet.MOBILE_OBJECTS.at(3, 9),
            SpriteSheet.MOBILE_OBJECTS.at(3, 10),
            SpriteSheet.MOBILE_OBJECTS.at(3, 11),
            SpriteSheet.MOBILE_OBJECTS.at(3, 12),
            SpriteSheet.STATIONARY_OBJECTS.at(1, 8)
    );


    /**
     * Animation for enemy walking downwards.
     * Consists of frames to create a walking animation when the enemy moves down.
     */
    public static final Animation<TextureRegion> ENEMY_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(3, 1),
            SpriteSheet.MOBILE_OBJECTS.at(3, 2),
            SpriteSheet.MOBILE_OBJECTS.at(3, 1),
            SpriteSheet.MOBILE_OBJECTS.at(3, 3)
    );
    /**
     * Animation for enemy walking upwards.
     * Consists of frames to create a walking animation when the enemy moves up.
     */
    public static final Animation<TextureRegion> ENEMY_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(3, 4),
            SpriteSheet.MOBILE_OBJECTS.at(3, 5),
            SpriteSheet.MOBILE_OBJECTS.at(3, 4),
            SpriteSheet.MOBILE_OBJECTS.at(3, 6)
    );
    /**
     * Animation for enemy walking to the right.
     * Consists of frames to create a walking animation when the enemy moves right.
     */
    public static final Animation<TextureRegion> ENEMY_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(4, 1),
            SpriteSheet.MOBILE_OBJECTS.at(4, 2),
            SpriteSheet.MOBILE_OBJECTS.at(4, 1),
            SpriteSheet.MOBILE_OBJECTS.at(4, 3)
    );
    /**
     * Animation for enemy walking to the left.
     * Consists of frames to create a walking animation when the enemy moves left.
     */
    public static final Animation<TextureRegion> ENEMY_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(4, 4),
            SpriteSheet.MOBILE_OBJECTS.at(4, 5),
            SpriteSheet.MOBILE_OBJECTS.at(4, 4),
            SpriteSheet.MOBILE_OBJECTS.at(4, 6)
    );

    /**
     * Animation for enemy death.
     * Displays a sequence of frames showing the enemy character dying.
     */
    public static final Animation<TextureRegion> ENEMY_DEATH = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(4, 7),
            SpriteSheet.MOBILE_OBJECTS.at(4, 7),
            SpriteSheet.MOBILE_OBJECTS.at(4, 7),
            SpriteSheet.MOBILE_OBJECTS.at(4, 7),
            SpriteSheet.MOBILE_OBJECTS.at(4, 8),
            SpriteSheet.MOBILE_OBJECTS.at(4, 9),
            SpriteSheet.MOBILE_OBJECTS.at(4, 10),
            SpriteSheet.MOBILE_OBJECTS.at(4, 11),
            SpriteSheet.MOBILE_OBJECTS.at(4, 12),
            SpriteSheet.STATIONARY_OBJECTS.at(1, 8)
    );
    /**
     * Animation for enemy's bomb.
     * Displays a sequence of frames animating the enemy bomb.
     */
    public static final Animation<TextureRegion> BOMB_ENEMY = new Animation<>(0.03f,
            SpriteSheet.BOMBS_AND_BLASTS.at(14, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(14, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(14, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(14, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(14, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(14, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(14, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(14, 8)
    );
    /**
     * Animation for player 1's bomb.
     * Displays a sequence of frames animating player 1's bomb.
     */
    public static final Animation<TextureRegion> BOMB_P1 = new Animation<>(0.03f,
            SpriteSheet.BOMBS_AND_BLASTS.at(15, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(15, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(15, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(15, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(15, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(15, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(15, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(15, 8)
    );
    /**
     * Animation for player 2's bomb.
     * Displays a sequence of frames animating player 2's bomb.
     */
    public static final Animation<TextureRegion> BOMB_P2 = new Animation<>(0.03f,
            SpriteSheet.BOMBS_AND_BLASTS.at(16, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(16, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(16, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(16, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(16, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(16, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(16, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(16, 8)
    );
    /**
     * Animation for the center of a blast.
     * Displays a sequence of frames animating the central explosion of a bomb.
     */
    public static final Animation<TextureRegion> BLAST_CENTER = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 3)
    );

    /**
     * Animation for a horizontal blast segment.
     * Displays a sequence of frames animating a horizontal part of a bomb's explosion.
     */
    public static final Animation<TextureRegion> BLAST_HORIZONTAL = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 2)
    );
    /**
     * Animation for a vertical blast segment.
     * Displays a sequence of frames animating a vertical part of a bomb's explosion.
     */
    public static final Animation<TextureRegion> BLAST_VERTICAL = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 6)
    );
    /**
     * Animation for the upward end of a blast.
     * Displays a sequence of frames animating the tip of a blast going upwards.
     */
    public static final Animation<TextureRegion> BLAST_UP = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 5)
    );
    /**
     * Animation for the downward end of a blast.
     * Displays a sequence of frames animating the tip of a blast going downwards.
     */
    public static final Animation<TextureRegion> BLAST_DOWN = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 7)
    );
    /**
     * Animation for the leftward end of a blast.
     * Displays a sequence of frames animating the tip of a blast going leftwards.
     */
    public static final Animation<TextureRegion> BLAST_LEFT = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 1)
    );
    /**
     * Animation for the rightward end of a blast.
     * Displays a sequence of frames animating the tip of a blast going rightwards.
     */
    public static final Animation<TextureRegion> BLAST_RIGHT = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(5, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(4, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(3, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(2, 4)
    );

    /**
     * Animation for the center of a blast for player 1.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_CENTER_P1 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 3)
    );

    /**
     * Animation for a horizontal blast segment for player 1.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_HORIZONTAL_P1 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 2)
    );
    /**
     * Animation for a vertical blast segment for player 1.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_VERTICAL_P1 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 6)
    );
    /**
     * Animation for the upward end of a blast for player 1.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_UP_P1 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 5)
    );
    /**
     * Animation for the downward end of a blast for player 1.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_DOWN_P1 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 7)
    );
    /**
     * Animation for the leftward end of a blast for player 1.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_LEFT_P1 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 1)
    );
    /**
     * Animation for the rightward end of a blast for player 1.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_RIGHT_P1 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(9, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(8, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(7, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(6, 4)
    );

    /**
     * Animation for the center of a blast for player 2.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_CENTER_P2 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 3)
    );

    /**
     * Animation for a horizontal blast segment for player 2.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_HORIZONTAL_P2 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 2)
    );
    /**
     * Animation for a vertical blast segment for player 2.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_VERTICAL_P2 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 6),
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 6)
    );
    /**
     * Animation for the upward end of a blast for player 2.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_UP_P2 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 5)
    );
    /**
     * Animation for the downward end of a blast for player 2.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_DOWN_P2 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 7),
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 7)
    );
    /**
     * Animation for the leftward end of a blast for player 2.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_LEFT_P2 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 1)
    );
    /**
     * Animation for the rightward end of a blast for player 2.
     * Player-specific colored blast animation.
     */
    public static final Animation<TextureRegion> BLAST_RIGHT_P2 = new Animation<>(0.4f / 8,
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(13, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(12, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(11, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(10, 4)
    );


    /**
     * Animation for wall destruction blast.
     * Displays a sequence of frames showing a wall being destroyed by a blast.
     */
    public static final Animation<TextureRegion> BLAST_WALL = new Animation<>(0.4f / 7,
            SpriteSheet.BOMBS_AND_BLASTS.at(1, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(1, 1),
            SpriteSheet.BOMBS_AND_BLASTS.at(1, 2),
            SpriteSheet.BOMBS_AND_BLASTS.at(1, 3),
            SpriteSheet.BOMBS_AND_BLASTS.at(1, 4),
            SpriteSheet.BOMBS_AND_BLASTS.at(1, 5),
            SpriteSheet.BOMBS_AND_BLASTS.at(1, 6)
    );

    /**
     * Animation for a closed exit.
     * Displays a sequence of frames animating the exit in its closed state.
     */
    public static final Animation<TextureRegion> EXIT_CLOSED = new Animation<>(0.08f,
            SpriteSheet.STATIONARY_OBJECTS.at(3, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(3, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(3, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(3, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(3, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(3, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(3, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(3, 8),
            SpriteSheet.STATIONARY_OBJECTS.at(4, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(4, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(4, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(4, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(4, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(4, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(4, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(4, 8)
    );

    /**
     * Animation for an opened exit.
     * Displays a sequence of frames animating the exit in its opened state.
     */
    public static final Animation<TextureRegion> EXIT_OPENED = new Animation<>(0.13f,
            SpriteSheet.STATIONARY_OBJECTS.at(2, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(2, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(2, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(2, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(2, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(2, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(2, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(2, 8)
    );

    /**
     * Animation for bombs power-up item.
     * Displays a sequence of frames animating the bombs power-up item.
     */
    public static final Animation<TextureRegion> BOMBS_POWER_UP = new Animation<>(0.13f,
            SpriteSheet.STATIONARY_OBJECTS.at(5, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(5, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(5, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(5, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(5, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(5, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(5, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(5, 8)
    );
    /**
     * Animation for flames power-up item.
     * Displays a sequence of frames animating the flames power-up item.
     */
    public static final Animation<TextureRegion> FLAMES_POWER_UP = new Animation<>(0.13f,
            SpriteSheet.STATIONARY_OBJECTS.at(6, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(6, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(6, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(6, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(6, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(6, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(6, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(6, 8)
    );
    /**
     * Animation for speed power-up item.
     * Displays a sequence of frames animating the speed power-up item.
     */
    public static final Animation<TextureRegion> SPEED_POWER_UP = new Animation<>(0.13f,
            SpriteSheet.STATIONARY_OBJECTS.at(7, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(7, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(7, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(7, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(7, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(7, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(7, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(7, 8)
    );
    /**
     * Animation for wallpass power-up item.
     * Displays a sequence of frames animating the wallpass power-up item.
     */
    public static final Animation<TextureRegion> WALLPASS_POWER_UP = new Animation<>(0.13f,
            SpriteSheet.STATIONARY_OBJECTS.at(8, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(8, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(8, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(8, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(8, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(8, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(8, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(8, 8)
    );
    /**
     * Animation for detonator power-up item. //TODO: not used in current version
     * Displays a sequence of frames animating the detonator power-up item.
     */
    public static final Animation<TextureRegion> DETONATOR_POWER_UP = new Animation<>(0.13f,
            SpriteSheet.STATIONARY_OBJECTS.at(9, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(9, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(9, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(9, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(9, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(9, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(9, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(9, 8)
    );
    /**
     * Animation for bombpass power-up item.
     * Displays a sequence of frames animating the bombpass power-up item.
     */
    public static final Animation<TextureRegion> BOMBPASS_POWER_UP = new Animation<>(0.13f,
            SpriteSheet.STATIONARY_OBJECTS.at(10, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(10, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(10, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(10, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(10, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(10, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(10, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(10, 8)
    );
    /**
     * Animation for flamepass power-up item.
     * Displays a sequence of frames animating the flamepass power-up item.
     */
    public static final Animation<TextureRegion> FLAMEPASS_POWER_UP = new Animation<>(0.13f,
            SpriteSheet.STATIONARY_OBJECTS.at(11, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(11, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(11, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(11, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(11, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(11, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(11, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(11, 8)
    );
    /**
     * Animation for mystery power-up item. //TODO: not used in current version
     * Displays a sequence of frames animating the mystery power-up item.
     */
    public static final Animation<TextureRegion> MYSTERY_POWER_UP = new Animation<>(0.13f,
            SpriteSheet.STATIONARY_OBJECTS.at(12, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(12, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(12, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(12, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(12, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(12, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(12, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(12, 8)
    );

    /**
     * Animation for +100 points display for player 1.
     * Displays a sequence of frames animating the "+100" points text in player 1's color.
     */
    public static final Animation<TextureRegion> PLUS_POINTS_P1 = new Animation<>(1f / 8,
            SpriteSheet.STATIONARY_OBJECTS.at(13, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(13, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(13, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(13, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(13, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(13, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(13, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(13, 8)
    );

    /**
     * Animation for +100 points display for player 2.
     * Displays a sequence of frames animating the "+100" points text in player 2's color.
     */
    public static final Animation<TextureRegion> PLUS_POINTS_P2 = new Animation<>(1f / 8,
            SpriteSheet.STATIONARY_OBJECTS.at(14, 1),
            SpriteSheet.STATIONARY_OBJECTS.at(14, 2),
            SpriteSheet.STATIONARY_OBJECTS.at(14, 3),
            SpriteSheet.STATIONARY_OBJECTS.at(14, 4),
            SpriteSheet.STATIONARY_OBJECTS.at(14, 5),
            SpriteSheet.STATIONARY_OBJECTS.at(14, 6),
            SpriteSheet.STATIONARY_OBJECTS.at(14, 7),
            SpriteSheet.STATIONARY_OBJECTS.at(14, 8)
    );


}