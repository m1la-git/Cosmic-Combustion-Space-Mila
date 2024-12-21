package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

public class PowerUp extends StationaryObject implements Drawable {
    private final WallContentType type;

    public PowerUp(World world, int x, int y, WallContentType type) {
        super(world, x, y, false);
        this.type = type;
    }

    public WallContentType getType() {
        return type;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return switch (type) {
            case BOMBS_POWER_UP -> Textures.BOMBS_POWER_UP;
            case FLAMES_POWER_UP -> Textures.FLAMES_POWER_UP;
            case SPEED_POWER_UP -> Textures.SPEED_POWER_UP;
            case WALLPASS_POWER_UP -> Textures.WALLPASS_POWER_UP;
            case DETONATOR_POWER_UP -> Textures.DETONATOR_POWER_UP;
            case BOMBPASS_POWER_UP -> Textures.BOMBPASS_POWER_UP;
            case FLAMEPASS_POWER_UP -> Textures.FLAMEPASS_POWER_UP;
            case MYSTERY_POWER_UP -> Textures.MYSTERY_POWER_UP;
            default -> null;
        };
    }
}
