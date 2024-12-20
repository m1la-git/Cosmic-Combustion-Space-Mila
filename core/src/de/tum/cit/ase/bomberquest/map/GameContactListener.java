package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.physics.box2d.*;

import java.util.HashSet;
import java.util.Set;

public class GameContactListener implements ContactListener {
    private final Set<Body> ignoredBombs = new HashSet<>();

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
        if (isPlayer(bodyA) && isBomb(bodyB)) {
            handleBombCollision(contact, bodyB);
        } else if (isPlayer(bodyB) && isBomb(bodyA)) {
            handleBombCollision(contact, bodyA);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Not needed for this use case
    }

    private boolean isPlayer(Body body) {
        return body.getUserData() instanceof Player; // Replace with your player identification logic
    }

    private boolean isBomb(Body body) {
        return body.getUserData() instanceof Bomb; // Replace with your bomb identification logic
    }

    //Actual handling of the collision:
    private void handleBombCollision(Contact contact, Body bomb) {
        if (ignoredBombs.contains(bomb)) {
            // Disable collision while ignoring this bomb
            contact.setEnabled(false);
        }
    }

    // Methods to manage ignored bombs
    public void addIgnoredBomb(Body bomb) {
        ignoredBombs.add(bomb);
    }

    public void removeIgnoredBomb(Body bomb) {
        ignoredBombs.remove(bomb);
    }
}
