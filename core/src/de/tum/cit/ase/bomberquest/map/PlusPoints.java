package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;

public class PlusPoints implements Drawable {
    private final float x;
    private final float y;
    private float elapsedTime = 0;
    private boolean finished = false;
    private final boolean player1;
    public PlusPoints(float x, float y, boolean player1) {
        this.x = x;
        this.y = y;
        this.player1 = player1;
    }
    public void tick(float frameTime) {
        elapsedTime += frameTime;
        if (elapsedTime >= 1f) {
            finished = true;
        }
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if (player1) return Animations.PLUS_POINTS_P1.getKeyFrame(elapsedTime);
        return Animations.PLUS_POINTS_P2.getKeyFrame(elapsedTime);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    public boolean isFinished() {
        return finished;
    }

}
