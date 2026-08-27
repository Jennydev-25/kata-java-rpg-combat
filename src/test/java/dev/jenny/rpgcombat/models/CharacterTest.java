package dev.jenny.rpgcombat.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
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
    void testHeal_WhenTargetIsNeitherSelfNorAlly_ShouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> character.heal(target, 100));
        assertThat(exception.getMessage(), is(equalTo("Can only heal yourself or an ally")));
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

    @Test
    void testGetFactions_WhenCharacterIsNew_ShouldBeEmpty() {
        assertThat(character.getFactions(), is(equalTo(Set.of())));
    }

    @ParameterizedTest
    @MethodSource("factionsToJoin")
    void testJoinFaction_WhenJoiningOneOrMoreFactions_ShouldAddThemToCharacter(
            List<Faction> factionsToJoin, Set<Faction> expectedFactions) {
        factionsToJoin.forEach(character::joinFaction);

        assertThat(character.getFactions(), is(equalTo(expectedFactions)));
    }

    private static Stream<Arguments> factionsToJoin() {
        Faction rebels = new Faction("Rebels");
        Faction empire = new Faction("Empire");
        return Stream.of(
                Arguments.of(List.of(rebels), Set.of(rebels)),
                Arguments.of(List.of(rebels, empire), Set.of(rebels, empire)));
    }

    @ParameterizedTest
    @MethodSource("factionsToLeave")
    void testLeaveFaction_WhenLeavingOneOrMoreFactions_ShouldRemoveThemFromCharacter(
            List<Faction> factionsToJoin, List<Faction> factionsToLeave, Set<Faction> expectedFactions) {
        factionsToJoin.forEach(character::joinFaction);
        factionsToLeave.forEach(character::leaveFaction);

        assertThat(character.getFactions(), is(equalTo(expectedFactions)));
    }

    private static Stream<Arguments> factionsToLeave() {
        Faction rebels = new Faction("Rebels");
        Faction empire = new Faction("Empire");
        return Stream.of(
                Arguments.of(List.of(rebels), List.of(rebels), Set.of()),
                Arguments.of(List.of(rebels, empire), List.of(rebels), Set.of(empire)),
                Arguments.of(List.of(rebels, empire), List.of(rebels, empire), Set.of()));
    }

    @ParameterizedTest
    @MethodSource("factionsForAllyCheck")
    void testIsAllyOf_WhenCharactersShareOrDoNotShareFactions_ShouldReturnExpectedResult(
            List<Faction> characterFactions, List<Faction> targetFactions, boolean expectedIsAlly) {
        characterFactions.forEach(character::joinFaction);
        targetFactions.forEach(target::joinFaction);

        assertThat(character.isAllyOf(target), is(equalTo(expectedIsAlly)));
    }

    private static Stream<Arguments> factionsForAllyCheck() {
        Faction rebels = new Faction("Rebels");
        Faction empire = new Faction("Empire");
        return Stream.of(
                Arguments.of(List.of(), List.of(), false),
                Arguments.of(List.of(rebels), List.of(), false),
                Arguments.of(List.of(rebels), List.of(rebels), true),
                Arguments.of(List.of(rebels), List.of(empire), false),
                Arguments.of(List.of(rebels, empire), List.of(empire), true));
    }

    @Test
    void testDealDamage_WhenTargetIsAlly_ShouldThrowIllegalStateException() {
        Faction rebels = new Faction("Rebels");
        character.joinFaction(rebels);
        target.joinFaction(rebels);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> character.dealDamage(target, 100));
        assertThat(exception.getMessage(), is(equalTo("Cannot deal damage to an ally")));
    }

    @Test
    void testHeal_WhenTargetIsAlly_ShouldIncreaseHealthByAmount() {
        Faction rebels = new Faction("Rebels");
        Character attacker = new Character();
        attacker.dealDamage(target, 500);
        character.joinFaction(rebels);
        target.joinFaction(rebels);

        character.heal(target, 200);

        assertThat(target.getHealth(), is(equalTo(700)));
    }
}
