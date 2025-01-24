package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Represents the player character in the game.
 * <p>
 * Players are mobile objects controlled by the user. They can move around the map, place bombs,
 * and collect power-ups. This class extends {@link MobileObject} to inherit basic mobile object
 * functionalities like movement and collision detection. Players are represented as dynamic bodies in the Box2D world.
 */
public class Player extends MobileObject implements Drawable {
    /**
     * Double-ended queue to manage the order of key presses for player movement.
     * This allows for responsive and ordered handling of simultaneous key inputs,
     * ensuring that the last pressed key dictates the player's current direction when multiple keys are held.
     */
    private final Deque<Integer> keyPressOrder = new ArrayDeque<>();

    /**
     * Flag to identify if this player is Player 1 or Player 2.
     * This is used to differentiate player-specific attributes like controls and appearance.
     */
    private final boolean player1;
    /**
     * The score or points accumulated by the player during the game.
     * Points are typically awarded for actions like defeating enemies.
     */
    private int points;
    /**
     * The name of the player, used for display purposes such as in game over messages or scoreboards.
     */
    private String name;

    //power-ups
    /**
     * Flag indicating if the player has the 'wallpass' power-up, allowing them to move through walls.
     */
    private boolean wallpass = false;
    /**
     * Flag indicating if the player has the 'bombpass' power-up, allowing them to move through bombs.
     */
    private boolean bombpass = false;
    /**
     * Flag indicating if the player has the 'flamepass' power-up, making them immune to bomb blasts.
     */
    private boolean flamepass = false;


    /**
     * Constructor for creating a new Player object, specifically for Player 1 or Player 2 differentiation.
     *
     * @param world   The Box2D world to which the player's hitbox will be added.
     * @param x       The starting x-coordinate of the player in world units.
     * @param y       The starting y-coordinate of the player in world units.
     * @param player1 Boolean flag to indicate if this is Player 1 (true) or Player 2 (false).
     */
    public Player(World world, float x, float y, boolean player1) {
        super(world, x, y, 2, 0.3f);
        this.player1 = player1;
        points = 0;
        name = (player1) ? "Player 1" : "Player 2";
    }

    /**
     * Constructor for creating a new Player object, defaulting to Player 1.
     * This constructor is used when only one player is needed, and it defaults to creating Player 1.
     *
     * @param world The Box2D world to which the player's hitbox will be added.
     * @param x     The starting x-coordinate of the player in world units.
     * @param y     The starting y-coordinate of the player in world units.
     */
    public Player(World world, float x, float y) {
        super(world, x, y, 2, 0.3f);
        this.player1 = true;
        points = 0;
        name = "Player 1";
    }

    /**
     * Updates the player's state every frame.
     * <p>
     * This method is called in the game loop and is responsible for handling player input,
     * moving the player based on input, and managing player death state.
     *
     * @param frameTime The time elapsed since the last frame in seconds.
     */
    @Override
    public void tick(float frameTime) {
        increaseElapsedTime(frameTime);
        if (isAlive()) {
            handleInput(); // Process player input to determine direction
            moveInDirection(); // Move the player in the determined direction
        } else {
            if (!isAlive() && getElapsedTime() >= 1.15f && !isDead())
                setDead(); // Manage death animation and set dead state
        }
    }

    /**
     * Handles player input to determine the movement direction.
     * <p>
     * This method checks the {@link #keyPressOrder} queue to determine the most recent movement key pressed by the player.
     * Based on the last key pressed, it sets the player's direction for movement in the {@link #tick(float)} method.
     * If no movement keys are currently pressed, the player's direction is set to {@link DirectionType#NONE}.
     */
    private void handleInput() {
        // set the direction
        if (!keyPressOrder.isEmpty()) {
            switch (keyPressOrder.peekLast()) {
                case Input.Keys.W, Input.Keys.UP:
                    setDirection(DirectionType.UP); // Set direction to UP if W or UP arrow key is last pressed
                    break;
                case Input.Keys.S, Input.Keys.DOWN:
                    setDirection(DirectionType.DOWN); // Set direction to DOWN if S or DOWN arrow key is last pressed
                    break;
                case Input.Keys.A, Input.Keys.LEFT:
                    setDirection(DirectionType.LEFT); // Set direction to LEFT if A or LEFT arrow key is last pressed
                    break;
                case Input.Keys.D, Input.Keys.RIGHT:
                    setDirection(DirectionType.RIGHT); // Set direction to RIGHT if D or RIGHT arrow key is last pressed
                    break;
            }
        } else {
            setDirection(DirectionType.NONE); // Set direction to NONE if no movement key is pressed
        }
    }


    /**
     * Checks if this player is Player 1.
     *
     * @return {@code true} if this player is Player 1, {@code false} if it is Player 2.
     */
    public boolean isPlayer1() {
        return player1;
    }

    /**
     * Adds a keycode to the player's key press order queue.
     * <p>
     * This method is typically called when a movement key is pressed. It ensures that keys are added only once
     * to maintain the correct order of precedence for movement direction.
     *
     * @param keycode The keycode of the pressed key (e.g., Input.Keys.W, Input.Keys.UP).
     */
    public void addKeys(int keycode) {
        if (!keyPressOrder.contains(keycode)) {
            keyPressOrder.addLast(keycode); // Add keycode to the end of the queue if not already present
        }

    }

    /**
     * Removes a keycode from the player's key press order queue.
     * <p>
     * This method is called when a movement key is released. It removes the corresponding keycode from the queue,
     * allowing the player to change direction if another key is still pressed.
     *
     * @param keycode The keycode of the released key (e.g., Input.Keys.W, Input.Keys.UP).
     */
    public void removeKeys(int keycode) {
        keyPressOrder.remove(keycode); // Remove keycode from the queue
    }

    /**
     * Gets the current score of the player.
     *
     * @return The player's score as an integer.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Increases the player's score by a fixed amount (100 points).
     * This method is typically called when the player performs a scoring action, such as defeating an enemy.
     */
    public void increasePoints() {
        this.points += 100;
    }

    /**
     * Gets the current texture region representing the player's appearance.
     * <p>
     * The appearance changes based on the player's direction of movement and player type (Player 1 or Player 2).
     * If the player is not moving, a static texture based on the last direction is returned. When moving, animated textures
     * for walking in each direction are used. If the player is not alive, the death animation texture is returned.
     *
     * @return The current {@link TextureRegion} for rendering the player.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        if (isAlive()) {
            // Get the frame of the walk animation that corresponds to the current time and direction.
            if (player1) {
                return switch (getDirection()) {
                    case UP ->
                            Animations.PLAYER1_WALK_UP.getKeyFrame(getElapsedTime(), true); // Player 1 walking up animation
                    case DOWN ->
                            Animations.PLAYER1_WALK_DOWN.getKeyFrame(getElapsedTime(), true); // Player 1 walking down animation
                    case LEFT ->
                            Animations.PLAYER1_WALK_LEFT.getKeyFrame(getElapsedTime(), true); // Player 1 walking left animation
                    case RIGHT ->
                            Animations.PLAYER1_WALK_RIGHT.getKeyFrame(getElapsedTime(), true); // Player 1 walking right animation
                    case NONE -> { // Player 1 static texture when not moving
                        yield switch (getLastDirection()) {
                            case UP -> Textures.PLAYER1_UP; // Static texture facing up
                            case DOWN, NONE -> Textures.PLAYER1_DOWN; // Static texture facing down or default
                            case LEFT -> Textures.PLAYER1_LEFT; // Static texture facing left
                            case RIGHT -> Textures.PLAYER1_RIGHT; // Static texture facing right
                        };
                    }
                };
            } else {
                return switch (getDirection()) {
                    case UP ->
                            Animations.PLAYER2_WALK_UP.getKeyFrame(getElapsedTime(), true); // Player 2 walking up animation
                    case DOWN ->
                            Animations.PLAYER2_WALK_DOWN.getKeyFrame(getElapsedTime(), true); // Player 2 walking down animation
                    case LEFT ->
                            Animations.PLAYER2_WALK_LEFT.getKeyFrame(getElapsedTime(), true); // Player 2 walking left animation
                    case RIGHT ->
                            Animations.PLAYER2_WALK_RIGHT.getKeyFrame(getElapsedTime(), true); // Player 2 walking right animation
                    case NONE -> { // Player 2 static texture when not moving
                        yield switch (getLastDirection()) {
                            case UP -> Textures.PLAYER2_UP; // Static texture facing up
                            case DOWN, NONE -> Textures.PLAYER2_DOWN; // Static texture facing down or default
                            case LEFT -> Textures.PLAYER2_LEFT; // Static texture facing left
                            case RIGHT -> Textures.PLAYER2_RIGHT; // Static texture facing right
                        };
                    }
                };
            }


        }
        return Animations.PLAYER_DEATH.getKeyFrame(getElapsedTime(), false); // Player death animation
    }

    /**
     * Gets the name of the player.
     *
     * @return The player's name as a String (e.g., "Player 1", "Player 2").
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     *
     * @param name The new name to set for the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the player has the 'wallpass' power-up.
     *
     * @return {@code true} if the player has wallpass, {@code false} otherwise.
     */
    public boolean isWallpass() {
        return wallpass;
    }

    /**
     * Checks if the player has the 'bombpass' power-up.
     *
     * @return {@code true} if the player has bombpass, {@code false} otherwise.
     */
    public boolean isBombpass() {
        return bombpass;
    }

    /**
     * Checks if the player has the 'flamepass' power-up.
     *
     * @return {@code true} if the player has flamepass, {@code false} otherwise.
     */
    public boolean isFlamepass() {
        return flamepass;
    }

    /**
     * Grants the 'wallpass' power-up to the player, allowing them to pass through walls.
     */
    public void gotWallpass() {
        wallpass = true;
    }

    /**
     * Grants the 'bombpass' power-up to the player, allowing them to pass through bombs.
     */
    public void gotBombpass() {
        bombpass = true;
    }

    /**
     * Grants the 'flamepass' power-up to the player, making them immune to bomb blasts.
     */
    public void gotFlamepass() {
        flamepass = true;
    }


}