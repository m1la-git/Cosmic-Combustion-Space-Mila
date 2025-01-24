package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.audio.SoundEffects;

import java.util.*;

/**
 * Represents the game map, which is the central component of the game world.
 * <p>
 * The {@code GameMap} class is responsible for managing all game objects and entities,
 * including players, enemies, bombs, blasts, and walls. It also handles game logic,
 * such as loading the map from a file, updating game state, handling player input,
 * managing physics simulations, and processing game events like explosions and collisions.
 * It acts as a container and orchestrator for all elements within a game level.
 */
public class GameMap {

    /**
     * The fixed time step used for the Box2D physics simulation.
     * <p>
     * This ensures consistent physics behavior across different frame rates.
     * It is derived from the monitor's refresh rate to synchronize physics updates with rendering.
     */
    private static final float TIME_STEP = 1f / Gdx.graphics.getDisplayMode().refreshRate;

    /**
     * The number of velocity iterations performed per physics step in Box2D.
     * <p>
     * More iterations increase accuracy but also increase computational cost.
     * This value is set to balance performance and simulation quality.
     */
    private static final int VELOCITY_ITERATIONS = 6;
    /**
     * The number of position iterations performed per physics step in Box2D.
     * <p>
     * Similar to velocity iterations, more position iterations improve stability and accuracy,
     * especially for stacked bodies, at the cost of performance.
     */
    private static final int POSITION_ITERATIONS = 2;

    // A static block is executed once when the class is loaded.
    static {
        // Initialize the Box2D physics engine when the GameMap class is first loaded.
        Box2D.init();
    }

    /**
     * Reference to the main game class, providing access to game-level functionalities and resources.
     * <p>
     * This allows the map to interact with the broader game context, such as error handling or accessing game settings.
     */
    private final BomberQuestGame game;
    /**
     * The Box2D world that manages the physics simulation for all entities in the game map.
     * <p>
     * It handles collisions, physics interactions, and movement of dynamic bodies within the game world.
     */
    private final World world;
    /**
     * The contact listener responsible for handling collision events within the Box2D world.
     * <p>
     * It detects when bodies begin or end contact and allows for custom collision response logic to be implemented.
     */
    private final GameContactListener contactListener;
    // Game objects
    /**
     * The first player entity in the game.
     */
    private final Player player1;
    /**
     * The second player entity in the game, can be null for single-player mode.
     */
    private final Player player2;
    /**
     * A list of all enemy entities currently active in the game map.
     */
    private final List<Enemy> enemies;
    /**
     * A map of bombs currently placed on the map, indexed by their cell coordinates (x,y string).
     * <p>
     * This allows for quick lookup and management of bombs based on their location.
     */
    private final Map<String, Bomb> bombs;
    /**
     * A list of active blast effects currently on the map.
     * <p>
     * Blasts are visual effects created by bomb explosions, managed separately for rendering and game logic.
     */
    private final List<Blast> blasts;
    /**
     * A map of stationary wall objects (both indestructible and destructible), indexed by their cell coordinates (x,y string).
     * <p>
     * Used for efficient access and management of walls based on their position in the game grid.
     */
    private final Map<String, StationaryObject> walls;
    /**
     * The maximum x-coordinate extent of the game map in grid cells.
     */
    private final int MAX_X;
    /**
     * The maximum y-coordinate extent of the game map in grid cells.
     */
    private final int MAX_Y;
    /**
     * A list of cell coordinates (x,y string) where power-ups are located or will be spawned.
     */
    private final List<String> powerUps;
    /**
     * A list of visual point effects displayed when enemies are defeated.
     */
    private final List<PlusPoints> plusPoints;
    /**
     * The game timer, counting down from an initial value.
     * <p>
     * When the timer reaches zero, the game may end in a game over state if not configured otherwise.
     */
    private float timer;
    /**
     * Flag to indicate if the game is currently in a game over state.
     */
    private boolean gameOver;
    /**
     * Message to be displayed when the game is over, indicating the reason for game over.
     */
    private String gameOverMessage;
    /**
     * The initial number of enemies at the start of the game.
     * <p>
     * Used to track progress and determine when all enemies have been defeated.
     */
    private int numberOfEnemies;
    /**
     * Elapsed time since the game started, used for timer countdown and tracking game duration.
     */
    private float elapsedTime = 0;
    /**
     * The exit object in the game map, which players must reach to complete the level.
     */
    private Exit exit;
    /**
     * Flag to indicate if the exit is currently open and accessible for players to finish the level.
     */
    private boolean exitOpen;
    /**
     * Accumulated time for physics simulation, used to maintain a fixed time step.
     */
    private float physicsTime = 0;

    /**
     * Constructs a new {@code GameMap}.
     * <p>
     * Initializes the game map, loads map data from a file, sets up the Box2D world,
     * creates players and enemies based on the map configuration, and initializes game settings.
     *
     * @param game     The main game class instance.
     * @param mapFile  The file path to the map data file.
     * @param settings The game settings object containing game configurations.
     */
    public GameMap(BomberQuestGame game, String mapFile, Settings settings) {
        this.game = game;
        this.world = new World(Vector2.Zero, true); // Initialize Box2D world with no gravity
        this.contactListener = new GameContactListener(this); // Initialize contact listener for collision handling
        this.world.setContactListener(contactListener); // Set the contact listener for the Box2D world
        this.walls = new HashMap<>(); // Initialize map to store walls
        this.bombs = new HashMap<>(); // Initialize map to store bombs
        this.blasts = new ArrayList<>(); // Initialize list to store blasts
        this.enemies = new ArrayList<>(); // Initialize list to store enemies
        this.gameOver = false; // Game is not over initially
        this.gameOverMessage = ""; // No game over message initially
        exitOpen = false; // Exit is initially closed
        plusPoints = new ArrayList<>(); // Initialize list for plus points effects

        powerUps = new ArrayList<>(); // Initialize list for power-up locations


        int[] temp = loadMap(mapFile, settings); // Load map data from file
        this.MAX_X = temp[0]; // Set max X extent from loaded map data
        this.MAX_Y = temp[1]; // Set max Y extent from loaded map data
        if (temp[4] == -1) {
            this.player1 = new Player(world, temp[2], temp[3]); // Create player 1 at entrance 1
            this.player2 = null; // No player 2 in single player mode
        } else {
            this.player1 = new Player(world, temp[2], temp[3], true); // Create player 1 at entrance 1
            this.player2 = new Player(world, temp[4], temp[5], false); // Create player 2 at entrance 2 in multiplayer mode
        }

        numberOfEnemies = enemies.size(); // Count initial number of enemies
        if (numberOfEnemies == 0) {
            exitOpen = true; // Open exit if no enemies at start
        }

        timer = settings.getTimer(); // Set game timer from settings
        // Create a player with initial position
    }

    /**
     * Loads the game map from a specified file.
     * <p>
     * Parses the map file, creates game objects (walls, players, enemies, exit, power-ups) based on the file content,
     * and handles potential errors during file loading or map parsing.
     *
     * @param filename The path to the map file.
     * @param settings Game settings that influence map loading, like power-up chance and enemy properties.
     * @return An array of integers containing map dimensions and player entrance coordinates.
     * Returns {-1, -1, 0, 0, 0, 0} in case of an error during map loading.
     */
    public int[] loadMap(String filename, Settings settings) {
        boolean powerUpsWritten = false; // Flag to check if power-ups are defined in map file
        int maxX = 0; // Initialize max X map dimension
        int maxY = 0; // Initialize max Y map dimension
        int[] entrance1 = new int[]{-1, -1}; // Initialize entrance 1 coordinates
        int[] entrance2 = new int[]{-1, -1}; // Initialize entrance 2 coordinates
        boolean existsExit = false; // Flag to check if exit is defined in map file
        List<String> freeDestructibleWalls = new ArrayList<>(); // List to track destructible walls for potential exit/power-up placement
        FileHandle file = Gdx.files.internal(filename); // Get file handle for map file
        try {
            for (String line : file.readString().split("\\r?\\n")) { // Read file line by line
                line = line.trim(); // Trim whitespace from line
                if (line.startsWith("#") || line.isEmpty()) {
                    continue; // Skip comments and empty lines
                }
                String[] parts = line.split("="); // Split line into coordinates and type
                if (parts.length != 2) {
                    game.errorLoadingMap("Invalid line format: " + line); // Handle invalid line format error
                    return new int[]{-1, -1, 0, 0, 0, 0};
                }
                String[] coords = parts[0].split(","); // Split coordinates into x and y
                int type = Integer.parseInt(parts[1]); // Parse object type
                if (coords.length != 2) {
                    game.errorLoadingMap("Invalid coordinate format: " + parts[0]); // Handle invalid coordinate format error
                    return new int[]{-1, -1, 0, 0, 0, 0};
                }
                int x = Integer.parseInt(coords[0]); // Parse x coordinate
                int y = Integer.parseInt(coords[1]); // Parse y coordinate
                if (x > maxX) {
                    maxX = x; // Update max X dimension if necessary
                }
                if (y > maxY) {
                    maxY = y; // Update max Y dimension if necessary
                }

                switch (type) {
                    case 0: // indestructibleWall
                        walls.put(x + "," + y, new IndestructibleWall(world, x, y)); // Create indestructible wall and add to map
                        break;
                    case 1: // destructibleWall
                        walls.put(x + "," + y, new DestructibleWall(world, x, y)); // Create destructible wall and add to map
                        freeDestructibleWalls.add(x + "," + y); // Add to list of free destructible walls
                        break;
                    case 2: // entrance
                        if (entrance1[0] == -1) {
                            entrance1[0] = x; // Set entrance 1 coordinates
                            entrance1[1] = y;
                        }
                        //max 2 players
                        else {

                            entrance2[0] = x; // Set entrance 2 coordinates
                            entrance2[1] = y;
                        }
                        break;
                    case 3: // enemy
                        enemies.add(new Enemy(world, x, y, this, settings.isAliensSmart(), settings.isAliensBombs())); // Create enemy and add to list
                        break;
                    case 4: // exit
                        if (!existsExit) {
                            walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.EXIT)); // Create destructible wall with exit content
                            existsExit = true; // Mark exit as existing
                        }
                        break;
                    case 5: // powerUp: bombs
                        powerUpsWritten = true; // Mark power-ups as written in map file
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.BOMBS_POWER_UP)); // Create destructible wall with bombs power-up
                        break;
                    case 6: // powerUp: flames
                        powerUpsWritten = true; // Mark power-ups as written in map file
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.FLAMES_POWER_UP)); // Create destructible wall with flames power-up
                        break;
                    case 7: // powerUp: speed
                        powerUpsWritten = true; // Mark power-ups as written in map file
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.SPEED_POWER_UP)); // Create destructible wall with speed power-up
                        break;
                    case 8: // powerUp: wallpass
                        powerUpsWritten = true; // Mark power-ups as written in map file
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.WALLPASS_POWER_UP)); // Create destructible wall with wallpass power-up
                        break;
                    case 9: // powerUp: bombpass
                        powerUpsWritten = true; // Mark power-ups as written in map file
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.BOMBPASS_POWER_UP)); // Create destructible wall with bombpass power-up
                        break;
                    case 10: // powerUp: flamepass
                        powerUpsWritten = true; // Mark power-ups as written in map file
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.FLAMEPASS_POWER_UP)); // Create destructible wall with flamepass power-up
                        break;
                }

            }
            //at least one player
            if (entrance1[0] == -1) {
                Gdx.app.error("GameMap", "Entrance not found."); // Log error if entrance not found
            }
            //create exit if absent
            if (!existsExit) {
                if (freeDestructibleWalls.isEmpty()) {
                    Gdx.app.error("GameMap", "No place for an exit."); // Log error if no place for exit
                } else {
                    Random random = new Random();
                    int randomIndex = random.nextInt(freeDestructibleWalls.size()); // Choose random destructible wall
                    String[] randomCoords = freeDestructibleWalls.get(randomIndex).split(","); // Get coordinates of random wall
                    int randomX = Integer.parseInt(randomCoords[0]); // Parse x coordinate
                    int randomY = Integer.parseInt(randomCoords[1]); // Parse y coordinate
                    walls.get(randomX + "," + randomY).destroy(world); // Destroy the wall to place exit
                    walls.replace(randomX + "," + randomY, new DestructibleWall(world, randomX, randomY, WallContentType.EXIT)); // Replace with exit wall
                    System.out.println("Random exit coords: " + randomX + "," + randomY); // Log exit coordinates
                    freeDestructibleWalls.remove(randomX + "," + randomY); // Remove from free walls list
                }
            }
            // generate power-ups if there are none on the map
            if (!powerUpsWritten && !freeDestructibleWalls.isEmpty()) {
                for (String key : freeDestructibleWalls) {
                    Random random = new Random();
                    if (random.nextInt(100) < settings.getPowerUpChance()) { // 20% default chance power-up drop
                        String[] wallCoords = key.split(","); // Get coordinates of destructible wall
                        int wallX = Integer.parseInt(wallCoords[0]); // Parse x coordinate
                        int wallY = Integer.parseInt(wallCoords[1]); // Parse y coordinate
                        int randomNum = random.nextInt(100); // Generate random number for power-up type
                        walls.get(wallX + "," + wallY).destroy(world); // Destroy the wall to place power-up
                        if (randomNum < 10)
                            walls.replace(key, new DestructibleWall(world, wallX, wallY, WallContentType.SPEED_POWER_UP)); // 10% speed power-up
                        else if (randomNum < 55 && randomNum > 10)
                            walls.replace(key, new DestructibleWall(world, wallX, wallY, WallContentType.BOMBS_POWER_UP));// 45% bombs
                        else
                            walls.replace(key, new DestructibleWall(world, wallX, wallY, WallContentType.FLAMES_POWER_UP)); // 45% flames

                    }
                }
            }
        } catch (Exception e) {
            Gdx.app.error("GameMap", "Error reading map file: " + e.getMessage()); // Log error reading map file
        }
        return new int[]{maxX, maxY, entrance1[0], entrance1[1], entrance2[0], entrance2[1]}; // Return map dimensions and entrance coordinates
    }


    /**
     * Updates the game state for each frame.
     * <p>
     * This method is called in the game loop and is responsible for handling player input,
     * updating entities (players, enemies, bombs, blasts), processing game logic (collisions, explosions, power-ups),
     * and advancing the physics simulation.
     *
     * @param frameTime The time elapsed since the last frame in seconds.
     */
    public void tick(float frameTime) {
        handleInput(); // Handle player input
        player1.tick(frameTime); // Update player 1 state
        if (player2 != null) player2.tick(frameTime); // Update player 2 state if exists
        elapsedTime += frameTime; // Increase elapsed game time
        if (elapsedTime >= timer) {
            setGameOverMessage("Too slow! The timer hit zero."); // Set game over message if timer runs out
            gameOver = true; // Set game over flag
        }
        if (isPlayerDead()) {
            gameOver = true; // Set game over flag if player is dead
        }

        if (exit != null) exit.tick(frameTime); // Update exit animation

        if (player1.isAlive()) {
            //power-ups
            collectPowerUp(player1); // Check and collect power-ups for player 1

            checkExit(); // Check if player 1 reached exit
            // place bomb if space is pressed and limit of bombs isn't reached
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                placeBomb(player1); // Place bomb for player 1 on space key press
            }
        }
        if (player2 != null && player2.isAlive()) {
            collectPowerUp(player2); // Check and collect power-ups for player 2
            // place bomb if enter is pressed and limit of bombs isn't reached
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                placeBomb(player2); // Place bomb for player 2 on enter key press
            }
        }

        //enemies ticks
        Iterator<Enemy> iteratorEnemy = enemies.iterator(); // Iterate through enemies
        while (iteratorEnemy.hasNext()) {
            Enemy enemy = iteratorEnemy.next();
            enemy.tick(frameTime); // Update enemy state
            if (enemy.isDead()) {
                iteratorEnemy.remove(); // Safe removal of dead enemies
            }
        }

        // bomb ticks and handling bomb collisions
        Iterator<Map.Entry<String, Bomb>> iteratorBombs = bombs.entrySet().iterator(); // Iterate through bombs
        while (iteratorBombs.hasNext()) {
            Map.Entry<String, Bomb> entry = iteratorBombs.next();
            Bomb bomb = entry.getValue();
            bomb.tick(frameTime); // Update bomb state
            handleBombsOverlapping(player1, bomb); // Handle bomb overlapping with player 1
            if (player2 != null)
                handleBombsOverlapping(player2, bomb); // Handle bomb overlapping with player 2 if exists
            for (Enemy enemy : enemies) handleBombsOverlapping(enemy, bomb); // Handle bomb overlapping with each enemy
            if (bomb.isExploded()) {
                SoundEffects.BOMB_EXPLOSION.play(); // Play bomb explosion sound effect
                int bombX = bomb.getCellX(); // Get bomb cell X coordinate
                int bombY = bomb.getCellY(); // Get bomb cell Y coordinate
                releaseBlast(bombX, bombY, 0, 1, bomb);  // Up blast
                releaseBlast(bombX, bombY, 0, -1, bomb); // Down blast
                releaseBlast(bombX, bombY, 1, 0, bomb);  // Right blast
                releaseBlast(bombX, bombY, -1, 0, bomb); // Left blast
                blasts.add(new Blast(world, bombX, bombY, BlastType.CENTER, bomb.getOwner())); // Create center blast effect
                bomb.destroy(world); // Destroy bomb body from world
                iteratorBombs.remove();
                // Safely remove the current entry
            }
        }

        //blasts ticks
        Iterator<Blast> iteratorBlasts = blasts.iterator(); // Iterate through blasts
        while (iteratorBlasts.hasNext()) {
            Blast blast = iteratorBlasts.next();
            blast.tick(frameTime); // Update blast state
            if (blast.isFinished()) {
                iteratorBlasts.remove(); // Safe removal of finished blasts
                if (blast.getType() == BlastType.WALL) {
                    blast.destroy(world); // Destroy wall blast body from world
                }
                continue; // No need to process further if removed
            }
            //explode the bombs under the blast
            if (bombs.containsKey(blast.getCellX() + "," + blast.getCellY())) {
                bombs.get(blast.getCellX() + "," + blast.getCellY()).explodeNow(); // Explode bombs caught in blast
            }

            // enemies' death
            for (Enemy enemy : enemies) {
                if (!enemy.isAlive()) {
                    continue;
                }
                if (isBlasted(enemy, blast)) {
                    if (blast.getOwner() instanceof Player player) {
                        player.increasePoints(); // Increase player points for enemy kill
                        plusPoints.add(new PlusPoints(enemy.getX(), enemy.getY() + 1f, player.isPlayer1())); // Add plus points visual effect
                        timer += 20; // Increase game timer for enemy kill
                    }
                    enemy.death(world); // Kill enemy
                    SoundEffects.ENEMY_DEATH.play(); // Play enemy death sound effect
                    numberOfEnemies--; // Decrease number of enemies alive

                    if (numberOfEnemies == 0 && player1.isAlive()) {
                        SoundEffects.STAGE_CLEAR.play(); // Play stage clear sound effect
                        exitOpen = true; // Open exit when all enemies are defeated
                        if (exit != null) exit.open(); // Open exit animation
                    }
                }
            }
            //players' death
            if (player1.isAlive()) {
                playerBlasted(blast, player1, player2); // Check if player 1 is hit by blast
            }
            if (player2 != null && player2.isAlive()) {
                playerBlasted(blast, player2, player1); // Check if player 2 is hit by blast if exists
            }
        }

        // power-up ticks for animation
        for (String key : powerUps) {
            if (walls.get(key) instanceof PowerUp powerUp) powerUp.tick(frameTime); // Update power-up animation
        }

        //plus points
        Iterator<PlusPoints> iteratorPoints = plusPoints.iterator(); // Iterate through plus points effects
        while (iteratorPoints.hasNext()) {
            PlusPoints plusPoint = iteratorPoints.next();
            plusPoint.tick(frameTime); // Update plus points effect state
            if (plusPoint.isFinished()) {
                iteratorPoints.remove(); // Safe removal of finished plus points effects
            }
        }


        contactListener.processQueuedDestruction(); // Process bodies queued for destruction from contact listener
        doPhysicsStep(frameTime); // Advance physics simulation by frame time

    }

    /**
     * Checks if a player is caught in a blast and handles player death if necessary.
     *
     * @param blast       The blast effect to check against.
     * @param player      The player to check for blast collision.
     * @param playerOther The other player, used for game over messages in multiplayer.
     */
    private void playerBlasted(Blast blast, Player player, Player playerOther) {
        if (isBlasted(player, blast) && !player.isFlamepass()) {
            player.death(world); // Kill player if hit by blast and no flamepass power-up
            if (blast.getOwner() instanceof Enemy)
                setGameOverMessage("BOOM! " + player.getName() + " got caught in Alien's blast!"); // Set game over message for enemy blast
            else if (blast.getOwner().equals(player))
                setGameOverMessage("Oops! " + player.getName() + " blew themselves up!"); // Set game over message for self-inflicted blast
            else
                setGameOverMessage(player.getName() + " got caught in " + playerOther.getName() + "'s explosion!"); // Set game over message for other player's blast
        }
    }

    /**
     * Advances the Box2D physics simulation by a fixed time step.
     * <p>
     * This method ensures that the physics simulation runs at a consistent rate,
     * independent of the frame rate of the game.
     *
     * @param frameTime Time since last frame in seconds.
     */
    private void doPhysicsStep(float frameTime) {
        this.physicsTime += frameTime; // Accumulate frame time for physics step
        while (this.physicsTime >= TIME_STEP) {
            this.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS); // Perform physics step with fixed time step
            this.physicsTime -= TIME_STEP; // Reduce accumulated time by time step
        }
    }

    /**
     * Handles player input for movement.
     * <p>
     * Checks for key presses for each player and updates their intended movement direction accordingly.
     * Supports input for single and dual player scenarios, potentially mapping player 1 controls to both WASD and Arrows in single-player mode.
     */
    private void handleInput() {
        // wasd
        if (player1.isAlive()) {
            handlePlayerInput(player1, Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D); // Handle player 1 WASD input
        }
        // arrows
        if (player2 != null && player2.isAlive()) {
            handlePlayerInput(player2, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT); // Handle player 2 Arrow input if exists and alive
        } else if (player2 == null && player1.isAlive()) {
            handlePlayerInput(player1, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT); // If no player 2 and player 1 alive, handle player 1 Arrow input as well
        }
    }

    /**
     * Processes input for a specific player based on provided key mappings.
     * <p>
     * Adds pressed movement keys to the player's input queue and removes keys that are no longer pressed,
     * allowing for smooth and responsive player movement.
     *
     * @param player   The player entity to handle input for.
     * @param upKey    The key code for moving up.
     * @param downKey  The key code for moving down.
     * @param leftKey  The key code for moving left.
     * @param rightKey The key code for moving right.
     */
    private void handlePlayerInput(Player player, int upKey, int downKey, int leftKey, int rightKey) {
        // Check and add keys if pressed
        if (Gdx.input.isKeyPressed(upKey)) {
            player.addKeys(upKey); // Add up key to player input queue
        } else {
            player.removeKeys(upKey); // Remove up key from player input queue if not pressed
        }

        if (Gdx.input.isKeyPressed(downKey)) {
            player.addKeys(downKey); // Add down key to player input queue
        } else {
            player.removeKeys(downKey); // Remove down key from player input queue if not pressed
        }

        if (Gdx.input.isKeyPressed(leftKey)) {
            player.addKeys(leftKey); // Add left key to player input queue
        } else {
            player.removeKeys(leftKey); // Remove left key from player input queue if not pressed
        }

        if (Gdx.input.isKeyPressed(rightKey)) {
            player.addKeys(rightKey); // Add right key to player input queue
        } else {
            player.removeKeys(rightKey); // Remove right key from player input queue if not pressed
        }
    }

    /**
     * Checks if any player has reached the exit tile and if the exit is open.
     * <p>
     * If both conditions are met, the game is set to a game over state, indicating level completion.
     * Handles single and multiplayer scenarios, requiring both players to reach the exit in multiplayer.
     */
    private void checkExit() {
        int player1CellX = player1.getCellX(); // Get player 1 cell X coordinate
        int player1CellY = player1.getCellY(); // Get player 1 cell Y coordinate
        if (exit != null) {
            //exit
            if (exit.getCellX() == player1CellX && exit.getCellY() == player1CellY && exitOpen) {
                if (player2 != null) {
                    if (player2.isAlive() && exit.getCellX() == player2.getCellX() && exit.getCellY() == player2.getCellY()) {
                        gameOver = true; // Set game over if both players at exit and exit is open in multiplayer
                    }
                } else {
                    gameOver = true; // Set game over if player 1 at exit and exit is open in singleplayer
                }
            }
        }
    }

    /**
     * Places a bomb at the mobile object's current cell location if possible.
     * <p>
     * Checks if the cell is free of walls and existing bombs before placing a new bomb.
     * Decrements the mobile object's available bomb count and plays a bomb placement sound effect for players.
     *
     * @param mobileObject The mobile object (Player or Enemy) placing the bomb.
     */
    public void placeBomb(MobileObject mobileObject) {
        if (mobileObject.getBombsNow() > 0) {
            int cellX = mobileObject.getCellX(); // Get mobile object cell X coordinate
            int cellY = mobileObject.getCellY(); // Get mobile object cell Y coordinate
            if (isCellFree(cellX, cellY)) {
                bombs.put(cellX + "," + cellY, new Bomb(world, cellX, cellY, mobileObject)); // Create and place bomb at mobile object's location
                mobileObject.placedBomb(); // Decrease mobile object's bomb count
                if (mobileObject instanceof Player)
                    SoundEffects.PLACE_BOMB.play(); // Play bomb placement sound for players
            }
        }
    }

    /**
     * Handles the collection of power-ups by a player.
     * <p>
     * Checks if the player's current cell contains a power-up. If so, applies the power-up effect to the player,
     * plays a power-up collection sound, and removes the power-up from the map.
     *
     * @param player The player attempting to collect a power-up.
     */
    private void collectPowerUp(Player player) {
        int playerCellX = player.getCellX(); // Get player cell X coordinate
        int playerCellY = player.getCellY(); // Get player cell Y coordinate
        if (walls.containsKey(playerCellX + "," + playerCellY)) {
            if (walls.get(playerCellX + "," + playerCellY) instanceof PowerUp powerUp) {
                SoundEffects.POWER_UP.play(); // Play power-up collection sound effect
                WallContentType type = powerUp.getType(); // Get power-up type
                switch (type) {
                    case BOMBS_POWER_UP:
                        player.increaseConcurrentBombs(); // Increase player's concurrent bomb count
                        break;
                    case FLAMES_POWER_UP:
                        player.increaseBlastRadius(); // Increase player's blast radius
                        break;
                    case SPEED_POWER_UP:
                        player.increaseSpeed(); // Increase player's speed
                        break;
                    case WALLPASS_POWER_UP:
                        player.gotWallpass(); // Grant player wallpass ability
                        break;
                    case BOMBPASS_POWER_UP:
                        player.gotBombpass(); // Grant player bombpass ability
                        break;
                    case FLAMEPASS_POWER_UP:
                        player.gotFlamepass(); // Grant player flamepass ability
                        break;
                }
                walls.remove(playerCellX + "," + playerCellY); // Remove power-up from map
                powerUps.remove(playerCellX + "," + playerCellY); // Remove power-up location from list
            }
        }
    }


    /**
     * Checks if a mobile object is within the blast radius of a blast effect.
     *
     * @param obj   The mobile object (Player or Enemy) to check.
     * @param blast The blast effect to check against.
     * @return {@code true} if the mobile object is in the same cell as the blast, {@code false} otherwise.
     */
    private boolean isBlasted(MobileObject obj, Blast blast) {
        return obj.getCellX() == blast.getX() && obj.getCellY() == blast.getY(); // Check if object cell matches blast cell
    }

    /**
     * Checks if a mobile object is overlapping with a bomb.
     *
     * @param mobileObject The mobile object (Player or Enemy) to check.
     * @param bomb         The bomb to check for overlap.
     * @return {@code true} if the mobile object's hitbox is overlapping with the bomb's hitbox, {@code false} otherwise.
     */
    private boolean isOverlapping(MobileObject mobileObject, Bomb bomb) {
        Body bodyA = mobileObject.getHitbox(); // Get mobile object hitbox
        Body bodyB = bomb.getHitbox(); // Get bomb hitbox
        if (mobileObject instanceof Player)
            return bodyA.getPosition().dst(bodyB.getPosition()) < 0.77f; // Player overlap threshold
        return bodyA.getPosition().dst(bodyB.getPosition()) < 0.94f; // Adjust threshold as needed for enemies
    }

    /**
     * Handles the overlapping state between a mobile object and a bomb.
     * <p>
     * Adds the bomb to the mobile object's ignored bombs list if they are overlapping to prevent immediate self-explosion.
     * Removes the bomb from the ignored list when they are no longer overlapping, allowing for collision detection again.
     *
     * @param mobileObject The mobile object (Player or Enemy) to handle bomb overlap for.
     * @param bomb         The bomb to check for overlap.
     */
    private void handleBombsOverlapping(MobileObject mobileObject, Bomb bomb) {
        if (mobileObject.isAlive()) {
            if (isOverlapping(mobileObject, bomb)) {
                if (!(mobileObject.getIgnoredBombs().contains(bomb.getHitbox()))) {
                    mobileObject.addIgnoredBomb(bomb.getHitbox()); // Add bomb to ignored list if overlapping and not already ignored
                }
            } else {
                if (mobileObject.getIgnoredBombs().contains(bomb.getHitbox())) {
                    mobileObject.removeIgnoredBomb(bomb.getHitbox()); // Remove bomb from ignored list if no longer overlapping
                }
            }
        }
    }

    /**
     * Releases a blast effect in a specified direction from a bomb's explosion center.
     * <p>
     * Creates blast segments outwards from the center, stopping at indestructible walls or destructible walls (destroying them).
     * Manages blast type (CENTER, HORIZONTAL, VERTICAL, directional ends, WALL) and creates blast entities accordingly.
     *
     * @param x    The x-coordinate of the blast center.
     * @param y    The y-coordinate of the blast center.
     * @param dx   The x-direction of the blast (0, 1, or -1).
     * @param dy   The y-direction of the blast (0, 1, or -1).
     * @param bomb The bomb that initiated the blast, used to determine blast radius and owner.
     */
    private void releaseBlast(int x, int y, int dx, int dy, Bomb bomb) {
        MobileObject owner = bomb.getOwner(); // Get bomb owner
        for (int i = 1; i <= bomb.getBlastRadius(); i++) {
            int currentX = x + i * dx; // Calculate blast segment x coordinate
            int currentY = y + i * dy; // Calculate blast segment y coordinate
            if (walls.containsKey(currentX + "," + currentY)) {
                StationaryObject obj = walls.get(currentX + "," + currentY);
                if (obj instanceof DestructibleWall wall) {
                    WallContentType type = wall.getWallContentType(); // Get destructible wall content type
                    wall.destroy(world); // Destroy destructible wall
                    walls.remove(currentX + "," + currentY); // Remove wall from map
                    if (type != WallContentType.EMPTY && type != WallContentType.EXIT) {
                        walls.put(currentX + "," + currentY, new PowerUp(world, currentX, currentY, type)); // Place power-up at destroyed wall location
                        powerUps.add(currentX + "," + currentY); // Add power-up location to list
                    } else if (type == WallContentType.EXIT) {
                        exit = new Exit(world, currentX, currentY); // Place exit at destroyed wall location
                        if (exitOpen) exit.open(); // Open exit if game condition is met
                    }
                    blasts.add(new Blast(world, currentX, currentY, BlastType.WALL, owner)); // Add wall blast effect
                    break; // Stop blast propagation in this direction after hitting a wall
                }
                if (!(obj instanceof PowerUp)) {
                    break; // Stop blast propagation if it hits something other than a destructible wall or power-up
                }
            }
            if (i == bomb.getBlastRadius()) {
                if (dx == 0 && dy < 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.DOWN, owner)); // Add down blast end segment
                } else if (dx == 0 && dy > 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.UP, owner)); // Add up blast end segment
                } else if (dy == 0 && dx > 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.RIGHT, owner)); // Add right blast end segment
                } else {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.LEFT, owner)); // Add left blast end segment
                }
            } else {
                if (dx == 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.VERTICAL, owner)); // Add vertical blast segment
                } else {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.HORIZONTAL, owner)); // Add horizontal blast segment
                }
            }

        }
    }

    /**
     * Checks if a cell in the game grid is currently free for placing objects like bombs.
     * <p>
     * A cell is considered free if it does not contain any walls or bombs (except for PowerUp walls, which are passable).
     *
     * @param x The x-coordinate of the cell to check.
     * @param y The y-coordinate of the cell to check.
     * @return {@code true} if the cell is free, {@code false} otherwise.
     */
    public boolean isCellFree(int x, int y) {
        if (walls.containsKey(x + "," + y)) {
            return (walls.get(x + "," + y) instanceof PowerUp); // Cell is free if it only contains a power-up
        }
        return !bombs.containsKey(x + "," + y); // Cell is free if it doesn't contain a bomb
    }

    /**
     * Checks if any player is currently dead.
     *
     * @return {@code true} if player 1 is dead, or if player 2 exists and is dead, {@code false} otherwise.
     */
    public boolean isPlayerDead() {
        if (player1.isDead()) return true; // Player 1 is dead
        if (player2 != null) return player2.isDead(); // Player 2 is dead if exists and dead
        return false; // No player is dead
    }

    /**
     * Gets the first player entity.
     *
     * @return The {@link Player} object for player 1.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Gets the second player entity.
     *
     * @return The {@link Player} object for player 2, or {@code null} if player 2 does not exist (single-player mode).
     */
    public Player getPlayer2() {
        return player2;
    }


    /**
     * Gets the map of walls in the game.
     *
     * @return A {@link Map} containing all wall objects, indexed by their cell coordinates.
     */
    public Map<String, StationaryObject> getWalls() {
        return walls;
    }

    /**
     * Gets the list of plus points visual effects.
     *
     * @return A {@link List} of {@link PlusPoints} objects.
     */
    public List<PlusPoints> getPlusPoints() {
        return plusPoints;
    }

    /**
     * Gets the list of enemy entities in the game.
     *
     * @return A {@link List} of {@link Enemy} objects.
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Gets the exit object of the game map.
     *
     * @return The {@link Exit} object, or {@code null} if no exit exists.
     */
    public Exit getExit() {
        return exit;
    }

    /**
     * Gets a list of all bombs currently active in the game.
     *
     * @return A {@link List} of {@link Bomb} objects.
     */
    public List<Bomb> getBombs() {
        return bombs.values().stream().toList();
    }

    /**
     * Gets the list of blast effects currently active in the game.
     *
     * @return A {@link List} of {@link Blast} objects.
     */
    public List<Blast> getBlasts() {
        return blasts;
    }


    /**
     * Checks if the exit is currently open.
     *
     * @return {@code true} if the exit is open, {@code false} otherwise.
     */
    public boolean isExitOpen() {
        return exitOpen;
    }

    /**
     * Gets the maximum x-coordinate of the game map in grid cells.
     *
     * @return The maximum x-coordinate.
     */
    public int getMAX_X() {
        return MAX_X;
    }

    /**
     * Gets the maximum y-coordinate of the game map in grid cells.
     *
     * @return The maximum y-coordinate.
     */
    public int getMAX_Y() {
        return MAX_Y;
    }

    /**
     * Checks if the game is currently in a game over state.
     *
     * @return {@code true} if the game is over, {@code false} otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Gets the game over message, which describes the reason for game over.
     *
     * @return The game over message string.
     */
    public String getGameOverMessage() {
        return gameOverMessage;
    }

    /**
     * Sets the game over message.
     * <p>
     * Only sets the message if it is currently empty, preventing overwriting of the initial game over reason.
     *
     * @param gameOverMessage The game over message to set.
     */
    public void setGameOverMessage(String gameOverMessage) {
        if (this.gameOverMessage.isEmpty())
            this.gameOverMessage = gameOverMessage; // Set game over message only if it's empty
    }

    /**
     * Gets the number of enemies currently alive in the game.
     *
     * @return The number of active enemies.
     */
    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }

    /**
     * Gets the remaining time on the game timer in seconds.
     *
     * @return The remaining time in seconds, rounded up to the nearest integer.
     */
    public int getTimer() {
        return (int) Math.ceil(timer - elapsedTime);
    }

    /**
     * Checks if the game has started (i.e., if any time has elapsed).
     *
     * @return {@code true} if the game has started, {@code false} otherwise.
     */
    public boolean isStarted() {
        return elapsedTime > 0;
    }


}