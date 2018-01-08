package logic;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static logic.FDTest.newAttributeSet;
import static logic.Relations.thirdNF;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThirdNFTest {
    @Test
    public void noTransitiveDepTest() {
        Set<FD> fds = newHashSet(
                new FD(newAttributeSet("Tournament", "Year"), newAttributeSet("Winner"))
        );
        Set<Table> expected = newHashSet(
                new Table(newAttributeSet("Tournament", "Year", "Winner"))
        );

        assertEquals(expected, thirdNF(fds).getTables());
    }

    @Test
    public void oneTransitiveDepTest() {
        Set<FD> fds = newHashSet(
                new FD(newAttributeSet("Tournament", "Year"), newAttributeSet("Winner")),
                new FD(newAttributeSet("Winner"), newAttributeSet("Date of Birth"))
        );
        Set<Table> expected = newHashSet(
                new Table(newAttributeSet("Tournament", "Year", "Winner")),
                new Table(newAttributeSet("Winner", "Date of Birth"))
        );

        assertEquals(expected, thirdNF(fds).getTables());

    }

    @Test
    public void multipleTransitiveDepTest() {
        Set<FD> fds = newHashSet(
                new FD(newAttributeSet("Tournament", "Year"), newAttributeSet("Winner", "Country")),
                new FD(newAttributeSet("Winner"), newAttributeSet("Date of Birth", "Nationality")),
                new FD(newAttributeSet("Country"), newAttributeSet("Flag"))
        );
        Set<Table> expected = newHashSet(
                new Table(newAttributeSet("Tournament", "Year", "Winner", "Country")),
                new Table(newAttributeSet("Winner", "Date of Birth", "Nationality")),
                new Table(newAttributeSet("Country", "Flag"))
        );

        assertEquals(expected, thirdNF(fds).getTables());
    }

    @Test
    public void recursiveTransitiveDepTest() {
        Set<FD> fds = newHashSet(
                new FD(newAttributeSet("Tournament", "Year"), newAttributeSet("Winner")),
                new FD(newAttributeSet("Winner"), newAttributeSet("Date of Birth", "Country")),
                new FD(newAttributeSet("Country"), newAttributeSet("Flag"))
        );
        Set<Table> expected = newHashSet(
                new Table(newAttributeSet("Tournament", "Year", "Winner")),
                new Table(newAttributeSet("Winner", "Date of Birth", "Country")),
                new Table(newAttributeSet("Country", "Flag"))
        );

        assertEquals(expected, thirdNF(fds).getTables());
    }

    @Test
    public void losslessJoinDecompositionTest() {
        HashSet<FD> fds = newHashSet(
                new FD(newAttributeSet("artist"), newAttributeSet("members", "genre")),
                new FD(newAttributeSet("msin"), newAttributeSet("mfn", "mln", "inst"))
        );
        HashSet<Table> expectedDecomposition = newHashSet(
                new Table(newAttributeSet("artist", "members", "genre")),
                new Table(newAttributeSet("msin", "mfn", "mln", "inst")),
                new Table(newAttributeSet("artist", "msin"))
        );

        assertEquals(expectedDecomposition, thirdNF(fds).getTables());
    }
}
