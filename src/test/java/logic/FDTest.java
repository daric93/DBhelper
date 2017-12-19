package logic;


import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FDTest {

    @Test
    public void isExtraneousLHSTest1NoExtr() {
        FD fd1 = new FD(Arrays.asList("A"), Arrays.asList("B"));
        FD fd2 = new FD(Arrays.asList("A"), Arrays.asList("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        assertFalse(FD.isExtraneousLHS(fd1, fds));
    }

    @Test
    public void isExtraneousLHSTest2Extr() {
        FD fd1 = new FD(Arrays.asList("A", "B"), Arrays.asList("C"));
        FD fd2 = new FD(Arrays.asList("A"), Arrays.asList("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        assertTrue(FD.isExtraneousLHS(fd1, fds));
    }

    @Test
    public void isExtraneousLHSTest3Extr() {
        FD fd1 = new FD(Arrays.asList("A", "B"), Arrays.asList("D"));
        FD fd2 = new FD(Arrays.asList("B"), Arrays.asList("C"));
        FD fd3 = new FD(Arrays.asList("A"), Arrays.asList("D"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);

        assertTrue(FD.isExtraneousLHS(fd1, fds));
    }

    @Test
    public void isExtraneousLHSTest4NoExtr() {
        FD fd1 = new FD(Arrays.asList("C", "E"), Arrays.asList("F"));
        FD fd2 = new FD(Arrays.asList("C", "D"), Arrays.asList("B"));
        FD fd3 = new FD(Arrays.asList("B"), Arrays.asList("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);

        assertFalse(FD.isExtraneousLHS(fd1, fds));
    }

    @Test
    public void isExtraneousRHSNoExtrTest1() {
        FD fd1 = new FD(Arrays.asList("C"), Arrays.asList("F"));
        FD fd2 = new FD(Arrays.asList("C"), Arrays.asList("D"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        assertFalse(FD.isExtraneousRHS(fd1, fds));
    }

    @Test
    public void isExtraneousRHSExtrTest2() {
        FD fd1 = new FD(Arrays.asList("A"), Arrays.asList("B"));
        FD fd2 = new FD(Arrays.asList("A"), Arrays.asList("B"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        assertTrue(FD.isExtraneousRHS(fd1, fds));
    }

    @Test
    public void isExtraneousRHSExtrTest3() {
        FD fd1 = new FD(Arrays.asList("A"), Arrays.asList("B"));
        FD fd2 = new FD(Arrays.asList("B"), Arrays.asList("C"));
        FD fd3 = new FD(Arrays.asList("A"), Arrays.asList("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);

        assertTrue(FD.isExtraneousRHS(fd3, fds));
    }

    @Test
    public void splitRHSTest1() {
        FD fd = new FD(Arrays.asList("A"), Arrays.asList("B", "C"));
        Set<FD> result = new HashSet<>();
        result.add(new FD(Arrays.asList("A"), Arrays.asList("B")));
        result.add(new FD(Arrays.asList("A"), Arrays.asList("C")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd);

        assertTrue(FD.splitRHS(fd).equals(result));
    }

    @Test
    public void splitRHSTest2() {
        FD fd = new FD(Arrays.asList("A"), Arrays.asList("B"));
        Set<FD> result = new HashSet<>();
        result.add(new FD(Arrays.asList("A"), Arrays.asList("B")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd);

        assertTrue(FD.splitRHS(fd).equals(result));
    }


}