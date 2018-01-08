package logic;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static logic.FDTest.newAttributeSet;
import static logic.Relations.secondNF;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecondNFTest {
    @Test
    public void noPartialDependenciesTest() {
        Set<FD> fds = newHashSet(
                new FD(newAttributeSet("Tournament", "Year"), newAttributeSet("Winner"))
        );
        Set<Table> expected = newHashSet(
                new Table(newAttributeSet("Tournament", "Year", "Winner"))
        );

        assertEquals(expected, secondNF(fds));
    }

    @Test
    public void somePartialDependenciesTest() {
        Set<FD> fds = newHashSet(
                new FD(newAttributeSet("Manufacturer", "Model"), newAttributeSet("Model Full Name", "Manufacturer Country")),
                new FD(newAttributeSet("Manufacturer"), newAttributeSet("Manufacturer Country"))
        );
        Set<Table> expected = newHashSet(
                new Table(newAttributeSet("Manufacturer", "Model", "Model Full Name")),
                new Table(newAttributeSet("Manufacturer", "Manufacturer Country"))
        );

        assertEquals(expected, secondNF(fds));
    }
}
