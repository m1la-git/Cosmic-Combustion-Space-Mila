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
        Box2D.init();
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
    private final Player player1;
    private final Player player2;
    private final List<Enemy> enemies;
    private final Map<String, Bomb> bombs;
    private final List<Blast> blasts;
    private final float TIMER;

    private final Map<String, StationaryObject> walls;

    private final int MAX_X;
    private final int MAX_Y;
    private boolean gameOver;
    private String gameOverMessage;
    private int numberOfEnemies;
    private float elapsedTime = 0;
    private Exit exit;
    private final List<String> powerUps;
    private boolean exitOpen;
    private final List<PlusPoints> plusPoints;

    /**
     * The accumulated time since the last physics step.
     * We use this to keep the physics simulation at a constant rate even if the frame rate is variable.
     */
    private float physicsTime = 0;

    public GameMap(BomberQuestGame game, String mapFile) {
        this.game = game;
        this.world = new World(Vector2.Zero, true);
        this.contactListener = new GameContactListener(this);
        this.world.setContactListener(contactListener);
        this.walls = new HashMap<>();
        this.bombs = new HashMap<>();
        this.blasts = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.gameOver = false;
        this.gameOverMessage = "";
        exitOpen = false;
        plusPoints = new ArrayList<>();

        powerUps = new ArrayList<>();


        int[] temp = loadMap(mapFile);
        this.MAX_X = temp[0];
        this.MAX_Y = temp[1];
        if (temp[4] == -1) {
            this.player1 = new Player(world, temp[2], temp[3]);
            this.player2 = null;
        } else {
            this.player1 = new Player(world, temp[2], temp[3], true);
            this.player2 = new Player(world, temp[4], temp[5], false);
        }

        numberOfEnemies = enemies.size();
        if (numberOfEnemies == 0) {
            exitOpen = true;
        }

        if (player2 != null) TIMER = 250f;
        else TIMER = 400f;
        // Create a player with initial position
    }

    /**
     * reads the map file line by line
     * throws exceptions when the file is in the wrong format
     * adds to the list with the respective types of the objects (walls, entrance, etc.)
     *
     * @param filename the name of the file with the map
     */
    public int[] loadMap(String filename) {
        boolean powerUpsWritten = false;
        int maxX = 0;
        int maxY = 0;
        int[] entrance1 = new int[]{-1, -1};
        int[] entrance2 = new int[]{-1, -1};
        boolean existsExit = false;
        List<String> freeDestructibleWalls = new ArrayList<>();
        FileHandle file = Gdx.files.internal(filename);
        try {
            for (String line : file.readString().split("\\r?\\n")) {
                line = line.trim();
                if (line.startsWith("#") || line.isEmpty()) {
                    continue; // Skip comments and empty lines
                }
                String[] parts = line.split("=");
                if (parts.length != 2) {
                    game.errorLoadingMap("Invalid line format: " + line);
                    return new int[]{-1, -1, 0, 0, 0, 0};
                }
                String[] coords = parts[0].split(",");
                int type = Integer.parseInt(parts[1]);
                if (coords.length != 2) {
                    game.errorLoadingMap("Invalid coordinate format: " + parts[0]);
                    return new int[]{-1, -1, 0, 0, 0, 0};
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
                        if (entrance1[0] == -1) {
                            entrance1[0] = x;
                            entrance1[1] = y;
                        }
                        //max 2 players
                        else {

                            entrance2[0] = x;
                            entrance2[1] = y;
                        }
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
                        powerUpsWritten = true;
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.BOMBS_POWER_UP));
                        break;
                    case 6: // powerUp: flames
                        powerUpsWritten = true;
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.FLAMES_POWER_UP));
                        break;
                    case 7: // powerUp: speed
                        powerUpsWritten = true;
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.SPEED_POWER_UP));
                        break;
                    case 8: // powerUp: wallpass
                        powerUpsWritten = true;
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.WALLPASS_POWER_UP));
                        break;
                    case 9: // powerUp: wallpass
                        powerUpsWritten = true;
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.DETONATOR_POWER_UP));
                        break;
                    case 10: // powerUp: bombpass
                        powerUpsWritten = true;
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.BOMBPASS_POWER_UP));
                        break;
                    case 11: // powerUp: flamepass
                        powerUpsWritten = true;
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.FLAMEPASS_POWER_UP));
                        break;
                    case 12: // powerUp: mystery
                        powerUpsWritten = true;
                        walls.put(x + "," + y, new DestructibleWall(world, x, y, WallContentType.MYSTERY_POWER_UP));
                        break;
                }

            }
            //at least one player
            if (entrance1[0] == -1) {
                Gdx.app.error("GameMap", "Entrance not found.");
            }
            //create exit if absent
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
                    freeDestructibleWalls.remove(randomX + "," + randomY);
                }
            }
            // generate power-ups if there are none on the map
            if (!powerUpsWritten && !freeDestructibleWalls.isEmpty()) {
                for (String key : freeDestructibleWalls) {
                    Random random = new Random();
                    if (random.nextInt(100) < 20) { // 20% chance power-up drop
                        String[] wallCoords = key.split(",");
                        int wallX = Integer.parseInt(wallCoords[0]);
                        int wallY = Integer.parseInt(wallCoords[1]);
                        int randomNum = random.nextInt(100);
                        walls.get(wallX + "," + wallY).destroy(world);
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
            Gdx.app.error("GameMap", "Error reading map file: " + e.getMessage());
        }
        return new int[]{maxX, maxY, entrance1[0], entrance1[1], entrance2[0], entrance2[1]};
    }


    /**
     * Updates the game state. This is called once per frame.
     * Every dynamic object in the game should update its state here.
     *
     * @param frameTime the time that has passed since the last update
     */
    public void tick(float frameTime) {
        handleInput();
        player1.tick(frameTime);
        if (player2 != null) player2.tick(frameTime);
        elapsedTime += frameTime;
        if (elapsedTime >= TIMER) {
            setGameOverMessage("Too slow! The timer hit zero.");
            gameOver = true;
        }
        if (isPlayerDead()) {
            gameOver = true;
        }

        if (exit != null) exit.tick(frameTime);

        if (player1.isAlive()) {
            //power-ups
            collectPowerUp(player1);

            checkExit();
            // place bomb if space is pressed and limit of bombs isn't reached
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                placeBomb(player1);
            }
        }
        if (player2 != null && player2.isAlive()) {
            collectPowerUp(player2);
            // place bomb if enter is pressed and limit of bombs isn't reached
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                placeBomb(player2);
            }
        }

        //enemies ticks
        Iterator<Enemy> iteratorEnemy = enemies.iterator();
        while (iteratorEnemy.hasNext()) {
            Enemy enemy = iteratorEnemy.next();
            enemy.tick(frameTime);
            if (enemy.isDead()) {
                iteratorEnemy.remove(); // Safe removal
            }
        }

        // bomb ticks and handling bomb collisions
        Iterator<Map.Entry<String, Bomb>> iteratorBombs = bombs.entrySet().iterator();
        while (iteratorBombs.hasNext()) {
            Map.Entry<String, Bomb> entry = iteratorBombs.next();
            Bomb bomb = entry.getValue();
            bomb.tick(frameTime);
            handleBombsOverlapping(player1, bomb);
            if (player2 != null) handleBombsOverlapping(player2, bomb);
            for (Enemy enemy : enemies) handleBombsOverlapping(enemy, bomb);
            if (bomb.isExploded()) {
                SoundEffects.BOMB_EXPLOSION.play();
                int bombX = bomb.getCellX();
                int bombY = bomb.getCellY();
                releaseBlast(bombX, bombY, 0, 1, bomb);  // Up
                releaseBlast(bombX, bombY, 0, -1, bomb); // Down
                releaseBlast(bombX, bombY, 1, 0, bomb);  // Right
                releaseBlast(bombX, bombY, -1, 0, bomb); // Left
                blasts.add(new Blast(world, bombX, bombY, BlastType.CENTER, bomb.getOwner()));
                bomb.destroy(world);
                iteratorBombs.remove();
                // Safely remove the current entry
            }
        }

        //blasts ticks
        Iterator<Blast> iteratorBlasts = blasts.iterator();
        while (iteratorBlasts.hasNext()) {
            Blast blast = iteratorBlasts.next();
            blast.tick(frameTime);
            if (blast.isFinished()) {
                iteratorBlasts.remove(); // Safe removal
                if (blast.getType() == BlastType.WALL) {
                    blast.destroy(world);
                }
                continue; // No need to process further if removed
            }
            //explode the bombs under the blast
            if (bombs.containsKey(blast.getCellX() + "," + blast.getCellY())) {
                bombs.get(blast.getCellX() + "," + blast.getCellY()).explodeNow();
            }

            // enemies' death
            for (Enemy enemy : enemies) {
                if (!enemy.isAlive()) {
                    continue;
                }
                if (isBlasted(enemy, blast)) {
                    if (blast.getOwner() instanceof Player player) {
                        player.increasePoints();
                        plusPoints.add(new PlusPoints(enemy.getX(), enemy.getY() + 1f, player.isPlayer1()));
                    }
                    enemy.death(world);
                    SoundEffects.ENEMY_DEATH.play();
                    numberOfEnemies--;

                    if (numberOfEnemies == 0 && player1.isAlive()) {
                        SoundEffects.STAGE_CLEAR.play();
                        exitOpen = true;
                        if (exit != null) exit.open();
                    }
                }
            }
            //players' death
            if (player1.isAlive()) {
                playerBlasted(blast, player1, player2);
            }
            if (player2 != null && player2.isAlive()) {
                playerBlasted(blast, player2, player1);
            }
        }

        // power-up ticks for animation
        for (String key : powerUps) {
            if (walls.get(key) instanceof PowerUp powerUp) powerUp.tick(frameTime);
        }

        //plus points
        Iterator<PlusPoints> iteratorPoints = plusPoints.iterator();
        while (iteratorPoints.hasNext()) {
            PlusPoints plusPoint = iteratorPoints.next();
            plusPoint.tick(frameTime);
            if (plusPoint.isFinished()) {
                iteratorPoints.remove(); // Safe removal
            }
        }


        contactListener.processQueuedDestruction();
        doPhysicsStep(frameTime);

    }

    private void playerBlasted(Blast blast, Player player, Player playerOther) {
        if (isBlasted(player, blast)) {
            player.death(world);
            if (blast.getOwner() instanceof Enemy) setGameOverMessage("BOOM! " + player.getName() + " got caught in Alien's blast!");
            else if (blast.getOwner().equals(player)) setGameOverMessage("Oops! " + player.getName() + " blew themselves up!");
            else setGameOverMessage(player.getName() + " got caught in " + playerOther.getName() + "'s explosion!");
        }
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

    private void handleInput() {
        // wasd
        if (player1.isAlive()) {
            handlePlayerInput(player1, Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D);
        }
        // arrows
        if (player2 != null && player2.isAlive()) {
            handlePlayerInput(player2, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT);
        }
        else if (player2 == null && player1.isAlive()){
            handlePlayerInput(player1, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT);
        }
    }
    private void handlePlayerInput(Player player, int upKey, int downKey, int leftKey, int rightKey) {
        // Check and add keys if pressed
        if (Gdx.input.isKeyPressed(upKey)) {
            player.addKeys(upKey);
        } else {
            player.removeKeys(upKey);
        }

        if (Gdx.input.isKeyPressed(downKey)) {
            player.addKeys(downKey);
        } else {
            player.removeKeys(downKey);
        }

        if (Gdx.input.isKeyPressed(leftKey)) {
            player.addKeys(leftKey);
        }else{
            player.removeKeys(leftKey);
        }

        if (Gdx.input.isKeyPressed(rightKey)) {
            player.addKeys(rightKey);
        }else{
            player.removeKeys(rightKey);
        }
    }

    private void checkExit() {
        int player1CellX = player1.getCellX();
        int player1CellY = player1.getCellY();
        if (exit != null) {
            //exit
            if (exit.getCellX() == player1CellX && exit.getCellY() == player1CellY && exitOpen) {
                if (player2 != null) {
                    if (player2.isAlive() && exit.getCellX() == player2.getCellX() && exit.getCellY() == player2.getCellY()) {
                        gameOver = true;
                    }
                } else {
                    gameOver = true;
                }
            }
        }
    }

    public void placeBomb(MobileObject mobileObject) {
        if (mobileObject.getBombsNow() > 0) {
            int cellX = mobileObject.getCellX();
            int cellY = mobileObject.getCellY();
            boolean spaceEmpty = !(bombs.containsKey(cellX + "," + cellY));
            if (spaceEmpty) {
                bombs.put(cellX + "," + cellY, new Bomb(world, cellX, cellY, mobileObject));
                mobileObject.placedBomb();
                if (mobileObject instanceof Player) SoundEffects.PLACE_BOMB.play();
            }
        }
    }

    private void collectPowerUp(Player player) {
        int playerCellX = player.getCellX();
        int playerCellY = player.getCellY();
        if (walls.containsKey(playerCellX + "," + playerCellY)) {
            if (walls.get(playerCellX + "," + playerCellY) instanceof PowerUp powerUp) {
                SoundEffects.POWER_UP.play();
                WallContentType type = powerUp.getType();
                switch (type) {
                    case BOMBS_POWER_UP:
                        player.increaseConcurrentBombs();
                        break;
                    case FLAMES_POWER_UP:
                        player.increaseBlastRadius();
                        break;
                }
                walls.remove(playerCellX + "," + playerCellY);
                powerUps.remove(playerCellX + "," + playerCellY);
            }
        }
    }


    private boolean isBlasted(MobileObject obj, Blast blast) {
        return obj.getCellX() == blast.getX() && obj.getCellY() == blast.getY();
    }

    /**
     * Checks whether the bomb and the player are overlapping
     *
     * @param mobileObject: Player or Enemy
     * @param bomb: Bomb
     */
    private boolean isOverlapping(MobileObject mobileObject, Bomb bomb) {
        Body bodyA = mobileObject.getHitbox();
        Body bodyB = bomb.getHitbox();
        if (mobileObject instanceof Player) return bodyA.getPosition().dst(bodyB.getPosition()) < 0.77f;
        return bodyA.getPosition().dst(bodyB.getPosition()) < 0.94f; // Adjust threshold as needed
    }

    private void handleBombsOverlapping(MobileObject mobileObject, Bomb bomb) {
        if (mobileObject.isAlive()) {
            if (isOverlapping(mobileObject, bomb)) {
                if (!(mobileObject.getIgnoredBombs().contains(bomb.getHitbox()))) {
                    mobileObject.addIgnoredBomb(bomb.getHitbox());
                }
            } else {
                if (mobileObject.getIgnoredBombs().contains(bomb.getHitbox())) {
                    mobileObject.removeIgnoredBomb(bomb.getHitbox());
                }
            }
        }
    }

    /**
     * Releasing the blast in 1 direction in a certain radius
     * Until any stationaryObject is met on the way
     */
    private void releaseBlast(int x, int y, int dx, int dy, Bomb bomb) {
        MobileObject owner = bomb.getOwner();
        for (int i = 1; i <= bomb.getBlastRadius(); i++) {
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
                        powerUps.add(currentX + "," + currentY);
                    } else if (type == WallContentType.EXIT) {
                        exit = new Exit(world, currentX, currentY);
                        if (exitOpen) exit.open();
                    }
                    blasts.add(new Blast(world, currentX, currentY, BlastType.WALL, owner));
                    break;
                }
                if (!(obj instanceof PowerUp)) {
                    break;
                }
            }
            if (i == bomb.getBlastRadius()) {
                if (dx == 0 && dy < 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.DOWN, owner));
                } else if (dx == 0 && dy > 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.UP, owner));
                } else if (dy == 0 && dx > 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.RIGHT, owner));
                } else {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.LEFT, owner));
                }
            } else {
                if (dx == 0) {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.VERTICAL, owner));
                } else {
                    blasts.add(new Blast(world, currentX, currentY, BlastType.HORIZONTAL, owner));
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
        return !bombs.containsKey(x + "," + y);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }


    public Map<String, StationaryObject> getWalls() {
        return walls;
    }

    public List<PlusPoints> getPlusPoints() {
        return plusPoints;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Exit getExit() {
        return exit;
    }

    /**
     * Returns the bombs on the map.
     */
    public List<Bomb> getBombs() {
        return bombs.values().stream().toList();
    }

    public List<Blast> getBlasts() {
        return blasts;
    }

    public boolean isPlayerDead() {
        if (player1.isDead()) return true;
        if (player2 != null) return player2.isDead();
        return false;
    }
    public boolean isExitOpen() {
        return exitOpen;
    }

    public int getMAX_X() {
        return MAX_X;
    }

    public int getMAX_Y() {
        return MAX_Y;
    }

    public boolean isGameOver() {
        return gameOver;
    }
    public String getGameOverMessage() {
        return gameOverMessage;
    }
    public void setGameOverMessage(String gameOverMessage) {
        if (this.gameOverMessage.isEmpty()) this.gameOverMessage = gameOverMessage;
    }

    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }

    public int getTimer() {
        return (int) Math.ceil(TIMER - elapsedTime);
    }
    public boolean isStarted() {
        return elapsedTime > 0;
    }

}
