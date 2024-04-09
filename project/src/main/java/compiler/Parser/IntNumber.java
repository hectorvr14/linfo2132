package compiler.Parser;

public class IntNumber extends Operand {

    private int value;

    public IntNumber(int value) {
        super();
        this.value = value;
    }

    public String toString() {
        return "Integer: " + String.valueOf(this.value);
    }

    public boolean equals(Object o) {
        IntNumber other = (IntNumber) o;
        return this.value == other.value;
    }
}
