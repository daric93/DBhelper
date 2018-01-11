package logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class Table {
    private final Set<Attribute> attributes;
    private Set<Attribute> primaryKey;
    private Map<Set<Attribute>, Table> foreignKeys;

    Table(Attribute... attributes) {
        this.attributes = newHashSet(attributes);
        this.foreignKeys = new HashMap<>();
    }

    Table(Set<Attribute> attributes) {
        this.attributes = attributes;
        this.foreignKeys = new HashMap<>();
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public Set<Attribute> getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Set<Attribute> primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Map<Set<Attribute>, Table> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(Map<Set<Attribute>, Table> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;
        Table table = (Table) o;
        return Objects.equals(getAttributes(), table.getAttributes());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAttributes());
    }

    @Override
    public String toString() {
        return "Table{" +
                "attributes=" + attributes +
                ", primaryKey=" + primaryKey +
                ", foreignKeys=" + foreignKeys +
                '}';
    }
}
