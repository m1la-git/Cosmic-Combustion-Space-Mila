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


    BOMB_EXPLOSION("bomb-explodes.mp3"),
    POWER_UP("item-get.mp3"),
    PLACE_BOMB("place-bomb.mp3"),
    STAGE_CLEAR("stage-clear.mp3"),
    ENEMY_DEATH("enemy-dies.mp3");


    private final Sound sound;

    SoundEffects(String fileName) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/" + fileName));
    }


    public void play() {
        this.sound.play(1.0f);
    }
}
