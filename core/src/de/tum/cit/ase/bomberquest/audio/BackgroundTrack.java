package de.tum.cit.ase.bomberquest.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public enum BackgroundTrack {
    BACKGROUND("Background.mp3", 0.15f);

    /** The music file owned by this variant. */
    private final Music music;
    private boolean isPlaying;

    BackgroundTrack(String fileName, float volume) {
        this.music = Gdx.audio.newMusic(Gdx.files.internal("audio/" + fileName));
        this.music.setLooping(true);
        this.music.setVolume(volume);
        this.isPlaying = false;
    }

    /**
     * Play this music track.
     * This will not stop other music from playing - if you add more tracks, you will have to handle that yourself.
     */
    public void play() {
        if (isPlaying) {
            stop();
        }

        this.music.play();
        isPlaying = true;
    }
    public void stop() {
        if (isPlaying) {
            this.music.stop();
            this.isPlaying = false;
        }
    }
    public void pause() {
        if (isPlaying) {
            this.music.pause();
            this.isPlaying = false;
        }
    }
}
