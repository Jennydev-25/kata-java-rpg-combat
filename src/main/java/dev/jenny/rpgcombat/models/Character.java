package dev.jenny.rpgcombat.models;

/** Character that takes part in the fight. */
public class Character {

    private static final int MAX_HEALTH = 1000;
    private static final int INITIAL_LEVEL = 1;

    private int health;
    private int level;

    public Character() {
        this.health = MAX_HEALTH;
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
        if (target == this) {
            throw new IllegalArgumentException("Cannot deal damage to yourself");
        }
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        target.health = Math.max(0, target.health - damage);
    }

    public void heal(Character target, int amount) {
        if (target != this) {
            throw new IllegalArgumentException("Can only heal yourself");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (!target.isAlive()) {
            throw new IllegalStateException("Cannot heal a dead character");
        }
        target.health = Math.min(MAX_HEALTH, target.health + amount);
    }
}
