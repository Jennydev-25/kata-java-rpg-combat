package dev.jenny.rpgcombat.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

/** Tests for Faction. */
public class FactionTest {

    @Test
    void testFaction_WhenTwoFactionsHaveTheSameName_ShouldBeEqual() {
        Faction faction1 = new Faction("Rebels");
        Faction faction2 = new Faction("Rebels");

        assertThat(faction1, is(equalTo(faction2)));
    }
}
