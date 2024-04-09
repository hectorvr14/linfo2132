package compiler.Parser;

public class BinaryExp extends Operand {

    private Operand leftOperand;

    private String operator;

    private Operand rightOperand;

    public BinaryExp(Operand leftOperand, String operator, Operand rightOperand) {
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    public String toString() {
        return leftOperand.toString() + " " + operator + " " + rightOperand.toString();
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
