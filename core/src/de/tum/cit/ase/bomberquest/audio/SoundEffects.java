package de.tum.cit.ase.bomberquest.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * This enum is used to manage the music tracks in the game.
 * Currently, only one track is used, but this could be extended to include multiple tracks.
 * Using an enum for this purpose is a good practice, as it allows for easy management of the music tracks
 * and prevents the same track from being loaded into memory multiple times.
 * See the assets/audio folder for the actual music files.
 * Feel free to add your own music tracks and use them in the game!
 */
public enum SoundEffects {
    BOMB_EXPLOSION("bomb-explodes.mp3", 1.2f),
    POWER_UP("item-get.mp3", 1.5f),
    PLACE_BOMB("place-bomb.mp3", 1.2f),
    STAGE_CLEAR("stage-clear.mp3", 0.8f),
    ENEMY_DEATH("enemy-dies.mp3", 0.15f),
    BUTTON_CLICK("button-click.mp3", 1),
    GAME_OVER("game-over.mp3", 1.5f),
    VICTORY("victory.mp3", 1.5f);

    /** The sound file owned by this variant. */
    private final Sound sound;
    private final float volume;

    SoundEffects(String fileName, float volume) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/" + fileName));
        this.volume = volume;
    }

    /**
     * Play this sound effect.
     */
    public void play() {
        this.sound.play(volume);
    }
}
