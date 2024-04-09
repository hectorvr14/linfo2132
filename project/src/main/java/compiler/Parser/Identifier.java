package compiler.Parser;

public class Identifier extends Operand {

    String name;

    public Identifier(String name) {
        this.name = name;
    }

    public String toString() {
        return "Identifier: " + this.name;
    }

    public boolean equals(Object o) {
        Identifier other = (Identifier) o;
        return this.name == other.name;
    }

}
