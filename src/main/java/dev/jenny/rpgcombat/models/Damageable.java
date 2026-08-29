package dev.jenny.rpgcombat.models;

/** Something with health that can take damage and be destroyed. */
public interface Damageable {

    int getHealth();

    void takeDamage(int damage);

    boolean isDestroyed();
}
