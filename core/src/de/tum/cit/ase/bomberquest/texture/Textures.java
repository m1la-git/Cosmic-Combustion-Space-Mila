package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Contains all texture constants used in the game.
 * <p>
 * It is good practice to keep all textures and animations in constants to avoid loading them multiple times.
 * These constants can be statically referenced from anywhere in the codebase where these textures are needed,
 * improving performance and maintainability.
 * <p>
 * This class centralizes the access to all static textures used in the BomberQuest game,
 * ensuring textures are loaded once and reused throughout the application.
 */
public class Textures {

    /**
     * TextureRegion for Player 1 facing downwards (default direction).
     */
    public static final TextureRegion PLAYER1_DOWN = SpriteSheet.MOBILE_OBJECTS.at(1, 1);
    /**
     * TextureRegion for Player 1 facing upwards.
     */
    public static final TextureRegion PLAYER1_UP = SpriteSheet.MOBILE_OBJECTS.at(1, 4);
    /**
     * TextureRegion for Player 1 facing leftwards.
     */
    public static final TextureRegion PLAYER1_LEFT = SpriteSheet.MOBILE_OBJECTS.at(2, 4);
    /**
     * TextureRegion for Player 1 facing rightwards.
     */
    public static final TextureRegion PLAYER1_RIGHT = SpriteSheet.MOBILE_OBJECTS.at(2, 1);

    /**
     * TextureRegion for Player 2 facing downwards (default direction).
     */
    public static final TextureRegion PLAYER2_DOWN = SpriteSheet.MOBILE_OBJECTS.at(1, 7);
    /**
     * TextureRegion for Player 2 facing upwards.
     */
    public static final TextureRegion PLAYER2_UP = SpriteSheet.MOBILE_OBJECTS.at(1, 10);
    /**
     * TextureRegion for Player 2 facing leftwards.
     */
    public static final TextureRegion PLAYER2_LEFT = SpriteSheet.MOBILE_OBJECTS.at(2, 10);
    /**
     * TextureRegion for Player 2 facing rightwards.
     */
    public static final TextureRegion PLAYER2_RIGHT = SpriteSheet.MOBILE_OBJECTS.at(2, 7);

    /**
     * TextureRegion for the ground tile.
     */
    public static final TextureRegion GROUND = SpriteSheet.STATIONARY_OBJECTS.at(1, 3);
    /**
     * TextureRegion for indestructible walls.
     */
    public static final TextureRegion INDESTRUCTIBLE_WALL = SpriteSheet.STATIONARY_OBJECTS.at(1, 2);
    /**
     * TextureRegion for destructible walls.
     */
    public static final TextureRegion DESTRUCTIBLE_WALL = SpriteSheet.STATIONARY_OBJECTS.at(1, 1);

    /**
     * TextureRegion for enemy characters.
     */
    public static final TextureRegion ENEMY = SpriteSheet.MOBILE_OBJECTS.at(3, 1);

    /**
     * TextureRegion for the enemy counter icon in the HUD.
     */
    public static final TextureRegion ENEMY_HUD = SpriteSheet.HUD.at(1, 4);
    /**
     * TextureRegion for the bomb counter icon in the HUD.
     */
    public static final TextureRegion BOMB_HUD = SpriteSheet.HUD.at(3, 4);
    /**
     * TextureRegion for the blast radius icon in the HUD.
     */
    public static final TextureRegion BLAST_HUD = SpriteSheet.HUD.at(4, 4);
    /**
     * TextureRegion for the timer icon in the HUD.
     */
    public static final TextureRegion TIMER = SpriteSheet.HUD.at(2, 4);
    /**
     * TextureRegion for the star icon (likely for score or points) in the HUD.
     */
    public static final TextureRegion STAR = SpriteSheet.HUD.at(5, 4);

    /**
     * TextureRegion for a check mark icon (e.g., for indicating exit open) in the HUD.
     */
    public static final TextureRegion CHECK_MARK = SpriteSheet.HUD.at(6, 4);
    /**
     * TextureRegion for a cross mark icon (e.g., for indicating exit closed) in the HUD.
     */
    public static final TextureRegion CROSS_MARK = SpriteSheet.HUD.at(7, 4);

    /**
     * TextureRegion for the entire HUD background panel.
     */
    public static final TextureRegion HUD = new TextureRegion(new Texture(Gdx.files.internal("texture/Hud.png")), 0, 0, 48, 96);

    /**
     * Texture for the game background image.
     */
    public static final Texture BACKGROUND = new Texture(Gdx.files.internal("texture/Background.png"));

    /**
     * Texture for the game logo image, displayed in menus.
     */
    public static final Texture GAME_LOGO = new Texture(Gdx.files.internal("texture/Game Logo.png"));


}