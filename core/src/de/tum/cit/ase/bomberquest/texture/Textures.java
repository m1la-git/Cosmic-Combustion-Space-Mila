package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Contains all texture constants used in the game.
 * It is good practice to keep all textures and animations in constants to avoid loading them multiple times.
 * These can be referenced anywhere they are needed.
 */
public class Textures {
    
    public static final TextureRegion FLOWERS = SpriteSheet.BASIC_TILES.at(2, 5);

    public static final TextureRegion CHEST = SpriteSheet.BASIC_TILES.at(5, 5);

    public static final TextureRegion INDESTRUCTIBLE_WALL = SpriteSheet.BASIC_TILES.at(3, 2);

    public static final TextureRegion DESTRUCTIBLE_WALL = SpriteSheet.BASIC_TILES.at(1, 1);

    public static final TextureRegion BOMB = SpriteSheet.BOMBERMAN.at(4, 1);


    
}
