package logic;

import java.util.Objects;

public class Attribute {
    private final String name;
    private final String type;

    public Attribute(String name) {
        this.name = name;
        this.type = "SQL_VARIANT";
    }

    public Attribute(String name, String type) {
        this.name = name;
        this.type = type == null ? "SQL_VARIANT" : type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;
        Attribute attribute = (Attribute) o;
        return Objects.equals(getName(), attribute.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
