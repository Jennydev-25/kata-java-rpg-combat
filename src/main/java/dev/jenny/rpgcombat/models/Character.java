package dev.jenny.rpgcombat.models;

/** Character that takes part in the fight. */
public class Character {

    private static final int INITIAL_HEALTH = 1000;
    private static final int INITIAL_LEVEL = 1;

    private int health;
    private int level;

    public Character() {
        this.health = INITIAL_HEALTH;
        this.level = INITIAL_LEVEL;
    }

    public int getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void dealDamage(Character target, int damage) {
        target.health -= damage;
    }
}
