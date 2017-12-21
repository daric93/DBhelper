package logic;

public class Table {
    private String[] primaryKey;
    private String[] attributes;

    public Table(String[] primaryKey, String[] attributes) {
        this.primaryKey = primaryKey;
        this.attributes = attributes;
    }

    public String[] getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String[] primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }
}
