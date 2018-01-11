package app;

import logic.Attribute;
import logic.Decomposition;
import logic.Table;

import java.util.Set;
import java.util.stream.Collectors;

public class SQLGen {
    public static String createScript(Decomposition decomposition) {
        StringBuilder builder = new StringBuilder();
        decomposition.getTables().forEach(table -> builder.append(createTableScript(table))
                .append("\n"));
        return builder.toString();
    }

    static String createTableScript(Table table) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ")
                .append(tableName(table))
                .append("(\n");

        for (Attribute attribute : table.getAttributes()) {
            builder.append(attribute.getName()).append(" ")
                    .append(attribute.getType()).append(",\n");
        }

        builder.append("PRIMARY KEY (")
                .append(getPrimaryKey(table))
                .append(")");
        if (table.getForeignKeys().isEmpty())
            builder.append("\n);\n");
        else {
            builder.append(",\n");
            table.getForeignKeys().forEach((k, v) -> {
                String key = foreignKey(k);
                builder
                        .append("CONSTRAINT fk_").append(tableName(table))
                        .append(" FOREIGN KEY (").append(key).append(") ")
                        .append("REFERENCES ").append(tableName(v))
                        .append(" (").append(key).append("),\n");
            });
            builder.deleteCharAt(builder.lastIndexOf(","));
            builder.append(");\n");
        }
        return builder.toString();
    }

    static String tableName(Table table) {
        return table.getPrimaryKey()
                .stream()
                .map(Attribute::getName)
                .collect(Collectors.joining());
    }

    static String getPrimaryKey(Table table) {
        StringBuilder builder = new StringBuilder();
        table.getPrimaryKey().forEach(attr -> builder.append(attr.getName()).append(","));
        builder.deleteCharAt(builder.lastIndexOf(","));
        return builder.toString();
    }

    static String foreignKey(Set<Attribute> key) {
        StringBuilder builder = new StringBuilder();
        key.forEach(attr -> builder.append(attr.getName()));
        return builder.toString();
    }
}
