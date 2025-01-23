package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * Represents Power Ups.
 * Power Ups are Stationary Objects without hitbox.
 * Have their own type.
 */
public class PowerUp extends StationaryObject implements Drawable {
    private final WallContentType type;
    private float elapsedTime;

    public PowerUp(World world, int x, int y, WallContentType type) {
        super(world, x, y, false);
        this.type = type;
        elapsedTime = 0;
    }

    public void tick(float frameTime) {
        elapsedTime += frameTime;
    }

    public WallContentType getType() {
        return type;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return switch (type) {
            case BOMBS_POWER_UP -> Animations.BOMBS_POWER_UP.getKeyFrame(elapsedTime, true);
            case FLAMES_POWER_UP -> Animations.FLAMES_POWER_UP.getKeyFrame(elapsedTime, true);
            case SPEED_POWER_UP -> Animations.SPEED_POWER_UP.getKeyFrame(elapsedTime, true);
            case WALLPASS_POWER_UP -> Animations.WALLPASS_POWER_UP.getKeyFrame(elapsedTime, true);
            case BOMBPASS_POWER_UP -> Animations.BOMBPASS_POWER_UP.getKeyFrame(elapsedTime, true);
            case FLAMEPASS_POWER_UP -> Animations.FLAMEPASS_POWER_UP.getKeyFrame(elapsedTime, true);
            default -> null;
        };
    }
}
