package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.BomberQuestGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the game map.
 * Holds all the objects and entities in the game.
 */
public class GameMap {

    // A static block is executed once when the class is referenced for the first time.
    static {
        // Initialize the Box2D physics engine.
        com.badlogic.gdx.physics.box2d.Box2D.init();
    }
    
    // Box2D physics simulation parameters (you can experiment with these if you want, but they work well as they are)
    /**
     * The time step for the physics simulation.
     * This is the amount of time that the physics simulation advances by in each frame.
     * It is set to 1/refreshRate, where refreshRate is the refresh rate of the monitor, e.g., 1/60 for 60 Hz.
     */
    private static final float TIME_STEP = 1f / Gdx.graphics.getDisplayMode().refreshRate;
    /** The number of velocity iterations for the physics simulation. */
    private static final int VELOCITY_ITERATIONS = 6;
    /** The number of position iterations for the physics simulation. */
    private static final int POSITION_ITERATIONS = 2;
    /**
     * The accumulated time since the last physics step.
     * We use this to keep the physics simulation at a constant rate even if the frame rate is variable.
     */
    private float physicsTime = 0;
    
    /** The game, in case the map needs to access it. */
    private final BomberQuestGame game;
    /** The Box2D world for physics simulation. */
    private final World world;
    private final GameContactListener contactListener;


    private final int[][] filledCells;

    // Game objects
    private final Player player;

    
    private final Flowers[][] flowers;

    private final DestructibleWall[][] destructibleWalls;

    private final IndestructibleWall[][] indestructibleWalls;

    private final int[] entrance;

    private final List<Bomb> bombs;

    
    public GameMap(BomberQuestGame game) {
        this.game = game;
        this.world = new World(Vector2.Zero, true);
        this.contactListener = new GameContactListener();
        this.world.setContactListener(contactListener);

        this.flowers = new Flowers[21][21];
        this.destructibleWalls = new DestructibleWall[21][21];
        this.indestructibleWalls = new IndestructibleWall[21][21];
        this.filledCells = new int[21][21];
        this.entrance = new int[] {-1, -1};
        this.bombs = new ArrayList<>();

        loadMap("map.properties");

        // Create a player with initial position
        this.player = new Player(this.world, entrance[0], entrance[1]);
        // Create a chest in the middle of the map
    }
    /**
     * TBA
     */
    public void loadMap(String filename) {
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
                switch (type) {
                    case 0: indestructibleWalls[x][y] = new IndestructibleWall(world, x, y); break;
                    case 1: destructibleWalls[x][y] = new DestructibleWall(world, x, y); break;
                    case 2: entrance[0] = x; entrance[1] = y; flowers[x][y] = new Flowers(x, y); break;
                    default: flowers[x][y] = new Flowers(x, y); break;
                }
                filledCells[x][y] = 1;
            }
        } catch (Exception e) {
            Gdx.app.error("GameMap", "Error reading map file: " + e.getMessage());
        }
        if (entrance[0] == -1) {
            entrance[0] = 0;
            entrance[1] = 0;
        }

    }

    
    /**
     * Updates the game state. This is called once per frame.
     * Every dynamic object in the game should update its state here.
     * @param frameTime the time that has passed since the last update
     */
    public void tick(float frameTime) {
        this.player.tick(frameTime);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Bomb newBomb = new Bomb(world, Math.round(player.getX()), Math.round(player.getY()));
            bombs.add(newBomb);
            contactListener.addIgnoredBomb(newBomb.getHitbox());
        }

        for (Bomb bomb : bombs) {
            if (isOverlapping(player.getHitbox(), bomb.getHitbox())) {
                contactListener.addIgnoredBomb(bomb.getHitbox());
            } else {
                contactListener.removeIgnoredBomb(bomb.getHitbox());
            }
        }


        doPhysicsStep(frameTime);

    }

    /**
     * Performs as many physics steps as necessary to catch up to the given frame time.
     * This will update the Box2D world by the given time step.
     * @param frameTime Time since last frame in seconds
     */
    private void doPhysicsStep(float frameTime) {
        this.physicsTime += frameTime;
        while (this.physicsTime >= TIME_STEP) {
            this.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            this.physicsTime -= TIME_STEP;
        }
    }

    private boolean isOverlapping(Body bodyA, Body bodyB) {
        return bodyA.getPosition().dst(bodyB.getPosition()) < 0.6f; // Adjust threshold as needed
    }
    
    /** Returns the player on the map. */
    public Player getPlayer() {
        return player;
    }

    
    /** Returns the flowers on the map. */
    public List<Flowers> getFlowers() {
        return Arrays.stream(flowers).flatMap(Arrays::stream).toList();
    }

    public List<DestructibleWall> getDestructibleWalls() {
        return Arrays.stream(destructibleWalls).flatMap(Arrays::stream).toList();
    }
    public List<IndestructibleWall> getIndestructibleWalls() {
        return Arrays.stream(indestructibleWalls).flatMap(Arrays::stream).toList();
    }
    public List<Bomb> getBombs() {
        return bombs;
    }
    public int[][] getFilledCells() {
        return filledCells;
    }

}
