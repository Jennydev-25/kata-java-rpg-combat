package dev.jenny.rpgcombat.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

/** Tests for Prop. */
public class PropTest {

    @Test
    void testConstructor_ShouldInitializeWithGivenHealth() {
        Prop tree = new Prop(2000);

        assertThat(tree.getHealth(), is(equalTo(2000)));
        assertThat(tree.isDestroyed(), is(false));
    }
}
