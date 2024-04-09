package compiler.Parser;

public class StringOp extends Operand {

    private String value;

    public StringOp(String value) {
        super();
        this.value = value;
    }

    public String toString() {
        return "String: " + this.value;
    }

    public boolean equals(Object o) {
        StringOp other = (StringOp) o;
        return this.value == other.value;
    }

}
