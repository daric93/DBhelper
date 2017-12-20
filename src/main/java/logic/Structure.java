package logic;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Structure {
    private Set<FD> funcDependencies;

    Structure(Set<FD> fds) {
        this.funcDependencies = fds;
    }

    public Set<FD> getFDs() {
        return funcDependencies;
    }

    public Set<FD> canonicalCover() {
        splitRHS();
        removeExtrLHS();
        removeExtrRHS();
        return funcDependencies;
    }

    private void removeExtrLHS() {
        funcDependencies.forEach(fd -> fd.removeExtraneousLHS(funcDependencies));
    }

    private void removeExtrRHS() {
        funcDependencies.forEach(fd -> fd.removeExtraneousRHS(funcDependencies));
        funcDependencies = funcDependencies.stream()
                .filter(fd -> fd.getRhs() != null)
                .collect(Collectors.toSet());
    }

    private void splitRHS() {
        Set<FD> fds = new HashSet<>();
        for (FD fd : funcDependencies)
            fds.addAll(fd.splitRHS());
        funcDependencies = fds;
    }
}
