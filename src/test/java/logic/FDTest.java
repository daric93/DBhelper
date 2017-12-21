package logic;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static logic.Structure.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class FDTest {

    @Test
    public void removeExtraneousLHSTest1NoExtr() {
        FD fd1 = new FD(Arrays.asList("A"), Arrays.asList("B"));
        FD fd2 = new FD(Arrays.asList("A"), Arrays.asList("C"));

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
        FD fd1 = new FD(Arrays.asList("A", "B"), Arrays.asList("C"));
        FD fd2 = new FD(Arrays.asList("A"), Arrays.asList("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        Set<FD> result = new HashSet<>();
        result.add(new FD(Arrays.asList("A"), Arrays.asList("C")));
        result.add(fd2);

        assertEquals(result, removeExtrLHS(fds));
    }

    @Test
    public void removeExtraneousLHSTest3Extr() {
        FD fd1 = new FD(Arrays.asList("A", "B"), Arrays.asList("D"));
        FD fd2 = new FD(Arrays.asList("B"), Arrays.asList("C"));
        FD fd3 = new FD(Arrays.asList("A"), Arrays.asList("D"));

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
        FD fd1 = new FD(Arrays.asList("C", "E"), Arrays.asList("F"));
        FD fd2 = new FD(Arrays.asList("C", "D"), Arrays.asList("B"));
        FD fd3 = new FD(Arrays.asList("B"), Arrays.asList("C"));

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
        FD fd1 = new FD(Arrays.asList("A"), Arrays.asList("D"));
        FD fd2 = new FD(Arrays.asList("B", "C"), Arrays.asList("A", "D"));
        FD fd3 = new FD(Arrays.asList("C"), Arrays.asList("B"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);

        Set<FD> result = new HashSet<>();
        result.add(fd1);
        result.add(fd3);
        result.add(new FD(Arrays.asList("C"), Arrays.asList("A", "D")));

        assertEquals(result, removeExtrLHS(fds));
    }

    @Test
    public void removeExtraneousRHSNoExtrTest1() {
        FD fd1 = new FD(Arrays.asList("C"), Arrays.asList("F"));
        FD fd2 = new FD(Arrays.asList("C"), Arrays.asList("D"));

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
        FD fd1 = new FD(Arrays.asList("A"), Arrays.asList("B"));
        FD fd2 = new FD(Arrays.asList("B"), Arrays.asList("C"));
        FD fd3 = new FD(Arrays.asList("A"), Arrays.asList("C"));

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
        FD fd = new FD(Arrays.asList("A"), Arrays.asList("B", "C"));
        Set<FD> result = new HashSet<>();
        result.add(new FD(Arrays.asList("A"), Arrays.asList("B")));
        result.add(new FD(Arrays.asList("A"), Arrays.asList("C")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd);

        assertIterableEquals(result, splitRHS(fds));
    }

    @Test
    public void splitRHSTest2() {
        FD fd = new FD(Arrays.asList("A"), Arrays.asList("B"));
        Set<FD> result = new HashSet<>();
        result.add(new FD(Arrays.asList("A"), Arrays.asList("B")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd);

        assertEquals(result, splitRHS(fds));
    }

    @Test
    public void splitRHSTest3() {
        FD fd1 = new FD(Arrays.asList("A"), Arrays.asList("B", "C"));
        FD fd2 = new FD(Arrays.asList("B"), Arrays.asList("B", "C"));

        Set<FD> result = new HashSet<>();
        result.add(new FD(Arrays.asList("A"), Arrays.asList("B")));
        result.add(new FD(Arrays.asList("A"), Arrays.asList("C")));
        result.add(new FD(Arrays.asList("B"), Arrays.asList("B")));
        result.add(new FD(Arrays.asList("B"), Arrays.asList("C")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        assertEquals(result, splitRHS(fds));
    }

}