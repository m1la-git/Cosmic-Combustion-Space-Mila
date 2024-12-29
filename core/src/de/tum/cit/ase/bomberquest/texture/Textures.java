package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Contains all texture constants used in the game.
 * It is good practice to keep all textures and animations in constants to avoid loading them multiple times.
 * These can be referenced anywhere they are needed.
 */
public class Textures {

    public static final TextureRegion PLAYER1_DOWN = SpriteSheet.MOBILE_OBJECTS.at(1, 1);
    public static final TextureRegion PLAYER1_UP = SpriteSheet.MOBILE_OBJECTS.at(1, 4);
    public static final TextureRegion PLAYER1_LEFT = SpriteSheet.MOBILE_OBJECTS.at(2, 4);
    public static final TextureRegion PLAYER1_RIGHT = SpriteSheet.MOBILE_OBJECTS.at(2, 1);

    public static final TextureRegion PLAYER2_DOWN = SpriteSheet.MOBILE_OBJECTS.at(1, 7);
    public static final TextureRegion PLAYER2_UP = SpriteSheet.MOBILE_OBJECTS.at(1, 10);
    public static final TextureRegion PLAYER2_LEFT = SpriteSheet.MOBILE_OBJECTS.at(2, 10);
    public static final TextureRegion PLAYER2_RIGHT = SpriteSheet.MOBILE_OBJECTS.at(2, 7);

    public static final TextureRegion GROUND = SpriteSheet.STATIONARY_OBJECTS.at(1, 3);

    public static final TextureRegion INDESTRUCTIBLE_WALL = SpriteSheet.STATIONARY_OBJECTS.at(1, 2);

    public static final TextureRegion DESTRUCTIBLE_WALL = SpriteSheet.STATIONARY_OBJECTS.at(1, 1);

    public static final TextureRegion BOMBS_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 1);

    public static final TextureRegion FLAMES_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 2);

    public static final TextureRegion SPEED_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 3);

    public static final TextureRegion ENEMY = SpriteSheet.STATIONARY_OBJECTS.at(1, 4);

    public static final TextureRegion BOMB_HUD = SpriteSheet.STATIONARY_OBJECTS.at(1, 6);

    public static final TextureRegion BLAST_HUD = SpriteSheet.STATIONARY_OBJECTS.at(1, 7);

    public static final TextureRegion TIMER = SpriteSheet.STATIONARY_OBJECTS.at(1, 5);

    public static final TextureRegion WALLPASS_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 4);

    public static final TextureRegion DETONATOR_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 5);

    public static final TextureRegion BOMBPASS_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 6);

    public static final TextureRegion FLAMEPASS_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 7);

    public static final TextureRegion MYSTERY_POWER_UP = SpriteSheet.BOMBERMAN.at(15, 8);

    public static final TextureRegion HUD = SpriteSheet.HUD.at(3, 1);

    public static final Texture BACKGROUND = new Texture(Gdx.files.internal("texture/background.jpg"));


}
