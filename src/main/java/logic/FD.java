package logic;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class FD {
    private final List<String> lhs;
    private final List<String> rhs;

    FD(List<String> lhs, List<String> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public List<String> getLhs() {
        return lhs;
    }

    public List<String> getRhs() {
        return rhs;
    }

    @Override
    public String toString() {
        return "FD{" +
                "lhs=" + lhs +
                ", rhs=" + rhs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FD)) return false;
        FD fd = (FD) o;
        boolean b = getLhs().size() == fd.getLhs().size() && getLhs().containsAll(fd.getLhs()) &&
                getRhs().size() == fd.getRhs().size() && getRhs().containsAll(fd.getRhs());
        return b;
    }

    @Override
    public int hashCode() {
        getLhs().sort(Comparator.naturalOrder());
        return Objects.hash(getLhs(), getRhs());
    }
}
