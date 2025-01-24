package de.tum.cit.ase.bomberquest.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Enumeration of background music tracks in the game.
 * Each variant represents a different background music track that can be played, paused, or stopped.
 */
public enum BackgroundTrack {
    /**
     * Represents the main background music track.
     * The music file is "Background.mp3" and it is played at a volume of 0.2.
     */
    BACKGROUND("Background.mp3", 0.2f);

    /**
     * The music file owned by this variant.
     */
    private final Music music;
    /**
     * True or False whether the music is playing right now.
     */
    private boolean isPlaying;

    /**
     * Constructor for {@code BackgroundTrack}.
     * Loads the music file from the assets, sets it to looping, and sets the volume.
     *
     * @param fileName The name of the music file in the "audio/" directory.
     * @param volume   The volume level of the music, ranging from 0.0 (muted) to 1.0 (full volume).
     */
    BackgroundTrack(String fileName, float volume) {
        this.music = Gdx.audio.newMusic(Gdx.files.internal("audio/" + fileName));
        this.music.setLooping(true);
        this.music.setVolume(volume);
        this.isPlaying = false;
    }

    /**
     * Plays this music track.
     * If the track is already playing, it will be stopped and restarted.
     * If the track was paused, it will resume from the paused position.
     */
    public void play() {
        if (isPlaying) {
            stop(); // Stop current playback to restart from beginning
        }

        this.music.play();
        isPlaying = true;
    }

    /**
     * Stops playing the music track completely.
     * Resets the playback position to the beginning of the track.
     */
    public void stop() {
        this.music.stop();
        this.isPlaying = false;
    }

    /**
     * Pauses the music track if it is currently playing.
     * If the track is not playing, this method has no effect.
     */
    public void pause() {
        if (isPlaying) {
            this.music.pause();
            this.isPlaying = false;
        }
    }
}