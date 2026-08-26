package dev.jenny.rpgcombat.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** Tests for Character. */
public class CharacterTest {

    private Character character;
    private Character target;

    @BeforeEach
    void setUp() {
        character = new Character();
        target = new Character();
    }

    @Test
    void testConstructor_ShouldInitializeWithDefaultValues() {
        assertThat(character.getHealth(), is(equalTo(1000)));
        assertThat(character.getLevel(), is(equalTo(1)));
        assertThat(character.isAlive(), is(true));
    }

    @Test
    void testDealDamage_WhenDamageIsLessThanHealth_ShouldReduceHealthByDamage() {
        character.dealDamage(target, 300);
        assertThat(target.getHealth(), is(equalTo(700)));
    }

    @ParameterizedTest(name = "damage {0} should reduce health to zero and kill the target")
    @ValueSource(ints = { 1000, 1500 })
    void testDealDamage_WhenDamageIsAtLeastHealth_ShouldReduceHealthToZeroAndKillTarget(int damage) {
        character.dealDamage(target, damage);
        assertThat(target.getHealth(), is(equalTo(0)));
        assertThat(target.isAlive(), is(false));
    }
}
