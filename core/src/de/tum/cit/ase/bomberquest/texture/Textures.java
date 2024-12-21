package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Contains all texture constants used in the game.
 * It is good practice to keep all textures and animations in constants to avoid loading them multiple times.
 * These can be referenced anywhere they are needed.
 */
public class Textures {

    public static final TextureRegion FLOWERS = SpriteSheet.BASIC_TILES.at(2, 5);

    public static final TextureRegion INDESTRUCTIBLE_WALL = SpriteSheet.BASIC_TILES.at(3, 2);

    public static final TextureRegion DESTRUCTIBLE_WALL = SpriteSheet.BASIC_TILES.at(1, 1);

    public static final TextureRegion EXIT = SpriteSheet.BOMBERMAN.at(4, 12);

    public static final TextureRegion BOMBS_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 1);

    public static final TextureRegion FLAMES_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 2);

    public static final TextureRegion SPEED_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 3);

    public static final TextureRegion ENEMY = SpriteSheet.BOMBERMAN.at(16, 7);

    public static final TextureRegion WALLPASS_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 4);
    public static final TextureRegion DETONATOR_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 5);
    public static final TextureRegion BOMBPASS_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 6);
    public static final TextureRegion FLAMEPASS_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 7);
    public static final TextureRegion MYSTERY_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 8);


}
