package logic;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;
import static logic.Relations.*;
import static org.junit.jupiter.api.Assertions.*;

public class FDTest {

    static Set<Attribute> newAttributeSet(String... attr) {
        return Arrays.stream(attr)
                .map(Attribute::new)
                .collect(Collectors.toSet());
    }

    @Test
    public void removeExtraneousLHSTest1NoExtr() {
        FD fd1 = new FD(newAttributeSet("A"), newAttributeSet("B"));
        FD fd2 = new FD(newAttributeSet("A"), newAttributeSet("C"));

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
        FD fd1 = new FD(newAttributeSet("A", "B"), newAttributeSet("C"));
        FD fd2 = new FD(newAttributeSet("A"), newAttributeSet("C"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        Set<FD> result = new HashSet<>();
        result.add(new FD(newAttributeSet("A"), newAttributeSet("C")));
        result.add(fd2);

        assertEquals(result, removeExtrLHS(fds));
    }

    @Test
    public void removeExtraneousLHSTest3Extr() {
        FD fd1 = new FD(newAttributeSet("A", "B"), newAttributeSet("D"));
        FD fd2 = new FD(newAttributeSet("B"), newAttributeSet("C"));
        FD fd3 = new FD(newAttributeSet("A"), newAttributeSet("D"));

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
        FD fd1 = new FD(newAttributeSet("C", "E"), newAttributeSet("F"));
        FD fd2 = new FD(newAttributeSet("C", "D"), newAttributeSet("B"));
        FD fd3 = new FD(newAttributeSet("B"), newAttributeSet("C"));

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
        FD fd1 = new FD(newAttributeSet("A"), newAttributeSet("D"));
        FD fd2 = new FD(newAttributeSet("B", "C"), newAttributeSet("A", "D"));
        FD fd3 = new FD(newAttributeSet("C"), newAttributeSet("B"));

        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);

        Set<FD> result = new HashSet<>();
        result.add(fd1);
        result.add(fd3);
        result.add(new FD(newAttributeSet("C"), newAttributeSet("A", "D")));

        assertEquals(result, removeExtrLHS(fds));
    }

    @Test
    public void removeExtraneousRHSNoExtrTest1() {
        FD fd1 = new FD(newAttributeSet("C"), newAttributeSet("F"));
        FD fd2 = new FD(newAttributeSet("C"), newAttributeSet("D"));

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
        FD fd1 = new FD(newAttributeSet("A"), newAttributeSet("B"));
        FD fd2 = new FD(newAttributeSet("B"), newAttributeSet("C"));
        FD fd3 = new FD(newAttributeSet("A"), newAttributeSet("C"));

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
        FD fd = new FD(newAttributeSet("A"), newAttributeSet("B", "C"));
        Set<FD> result = new HashSet<>();
        result.add(new FD(newAttributeSet("A"), newAttributeSet("B")));
        result.add(new FD(newAttributeSet("A"), newAttributeSet("C")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd);

        assertIterableEquals(result, splitRHS(fds));
    }

    @Test
    public void splitRHSTest2() {
        FD fd = new FD(newAttributeSet("A"), newAttributeSet("B"));
        Set<FD> result = new HashSet<>();
        result.add(new FD(newAttributeSet("A"), newAttributeSet("B")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd);

        assertEquals(result, splitRHS(fds));
    }

    @Test
    public void splitRHSTest3() {
        FD fd1 = new FD(newAttributeSet("A"), newAttributeSet("B", "C"));
        FD fd2 = new FD(newAttributeSet("B"), newAttributeSet("B", "C"));

        Set<FD> result = new HashSet<>();
        result.add(new FD(newAttributeSet("A"), newAttributeSet("B")));
        result.add(new FD(newAttributeSet("A"), newAttributeSet("C")));
        result.add(new FD(newAttributeSet("B"), newAttributeSet("B")));
        result.add(new FD(newAttributeSet("B"), newAttributeSet("C")));


        Set<FD> fds = new HashSet<>();
        fds.add(fd1);
        fds.add(fd2);

        assertEquals(result, splitRHS(fds));
    }

    @Test
    public void findCandidateKeysTest1() {
        Set<FD> fds = new HashSet<>();
        fds.add(new FD(newAttributeSet("A"), newAttributeSet("B")));
        fds.add(new FD(newAttributeSet("B"), newAttributeSet("C")));

        Set<Set<Attribute>> result = new HashSet<>();
        result.add(newAttributeSet("A"));

        assertEquals(result, findCandidateKeys(fds, newAttributeSet("A", "B", "C")));

    }

    @Test
    public void findCandidateKeysTest2() {
        Set<FD> fds = new HashSet<>();
        fds.add(new FD(newAttributeSet("A", "B"), newAttributeSet("C")));
        fds.add(new FD(newAttributeSet("C"), newAttributeSet("B")));
        fds.add(new FD(newAttributeSet("C"), newAttributeSet("D")));


        Set<Set<Attribute>> result = new HashSet<>();
        result.add(newAttributeSet("A", "B"));
        result.add(newAttributeSet("A", "C"));

        assertEquals(result, findCandidateKeys(fds, newAttributeSet("A", "B", "C", "D")));

    }

    @Test
    public void findCandidateKeysTest3() {
        Set<FD> fds = new HashSet<>();
        fds.add(new FD(newAttributeSet("A"), newAttributeSet("B")));
        fds.add(new FD(newAttributeSet("B"), newAttributeSet("C")));
        fds.add(new FD(newAttributeSet("C"), newAttributeSet("A")));


        Set<Set<Attribute>> result = new HashSet<>();
        result.add(newAttributeSet("A"));
        result.add(newAttributeSet("B"));
        result.add(newAttributeSet("C"));

        assertEquals(result, findCandidateKeys(fds, newAttributeSet("A", "B", "C")));

    }

    @Test
    public void findCandidateKeysNoFDTest4() {
        Set<FD> fds = new HashSet<>();

        Set<Set<Attribute>> result = new HashSet<>();
        result.add(newAttributeSet("A", "B", "C"));

        assertEquals(result, findCandidateKeys(fds, newAttributeSet("A", "B", "C")));

    }

    @Test
    public void findCandidateKeysTest5() {
        Set<FD> fds = newHashSet(
                new FD(newAttributeSet("Tournament", "Year"), newAttributeSet("Winner", "A")),
                new FD(newAttributeSet("Winner", "A"), newAttributeSet("Date of Birth"))
        );

        Set<Set<Attribute>> result = new HashSet<>();
        result.add(newAttributeSet("Tournament", "Year"));

        Set<Attribute> attributes = newAttributeSet("Tournament", "Year", "Winner", "A", "Date of Birth");
        assertEquals(result, findCandidateKeys(fds, attributes));
    }

    @Test
    public void attributeClosureTest() {
        Set<FD> fds = new HashSet<>();
        FD fd1 = new FD(newAttributeSet("A", "B"), newAttributeSet("C"));
        FD fd2 = new FD(newAttributeSet("C"), newAttributeSet("B"));
        FD fd3 = new FD(newAttributeSet("C"), newAttributeSet("D"));
        FD fd4 = new FD(newAttributeSet("N"), newAttributeSet("N"));
        fds.add(fd1);
        fds.add(fd2);
        fds.add(fd3);
        fds.add(fd4);

        assertEquals(newAttributeSet("A", "B", "C", "D"), attributeClosure(fd1.getLhs(), fds));
        assertEquals(newAttributeSet("C", "B", "D"), attributeClosure(fd2.getLhs(), fds));
        assertEquals(newAttributeSet("C", "B", "D"), attributeClosure(fd3.getLhs(), fds));
        assertEquals(newAttributeSet("N"), attributeClosure(fd4.getLhs(), fds));

    }

    @Test
    public void removeFDTest() {
        Set<FD> fds = new HashSet<>();
        FD fd1 = new FD(newAttributeSet("A", "B"), newAttributeSet("C"));
        FD fd2 = new FD(newAttributeSet("C"), newAttributeSet("B"));
        FD fd3 = new FD(newAttributeSet("C"), newAttributeSet("D"));
        FD fd4 = new FD(newAttributeSet("N"), newAttributeSet("N"));
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
                () -> assertEquals(result3, removeFD(fds, new FD(newAttributeSet("C"), newAttributeSet("A"))))
        );
    }

    @Test
    public void combineRHSTest1() {

    }

    @Test
    public void combineRHSTest2() {

    }

}
