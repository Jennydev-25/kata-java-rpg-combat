package dev.jenny.rpgcombat.models;

/** A non-character object that can take damage and be destroyed. */
public class Prop implements Damageable {

    private int health;

    public Prop(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }
}
