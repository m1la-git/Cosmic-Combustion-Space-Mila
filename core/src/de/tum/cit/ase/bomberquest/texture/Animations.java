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
     * The animation for the character walking down.
     */
    public static final Animation<TextureRegion> CHARACTER_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(1, 1),
            SpriteSheet.CHARACTER.at(1, 2),
            SpriteSheet.CHARACTER.at(1, 3),
            SpriteSheet.CHARACTER.at(1, 4)
    );
    /**
     * The animation for the character walking up.
     */
    public static final Animation<TextureRegion> CHARACTER_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(3, 1),
            SpriteSheet.CHARACTER.at(3, 2),
            SpriteSheet.CHARACTER.at(3, 3),
            SpriteSheet.CHARACTER.at(3, 4)
    );
    /**
     * The animation for the character walking right.
     */
    public static final Animation<TextureRegion> CHARACTER_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(2, 1),
            SpriteSheet.CHARACTER.at(2, 2),
            SpriteSheet.CHARACTER.at(2, 3),
            SpriteSheet.CHARACTER.at(2, 4)
    );
    /**
     * The animation for the character walking left.
     */
    public static final Animation<TextureRegion> CHARACTER_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(4, 1),
            SpriteSheet.CHARACTER.at(4, 2),
            SpriteSheet.CHARACTER.at(4, 3),
            SpriteSheet.CHARACTER.at(4, 4)
    );


    public static final Animation<TextureRegion> ENEMY_WALK_DOWN_OR_LEFT = new Animation<>(0.1f,
            SpriteSheet.BOMBERMAN.at(16, 4),
            SpriteSheet.BOMBERMAN.at(16, 5),
            SpriteSheet.BOMBERMAN.at(16, 6),
            SpriteSheet.BOMBERMAN.at(16, 5)
    );
    public static final Animation<TextureRegion> ENEMY_WALK_UP_OR_RIGHT = new Animation<>(0.1f,
            SpriteSheet.BOMBERMAN.at(16, 1),
            SpriteSheet.BOMBERMAN.at(16, 2),
            SpriteSheet.BOMBERMAN.at(16, 3),
            SpriteSheet.BOMBERMAN.at(16, 2)
    );


    /**
     * The animation for the bomb.
     */
    public static final Animation<TextureRegion> BOMB = new Animation<>(0.15f,
            SpriteSheet.BOMBERMAN.at(4, 1),
            SpriteSheet.BOMBERMAN.at(4, 2),
            SpriteSheet.BOMBERMAN.at(4, 3),
            SpriteSheet.BOMBERMAN.at(4, 2)
    );

    public static final Animation<TextureRegion> BLAST_CENTER = new Animation<>(0.15f,
            SpriteSheet.BOMBERMAN.at(7, 3),
            SpriteSheet.BOMBERMAN.at(7, 8),
            SpriteSheet.BOMBERMAN.at(12, 3),
            SpriteSheet.BOMBERMAN.at(12, 8)
    );

    public static final Animation<TextureRegion> BLAST_HORIZONTAL = new Animation<>(0.15f,
            SpriteSheet.BOMBERMAN.at(7, 2),
            SpriteSheet.BOMBERMAN.at(7, 7),
            SpriteSheet.BOMBERMAN.at(12, 2),
            SpriteSheet.BOMBERMAN.at(12, 7)
    );
    public static final Animation<TextureRegion> BLAST_VERTICAL = new Animation<>(0.15f,
            SpriteSheet.BOMBERMAN.at(6, 3),
            SpriteSheet.BOMBERMAN.at(6, 8),
            SpriteSheet.BOMBERMAN.at(11, 3),
            SpriteSheet.BOMBERMAN.at(11, 8)
    );
    public static final Animation<TextureRegion> BLAST_UP = new Animation<>(0.15f,
            SpriteSheet.BOMBERMAN.at(5, 3),
            SpriteSheet.BOMBERMAN.at(5, 8),
            SpriteSheet.BOMBERMAN.at(10, 3),
            SpriteSheet.BOMBERMAN.at(10, 8)
    );
    public static final Animation<TextureRegion> BLAST_DOWN = new Animation<>(0.15f,
            SpriteSheet.BOMBERMAN.at(9, 3),
            SpriteSheet.BOMBERMAN.at(9, 8),
            SpriteSheet.BOMBERMAN.at(14, 3),
            SpriteSheet.BOMBERMAN.at(14, 8)
    );
    public static final Animation<TextureRegion> BLAST_LEFT = new Animation<>(0.15f,
            SpriteSheet.BOMBERMAN.at(7, 1),
            SpriteSheet.BOMBERMAN.at(7, 6),
            SpriteSheet.BOMBERMAN.at(12, 1),
            SpriteSheet.BOMBERMAN.at(12, 6)
    );
    public static final Animation<TextureRegion> BLAST_RIGHT = new Animation<>(0.15f,
            SpriteSheet.BOMBERMAN.at(7, 5),
            SpriteSheet.BOMBERMAN.at(7, 10),
            SpriteSheet.BOMBERMAN.at(12, 5),
            SpriteSheet.BOMBERMAN.at(12, 10)
    );
    public static final Animation<TextureRegion> BLAST_WALL = new Animation<>(0.1f,
            SpriteSheet.BOMBERMAN.at(4, 6),
            SpriteSheet.BOMBERMAN.at(4, 7),
            SpriteSheet.BOMBERMAN.at(4, 8),
            SpriteSheet.BOMBERMAN.at(4, 9),
            SpriteSheet.BOMBERMAN.at(4, 10),
            SpriteSheet.BOMBERMAN.at(4, 11)
    );

}
