package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Contact Listener of the game
 * Handles the physics of collisions of the Bodies.
 */
public class GameContactListener implements ContactListener {

    private final List<Body> bodiesToDestroy = new ArrayList<>();
    private final GameMap map;

    public GameContactListener(GameMap map) {
        this.map = map;
    }

    @Override
    public void beginContact(Contact contact) {
        // Not needed for this use case
    }

    @Override
    public void endContact(Contact contact) {
        // Not needed for this use case
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Get the bodies involved in the contact
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        // Check if one body is the player, and the other is a bomb
        if (isMobileObject(bodyA) && isBomb(bodyB)) {
            handleBombCollision(contact, bodyA, bodyB);
        } else if (isMobileObject(bodyB) && isBomb(bodyA)) {
            handleBombCollision(contact, bodyB, bodyA);
        } else if (isPlayer(bodyA) && isEnemy(bodyB)) {
            handlePlayerEnemyCollision(bodyA);
        } else if (isPlayer(bodyB) && isEnemy(bodyA)) {
            handlePlayerEnemyCollision(bodyB);
        } else if (isEnemy(bodyA) && isEnemy(bodyB)) {
            contact.setEnabled(false);
        }else if (isPlayer(bodyB) && isPlayer(bodyA)) {
            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Not needed for this use case
    }

    private boolean isMobileObject(Body body) {
        return body.getUserData() instanceof MobileObject;
    }

    private boolean isPlayer(Body body) {
        return body.getUserData() instanceof Player; // Replace with your player identification logic
    }

    private boolean isEnemy(Body body) {
        return body.getUserData() instanceof Enemy; // Replace with your enemy identification logic
    }

    private boolean isBomb(Body body) {
        return body.getUserData() instanceof Bomb; // Replace with your bomb identification logic
    }

    //Actual handling of the collision:
    private void handleBombCollision(Contact contact, Body mobileObjectBody, Body bomb) {
        MobileObject mobileObject = (MobileObject) mobileObjectBody.getUserData();
        if (mobileObject.getIgnoredBombs().contains(bomb)) {
            // Disable collision while ignoring this bomb
            contact.setEnabled(false);
        }
    }

    private void handlePlayerEnemyCollision(Body playerBody) {
        Player player = (Player) playerBody.getUserData();
        if (player.isAlive()) {
            player.markForDeath(playerBody.getWorld(), this);
            map.setGameOverMessage("Too close for comfort! " + player.getName() + " got squished by the Alien.");
        }
    }

    public void queueBodyForDestruction(Body body) {
        bodiesToDestroy.add(body);
    }

    public void processQueuedDestruction() {
        for (Body body : bodiesToDestroy) {
            body.getWorld().destroyBody(body);
        }
        bodiesToDestroy.clear();
    }

}
