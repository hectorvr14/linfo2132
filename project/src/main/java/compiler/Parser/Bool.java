package compiler.Parser;

public class Bool extends Operand {

    private boolean value;

    public Bool(boolean value) {
        super();
        this.value = value;
    }

    public String toString() {
        return "Boolean: " + String.valueOf(this.value);
    }

    public boolean equals(Object o) {
        Bool other = (Bool) o;
        return this.value == other.value;
    }

}
