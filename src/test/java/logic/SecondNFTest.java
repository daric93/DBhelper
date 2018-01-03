package logic;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static logic.Relations.secondNF;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecondNFTest {
    @Test
    public void noPartialDependenciesTest() {
        Set<FD> fds = newHashSet(
                new FD(newHashSet("Tournament", "Year"), newHashSet("Winner"))
        );
        Set<Table> expected = newHashSet(
                new Table("Tournament", "Year", "Winner")
        );

        assertEquals(expected, secondNF(fds));
    }

    @Test
    public void somePartialDependenciesTest() {
        Set<FD> fds = newHashSet(
                new FD(newHashSet("Manufacturer", "Model"), newHashSet("Model Full Name", "Manufacturer Country")),
                new FD(newHashSet("Manufacturer"), newHashSet("Manufacturer Country"))
        );
        Set<Table> expected = newHashSet(
                new Table("Manufacturer", "Model", "Model Full Name"),
                new Table("Manufacturer", "Manufacturer Country")
        );

        assertEquals(expected, secondNF(fds));
    }
}
