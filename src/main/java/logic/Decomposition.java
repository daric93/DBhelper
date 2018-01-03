package logic;

import java.util.Set;

public class Decomposition {
    private final Set<Table> tables;
    private final Set<FD> funcDependencies;

    public Decomposition(Set<Table> tables, Set<FD> funcDependencies) {
        this.tables = tables;
        this.funcDependencies = funcDependencies;
    }

    public Set<Table> getTables() {
        return tables;
    }

    public Set<FD> getFuncDependencies() {
        return funcDependencies;
    }
}
