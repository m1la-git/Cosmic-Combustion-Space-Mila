package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Contains all animation constants used in the game.
 * It is good practice to keep all textures and animations in constants to avoid loading them multiple times.
 * These can be referenced anywhere they are needed.
 */
public class Animations {
    /**
     * player1 walking down
     */
    public static final Animation<TextureRegion> PLAYER1_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(1, 1),
            SpriteSheet.MOBILE_OBJECTS.at(1, 2),
            SpriteSheet.MOBILE_OBJECTS.at(1, 1),
            SpriteSheet.MOBILE_OBJECTS.at(1, 3)
    );
    /**
     * player1 walking up
     */
    public static final Animation<TextureRegion> PLAYER1_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(1, 4),
            SpriteSheet.MOBILE_OBJECTS.at(1, 5),
            SpriteSheet.MOBILE_OBJECTS.at(1, 4),
            SpriteSheet.MOBILE_OBJECTS.at(1, 6)
    );
    /**
     * player1 walking right
     */
    public static final Animation<TextureRegion> PLAYER1_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(2, 1),
            SpriteSheet.MOBILE_OBJECTS.at(2, 2),
            SpriteSheet.MOBILE_OBJECTS.at(2, 1),
            SpriteSheet.MOBILE_OBJECTS.at(2, 3)
    );
    /**
     * player1 walking left
     */
    public static final Animation<TextureRegion> PLAYER1_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(2, 4),
            SpriteSheet.MOBILE_OBJECTS.at(2, 5),
            SpriteSheet.MOBILE_OBJECTS.at(2, 4),
            SpriteSheet.MOBILE_OBJECTS.at(2, 6)
    );
    /**
     * player2 walking down
     */
    public static final Animation<TextureRegion> PLAYER2_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(1, 7),
            SpriteSheet.MOBILE_OBJECTS.at(1, 8),
            SpriteSheet.MOBILE_OBJECTS.at(1, 7),
            SpriteSheet.MOBILE_OBJECTS.at(1, 9)
    );
    /**
     * player2 walking up
     */
    public static final Animation<TextureRegion> PLAYER2_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(1, 10),
            SpriteSheet.MOBILE_OBJECTS.at(1, 11),
            SpriteSheet.MOBILE_OBJECTS.at(1, 10),
            SpriteSheet.MOBILE_OBJECTS.at(1, 12)
    );
    /**
     * player2 walking right
     */
    public static final Animation<TextureRegion> PLAYER2_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(2, 7),
            SpriteSheet.MOBILE_OBJECTS.at(2, 8),
            SpriteSheet.MOBILE_OBJECTS.at(2, 7),
            SpriteSheet.MOBILE_OBJECTS.at(2, 9)
    );
    /**
     * player2 walking left
     */
    public static final Animation<TextureRegion> PLAYER2_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(2, 10),
            SpriteSheet.MOBILE_OBJECTS.at(2, 11),
            SpriteSheet.MOBILE_OBJECTS.at(2, 10),
            SpriteSheet.MOBILE_OBJECTS.at(2, 12)
    );

    /**
     * player death animation
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
            SpriteSheet.STATIONARY_OBJECTS.at(1, 4)
    );


    /**
     * enemy walking down
     */
    public static final Animation<TextureRegion> ENEMY_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(3, 1),
            SpriteSheet.MOBILE_OBJECTS.at(3, 2),
            SpriteSheet.MOBILE_OBJECTS.at(3, 1),
            SpriteSheet.MOBILE_OBJECTS.at(3, 3)
    );
    /**
     * enemy walking up
     */
    public static final Animation<TextureRegion> ENEMY_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(3, 4),
            SpriteSheet.MOBILE_OBJECTS.at(3, 5),
            SpriteSheet.MOBILE_OBJECTS.at(3, 4),
            SpriteSheet.MOBILE_OBJECTS.at(3, 6)
    );
    /**
     * enemy walking right
     */
    public static final Animation<TextureRegion> ENEMY_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(4, 1),
            SpriteSheet.MOBILE_OBJECTS.at(4, 2),
            SpriteSheet.MOBILE_OBJECTS.at(4, 1),
            SpriteSheet.MOBILE_OBJECTS.at(4, 3)
    );
    /**
     * enemy walking left
     */
    public static final Animation<TextureRegion> ENEMY_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBILE_OBJECTS.at(4, 4),
            SpriteSheet.MOBILE_OBJECTS.at(4, 5),
            SpriteSheet.MOBILE_OBJECTS.at(4, 4),
            SpriteSheet.MOBILE_OBJECTS.at(4, 6)
    );

    /**
     * enemy death animation
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
            SpriteSheet.STATIONARY_OBJECTS.at(1, 4)
    );
    /**
     * bomb animation
     */
    public static final Animation<TextureRegion> BOMB = new Animation<>(0.15f,
            SpriteSheet.BOMBERMAN.at(4, 1),
            SpriteSheet.BOMBERMAN.at(4, 2),
            SpriteSheet.BOMBERMAN.at(4, 3),
            SpriteSheet.BOMBERMAN.at(4, 2)
    );
    /**
     * blast center
     */
    public static final Animation<TextureRegion> BLAST_CENTER = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(2, 3),
            SpriteSheet.BOMB.at(3, 3),
            SpriteSheet.BOMB.at(4, 3),
            SpriteSheet.BOMB.at(5, 3)
    );

    /**
     * horizontal blast
     */
    public static final Animation<TextureRegion> BLAST_HORIZONTAL = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(2, 2),
            SpriteSheet.BOMB.at(3, 2),
            SpriteSheet.BOMB.at(4, 2),
            SpriteSheet.BOMB.at(5, 2)
    );
    /**
     * vertical blast
     */
    public static final Animation<TextureRegion> BLAST_VERTICAL = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(2, 6),
            SpriteSheet.BOMB.at(3, 6),
            SpriteSheet.BOMB.at(4, 6),
            SpriteSheet.BOMB.at(5, 6)
    );
    /**
     * last upward blast
     */
    public static final Animation<TextureRegion> BLAST_UP = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(2, 5),
            SpriteSheet.BOMB.at(3, 5),
            SpriteSheet.BOMB.at(4, 5),
            SpriteSheet.BOMB.at(5, 5)
    );
    /**
     * last downward blast
     */
    public static final Animation<TextureRegion> BLAST_DOWN = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(2, 7),
            SpriteSheet.BOMB.at(3, 7),
            SpriteSheet.BOMB.at(4, 7),
            SpriteSheet.BOMB.at(5, 7)
    );
    /**
     * last left-bound blast
     */
    public static final Animation<TextureRegion> BLAST_LEFT = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(2, 1),
            SpriteSheet.BOMB.at(3, 1),
            SpriteSheet.BOMB.at(4, 1),
            SpriteSheet.BOMB.at(5, 1)
    );
    /**
     * last right-bound blast
     */
    public static final Animation<TextureRegion> BLAST_RIGHT = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(2, 4),
            SpriteSheet.BOMB.at(3, 4),
            SpriteSheet.BOMB.at(4, 4),
            SpriteSheet.BOMB.at(5, 4)
    );

    /**
     * blast center player1
     */
    public static final Animation<TextureRegion> BLAST_CENTER_P1 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(6, 3),
            SpriteSheet.BOMB.at(7, 3),
            SpriteSheet.BOMB.at(8, 3),
            SpriteSheet.BOMB.at(9, 3)
    );

    /**
     * horizontal blast player1
     */
    public static final Animation<TextureRegion> BLAST_HORIZONTAL_P1 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(6, 2),
            SpriteSheet.BOMB.at(7, 2),
            SpriteSheet.BOMB.at(8, 2),
            SpriteSheet.BOMB.at(9, 2)
    );
    /**
     * vertical blast player1
     */
    public static final Animation<TextureRegion> BLAST_VERTICAL_P1 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(6, 6),
            SpriteSheet.BOMB.at(7, 6),
            SpriteSheet.BOMB.at(8, 6),
            SpriteSheet.BOMB.at(9, 6)
    );
    /**
     * last upward blast player1
     */
    public static final Animation<TextureRegion> BLAST_UP_P1 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(6, 5),
            SpriteSheet.BOMB.at(7, 5),
            SpriteSheet.BOMB.at(8, 5),
            SpriteSheet.BOMB.at(9, 5)
    );
    /**
     * last downward blast player1
     */
    public static final Animation<TextureRegion> BLAST_DOWN_P1 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(6, 7),
            SpriteSheet.BOMB.at(7, 7),
            SpriteSheet.BOMB.at(8, 7),
            SpriteSheet.BOMB.at(9, 7)
    );
    /**
     * last left-bound blast player1
     */
    public static final Animation<TextureRegion> BLAST_LEFT_P1 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(6, 1),
            SpriteSheet.BOMB.at(7, 1),
            SpriteSheet.BOMB.at(8, 1),
            SpriteSheet.BOMB.at(9, 1)
    );
    /**
     * last right-bound blast player1
     */
    public static final Animation<TextureRegion> BLAST_RIGHT_P1 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(6, 4),
            SpriteSheet.BOMB.at(7, 4),
            SpriteSheet.BOMB.at(8, 4),
            SpriteSheet.BOMB.at(9, 4)
    );

    /**
     * blast center player2
     */
    public static final Animation<TextureRegion> BLAST_CENTER_P2 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(10, 3),
            SpriteSheet.BOMB.at(11, 3),
            SpriteSheet.BOMB.at(12, 3),
            SpriteSheet.BOMB.at(13, 3)
    );

    /**
     * horizontal blast player2
     */
    public static final Animation<TextureRegion> BLAST_HORIZONTAL_P2 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(10, 2),
            SpriteSheet.BOMB.at(11, 2),
            SpriteSheet.BOMB.at(12, 2),
            SpriteSheet.BOMB.at(13, 2)
    );
    /**
     * vertical blast player2
     */
    public static final Animation<TextureRegion> BLAST_VERTICAL_P2 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(10, 6),
            SpriteSheet.BOMB.at(11, 6),
            SpriteSheet.BOMB.at(12, 6),
            SpriteSheet.BOMB.at(13, 6)
    );
    /**
     * last upward blast player2
     */
    public static final Animation<TextureRegion> BLAST_UP_P2 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(10, 5),
            SpriteSheet.BOMB.at(11, 5),
            SpriteSheet.BOMB.at(12, 5),
            SpriteSheet.BOMB.at(13, 5)
    );
    /**
     * last downward blast player2
     */
    public static final Animation<TextureRegion> BLAST_DOWN_P2 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(10, 7),
            SpriteSheet.BOMB.at(11, 7),
            SpriteSheet.BOMB.at(12, 7),
            SpriteSheet.BOMB.at(13, 7)
    );
    /**
     * last left-bound blast player2
     */
    public static final Animation<TextureRegion> BLAST_LEFT_P2 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(10, 1),
            SpriteSheet.BOMB.at(11, 1),
            SpriteSheet.BOMB.at(12, 1),
            SpriteSheet.BOMB.at(13, 1)
    );
    /**
     * last right-bound blast player2
     */
    public static final Animation<TextureRegion> BLAST_RIGHT_P2 = new Animation<>(0.1f,
            SpriteSheet.BOMB.at(10, 4),
            SpriteSheet.BOMB.at(11, 4),
            SpriteSheet.BOMB.at(12, 4),
            SpriteSheet.BOMB.at(13, 4)
    );


    /**
     * wall destruction animation
     */
    public static final Animation<TextureRegion> BLAST_WALL = new Animation<>(0.4f / 6,
            SpriteSheet.BOMBERMAN.at(4, 6),
            SpriteSheet.BOMBERMAN.at(4, 7),
            SpriteSheet.BOMBERMAN.at(4, 8),
            SpriteSheet.BOMBERMAN.at(4, 9),
            SpriteSheet.BOMBERMAN.at(4, 10),
            SpriteSheet.BOMBERMAN.at(4, 11)
    );

    /**
     * closed exit animation
     */
    public static final Animation<TextureRegion> EXIT_CLOSED = new Animation<>(0.06f,
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
     * open exit animation
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


}
