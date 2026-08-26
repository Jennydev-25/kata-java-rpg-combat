package dev.jenny.rpgcombat.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
