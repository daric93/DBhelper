package logic;


import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static logic.Structure.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

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

}