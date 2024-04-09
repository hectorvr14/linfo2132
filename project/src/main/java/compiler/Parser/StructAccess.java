package compiler.Parser;

public class StructAccess extends Operand {

    // Either identifier is null or array is null

    private String identifier;

    private String structField;

    private ArrayAccess array;

    public StructAccess(String identifier, String structField) {
        this.identifier = identifier;
        this.structField = structField;
        this.array = null;
    }

    public StructAccess(ArrayAccess array, String structField) {
        this.array = array;
        this.structField = structField;
        this.identifier = null;
    }

    public String toString() {
        if(identifier == null) {
            return "StructAccess: " + array.toString() + "." + structField;
        }
        return "StructAccess: " + identifier + "." + structField;
    }

    public boolean equals(Object o) {
        StructAccess other = (StructAccess) o;
        return (this.identifier.equals(other.identifier) && this.structField.equals(other.structField)) || (this.array.equals(other.structField)) && (this.structField.equals(other.structField));
    }
}
