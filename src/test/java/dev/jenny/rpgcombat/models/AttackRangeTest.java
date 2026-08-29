package dev.jenny.rpgcombat.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

/** Tests for AttackRange. */
public class AttackRangeTest {

    @Test
    void testMelee_ShouldHaveTwoMetersRange() {
        assertThat(AttackRange.MELEE.getMeters(), is(equalTo(2)));
    }

    @Test
    void testRanged_ShouldHaveTwentyMetersRange() {
        assertThat(AttackRange.RANGED.getMeters(), is(equalTo(20)));
    }
}
