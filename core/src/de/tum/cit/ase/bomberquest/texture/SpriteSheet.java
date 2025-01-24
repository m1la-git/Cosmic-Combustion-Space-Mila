package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Enumerates all spritesheets used in the game and provides helper methods for grabbing texture regions from them.
 * It is assumed that every spritesheet has some standard grid size which can be used for easier coordinate specification.
 * See the assets/texture folder for the actual texture files (plus some more samples which are not enumerated here).
 * Feel free to add your own spritesheets and use them in the game!
 * This enum facilitates the management and access of game textures, making texture handling more organized and efficient.
 *
 * @see Texture A whole image loaded into memory, used as a source for texture regions.
 * @see TextureRegion A rectangular portion of a texture, used for rendering sprites and animations.
 */
public enum SpriteSheet {

    /**
     * Spritesheet containing textures for mobile objects like players and enemies.
     */
    MOBILE_OBJECTS("MobileObjects.png", 16, 32),

    /**
     * Spritesheet containing textures for HUD elements.
     */
    HUD("Hud.png", 16, 16),

    /**
     * Spritesheet containing textures for stationary objects like walls and power-ups.
     */
    STATIONARY_OBJECTS("StationaryObjects.png", 16, 16),

    /**
     * Spritesheet containing textures for bombs and blast effects.
     */
    BOMBS_AND_BLASTS("Bombs and Blasts.png", 16, 16);


    /**
     * The {@link Texture} object representing the loaded spritesheet image.
     */
    private final Texture spritesheet;
    /**
     * The width of a single grid cell within this spritesheet.
     */
    private final int width;
    /**
     * The height of a single grid cell within this spritesheet.
     */
    private final int height;

    /**
     * Constructor for each variant of this enum.
     * Every {@code SpriteSheet} variant is associated with a corresponding image file, grid cell width, and height.
     * It loads the texture from the assets and initializes the grid dimensions.
     *
     * @param filename The filename of the spritesheet image in the "texture/" directory.
     * @param width    The width of a single grid cell in pixels within the spritesheet.
     * @param height   The height of a single grid cell in pixels within the spritesheet.
     */
    SpriteSheet(String filename, int width, int height) {
        this.spritesheet = new Texture(Gdx.files.internal("texture/" + filename));
        this.width = width;
        this.height = height;
    }

    /**
     * Returns a {@link TextureRegion} representing a single sprite from the spritesheet at the specified row and column.
     * Coordinates are 1-based, with row 1 being at the top and column 1 at the left of the spritesheet.
     * The size of the returned TextureRegion is determined by the grid cell dimensions ({@code this.width} and {@code this.height}).
     *
     * @param row    The row index of the desired sprite, starting from 1 at the top of the spritesheet.
     * @param column The column index of the desired sprite, starting from 1 on the left of the spritesheet.
     * @return A {@link TextureRegion} representing the sprite at the specified row and column.
     */
    public TextureRegion at(int row, int column) {
        return new TextureRegion(
                spritesheet,
                (column - 1) * this.width,
                (row - 1) * this.height,
                this.width,
                this.height
        );
    }

}