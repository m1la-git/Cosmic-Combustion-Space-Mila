package de.tum.cit.ase.bomberquest.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Enumeration of sound effects used in the game.
 * This enum manages various sound effects, ensuring they are loaded only once and easily accessible throughout the game.
 * It's designed to handle different sound effects like explosions, power-ups, and button clicks.
 * Sound files are expected to be located in the assets/audio folder.
 * Developers can extend this enum to include more sound effects as needed for the game.
 */
public enum SoundEffects {
    /**
     * Sound effect for bomb explosions.
     */
    BOMB_EXPLOSION("bomb-explodes.mp3", 1.2f),
    /**
     * Sound effect for picking up a power-up item.
     */
    POWER_UP("item-get.mp3", 1.5f),
    /**
     * Sound effect for placing a bomb.
     */
    PLACE_BOMB("place-bomb.mp3", 1.2f),
    /**
     * Sound effect for clearing a game stage or level.
     */
    STAGE_CLEAR("stage-clear.mp3", 0.8f),
    /**
     * Sound effect for when an enemy dies or is defeated.
     */
    ENEMY_DEATH("enemy-dies.mp3", 0.15f),
    /**
     * Sound effect for a button click or interaction.
     */
    BUTTON_CLICK("button-click.mp3", 1),
    /**
     * Sound effect for game over or losing condition.
     */
    GAME_OVER("game-over.mp3", 1.5f),
    /**
     * Sound effect for achieving victory or winning the game.
     */
    VICTORY("victory.mp3", 1.5f);

    /**
     * The {@link Sound} object associated with this sound effect variant.
     */
    private final Sound sound;
    /**
     * The volume level for this sound effect.
     */
    private final float volume;

    /**
     * Constructor for {@code SoundEffects} enum variants.
     * Loads the sound file from the assets and sets the volume for the sound effect.
     *
     * @param fileName The name of the sound file located in the "audio/" directory.
     * @param volume   The volume level of the sound effect, ranging from 0.0 (muted) to 1.0 (full volume).
     */
    SoundEffects(String fileName, float volume) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/" + fileName));
        this.volume = volume;
    }

    /**
     * Plays this sound effect with its defined volume.
     */
    public void play() {
        this.sound.play(volume);
    }
}