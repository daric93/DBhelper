package logic;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Structure {

    public void isLossless() {

    }

    public void isDependencyPreserving() {

    }

    public void firstNF() {
    }

    public void secondNF() {

    }

    public void thirdNF() {

    }

    public void bcNF() {

    }

    public void fourthNF() {

    }

    public void fifthNF() {

    }

    public void sixthNF() {

    }


    static Set<FD> canonicalCover(Set<FD> funcDependencies) {
        Set<FD> fdSet = splitRHS(funcDependencies);
        fdSet = removeExtrLHS(fdSet);
        return removeExtrRHS(fdSet);
    }

    static Set<FD> removeExtrLHS(Set<FD> funcDependencies) {
        Set<FD> fds = new HashSet<>();
        funcDependencies.forEach(fd -> {
                List<String[]> powerSet = getPowerSet(fd.getLhs());
                for (String[] attr : powerSet) {
                    List<String> attributeClosure = attributeClosure(attr, funcDependencies);
                    if (attributeClosure.containsAll(fd.getRhs())) {
                        fds.add(new FD(Arrays.asList(attr), fd.getRhs()));
                        break;
                    }
                }
        });
        return fds;
    }

    static Set<FD> removeExtrRHS(Set<FD> funcDependencies) {
        return funcDependencies.stream()
                .filter(fd -> {
                    List<String> attrClsr = attributeClosure(fd.getLhs().toArray(new String[0]), removeFD(funcDependencies, fd));
                    return !attrClsr.containsAll(fd.getRhs());
                })
                .collect(Collectors.toSet());
    }

    static Set<FD> splitRHS(Set<FD> funcDependencies) {
        Set<FD> fds = new HashSet<>();
        for (FD fd : funcDependencies)
            for (String attr : fd.getRhs())
                fds.add(new FD(fd.getLhs(), Arrays.asList(attr)));
        return fds;
    }

    static List<String[]> getPowerSet(List<String> attr) {
        List<String[]> sets = new ArrayList<>();
        if (attr.isEmpty())
            return Collections.emptyList();
        String currAttr = attr.get(0);
        sets.add(new String[]{currAttr});
        List<String[]> powerSet = getPowerSet(attr.subList(1, attr.size()));
        for (String[] subs : powerSet) {
            sets.add(subs);
            String[] set = ArrayUtils.add(subs, currAttr);
                sets.add(set);
        }
        return sets;
    }

    static List<String> attributeClosure(String[] attr, Set<FD> funcDependencies) {
        List<String> attrs = new ArrayList<>();
        attrs.addAll(Arrays.asList(attr));
        boolean changed = true;
        while (changed) {
            changed = false;
            for (FD fd : funcDependencies)
                if (attrs.containsAll(fd.getLhs())) {
                    if (fd.getRhs() != null && !attrs.containsAll(fd.getRhs())) {
                        attrs.addAll(fd.getRhs());
                        changed = true;
                    }
                }
        }
        return attrs;
    }

    static Set<FD> removeFD(Set<FD> fds, FD fd) {
        return fds.stream().filter(f -> !f.equals(fd)).collect(Collectors.toSet());
    }
}
