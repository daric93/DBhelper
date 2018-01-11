package logic;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;

public class Relations {

    public static boolean isLossless(Decomposition decomposition) {
        return false;
    }

    public static boolean isDependencyPreserving(Decomposition decomposition) {
        return false;
    }

    static Set<Table> secondNF(Set<FD> funcDep) {
        Set<Table> secondNF = new HashSet<>();
        Set<Attribute> attributes = getAllAttr(funcDep);
        Set<FD> canonicalCover = canonicalCover(funcDep);
        Set<Set<Attribute>> candidateKeys = findCandidateKeys(canonicalCover, attributes);

        Map<Set<Attribute>, Table> foreignKeys = new HashMap<>();

        //TODO: check that only proper subsets and sorted in increasing order
        Set<Set<Attribute>> properSubsets = candidateKeys.stream()
                .map(ckey -> {
                    Set<Set<Attribute>> powerSet = powerSet(ckey);
                    powerSet.remove(ckey);
                    return powerSet;
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        for (Set<Attribute> subset : properSubsets) {
            Set<Attribute> closure = attributeClosure(subset, funcDep);
            if (closure.size() > subset.size()) {
                attributes.removeAll(closure);
                attributes.addAll(subset);
                Table newTable = new Table(closure);
                secondNF.add(newTable);
                //TODO : set primary key
                newTable.setPrimaryKey(subset);
                //TODO set foreign key
                foreignKeys.put(subset, newTable);
            }
        }
        Table table = new Table(attributes);
        //TODO : set primary key
        Set<FD> fdSet = funcDepPerTable(table, canonicalCover);
        table.setPrimaryKey(findCandidateKeys(fdSet, attributes).iterator().next());
        //TODO set foreign key
        table.setForeignKeys(foreignKeys);
        secondNF.add(table);
        return secondNF;
    }

    public static Decomposition thirdNF(Set<FD> funcDep) {
        Set<Table> thirdNF = new HashSet<>();
        Set<Table> secondNF = secondNF(funcDep);
        Set<FD> cover = canonicalCover(funcDep);

        for (Table table : secondNF) {
            getthirdNF(table, cover, thirdNF);
        }
        new Decomposition(thirdNF, cover);
        return new Decomposition(thirdNF, cover);
    }

    static void getthirdNF(Table table, Set<FD> cover, Set<Table> thirdNF) {
        Set<Attribute> attributes = table.getAttributes();
        Set<FD> fdSet = funcDepPerTable(table, cover);
        Set<Set<Attribute>> candidateKeys = findCandidateKeys(fdSet, attributes);

        Set<Attribute> primeAttributes = candidateKeys.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Set<Attribute> nonPrimeAttributes = Sets.difference(attributes, primeAttributes);


        for (Attribute attribute : nonPrimeAttributes) {
            Set<Attribute> leftSide = getLeftSide(attribute, fdSet);
            if (!primeAttributes.containsAll(leftSide)) {
                Set<Attribute> closure = attributeClosure(leftSide, fdSet);

                attributes.removeAll(closure);
                attributes.addAll(leftSide);

                Table newTable = new Table(closure);
                newTable.setPrimaryKey(leftSide);

                Table oldTable = new Table(attributes);
                oldTable.setPrimaryKey(table.getPrimaryKey());
                Map<Set<Attribute>, Table> foreignKeys = table.getForeignKeys();
                foreignKeys.put(leftSide, newTable);
                oldTable.setForeignKeys(foreignKeys);

                getthirdNF(newTable, cover, thirdNF);
                getthirdNF(oldTable, cover, thirdNF);
                return;
            }
        }
        thirdNF.add(table);
    }

    private static Set<Attribute> getLeftSide(Attribute attribute, Set<FD> fdSet) {
        for (FD fd : fdSet) {
            if (fd.getRhs().contains(attribute))
                return fd.getLhs();
        }
        throw new RuntimeException("Invalid canonical cover");
    }

    public static Decomposition bcNF(Set<FD> funcDep) {
        Set<Table> bcNFDecomp = new HashSet<>();
        Decomposition thirdNFDecomposition = thirdNF(funcDep);
        Set<FD> canonicalCover = thirdNFDecomposition.getFuncDependencies();

        for (Table table : thirdNFDecomposition.getTables()) {
            bcNF(table, bcNFDecomp, canonicalCover);
        }
        bcNFDecomp.forEach(table -> setPrimaryKey(table, canonicalCover));

        return new Decomposition(bcNFDecomp, canonicalCover);
    }

    static void bcNF(Table table, Set<Table> bcNFDecomp, Set<FD> canonicalCover) {
        Set<FD> fdSet = funcDepPerTable(table, canonicalCover);
        Set<Set<Attribute>> candidateKeys = findCandidateKeys(fdSet, table.getAttributes());
        //TODO: table with no fds, where all attributes are the key
        for (FD fd : fdSet) {
            if (!candidateKeys.contains(fd.getLhs())) {
                Table newTable = new Table(Sets.union(fd.getLhs(), fd.getRhs()));
                Table oldTable = new Table(Sets.difference(table.getAttributes(), fd.getRhs()));
                bcNF(newTable, bcNFDecomp, canonicalCover);
                bcNF(oldTable, bcNFDecomp, canonicalCover);
                break;
            }
        }
        bcNFDecomp.add(table);
    }

    private static Set<FD> funcDepPerTable(Table table, Set<FD> canonicalCover) {
        Set<FD> tableFds = new HashSet<>();
        Set<Attribute> attributes = table.getAttributes();

        canonicalCover.forEach(fd -> {
            if (attributes.containsAll(fd.getLhs()) && attributes.containsAll(fd.getRhs()))
                tableFds.add(fd);
        });
        return tableFds;
    }

    public void fourthNF() {

    }

    public void fifthNF() {

    }

    public void sixthNF() {

    }


    public static Set<FD> canonicalCover(Set<FD> funcDependencies) {
        Set<FD> fdSet = splitRHS(funcDependencies);
        fdSet = removeExtrLHS(fdSet);
        return removeExtrRHS(fdSet);
    }

    static Set<Attribute> attributeClosure(Set<Attribute> attrs, Set<FD> fds) {
        Set<Attribute> attrClosure = new HashSet<>(attrs);
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
            Set<Set<Attribute>> powerSet = powerSet(fd.getLhs());
            for (Set<Attribute> sub : powerSet) {
                Set<Attribute> attributeClosure = attributeClosure(sub, funcDependencies);
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
                    Set<Attribute> closure = attributeClosure(fd.getLhs(), removeFD(funcDependencies, fd));
                    return !closure.containsAll(fd.getRhs());
                })
                .collect(Collectors.toSet());
    }

    static Set<FD> splitRHS(Set<FD> funcDependencies) {
        Set<FD> fds = new HashSet<>();
        for (FD fd : funcDependencies)
            for (Attribute attr : fd.getRhs())
                fds.add(new FD(fd.getLhs(), newHashSet(attr)));
        return fds;
    }

    static Set<FD> removeFD(Set<FD> fds, FD fd) {
        return Sets.filter(fds, f -> !Objects.equals(f, fd));
    }


    public static Set<Set<Attribute>> findCandidateKeys(Set<FD> fds, Set<Attribute> attributes) {
        Set<Set<Attribute>> candKeys = new HashSet<>();

        if (fds.isEmpty()) {
            candKeys.add(attributes);
            return candKeys;
        }

        Set<Attribute> left = new HashSet<>();
        Set<Attribute> right = new HashSet<>();
        for (FD fd : fds) {
            left.addAll(fd.getLhs());
            right.addAll(fd.getRhs());
        }
        Set<Attribute> middle = Sets.intersection(left, right);
        left = Sets.difference(left, middle);

        Set<Attribute> attributeClosure = attributeClosure(left, fds);
        if (attributeClosure.containsAll(attributes)) {
            candKeys.add(left);
            return candKeys;
        }

        Set<Set<Attribute>> powerSet = powerSet(middle);
        boolean keyFound = false;
        int keySize = 0;
        for (Set<Attribute> set : powerSet) {
            Set<Attribute> union = Sets.union(left, set);
            if (!keyFound || keySize == union.size()) {
                Set<Attribute> closure = attributeClosure(union, fds);
                if (closure.containsAll(attributes)) {
                    candKeys.add(union);
                    keyFound = true;
                    keySize = union.size();
                }
            }//TODO: sort powerSet
        }

        return candKeys;
    }

    static Set<Attribute> getAllAttr(Set<FD> fds) {
        Set<Attribute> attrs = new HashSet<>();
        for (FD fd : fds) {
            attrs.addAll(fd.getLhs());
            attrs.addAll(fd.getRhs());
        }
        return attrs;
    }

    static Set<FD> combineRHS(Set<FD> fds) {
        Set<FD> funcDep = new HashSet<>();
        Map<Set<Attribute>, List<FD>> collect = fds.stream().collect(Collectors.groupingBy(FD::getLhs));
        collect.forEach((k, v) -> {
            Set<Attribute> set = new HashSet<>();
            v.forEach(fd -> set.addAll(fd.getRhs()));
            funcDep.add(new FD(k, set));
        });
        return funcDep;
    }

    //TODO: set primary key during creation
    static void setPrimaryKey(Table table, Set<FD> cover) {
        Set<FD> tableDep = funcDepPerTable(table, cover);
        Set<Set<Attribute>> keys = findCandidateKeys(tableDep, table.getAttributes());
        table.setPrimaryKey(keys.iterator().next());
    }

    //TODO: check that this work (with dep t -> t and without)
    static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        Set<Set<T>> sets = new HashSet<>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<>());
            return sets;
        }
        List<T> list = new ArrayList<>(originalSet);
        T head = list.get(0);
        Set<T> rest = new HashSet<>(list.subList(1, list.size()));
        for (Set<T> set : powerSet(rest)) {
            Set<T> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }
}
