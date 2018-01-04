package logic;


import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static logic.Relations.*;
import static org.junit.jupiter.api.Assertions.*;

public class FDTest {

    @Test
    public void removeExtraneousLHSTest1NoExtr() {
        FD fd1 = new FD(newHashSet("A"), newHashSet("B"));
        FD fd2 = new FD(newHashSet("A"), newHashSet("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        Set<FD> result = new HashSet<>();
        result.add(fd1);
        result.add(fd2);

        assertEquals(result, removeExtrLHS(fds));
    }

    @Test
    public void removeExtraneousLHSTest2Extr() {
        FD fd1 = new FD(newHashSet("A", "B"), newHashSet("C"));
        FD fd2 = new FD(newHashSet("A"), newHashSet("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        Set<FD> result = new HashSet<>();
        result.add(new FD(newHashSet("A"), newHashSet("C")));
        result.add(fd2);

        assertEquals(result, removeExtrLHS(fds));
    }

    @Test
    public void removeExtraneousLHSTest3Extr() {
        FD fd1 = new FD(newHashSet("A", "B"), newHashSet("D"));
        FD fd2 = new FD(newHashSet("B"), newHashSet("C"));
        FD fd3 = new FD(newHashSet("A"), newHashSet("D"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);

        Set<FD> result = new HashSet<>();
        result.add(fd3);
        result.add(fd2);
        result.add(fd3);

        assertEquals(result, removeExtrLHS(fds));
    }

    @Test
    public void removeExtraneousLHSTest4NoExtr() {
        FD fd1 = new FD(newHashSet("C", "E"), newHashSet("F"));
        FD fd2 = new FD(newHashSet("C", "D"), newHashSet("B"));
        FD fd3 = new FD(newHashSet("B"), newHashSet("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);

        Set<FD> result = new HashSet<>();
        result.add(fd1);
        result.add(fd2);
        result.add(fd3);

        assertEquals(result, removeExtrLHS(fds));
    }

    @Test
    public void removeExtraneousLHSExtrTest5() {
        FD fd1 = new FD(newHashSet("A"), newHashSet("D"));
        FD fd2 = new FD(newHashSet("B", "C"), newHashSet("A", "D"));
        FD fd3 = new FD(newHashSet("C"), newHashSet("B"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);

        Set<FD> result = new HashSet<>();
        result.add(fd1);
        result.add(fd3);
        result.add(new FD(newHashSet("C"), newHashSet("A", "D")));

        assertEquals(result, removeExtrLHS(fds));
    }

    @Test
    public void removeExtraneousRHSNoExtrTest1() {
        FD fd1 = new FD(newHashSet("C"), newHashSet("F"));
        FD fd2 = new FD(newHashSet("C"), newHashSet("D"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        Set<FD> result = new HashSet<>();
        result.add(fd1);
        result.add(fd2);

        assertEquals(result, removeExtrRHS(fds));
    }

    @Test
    public void removeExtraneousRHSExtrTest2() {
        FD fd1 = new FD(newHashSet("A"), newHashSet("B"));
        FD fd2 = new FD(newHashSet("B"), newHashSet("C"));
        FD fd3 = new FD(newHashSet("A"), newHashSet("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);

        Set<FD> result = new HashSet<>();
        result.add(fd1);
        result.add(fd2);

        assertEquals(result, removeExtrRHS(fds));
    }

    @Test
    public void splitRHSTest1() {
        FD fd = new FD(newHashSet("A"), newHashSet("B", "C"));
        Set<FD> result = new HashSet<>();
        result.add(new FD(newHashSet("A"), newHashSet("B")));
        result.add(new FD(newHashSet("A"), newHashSet("C")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd);

        assertIterableEquals(result, splitRHS(fds));
    }

    @Test
    public void splitRHSTest2() {
        FD fd = new FD(newHashSet("A"), newHashSet("B"));
        Set<FD> result = new HashSet<>();
        result.add(new FD(newHashSet("A"), newHashSet("B")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd);

        assertEquals(result, splitRHS(fds));
    }

    @Test
    public void splitRHSTest3() {
        FD fd1 = new FD(newHashSet("A"), newHashSet("B", "C"));
        FD fd2 = new FD(newHashSet("B"), newHashSet("B", "C"));

        Set<FD> result = new HashSet<>();
        result.add(new FD(newHashSet("A"), newHashSet("B")));
        result.add(new FD(newHashSet("A"), newHashSet("C")));
        result.add(new FD(newHashSet("B"), newHashSet("B")));
        result.add(new FD(newHashSet("B"), newHashSet("C")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        assertEquals(result, splitRHS(fds));
    }

    @Test
    public void findCandidateKeysTest1() {
        Set<FD> fds = new HashSet<>();
        fds.add(new FD(newHashSet("A"), newHashSet("B")));
        fds.add(new FD(newHashSet("B"), newHashSet("C")));

        Set<Set<String>> result = new HashSet<>();
        result.add(newHashSet("A"));

        assertEquals(result, findCandidateKeys(fds, newHashSet("A", "B", "C")));

    }

    @Test
    public void findCandidateKeysTest2() {
        Set<FD> fds = new HashSet<>();
        fds.add(new FD(newHashSet("A", "B"), newHashSet("C")));
        fds.add(new FD(newHashSet("C"), newHashSet("B")));
        fds.add(new FD(newHashSet("C"), newHashSet("D")));


        Set<Set<String>> result = new HashSet<>();
        result.add(newHashSet("A", "B"));
        result.add(newHashSet("A", "C"));

        assertEquals(result, findCandidateKeys(fds, newHashSet("A", "B", "C", "D")));

    }

    @Test
    public void findCandidateKeysTest3() {
        Set<FD> fds = new HashSet<>();
        fds.add(new FD(newHashSet("A"), newHashSet("B")));
        fds.add(new FD(newHashSet("B"), newHashSet("C")));
        fds.add(new FD(newHashSet("C"), newHashSet("A")));


        Set<Set<String>> result = new HashSet<>();
        result.add(newHashSet("A"));
        result.add(newHashSet("B"));
        result.add(newHashSet("C"));

        assertEquals(result, findCandidateKeys(fds, newHashSet("A", "B", "C")));

    }

    @Test
    public void findCandidateKeysNoFDTest3() {
        Set<FD> fds = new HashSet<>();

        Set<Set<String>> result = new HashSet<>();
        result.add(newHashSet("A", "B", "C"));

        assertEquals(result, findCandidateKeys(fds, newHashSet("A", "B", "C")));

    }

    @Test
    public void attributeClosureTest() {
        Set<FD> fds = new HashSet<>();
        FD fd1 = new FD(newHashSet("A", "B"), newHashSet("C"));
        FD fd2 = new FD(newHashSet("C"), newHashSet("B"));
        FD fd3 = new FD(newHashSet("C"), newHashSet("D"));
        FD fd4 = new FD(newHashSet("N"), newHashSet("N"));
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);
        fds.add(fd4);

        assertEquals(newHashSet("A", "B", "C", "D"), attributeClosure(fd1.getLhs(), fds));
        assertEquals(newHashSet("C", "B", "D"), attributeClosure(fd2.getLhs(), fds));
        assertEquals(newHashSet("C", "B", "D"), attributeClosure(fd3.getLhs(), fds));
        assertEquals(newHashSet("N"), attributeClosure(fd4.getLhs(), fds));

    }

    @Test
    public void removeFDTest() {
        Set<FD> fds = new HashSet<>();
        FD fd1 = new FD(newHashSet("A", "B"), newHashSet("C"));
        FD fd2 = new FD(newHashSet("C"), newHashSet("B"));
        FD fd3 = new FD(newHashSet("C"), newHashSet("D"));
        FD fd4 = new FD(newHashSet("N"), newHashSet("N"));
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);
        fds.add(fd4);

        Set<FD> result1 = newHashSet(fd1, fd2, fd3);
        Set<FD> result2 = newHashSet(fd2, fd3, fd4);
        Set<FD> result3 = newHashSet(fd1, fd2, fd3, fd4);


        assertAll("removeFD",
                () -> assertEquals(result1, removeFD(fds, fd4)),
                () -> assertEquals(result2, removeFD(fds, fd1)),
                () -> assertEquals(result3, removeFD(fds, new FD(newHashSet("C"), newHashSet("A"))))
        );
    }

    @Test
    public void combineRHSTest1() {

    }

    @Test
    public void combineRHSTest2() {

    }

}
