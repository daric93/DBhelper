package logic;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.*;

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
            Set<Set<String>> powerSet = Sets.powerSet(fd.getLhs());
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

        Set<String> left = new HashSet<>();
        Set<String> right = new HashSet<>();
        for (FD fd : fds) {
            left.addAll(fd.getLhs());
            right.addAll(fd.getRhs());
        }
        Set<String> middle = Sets.intersection(left, right);
        left = Sets.difference(left, middle);

//        Set<String> attrClsr = attributeClosure(left, fds);
//        if (attrClsr.containsAll(attributes)) {
//            candKeys.add(left);
//            return candKeys;
//        }
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
}
