package compiler.Parser;

public class Type {

    String identifier;

    public Type(String identifier) {
        this.identifier = identifier;
    }

    public String toString() {
        return "Type: " + identifier;
    }

    public boolean equals(Object o) {
        Type other = (Type) o;
        return identifier.equals(other.identifier);
    }
}
