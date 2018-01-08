package logic;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static logic.FDTest.newAttributeSet;
import static logic.Relations.bcNF;
import static logic.Relations.isDependencyPreserving;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BoyceCoddNFTest {
    @Test
    public void notInBCNFTest() {
        Set<FD> fds = newHashSet(
                new FD(newAttributeSet("A"), newAttributeSet("B")),
                new FD(newAttributeSet("B"), newAttributeSet("C"))
        );
        Set<Table> expectedBCNFDecomp = newHashSet(
                new Table(newAttributeSet("B", "C")),
                new Table(newAttributeSet("A", "B"))
        );

        assertEquals(expectedBCNFDecomp, bcNF(fds).getTables());
    }

    @Test
    public void notDependencyPreservingBCNFTest() {
        Set<FD> fds = newHashSet(
                new FD(newAttributeSet("A", "B"), newAttributeSet("C")),
                new FD(newAttributeSet("C"), newAttributeSet("B"))
        );
        Set<Table> expectedBCNFDecomp = newHashSet(
                new Table(newAttributeSet("A", "C")),
                new Table(newAttributeSet("C", "B"))
        );

        Decomposition decomposition = new Decomposition(expectedBCNFDecomp, fds);

        assertFalse(isDependencyPreserving(decomposition));
    }
}
