package logic;

import java.util.Objects;
import java.util.Set;

public class FD {
    private Set<Attribute> lhs;
    private Set<Attribute> rhs;

    public FD() {

    }

    public FD(Set<Attribute> lhs, Set<Attribute> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Set<Attribute> getLhs() {
        return lhs;
    }

    public Set<Attribute> getRhs() {
        return rhs;
    }

    public void setLhs(Set<Attribute> lhs) {
        this.lhs = lhs;
    }

    public void setRhs(Set<Attribute> rhs) {
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
