package dev.jenny.rpgcombat.models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** Character that takes part in the fight. */
public class Character implements Damageable {

    private static final int MAX_HEALTH = 1000;
    private static final int INITIAL_LEVEL = 1;
    private static final int LEVEL_DIFFERENCE_THRESHOLD = 5;
    private static final AttackRange DEFAULT_ATTACK_RANGE = AttackRange.MELEE;

    private int health;
    private int level;
    private final AttackRange attackRange;
    private final Set<Faction> factions = new HashSet<>();

    public Character() {
        this(INITIAL_LEVEL);
    }

    public Character(int level) {
        this(level, DEFAULT_ATTACK_RANGE);
    }

    public Character(int level, AttackRange attackRange) {
        this.health = MAX_HEALTH;
        this.level = level;
        this.attackRange = attackRange;
    }

    public int getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public Set<Faction> getFactions() {
        return Collections.unmodifiableSet(factions);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void dealDamage(Damageable target, int damage) {
        dealDamage(target, damage, 0);
    }

    public void dealDamage(Damageable target, int damage, int distance) {
        validateAttack(target, damage, distance);
        int modifiedDamage = calculateModifiedDamage(target, damage);
        target.takeDamage(modifiedDamage);
    }

    public void heal(Character target, int amount) {
        validateHeal(target, amount);
        target.health = Math.min(MAX_HEALTH, target.health + amount);
    }

    public void joinFaction(Faction faction) {
        factions.add(faction);
    }

    public void leaveFaction(Faction faction) {
        factions.remove(faction);
    }

    public boolean isAllyOf(Character other) {
        return !Collections.disjoint(this.factions, other.factions);
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    public boolean isDestroyed() {
        return !isAlive();
    }

    private int calculateModifiedDamage(Damageable target, int damage) {
        if (target instanceof Character characterTarget) {
            if (characterTarget.level - this.level >= LEVEL_DIFFERENCE_THRESHOLD) {
                return damage / 2;
            }
            if (this.level - characterTarget.level >= LEVEL_DIFFERENCE_THRESHOLD) {
                return damage + damage / 2;
            }
        }
        return damage;
    }

    private void validateAttack(Damageable target, int damage, int distance) {
        if (target == this) {
            throw new IllegalArgumentException("Cannot deal damage to yourself");
        }
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        if (distance > this.attackRange.getMeters()) {
            throw new IllegalArgumentException("Target is out of range");
        }
        if (target instanceof Character characterTarget && this.isAllyOf(characterTarget)) {
            throw new IllegalStateException("Cannot deal damage to an ally");
        }
    }

    private void validateHeal(Character target, int amount) {
        if (target != this && !this.isAllyOf(target)) {
            throw new IllegalArgumentException("Can only heal yourself or an ally");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (!target.isAlive()) {
            throw new IllegalStateException("Cannot heal a dead character");
        }
    }
}
