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
     * player walking down
     */

    public static final Animation<TextureRegion> PLAYER_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(2, 1),
            SpriteSheet.CHARACTER.at(1, 1),
            SpriteSheet.CHARACTER.at(2, 1),
            SpriteSheet.CHARACTER.at(1, 2)
    );
    /**
     * player walking up
     */
    public static final Animation<TextureRegion> PLAYER_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(2, 2),
            SpriteSheet.CHARACTER.at(1, 3),
            SpriteSheet.CHARACTER.at(2, 2),
            SpriteSheet.CHARACTER.at(1, 4)
    );
    /**
     * player walking right
     */
    public static final Animation<TextureRegion> PLAYER_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(1, 5),
            SpriteSheet.CHARACTER.at(1, 6)
    );
    /**
     * player walking left
     */
    public static final Animation<TextureRegion> PLAYER_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(2, 5),
            SpriteSheet.CHARACTER.at(2, 6)
    );
    /**
     * player death animation
     */
    public static final Animation<TextureRegion> PLAYER_DEATH = new Animation<>(0.15f,
            SpriteSheet.CHARACTER.at(5, 1),
            SpriteSheet.CHARACTER.at(5, 2),
            SpriteSheet.CHARACTER.at(5, 3),
            SpriteSheet.CHARACTER.at(5, 4),
            SpriteSheet.CHARACTER.at(5, 5),
            SpriteSheet.CHARACTER.at(5, 6)
    );
    /**
     * enemy walking down or left
     */
    public static final Animation<TextureRegion> ENEMY_WALK_DOWN_OR_LEFT = new Animation<>(0.1f,
            SpriteSheet.BOMBERMAN.at(16, 4),
            SpriteSheet.BOMBERMAN.at(16, 5),
            SpriteSheet.BOMBERMAN.at(16, 6),
            SpriteSheet.BOMBERMAN.at(16, 5)
    );
    /**
     * enemy walking up or right
     */
    public static final Animation<TextureRegion> ENEMY_WALK_UP_OR_RIGHT = new Animation<>(0.1f,
            SpriteSheet.BOMBERMAN.at(16, 1),
            SpriteSheet.BOMBERMAN.at(16, 2),
            SpriteSheet.BOMBERMAN.at(16, 3),
            SpriteSheet.BOMBERMAN.at(16, 2)
    );
    /**
     * enemy death animation
     */
    public static final Animation<TextureRegion> ENEMY_DEATH = new Animation<>(0.21f,
            SpriteSheet.BOMBERMAN.at(16, 7),
            SpriteSheet.BOMBERMAN.at(16, 8),
            SpriteSheet.BOMBERMAN.at(16, 9),
            SpriteSheet.BOMBERMAN.at(16, 10),
            SpriteSheet.BOMBERMAN.at(16, 11)
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
            SpriteSheet.BOMBERMAN.at(7, 3),
            SpriteSheet.BOMBERMAN.at(7, 8),
            SpriteSheet.BOMBERMAN.at(12, 3),
            SpriteSheet.BOMBERMAN.at(12, 8)
    );

    /**
     * horizontal blast
     */
    public static final Animation<TextureRegion> BLAST_HORIZONTAL = new Animation<>(0.1f,
            SpriteSheet.BOMBERMAN.at(7, 2),
            SpriteSheet.BOMBERMAN.at(7, 7),
            SpriteSheet.BOMBERMAN.at(12, 2),
            SpriteSheet.BOMBERMAN.at(12, 7)
    );
    /**
     * vertical blast
     */
    public static final Animation<TextureRegion> BLAST_VERTICAL = new Animation<>(0.1f,
            SpriteSheet.BOMBERMAN.at(6, 3),
            SpriteSheet.BOMBERMAN.at(6, 8),
            SpriteSheet.BOMBERMAN.at(11, 3),
            SpriteSheet.BOMBERMAN.at(11, 8)
    );
    /**
     * last upward blast
     */
    public static final Animation<TextureRegion> BLAST_UP = new Animation<>(0.1f,
            SpriteSheet.BOMBERMAN.at(5, 3),
            SpriteSheet.BOMBERMAN.at(5, 8),
            SpriteSheet.BOMBERMAN.at(10, 3),
            SpriteSheet.BOMBERMAN.at(10, 8)
    );
    /**
     * last downward blast
     */
    public static final Animation<TextureRegion> BLAST_DOWN = new Animation<>(0.1f,
            SpriteSheet.BOMBERMAN.at(9, 3),
            SpriteSheet.BOMBERMAN.at(9, 8),
            SpriteSheet.BOMBERMAN.at(14, 3),
            SpriteSheet.BOMBERMAN.at(14, 8)
    );
    /**
     * last left-bound blast
     */
    public static final Animation<TextureRegion> BLAST_LEFT = new Animation<>(0.1f,
            SpriteSheet.BOMBERMAN.at(7, 1),
            SpriteSheet.BOMBERMAN.at(7, 6),
            SpriteSheet.BOMBERMAN.at(12, 1),
            SpriteSheet.BOMBERMAN.at(12, 6)
    );
    /**
     * last right-bound blast
     */
    public static final Animation<TextureRegion> BLAST_RIGHT = new Animation<>(0.1f,
            SpriteSheet.BOMBERMAN.at(7, 5),
            SpriteSheet.BOMBERMAN.at(7, 10),
            SpriteSheet.BOMBERMAN.at(12, 5),
            SpriteSheet.BOMBERMAN.at(12, 10)
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

}
