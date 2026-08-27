package dev.jenny.rpgcombat.models;

/** Maximum attack range of a character, in meters. */
public enum AttackRange {
    MELEE(2);

    private final int meters;

    AttackRange(int meters) {
        this.meters = meters;
    }

    public int getMeters() {
        return meters;
    }
}
