package logic;

import java.util.Objects;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class Table {
    private final Set<String> attributes;

    Table(String... attributes) {
        this.attributes = newHashSet(attributes);
    }

    Table(Set<String> attributes) {
        this.attributes = attributes;
    }

    public Set<String> getAttributes() {
        return attributes;
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
                '}';
    }
}
