package de.tum.cit.ase.bomberquest.map;

public class Settings {
    private boolean aliensSmart;
    private boolean aliensBombs;
    private int timer;
    private int powerUpChance;
    public Settings() {
        aliensSmart = true;
        aliensBombs = false;
        timer = 350;
        powerUpChance = 20;
    }


    public boolean isAliensSmart() {
        return aliensSmart;
    }
    public boolean isAliensBombs() {
        return aliensBombs;
    }
    public int getTimer(){
        return timer;
    }

    public int getPowerUpChance() {
        return powerUpChance;
    }
    public void setAliensSmart(boolean aliensSmart) {
        this.aliensSmart = aliensSmart;
    }
    public void setAliensBombs(boolean aliensBombs) {
        this.aliensBombs = aliensBombs;
    }
    public void setTimer(int timer) {
        this.timer = timer;
    }
    public void setPowerUpChance(int powerUpChance) {
        this.powerUpChance = powerUpChance;
    }
}
