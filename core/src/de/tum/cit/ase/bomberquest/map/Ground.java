package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * Represents the ground tile in the game map.
 * Ground tiles are the most basic element of the game environment, forming the floor of the game world.
 * They are {@link Drawable} but have no special interactive properties or collision behavior.
 * Essentially, they are the static background on which all gameplay actions occur.
 */
public class Ground implements Drawable {

    /**
     * The x-coordinate of this ground tile in the game grid.
     */
    private final int x;
    /**
     * The y-coordinate of this ground tile in the game grid.
     */
    private final int y;

    /**
     * Constructs a new Ground tile.
     *
     * @param x The x-coordinate of the ground tile in the game grid.
     * @param y The y-coordinate of the ground tile in the game grid.
     */
    public Ground(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the current texture region for rendering the ground tile.
     * This method always returns the standard ground texture from {@link Textures#GROUND}.
     *
     * @return The {@link TextureRegion} representing the ground tile's appearance.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.GROUND;
    }

    /**
     * Gets the x-coordinate of this ground tile.
     *
     * @return The x-coordinate in the game grid.
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of this ground tile.
     *
     * @return The y-coordinate in the game grid.
     */
    @Override
    public float getY() {
        return y;
    }
}