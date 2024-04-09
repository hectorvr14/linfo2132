package compiler.Parser;

public class FloatNumber extends Operand {

    private float value;

    public FloatNumber(int value) {
        super();
        this.value = value;
    }

    public String toString() {
        return "Float: " + String.valueOf(this.value);
    }

    public boolean equals(Object o) {
        FloatNumber other = (FloatNumber) o;
        return this.value == other.value;
    }

}
