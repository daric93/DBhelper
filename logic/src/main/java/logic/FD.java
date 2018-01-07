package logic;

import java.util.Objects;
import java.util.Set;

public class FD {
    private Set<String> lhs;
    private Set<String> rhs;

    public FD() {

    }

    public FD(Set<String> lhs, Set<String> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Set<String> getLhs() {
        return lhs;
    }

    public Set<String> getRhs() {
        return rhs;
    }

    public void setLhs(Set<String> lhs) {
        this.lhs = lhs;
    }

    public void setRhs(Set<String> rhs) {
        this.rhs = rhs;
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
        return getLhs().size() == fd.getLhs().size() && getLhs().containsAll(fd.getLhs()) &&
                getRhs().size() == fd.getRhs().size() && getRhs().containsAll(fd.getRhs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLhs(), getRhs());
    }
}
