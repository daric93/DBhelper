package logic;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static logic.Relations.thirdNF;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThirdNFTest {
    @Test
    public void noTransitiveDepTest() {
        Set<FD> fds = newHashSet(
                new FD(newHashSet("Tournament", "Year"), newHashSet("Winner"))
        );
        Set<Table> expected = newHashSet(
                new Table("Tournament", "Year", "Winner")
        );

        assertEquals(expected, thirdNF(fds).getTables());
    }

    @Test
    public void oneTransitiveDepTest() {
        Set<FD> fds = newHashSet(
                new FD(newHashSet("Tournament", "Year"), newHashSet("Winner")),
                new FD(newHashSet("Winner"), newHashSet("Date of Birth"))
        );
        Set<Table> expected = newHashSet(
                new Table("Tournament", "Year", "Winner"),
                new Table("Winner", "Date of Birth")
        );

        assertEquals(expected, thirdNF(fds).getTables());

    }

    @Test
    public void multipleTransitiveDepTest() {
        Set<FD> fds = newHashSet(
                new FD(newHashSet("Tournament", "Year"), newHashSet("Winner", "Country")),
                new FD(newHashSet("Winner"), newHashSet("Date of Birth", "Nationality")),
                new FD(newHashSet("Country"), newHashSet("Flag"))
        );
        Set<Table> expected = newHashSet(
                new Table("Tournament", "Year", "Winner", "Country"),
                new Table("Winner", "Date of Birth", "Nationality"),
                new Table("Country", "Flag")
        );

        assertEquals(expected, thirdNF(fds).getTables());
    }

    @Test
    public void recursiveTransitiveDepTest() {
        Set<FD> fds = newHashSet(
                new FD(newHashSet("Tournament", "Year"), newHashSet("Winner")),
                new FD(newHashSet("Winner"), newHashSet("Date of Birth", "Country")),
                new FD(newHashSet("Country"), newHashSet("Flag"))
        );
        Set<Table> expected = newHashSet(
                new Table("Tournament", "Year", "Winner"),
                new Table("Winner", "Date of Birth", "Country"),
                new Table("Country", "Flag")
        );

        assertEquals(expected, thirdNF(fds).getTables());
    }

    @Test
    public void losslessJoinDecompositionTest() {
        HashSet<FD> fds = newHashSet(
                new FD(newHashSet("artist"), newHashSet("members", "genre")),
                new FD(newHashSet("msin"), newHashSet("mfn", "mln", "inst"))
        );
        HashSet<Table> expectedDecomposition = newHashSet(
                new Table("artist", "members", "genre"),
                new Table("msin", "mfn", "mln", "inst"),
                new Table("artist", "msin")
        );

        assertEquals(expectedDecomposition, thirdNF(fds).getTables());
    }
}
