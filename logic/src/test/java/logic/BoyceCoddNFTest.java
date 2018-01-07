package logic;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static logic.Relations.bcNF;
import static logic.Relations.isDependencyPreserving;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BoyceCoddNFTest {
    @Test
    public void notInBCNFTest() {
        Set<FD> fds = newHashSet(
                new FD(newHashSet("A"), newHashSet("B")),
                new FD(newHashSet("B"), newHashSet("C"))
        );
        Set<Table> expectedBCNFDecomp = newHashSet(
                new Table("B", "C"),
                new Table("A", "B")
        );

        assertEquals(expectedBCNFDecomp, bcNF(fds).getTables());
    }

    @Test
    public void notDependencyPreservingBCNFTest() {
        Set<FD> fds = newHashSet(
                new FD(newHashSet("A", "B"), newHashSet("C")),
                new FD(newHashSet("C"), newHashSet("B"))
        );
        Set<Table> expectedBCNFDecomp = newHashSet(
                new Table("A", "C"),
                new Table("C", "B")
        );

        Decomposition decomposition = new Decomposition(expectedBCNFDecomp, fds);

        assertFalse(isDependencyPreserving(decomposition));
    }
}
