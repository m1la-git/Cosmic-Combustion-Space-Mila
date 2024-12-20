package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

public class Bomb implements Drawable {

    private final int x;
    private final int y;
    private final Body hitbox;

    public Bomb(World world, int x, int y) {;
        this.x = x;
        this.y = y;
        hitbox = createHitbox(world);
    }

    private Body createHitbox(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(this.x, this.y);
        Body body = world.createBody(bodyDef);

        // Polygon shape for the bomb since it takes the whole cell -> a square with a side length of 1 tile.
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        body.createFixture(box, 1.0f);
        box.dispose();

        // Set the bomb as the user data of the body, so we can look up the bomb from the body later.
        body.setUserData(this);
        return body;
    }

    public Body getHitbox() {
        return hitbox;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.BOMB;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
