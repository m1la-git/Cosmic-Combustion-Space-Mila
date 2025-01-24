package de.tum.cit.ase.bomberquest.map;

/**
 * A record representing a tile within the game grid.
 * <p>
 * In BomberQuest, the game map is divided into a grid of tiles.
 * This record is a simple data structure to hold the coordinates of a tile,
 * primarily used for pathfinding.
 * It is immutable and identified by its x and y coordinates.
 *
 * @param x The x-coordinate of the tile in the grid.
 * @param y The y-coordinate of the tile in the grid.
 */
public record Tile(int x, int y) {
}