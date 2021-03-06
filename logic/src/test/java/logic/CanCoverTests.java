package logic;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static logic.FDTest.newAttributeSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CanCoverTests {


    private Set<FD> getFds(String[][][] arr) {
        Set<FD> fds = new HashSet<>();
        for (String[][] fd : arr)
            fds.add(new FD(newAttributeSet(fd[0]), newAttributeSet(fd[1])));
        return fds;
    }

    @Test
    public void canonicalCoverTest1() {
        String[][][] arr = {
                {{"A"}, {"B", "C"}},
                {{"B"}, {"C"}},
                {{"A"}, {"B"}},
                {{"A", "B"}, {"C"}}
        };
        String[][][] result = {
                {{"A"}, {"B"}},
                {{"B"}, {"C"}}
        };

        Set<FD> inFDs = getFds(arr);
        Set<FD> outFDs = getFds(result);

        assertEquals(outFDs, Relations.canonicalCover(inFDs));

    }

    @Test
    public void canonicalCoverTest2() {
        String[][][] arr = {
                {{"A", "B"}, {"C"}},
                {{"B"}, {"E"}},
                {{"C", "F"}, {"D"}},
                {{"C"}, {"A"}},
                {{"B"}, {"F"}},
                {{"C", "E"}, {"F"}},
                {{"C", "D"}, {"B"}},
                {{"B"}, {"C"}}
        };
        String[][][] result = {
                {{"B"}, {"C"}},
                {{"B"}, {"E"}},
                {{"C", "F"}, {"D"}},
                {{"C"}, {"A"}},
                {{"C", "E"}, {"F"}},
                {{"C", "D"}, {"B"}}
        };

        Set<FD> inFDs = getFds(arr);
        Set<FD> outFDs = getFds(result);

        assertEquals(outFDs, Relations.canonicalCover(inFDs));

    }

    @Test
    public void canonicalCoverTest3() {
        String[][][] arr = {
                {{"A"}, {"C"}},
                {{"A", "B"}, {"C"}}
        };
        String[][][] result = {
                {{"A"}, {"C"}}
        };

        Set<FD> inFDs = getFds(arr);
        Set<FD> outFDs = getFds(result);

        assertEquals(outFDs, Relations.canonicalCover(inFDs));

    }

    @Test
    public void canonicalCoverTest4() {
        String[][][] arr = {
                {{"A"}, {"C"}},
                {{"A", "B"}, {"C", "D"}}
        };
        String[][][] result = {
                {{"A", "B"}, {"D"}},
                {{"A"}, {"C"}}
        };

        Set<FD> inFDs = getFds(arr);
        Set<FD> outFDs = getFds(result);

        assertEquals(outFDs, Relations.canonicalCover(inFDs));

    }

    @Test
    public void canonicalCoverTest5() {
        String[][][] arr = {
                {{"A"}, {"D"}},
                {{"B", "C"}, {"A", "D"}},
                {{"C"}, {"B"}},
                {{"E"}, {"A"}},
                {{"E"}, {"D"}}
        };
        String[][][] result = {
                {{"A"}, {"D"}},
                {{"C"}, {"B"}},
                {{"C"}, {"A"}},
                {{"E"}, {"A"}}
        };

        Set<FD> inFDs = getFds(arr);
        Set<FD> outFDs = getFds(result);

        assertEquals(outFDs, Relations.canonicalCover(inFDs));

    }
}
