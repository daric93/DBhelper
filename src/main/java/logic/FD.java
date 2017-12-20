package logic;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

public class FD {
    private List<String> lhs;
    private List<String> rhs;

    public List<String> getLhs() {
        return lhs;
    }

    public void setLhs(List<String> lhs) {
        this.lhs = lhs;
    }

    public List<String> getRhs() {
        return rhs;
    }

    public void setRhs(List<String> rhs) {
        this.rhs = rhs;
    }

    FD(List<String> lhs, List<String> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public void removeExtraneousLHS(Set<FD> funcDependencies) {
        if (lhs.size() > 1) {
            List<String[]> powerSet = getPowerSet(lhs);
            for (String[] attr : powerSet) {
                List<String> attributeClosure = attributeClosure(attr, funcDependencies);
                if (attributeClosure.containsAll(rhs)) {
                    lhs = Arrays.asList(attr);
                }
            }
        }
    }

    public void removeExtraneousRHS(Set<FD> funcDependencies) {
        List<String> attrClsr = attributeClosure(lhs.toArray(new String[0]), removeFD(funcDependencies, this));
        if (attrClsr.containsAll(rhs))
            rhs = null;
    }

    public List<FD> splitRHS() {
        List<FD> list = new ArrayList<>();
        for (String attr : rhs)
            list.add(new FD(lhs, Collections.singletonList(attr)));
        return list;
    }

    private static List<String> attributeClosure(String[] attr, Set<FD> funcDependencies) {
        List<String> attrs = new ArrayList<>();
        attrs.addAll(Arrays.asList(attr));
        boolean changed = true;
        while (changed) {
            changed = false;
            for (FD fd : funcDependencies)
                if (attrs.containsAll(fd.lhs)) {
                    if (fd.rhs != null && !attrs.containsAll(fd.rhs)) {
                        attrs.addAll(fd.rhs);
                        changed = true;
                    }
                }
        }
        return attrs;
    }

    private Set<FD> removeFD(Set<FD> fds, FD fd) {
        return fds.stream().filter(f -> !f.equals(fd)).collect(Collectors.toSet());
    }

    private List<String[]> getPowerSet(List<String> attr) {
        List<String[]> sets = new ArrayList<>();
        if (attr.isEmpty())
            return Collections.EMPTY_LIST;
        String currAttr = attr.get(0);
        sets.add(new String[]{currAttr});
        List<String[]> powerSet = getPowerSet(attr.subList(1, attr.size()));
        for (String[] subs : powerSet) {
            sets.add(subs);
            String[] set = ArrayUtils.add(subs, currAttr);
            if (set.length != attr.size())
                sets.add(set);
        }
        return sets;
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
        return Objects.equals(getLhs(), fd.getLhs()) &&
                Objects.equals(getRhs(), fd.getRhs());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLhs(), getRhs());
    }
}
