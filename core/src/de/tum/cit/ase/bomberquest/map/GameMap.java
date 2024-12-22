package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.BomberQuestGame;

import java.util.*;

/**
 * Represents the game map.
 * Holds all the objects and entities in the game.
 */
public class GameMap {

    /**
     * The time step for the physics simulation.
     * This is the amount of time that the physics simulation advances by in each frame.
     * It is set to 1/refreshRate, where refreshRate is the refresh rate of the monitor, e.g., 1/60 for 60 Hz.
     */
    private static final float TIME_STEP = 1f / Gdx.graphics.getDisplayMode().refreshRate;

    // Box2D physics simulation parameters (you can experiment with these if you want, but they work well as they are)
    /**
     * The number of velocity iterations for the physics simulation.
     */
    private static final int VELOCITY_ITERATIONS = 6;
    /**
     * The number of position iterations for the physics simulation.
     */
    private static final int POSITION_ITERATIONS = 2;

    // A static block is executed once when the class is referenced for the first time.
    static {
        // Initialize the Box2D physics engine.
        com.badlogic.gdx.physics.box2d.Box2D.init();
    }

    /**
     * The game, in case the map needs to access it.
     */
    private final BomberQuestGame game;
    /**
     * The Box2D world for physics simulation.
     */
    private final World world;
    /**
     * ContactListener for handling the collisions of the player with new bombs
     */
    private final GameContactListener contactListener;
    // Game objects
    private final Player player;
    private final int[] entrance;
    private final List<Enemy> enemies;
    private final List<Bomb> bombs;
    private final List<Blast> blasts;
    // make sure that map won't have any empty spaces

    private final Map<String, StationaryObject> walls;

    private final int MAX_X;
    private final int MAX_Y;
    private int blastRadius;
    private int concurrentBombs;
    /**
     * The accumulated time since the last physics step.
     * We use this to keep the physics simulation at a constant rate even if the frame rate is variable.
     */
    private float physicsTime = 0;

    public GameMap(BomberQuestGame game) {
        this.game = game;
        this.world = new World(Vector2.Zero, true);
        this.contactListener = new GameContactListener();
        this.world.setContactListener(contactListener);
        this.walls = new HashMap<>();
        this.entrance = new int[]{0, 0};
        this.bombs = new ArrayList<>();
        this.blasts = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.blastRadius = 1;
        this.concurrentBombs = 1;


        int[] temp = loadMap("map.properties");
        this.MAX_X = temp[0];
        this.MAX_Y = temp[1];
        // Create a player with initial position
        this.player = new Player(this.world, entrance[0], entrance[1]);
    }

    /**
     * reads the map file line by line
     * throws exceptions when the file is in the wrong format
     * adds to the list with the respective types of the objects (walls, entrance, etc.)
     *
     * @param filename the name of the file with the map
     */
    public int[] loadMap(String filename) {
        int maxX = 0;
        int maxY = 0;
        boolean existsExit = false;
        List<String> freeDestructibleWalls = new ArrayList<>();
        FileHandle file = Gdx.files.internal("maps/" + filename);
        try {
            for (String line : file.readString().split("\\r?\\n")) {
                line = line.trim();
                if (line.startsWith("#") || line.isEmpty()) {
                    continue; // Skip comments and empty lines
                }
                String[] parts = line.split("=");
                if (parts.length != 2) {
                    Gdx.app.error("GameMap", "Invalid line format: " + line);
                    continue;
                }
                String[] coords = parts[0].split(",");
                int type = Integer.parseInt(parts[1]);
                if (coords.length != 2) {
                    Gdx.app.error("GameMap", "Invalid coordinate format: " + parts[0]);
                    continue;
                }
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                if (x > maxX) {
                    maxX = x;
                }
                if (y > maxY) {
                    maxY = y;
                }

                switch (type) {
                    case 0: // indestructibleWall
                        walls.put(x + "," + y, new IndestructibleWall(world, x, y));
                        break;
                    case 1: // destructibleWall
                        walls.put(x + "," + y, new DestructibleWall(world, x, y));
                        freeDestructibleWalls.add(x + "," + y);
                        break;
                    case 2: // entrance
                        entrance[0] = x;
                        entrance[1] = y;
                        break;
                    case 3: // enemy
                        enemies.add(new Enemy(world, x, y, this));
                        break;
                    case 4: // exit
                        if (!existsExit) {
                            walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.EXIT));
                            existsExit = true;
                        }
                        break;
                    case 5: // powerUp: bombs
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.BOMBS_POWER_UP));
                        break;
                    case 6: // powerUp: flames
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.FLAMES_POWER_UP));
                        break;
                    case 7: // powerUp: speed
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.SPEED_POWER_UP));
                        break;
                    case 8: // powerUp: wallpass
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.WALLPASS_POWER_UP));
                        break;
                    case 9: // powerUp: wallpass
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.DETONATOR_POWER_UP));
                        break;
                    case 10: // powerUp: bombpass
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.BOMBPASS_POWER_UP));
                        break;
                    case 11: // powerUp: flamepass
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.FLAMEPASS_POWER_UP));
                        break;
                    case 12: // powerUp: mystery
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.MYSTERY_POWER_UP));
                        break;
                }

            }
            if (!existsExit) {
                if (freeDestructibleWalls.isEmpty()) {
                    Gdx.app.error("GameMap", "No place for an exit.");
                } else {
                    Random random = new Random();
                    int randomIndex = random.nextInt(freeDestructibleWalls.size());
                    String[] randomCoords = freeDestructibleWalls.get(randomIndex).split(",");
                    int randomX = Integer.parseInt(randomCoords[0]);
                    int randomY = Integer.parseInt(randomCoords[1]);
                    walls.get(randomX + "," + randomY).destroy(world);
                    walls.replace(randomX + "," + randomY, new DestructibleWall(world, randomX, randomY, WallContentType.EXIT));
                    System.out.println("Random exit coords: " + randomX + "," + randomY);
                }
            }
        } catch (Exception e) {
            Gdx.app.error("GameMap", "Error reading map file: " + e.getMessage());
        }
        return new int[]{maxX, maxY};
    }


    /**
     * Updates the game state. This is called once per frame.
     * Every dynamic object in the game should update its state here.
     *
     * @param frameTime the time that has passed since the last update
     */
    public void tick(float frameTime) {
        this.player.tick(frameTime);
        //power-ups
        if (player.isAlive()) {
            int playerCellX = player.getCellX();
            int playerCellY = player.getCellY();

            // power-ups
            if (walls.containsKey(playerCellX + "," + playerCellY)) {
                if (walls.get(playerCellX + "," + playerCellY) instanceof PowerUp powerUp) {
                    WallContentType type = powerUp.getType();
                    switch (type) {
                        case BOMBS_POWER_UP:
                            if (concurrentBombs < 8) concurrentBombs++;
                            break;
                        case FLAMES_POWER_UP:
                            if (blastRadius < 8) blastRadius++;
                            break;
                    }
                    walls.remove(playerCellX + "," + playerCellY);
                }
            }
            // place bomb if space is pressed
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && bombs.size() < concurrentBombs) {
                boolean spaceEmpty = true;
                for (Bomb bomb : bombs) {
                    if (bomb.getX() == playerCellX && bomb.getY() == playerCellY) {
                        spaceEmpty = false;
                        break;
                    }
                }
                if (spaceEmpty) {
                    Bomb newBomb = new Bomb(world, playerCellX, playerCellY);
                    bombs.add(0, newBomb);
                }
            }
        }
        //enemies ticks
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.tick(frameTime);
            if (enemy.isDead()) {
                enemies.remove(i);
            }

        }


        // bomb ticks and handling bomb collisions
        for (int i = bombs.size() - 1; i >= 0; i--) {
            Bomb bomb = bombs.get(i);
            bomb.tick(frameTime);
            if (player.isAlive()) {
                if (isOverlapping(player.getHitbox(), bomb.getHitbox())) {
                    contactListener.addIgnoredBomb(bomb.getHitbox());
                } else {
                    contactListener.removeIgnoredBomb(bomb.getHitbox());
                }
            }
            if (bomb.isExploded()) {
                int bombX = Math.round(bomb.getX());
                int bombY = Math.round(bomb.getY());
                releaseBlast(bombX, bombY, 0, 1);  // Up
                releaseBlast(bombX, bombY, 0, -1); // Down
                releaseBlast(bombX, bombY, 1, 0);  // Right
                releaseBlast(bombX, bombY, -1, 0); // Left
                bomb.destroy(world);
                bombs.remove(bomb);
                blasts.add(new Blast(world, bombX, bombY, BlastType.CENTER));
            }
        }

        //blasts ticks
        for (int i = blasts.size() - 1; i >= 0; i--) {
            Blast blast = blasts.get(i);
            blast.tick(frameTime);
            if (blast.isFinished()) {
                blasts.remove(i);
                if (blast.getType() == BlastType.WALL) {
                    blast.destroy(world);
                }
            }
            // enemies' death
            for (Enemy enemy : enemies) {
                if (!enemy.isAlive()) {
                    continue;
                }
                if (isBlasted(enemy, blast)) {
                    enemy.death(world);
                }
            }
            //player's death
            if (player.isAlive()) {
                if (isBlasted(player, blast)) {
                    player.death(world);
                }
            }

        }


        doPhysicsStep(frameTime);

    }

    /**
     * Performs as many physics steps as necessary to catch up to the given frame time.
     * This will update the Box2D world by the given time step.
     *
     * @param frameTime Time since last frame in seconds
     */
    private void doPhysicsStep(float frameTime) {
        this.physicsTime += frameTime;
        while (this.physicsTime >= TIME_STEP) {
            this.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            this.physicsTime -= TIME_STEP;
        }
    }


    private boolean isBlasted(MobileObject obj, Blast blast) {
        return obj.getCellX() == blast.getX() && obj.getCellY() == blast.getY();
    }

    /**
     * Checks whether the bomb and the player are overlapping
     *
     * @param bodyA: Player's hitbox
     * @param bodyB: Bomb's hitbox
     */
    private boolean isOverlapping(Body bodyA, Body bodyB) {
        return bodyA.getPosition().dst(bodyB.getPosition()) < 1f; // Adjust threshold as needed
    }


    /**
     * Releasing the blast in 1 direction in a certain radius
     * Until any stationaryObject is met on the way
     */
    private void releaseBlast(int x, int y, int dx, int dy) {
        for (int i = 1; i <= blastRadius; i++) {
            int currentX = x + i * dx;
            int currentY = y + i * dy;
            if (walls.containsKey(currentX + "," + currentY)) {
                StationaryObject obj = walls.get(currentX + "," + currentY);
                if (obj instanceof DestructibleWall wall) {
                    WallContentType type = wall.getWallContentType();
                    wall.destroy(world);
                    walls.remove(currentX + "," + currentY);
                    if (type != WallContentType.EMPTY && type != WallContentType.EXIT) {
                        walls.put(currentX + "," + currentY, new PowerUp(world, currentX, currentY, type));
                    } else if (type == WallContentType.EXIT) {
                        walls.put(currentX + "," + currentY, new Exit(world, currentX, currentY));
                    }
                    blasts.add(new Blast(world, currentX, currentY, BlastType.WALL));
                    break;
                }
                break;
            }
            if (i == blastRadius) {
                if (dx == 0 && dy < 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.DOWN));
                } else if (dx == 0 && dy > 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.UP));
                } else if (dy == 0 && dx > 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.RIGHT));
                } else {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.LEFT));
                }
            } else {
                if (dx == 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.VERTICAL));
                } else {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.HORIZONTAL));
                }
            }

        }
    }

    /**
     * Returns the player on the map.
     */
    public boolean isCellFree(int x, int y) {
        if (walls.containsKey(x + "," + y)) {
            return (walls.get(x + "," + y) instanceof PowerUp);
        }
        return true;
    }

    public Player getPlayer() {
        return player;
    }


    public Map<String, StationaryObject> getWalls() {
        return walls;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Returns the bombs on the map.
     */
    public List<Bomb> getBombs() {
        return bombs;
    }

    public List<Blast> getBlasts() {
        return blasts;
    }

    public int getMaxX() {
        return MAX_X;
    }

    public int getMaxY() {
        return MAX_Y;
    }

}
