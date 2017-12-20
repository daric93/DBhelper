package logic;


import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class FDTest {

    @Test
    public void removeExtraneousLHSTest1NoExtr() {
        FD fd1 = new FD(Arrays.asList("A"), Arrays.asList("B"));
        FD fd2 = new FD(Arrays.asList("A"), Arrays.asList("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);
        FD result = new FD(Arrays.asList("A"), Arrays.asList("B"));
        ;
        fd1.removeExtraneousLHS(fds);

        assertEquals(result, fd1);
    }

    @Test
    public void removeExtraneousLHSTest2Extr() {
        FD fd1 = new FD(Arrays.asList("A", "B"), Arrays.asList("C"));
        FD fd2 = new FD(Arrays.asList("A"), Arrays.asList("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        FD result = new FD(Arrays.asList("A"), Arrays.asList("C"));
        fd1.removeExtraneousLHS(fds);

        assertEquals(result, fd1);
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

        FD result = new FD(Arrays.asList("A"), Arrays.asList("D"));
        fd1.removeExtraneousLHS(fds);

        assertEquals(result, fd1);
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

        FD result = new FD(Arrays.asList("C", "E"), Arrays.asList("F"));
        fd1.removeExtraneousLHS(fds);

        assertEquals(result, fd1);
    }

    @Test
    public void removeExtraneousLHSExtrTest5(){
        FD fd1 = new FD(Arrays.asList("A"), Arrays.asList("D"));
        FD fd2 = new FD(Arrays.asList("B","C"), Arrays.asList("A","D"));
        FD fd3 = new FD(Arrays.asList("C"), Arrays.asList("B"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);

        FD result = new FD(Arrays.asList("C"), Arrays.asList("A","D"));
        fd2.removeExtraneousLHS(fds);

        assertEquals(result, fd2);
    }

    @Test
    public void removeExtraneousRHSNoExtrTest1() {
        FD fd1 = new FD(Arrays.asList("C"), Arrays.asList("F"));
        FD fd2 = new FD(Arrays.asList("C"), Arrays.asList("D"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        FD result = new FD(Arrays.asList("C"), Arrays.asList("F"));
        fd1.removeExtraneousRHS(fds);

        assertEquals(result, fd1);
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

        FD result = new FD(Arrays.asList("A"), null);
        fd3.removeExtraneousRHS(fds);

        assertEquals(result, fd3);
    }

    @Test
    public void splitRHSTest1() {
        FD fd = new FD(Arrays.asList("A"), Arrays.asList("B", "C"));
        Set<FD> result = new HashSet<>();
        result.add(new FD(Arrays.asList("A"), Arrays.asList("B")));
        result.add(new FD(Arrays.asList("A"), Arrays.asList("C")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd);

        assertIterableEquals(fd.splitRHS(), result);
    }

    @Test
    public void splitRHSTest2() {
        FD fd = new FD(Arrays.asList("A"), Arrays.asList("B"));
        Set<FD> result = new HashSet<>();
        result.add(new FD(Arrays.asList("A"), Arrays.asList("B")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd);

        assertIterableEquals(fd.splitRHS(), result);
    }

}