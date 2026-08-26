package dev.jenny.rpgcombat.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void testDealDamage_WhenDamageIsNegative_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> character.dealDamage(target, -100));
        assertThat(exception.getMessage(), is(equalTo("Damage cannot be negative")));
    }

    @Test
    void testHeal_WhenTargetIsAlive_ShouldIncreaseHealthByAmount() {
        character.dealDamage(target, 500);
        character.heal(target, 200);
        assertThat(target.getHealth(), is(equalTo(700)));
    }

    @Test
    void testHeal_WhenTargetIsDead_ShouldThrowIllegalStateException() {
        character.dealDamage(target, 1000);
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> character.heal(target, 100));
        assertThat(exception.getMessage(), is(equalTo("Cannot heal a dead character")));
    }

    @ParameterizedTest(name = "healing by {0} should not raise health above 1000")
    @ValueSource(ints = { 500, 700 })
    void testHeal_WhenNewHealthExceedsMax_ShouldCapHealthAtMax(int amount) {
        character.dealDamage(target, 500);
        character.heal(target, amount);
        assertThat(target.getHealth(), is(equalTo(1000)));
    }

    @Test
    void testHeal_WhenAmountIsNegative_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> character.heal(target, -50));
        assertThat(exception.getMessage(), is(equalTo("Amount cannot be negative")));
    }

    @Test
    void testDealDamage_WhenTargetIsSelf_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> character.dealDamage(character, 100));
        assertThat(exception.getMessage(), is(equalTo("Cannot deal damage to yourself")));
    }

    @Test
    void testHeal_WhenTargetIsNotSelf_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> character.heal(target, 100));
        assertThat(exception.getMessage(), is(equalTo("Can only heal yourself")));
    }
}
