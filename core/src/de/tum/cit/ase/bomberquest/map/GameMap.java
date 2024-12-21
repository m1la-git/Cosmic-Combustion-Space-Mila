package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.BomberQuestGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final List<Bomb> bombs;
    private final List<Blast> blasts;
    // make sure that map won't have any empty spaces

    private final Map<String, StationaryObject> stationaryObjects;

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
        this.stationaryObjects = new HashMap<>();
        this.entrance = new int[]{0, 0};
        this.bombs = new ArrayList<>();
        this.blasts = new ArrayList<>();
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
                        stationaryObjects.put(x + "," + y, new IndestructibleWall(world, x, y));
                        break;
                    case 1: // destructibleWall
                        stationaryObjects.put(x + "," + y, new DestructibleWall(world, x, y));
                        break;
                    case 2: // entrance
                        entrance[0] = x;
                        entrance[1] = y;
                        break;
                    case 3: // enemy and exit
                        stationaryObjects.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.EXIT));
                        break;
                    case 4:
                        break;
                    case 5: // powerUp: bombs
                        stationaryObjects.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.BOMBS_POWER_UP));
                        break;
                    case 6: // powerUp: flames
                        stationaryObjects.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.FLAMES_POWER_UP));
                        break;
                    case 7: // powerUp: speed
                        stationaryObjects.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.SPEED_POWER_UP));
                        break;
                    case 8: // powerUp: wallpass
                        stationaryObjects.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.WALLPASS_POWER_UP));
                        break;
                    case 9: // powerUp: wallpass
                        stationaryObjects.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.DETONATOR_POWER_UP));
                        break;
                    case 10: // powerUp: bombpass
                        stationaryObjects.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.BOMBPASS_POWER_UP));
                        break;
                    case 11: // powerUp: flamepass
                        stationaryObjects.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.FLAMEPASS_POWER_UP));
                        break;
                    case 12: // powerUp: mystery
                        stationaryObjects.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.MYSTERY_POWER_UP));
                        break;

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
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && bombs.size() < concurrentBombs) {
            boolean spaceEmpty = true;
            int x = Math.round(player.getX());
            int y = Math.round(player.getY());
            for (Bomb bomb : bombs) {
                if (bomb.getX() == x && bomb.getY() == y) {
                    spaceEmpty = false;
                    break;
                }
            }
            if (spaceEmpty) {
                Bomb newBomb = new Bomb(world, x, y);
                bombs.add(0, newBomb);
                contactListener.addIgnoredBomb(newBomb.getHitbox());
            }
        }
        //handle bomb collisions
        for (int i = bombs.size() - 1; i >= 0; i--) {
            Bomb bomb = bombs.get(i);
            bomb.tick(frameTime);
            if (isOverlapping(player.getHitbox(), bomb.getHitbox())) {
                contactListener.addIgnoredBomb(bomb.getHitbox());
            } else {
                contactListener.removeIgnoredBomb(bomb.getHitbox());
            }
            if (bomb.isExploded()) {
                int x = Math.round(bomb.getX());
                int y = Math.round(bomb.getY());
                int blastUp = releaseBlast(x, y, 0, 1);  // Up
                int blastDown = releaseBlast(x, y, 0, -1); // Down
                int blastRight = releaseBlast(x, y, 1, 0);  // Right
                int blastLeft = releaseBlast(x, y, -1, 0); // Left
                bomb.destroy(world);
                bombs.remove(bomb);
                blasts.add(new Blast(world, x, y, BlastType.CENTER));
            }
        }
        if (!blasts.isEmpty()) {
            for (int i = blasts.size() - 1; i >= 0; i--) {
                Blast blast = blasts.get(i);
                blast.tick(frameTime);
                if (blast.isFinished()) {
                    blasts.remove(i);
                    if (blast.getType() == BlastType.WALL) {
                        blast.destroy(world);
                    }
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

    /**
     * Checks whether the bomb and the player are overlapping
     *
     * @param bodyA: Player's hitbox
     * @param bodyB: Bomb's hitbox
     */
    private boolean isOverlapping(Body bodyA, Body bodyB) {
        return bodyA.getPosition().dst(bodyB.getPosition()) < 0.8f; // Adjust threshold as needed
    }

    /**
     * Releasing the blast in 1 direction in a certain radius
     * Until any stationaryObject is met on the way
     */
    private int releaseBlast(int x, int y, int dx, int dy) {
        for (int i = 1; i <= blastRadius; i++) {
            int currentX = x + i * dx;
            int currentY = y + i * dy;
            if (stationaryObjects.containsKey(currentX + "," + currentY)) {
                StationaryObject obj = stationaryObjects.get(currentX + "," + currentY);
                if (obj instanceof DestructibleWall wall) {
                    WallContentType type = wall.getWallContentType();
                    wall.destroy(world);
                    stationaryObjects.remove(currentX + "," + currentY);
                    if (type != WallContentType.EMPTY && type != WallContentType.EXIT) {
                        stationaryObjects.put(currentX + "," + currentY, new PowerUp(world, currentX, currentY, type));
                    }
                    else if (type == WallContentType.EXIT) {
                        stationaryObjects.put(currentX + "," + currentY, new Exit(world, currentX, currentY));
                    }
                    blasts.add(new Blast(world, currentX, currentY, BlastType.WALL));
                    return i;
                }
                return i - 1;
            }
            if (i == blastRadius) {
                if (dx == 0 && dy < 0) {blasts.add(new Blast(world, currentX, currentY, BlastType.DOWN));}
                else if (dx == 0 && dy > 0) {blasts.add(new Blast(world, currentX, currentY, BlastType.UP));}
                else if (dy == 0 && dx > 0) {blasts.add(new Blast(world, currentX, currentY, BlastType.RIGHT));}
                else {blasts.add(new Blast(world, currentX, currentY, BlastType.LEFT));}
            }
            else {
                if (dx == 0) {blasts.add(new Blast(world, currentX, currentY, BlastType.VERTICAL));}
                else {blasts.add(new Blast(world, currentX, currentY, BlastType.HORIZONTAL));}
            }

        }
        return blastRadius;
    }

    /**
     * Returns the player on the map.
     */
    public Player getPlayer() {
        return player;
    }


    public Map<String, StationaryObject> getStationaryObjects() {
        return stationaryObjects;
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
