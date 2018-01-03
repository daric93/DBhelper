package logic;

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
}
