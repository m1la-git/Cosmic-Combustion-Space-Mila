package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

public class Enemy extends MobileObject implements Drawable {
    public Enemy(World world, float x, float y) {
        super(world, x, y, 1);
    }

    @Override
    public void tick(float frameTime) {
        increaseElapsedTime(frameTime);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return switch (getDirection()) {
            case UP, RIGHT -> Animations.ENEMY_WALK_UP_OR_RIGHT.getKeyFrame(getElapsedTime(), true);
            case DOWN, LEFT -> Animations.ENEMY_WALK_DOWN_OR_LEFT.getKeyFrame(getElapsedTime(), true);
            case NONE -> Textures.ENEMY;
        };
    }
}
