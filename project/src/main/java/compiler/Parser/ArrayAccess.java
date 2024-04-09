package compiler.Parser;

public class ArrayAccess extends Operand {

    private String identifier;
    private int index;

    public ArrayAccess(String identifier, int index) {
        super();
        this.identifier = identifier;
        this.index = index;
    }

    public String toString() {
        return "ArrayAccess: " + this.identifier + "[" + this.index + "]";
    }

    public boolean equals(Object o) {
        ArrayAccess other = (ArrayAccess) o;
        return ((this.identifier == other.identifier) && (this.index == other.index));
    }

}
