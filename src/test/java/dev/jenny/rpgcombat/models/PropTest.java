package dev.jenny.rpgcombat.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** Tests for Prop. */
public class PropTest {

    @Test
    void testConstructor_ShouldInitializeWithGivenHealth() {
        Prop tree = new Prop(2000);

        assertThat(tree.getHealth(), is(equalTo(2000)));
        assertThat(tree.isDestroyed(), is(false));
    }

    @Test
    void testTakeDamage_WhenDamageIsLessThanHealth_ShouldReduceHealthByDamage() {
        Prop tree = new Prop(2000);

        tree.takeDamage(300);

        assertThat(tree.getHealth(), is(equalTo(1700)));
    }

    @ParameterizedTest(name = "damage {0} should reduce health to zero and destroy the prop")
    @ValueSource(ints = { 2000, 2500 })
    void testTakeDamage_WhenDamageIsAtLeastHealth_ShouldReduceHealthToZeroAndDestroyProp(int damage) {
        Prop tree = new Prop(2000);

        tree.takeDamage(damage);

        assertThat(tree.getHealth(), is(equalTo(0)));
        assertThat(tree.isDestroyed(), is(true));
    }
}
