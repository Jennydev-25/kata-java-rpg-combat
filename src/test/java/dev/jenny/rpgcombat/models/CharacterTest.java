package dev.jenny.rpgcombat.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
        target.dealDamage(character, 500);
        character.heal(character, 200);
        assertThat(character.getHealth(), is(equalTo(700)));
    }

    @Test
    void testHeal_WhenTargetIsDead_ShouldThrowIllegalStateException() {
        target.dealDamage(character, 1000);
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> character.heal(character, 100));
        assertThat(exception.getMessage(), is(equalTo("Cannot heal a dead character")));
    }

    @ParameterizedTest(name = "healing by {0} should not raise health above 1000")
    @ValueSource(ints = { 500, 700 })
    void testHeal_WhenNewHealthExceedsMax_ShouldCapHealthAtMax(int amount) {
        target.dealDamage(character, 500);
        character.heal(character, amount);
        assertThat(character.getHealth(), is(equalTo(1000)));
    }

    @Test
    void testHeal_WhenAmountIsNegative_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> character.heal(character, -50));
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

    @Test
    void testDealDamage_WhenTargetIsAtLeast5Higher_ShouldReduceDamageByHalf() {
        Character higherLevelTarget = new Character(6);
        character.dealDamage(higherLevelTarget, 100);
        assertThat(higherLevelTarget.getHealth(), is(equalTo(950)));
    }

    @Test
    void testDealDamage_WhenAttackerIsAtLeast5Higher_ShouldIncreaseDamageByHalf() {
        Character higherLevelAttacker = new Character(6);
        higherLevelAttacker.dealDamage(target, 100);
        assertThat(target.getHealth(), is(equalTo(850)));
    }

    @ParameterizedTest
    @CsvSource({
        "MELEE, 3",
        "RANGED, 25"
    })
    void testDealDamage_WhenDistanceExceedsRange_ShouldThrowIllegalArgumentException(
            AttackRange attackRange, int distance) {
        Character attacker = new Character(1, attackRange);
        Character target = new Character();

        assertThrows(IllegalArgumentException.class, () -> attacker.dealDamage(target, 10, distance));
    }

    @ParameterizedTest
    @CsvSource({
        "MELEE, 2, 100, 900",
        "RANGED, 15, 100, 900"
    })
    void testDealDamage_WhenDistanceIsWithinRange_ShouldReduceTargetHealth(
            AttackRange attackRange, int distance, int damage, int expectedHealth) {
        Character attacker = new Character(1, attackRange);
        Character target = new Character();

        attacker.dealDamage(target, damage, distance);

        assertThat(target.getHealth(), is(equalTo(expectedHealth)));
    }
}
