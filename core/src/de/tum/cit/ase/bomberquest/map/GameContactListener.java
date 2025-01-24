package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Box2D {@link ContactListener} interface to handle collision events in the game world.
 * This class is responsible for detecting and resolving collisions between different game objects,
 * such as players, enemies, bombs, and walls. It determines the type of objects involved in a collision
 * and applies specific game logic to handle the interaction, like preventing collisions under certain conditions
 * (e.g., bomb immunity), handling player death on enemy contact, and queueing bodies for destruction.
 */
public class GameContactListener implements ContactListener {

    /**
     * A list to hold Box2D bodies that are queued for destruction.
     * Bodies are added to this list during collision handling and destroyed in the {@link #processQueuedDestruction()} method
     * to avoid modifying the Box2D world during collision callbacks, which can lead to errors.
     */
    private final List<Body> bodiesToDestroy = new ArrayList<>();
    /**
     * Reference to the game map, allowing the contact listener to access game state and perform actions
     * based on the game map, such as setting game over conditions.
     */
    private final GameMap map;

    /**
     * Constructs a new {@code GameContactListener}.
     *
     * @param map The {@link GameMap} instance associated with this contact listener.
     *            This map is used to access game elements and control game state based on collision events.
     */
    public GameContactListener(GameMap map) {
        this.map = map;
    }

    /**
     * Called when two fixtures begin to touch.
     * This method is part of the {@link ContactListener} interface but is not used in this implementation.
     * Subclasses can override this method to implement specific logic for the start of a contact.
     *
     * @param contact The contact fixture.
     */
    @Override
    public void beginContact(Contact contact) {
        // Not needed for this use case
    }

    /**
     * Called when two fixtures cease to touch.
     * This method is part of the {@link ContactListener} interface but is not used in this implementation.
     * Subclasses can override this method to implement specific logic for the end of a contact.
     *
     * @param contact The contact fixture.
     */
    @Override
    public void endContact(Contact contact) {
        // Not needed for this use case
    }


    /**
     * Called before processing contact physics.
     * This method is invoked just before the collision response is calculated. It allows for modifying
     * the contact to disable the collision response based on game logic. This implementation checks for
     * specific pairs of colliding bodies (e.g., MobileObject and Bomb, Player and Wall, Player and Enemy)
     * and handles them accordingly to implement game rules like bomb immunity, wall passing, and player-enemy interactions.
     *
     * @param contact     The contact fixture.
     * @param oldManifold The manifold at the last time step.
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Get the fixtures and bodies involved in the contact
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        // Handle collisions based on the types of bodies involved

        if (isMobileObject(bodyA) && isBomb(bodyB)) {
            handleBombCollision(contact, bodyA, bodyB); // Handle MobileObject colliding with a Bomb
        } else if (isMobileObject(bodyB) && isBomb(bodyA)) {
            handleBombCollision(contact, bodyB, bodyA); // Handle MobileObject colliding with a Bomb (order reversed)
        } else if (isPlayer(bodyA) && isWall(bodyB)) {
            handleWallPlayerCollision(contact, bodyA, bodyB); // Handle Player colliding with a Wall
        } else if (isPlayer(bodyB) && isWall(bodyA)) {
            handleWallPlayerCollision(contact, bodyB, bodyA); // Handle Player colliding with a Wall (order reversed)
        } else if (isPlayer(bodyA) && isEnemy(bodyB)) {
            handlePlayerEnemyCollision(bodyA); // Handle Player colliding with an Enemy
        } else if (isPlayer(bodyB) && isEnemy(bodyA)) {
            handlePlayerEnemyCollision(bodyB); // Handle Player colliding with an Enemy (order reversed)
        } else if (isEnemy(bodyA) && isEnemy(bodyB)) {
            contact.setEnabled(false); // Prevent Enemies from colliding with each other
        } else if (isPlayer(bodyB) && isPlayer(bodyA)) {
            contact.setEnabled(false); // Prevent Players from colliding with each other
        }
    }

    /**
     * Called after processing contact physics and applying impulses.
     * This method is part of the {@link ContactListener} interface but is not used in this implementation.
     * Subclasses can override this method to implement specific logic after the collision is resolved.
     *
     * @param contact The contact fixture.
     * @param impulse The impulse applied by the contact fixture.
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Not needed for this use case
    }

    /**
     * Checks if a Box2D body's user data is an instance of {@link MobileObject}.
     *
     * @param body The Box2D body to check.
     * @return {@code true} if the body's user data is a {@link MobileObject}, {@code false} otherwise.
     */
    private boolean isMobileObject(Body body) {
        return body.getUserData() instanceof MobileObject;
    }

    /**
     * Checks if a Box2D body's user data is an instance of {@link Player}.
     *
     * @param body The Box2D body to check.
     * @return {@code true} if the body's user data is a {@link Player}, {@code false} otherwise.
     */
    private boolean isPlayer(Body body) {
        return body.getUserData() instanceof Player; // Checks if the body belongs to a Player
    }

    /**
     * Checks if a Box2D body's user data is an instance of {@link Enemy}.
     *
     * @param body The Box2D body to check.
     * @return {@code true} if the body's user data is an {@link Enemy}, {@code false} otherwise.
     */
    private boolean isEnemy(Body body) {
        return body.getUserData() instanceof Enemy; // Checks if the body belongs to an Enemy
    }

    /**
     * Checks if a Box2D body's user data is an instance of {@link Bomb}.
     *
     * @param body The Box2D body to check.
     * @return {@code true} if the body's user data is a {@link Bomb}, {@code false} otherwise.
     */
    private boolean isBomb(Body body) {
        return body.getUserData() instanceof Bomb; // Checks if the body belongs to a Bomb
    }

    /**
     * Checks if a Box2D body's user data is an instance of either {@link IndestructibleWall} or {@link DestructibleWall}.
     *
     * @param body The Box2D body to check.
     * @return {@code true} if the body's user data is a wall type, {@code false} otherwise.
     */
    private boolean isWall(Body body) {
        return body.getUserData() instanceof IndestructibleWall || body.getUserData() instanceof DestructibleWall;
    }

    /**
     * Handles the collision between a {@link MobileObject} and a {@link Bomb}.
     * This method checks if the mobile object is currently ignoring the bomb or if the player has 'bombpass' power-up.
     * If either condition is true, the collision is disabled, allowing the mobile object to pass through the bomb.
     *
     * @param contact          The contact fixture.
     * @param mobileObjectBody The Box2D body of the {@link MobileObject}.
     * @param bomb             The Box2D body of the {@link Bomb}.
     */
    private void handleBombCollision(Contact contact, Body mobileObjectBody, Body bomb) {
        MobileObject mobileObject = (MobileObject) mobileObjectBody.getUserData();
        if (mobileObject.getIgnoredBombs().contains(bomb)) {
            // Disable collision if the mobile object is set to ignore this specific bomb
            contact.setEnabled(false);
        } else {
            if (mobileObject instanceof Player player && player.isBombpass()) {
                contact.setEnabled(false); // Disable collision if player has bombpass power-up
            }
        }
    }

    /**
     * Handles the collision between a {@link Player} and a Wall (either {@link IndestructibleWall} or {@link DestructibleWall}).
     * If the player has the 'wallpass' power-up, the collision is disabled, allowing the player to pass through walls.
     *
     * @param contact    The contact fixture.
     * @param playerBody The Box2D body of the {@link Player}.
     * @param wallBody   The Box2D body of the Wall.
     */
    private void handleWallPlayerCollision(Contact contact, Body playerBody, Body wallBody) {
        if (playerBody.getUserData() instanceof Player player && player.isWallpass()) {
            contact.setEnabled(false); // Disable collision if player has wallpass power-up
        }
    }

    /**
     * Handles the collision between a {@link Player} and an {@link Enemy}.
     * If the player is alive, this method marks the player for death, triggers game over logic,
     * and sets a game over message indicating the player's demise.
     *
     * @param playerBody The Box2D body of the {@link Player}.
     */
    private void handlePlayerEnemyCollision(Body playerBody) {
        Player player = (Player) playerBody.getUserData();
        if (player.isAlive()) {
            player.markForDeath(playerBody.getWorld(), this); // Mark player for death
            map.setGameOverMessage("Too close for comfort! " + player.getName() + " got squished by the Alien."); // Set game over message
        }
    }

    /**
     * Queues a Box2D body for destruction.
     * Bodies that need to be destroyed are added to the {@link #bodiesToDestroy} list.
     * The actual destruction is deferred to the {@link #processQueuedDestruction()} method to avoid
     * modifying the Box2D world during collision callbacks.
     *
     * @param body The Box2D body to be destroyed.
     */
    public void queueBodyForDestruction(Body body) {
        bodiesToDestroy.add(body); // Add body to the list of bodies to destroy
    }

    /**
     * Processes all bodies that have been queued for destruction.
     * This method iterates through the {@link #bodiesToDestroy} list, destroys each body in the Box2D world,
     * and then clears the list. This ensures that body destruction is handled safely outside of collision callbacks.
     */
    public void processQueuedDestruction() {
        for (Body body : bodiesToDestroy) {
            body.getWorld().destroyBody(body); // Destroy each queued body in the Box2D world
        }
        bodiesToDestroy.clear(); // Clear the list of bodies to destroy after processing
    }

}