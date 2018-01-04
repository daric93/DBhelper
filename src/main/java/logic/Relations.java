package logic;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.powerSet;

public class Relations {

    public static boolean isLossless(Decomposition decomposition) {
        return false;
    }

    public static boolean isDependencyPreserving(Decomposition decomposition) {
        return false;
    }

    static Set<Table> secondNF(Set<FD> funcDep) {
        return null;
    }


    public static Set<Table> thirdNF(Set<FD> funcDep) {
        Set<Table> tables = new HashSet<>();
        Set<Set<String>> candidateKeys = findCandidateKeys(funcDep, getAllAttr(funcDep));
        Set<FD> canonicalCover = canonicalCover(funcDep);

        Set<FD> fdSet = combineRHS(canonicalCover);
        boolean containsCandidateKey = false;
        for (FD fd : fdSet) {
            Table table = new Table(Sets.union(fd.getRhs(), fd.getLhs()));
            tables.add(table);
            if (!containsCandidateKey)
                containsCandidateKey = candidateKeys.stream().anyMatch(table.getAttributes()::containsAll);
        }
        if (!containsCandidateKey)
            tables.add(new Table(candidateKeys.iterator().next()));
        return tables;
    }

    public static Set<Table> bcNF(Set<FD> funcDep) {
        return null;
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

    static Set<String> attributeClosure(Set<String> attrs, Set<FD> fds) {
        Set<String> attrClosure = new HashSet<>(attrs);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (FD fd : fds)
                if (attrClosure.containsAll(fd.getLhs())) {
                    changed = attrClosure.addAll(fd.getRhs());
                }
        }
        return attrClosure;
    }

    static Set<FD> removeExtrLHS(Set<FD> funcDependencies) {
        Set<FD> fds = new HashSet<>();
        funcDependencies.forEach(fd -> {
            Set<Set<String>> powerSet = powerSet(fd.getLhs());
            for (Set<String> sub : powerSet) {
                Set<String> attributeClosure = attributeClosure(sub, funcDependencies);
                if (attributeClosure.containsAll(fd.getRhs())) {
                    fds.add(new FD(sub, fd.getRhs()));
                    break;
                }
            }
        });
        return fds;
    }

    static Set<FD> removeExtrRHS(Set<FD> funcDependencies) {
        return funcDependencies.stream()
                .filter(fd -> {
                    Set<String> closure = attributeClosure(fd.getLhs(), removeFD(funcDependencies, fd));
                    return !closure.containsAll(fd.getRhs());
                })
                .collect(Collectors.toSet());
    }

    static Set<FD> splitRHS(Set<FD> funcDependencies) {
        Set<FD> fds = new HashSet<>();
        for (FD fd : funcDependencies)
            for (String attr : fd.getRhs())
                fds.add(new FD(fd.getLhs(), Sets.newHashSet(attr)));
        return fds;
    }

    static Set<FD> removeFD(Set<FD> fds, FD fd) {
        return Sets.filter(fds, f -> !Objects.equals(f, fd));
    }


    static Set<Set<String>> findCandidateKeys(Set<FD> fds, Set<String> attributes) {
        Set<Set<String>> candKeys = new HashSet<>();

        if (fds.isEmpty()) {
            candKeys.add(attributes);
            return candKeys;
        }

        Set<String> left = new HashSet<>();
        Set<String> right = new HashSet<>();
        for (FD fd : fds) {
            left.addAll(fd.getLhs());
            right.addAll(fd.getRhs());
        }
        Set<String> middle = Sets.intersection(left, right);
        left = Sets.difference(left, middle);

        Set<Set<String>> powerSet = powerSet(middle);
        boolean keyFound = false;
        int keySize = 0;
        for (Set<String> set : powerSet) {
            Set<String> union = Sets.union(left, set);
            if (!keyFound || keySize == union.size()) {
                Set<String> closure = attributeClosure(union, fds);
                if (closure.containsAll(attributes)) {
                    candKeys.add(union);
                    keyFound = true;
                    keySize = union.size();
                }
            }//TODO: sort powerSet
        }

        return candKeys;
    }

    static Set<String> getAllAttr(Set<FD> fds) {
        Set<String> attrs = new HashSet<>();
        for (FD fd : fds) {
            attrs.addAll(fd.getLhs());
            attrs.addAll(fd.getRhs());
        }
        return attrs;
    }

    static Set<FD> combineRHS(Set<FD> fds) {
        Set<FD> funcDep = new HashSet<>();
        Map<Set<String>, List<FD>> collect = fds.stream().collect(Collectors.groupingBy(FD::getLhs));
        collect.forEach((k, v) -> {
            Set<String> set = new HashSet<>();
            v.forEach(fd -> set.addAll(fd.getRhs()));
            funcDep.add(new FD(k, set));
        });
        return funcDep;
    }
}
