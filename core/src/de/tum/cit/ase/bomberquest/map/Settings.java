package de.tum.cit.ase.bomberquest.map;

/**
 * Configuration class for game settings.
 * This class holds various settings that can be adjusted to modify gameplay,
 * such as enemy AI behavior, enemy bomb placement capability, game timer duration,
 * and the probability of power-ups appearing.
 * It provides getters and setters for each setting, allowing for dynamic adjustment of game parameters.
 */
public class Settings {
    /**
     * Determines if enemies (aliens) use smart pathfinding AI to locate players.
     * If true, enemies will actively seek out players; if false, they may move more randomly.
     */
    private boolean aliensSmart;
    /**
     * Determines if enemies (aliens) are capable of placing bombs.
     * If true, enemies can strategically place bombs to hinder players; if false, they will not place bombs.
     */
    private boolean aliensBombs;
    /**
     * The duration of the game timer in seconds.
     * This timer counts down during gameplay, and the game may end if the timer reaches zero.
     */
    private int timer;
    /**
     * The percentage chance (out of 100) that a destructible wall will reveal a power-up when destroyed.
     * A higher percentage increases the frequency of power-up appearances in the game.
     */
    private int powerUpChance;

    /**
     * Default constructor for {@code Settings}.
     * Initializes settings with default values:
     * - {@code aliensSmart} is set to {@code true} (enemies use smart AI).
     * - {@code aliensBombs} is set to {@code false} (enemies do not place bombs).
     * - {@code timer} is set to 350 seconds.
     * - {@code powerUpChance} is set to 20% (chance for power-ups to appear).
     */
    public Settings() {
        aliensSmart = true;
        aliensBombs = false;
        timer = 350;
        powerUpChance = 20;
    }

    /**
     * Checks if the 'aliensSmart' setting is enabled.
     *
     * @return {@code true} if enemies use smart AI, {@code false} otherwise.
     */
    public boolean isAliensSmart() {
        return aliensSmart;
    }

    /**
     * Sets the 'aliensSmart' setting.
     *
     * @param aliensSmart {@code true} to enable smart AI for enemies, {@code false} to disable it.
     */
    public void setAliensSmart(boolean aliensSmart) {
        this.aliensSmart = aliensSmart;
    }

    /**
     * Checks if the 'aliensBombs' setting is enabled.
     *
     * @return {@code true} if enemies can place bombs, {@code false} otherwise.
     */
    public boolean isAliensBombs() {
        return aliensBombs;
    }

    /**
     * Sets the 'aliensBombs' setting.
     *
     * @param aliensBombs {@code true} to enable bomb placement for enemies, {@code false} to disable it.
     */
    public void setAliensBombs(boolean aliensBombs) {
        this.aliensBombs = aliensBombs;
    }

    /**
     * Gets the current game timer duration.
     *
     * @return The game timer duration in seconds.
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Sets the game timer duration.
     *
     * @param timer The new timer duration in seconds.
     */
    public void setTimer(int timer) {
        this.timer = timer;
    }

    /**
     * Gets the current power-up appearance chance.
     *
     * @return The power-up chance as a percentage (0-100).
     */
    public int getPowerUpChance() {
        return powerUpChance;
    }

    /**
     * Sets the power-up appearance chance.
     *
     * @param powerUpChance The new power-up chance as a percentage (0-100).
     */
    public void setPowerUpChance(int powerUpChance) {
        this.powerUpChance = powerUpChance;
    }
}