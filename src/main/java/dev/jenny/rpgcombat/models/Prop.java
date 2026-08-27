package dev.jenny.rpgcombat.models;

/** A non-character object that can take damage and be destroyed. */
public class Prop {

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
}
