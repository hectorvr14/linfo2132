package compiler.Parser;

public class Constant {

    // Datatype
    private Type datatype;

    // Identifier
    private Identifier identifier;

    // Assigned value
    private BinaryExp assignment;

    // Constructor
    public Constant(Type datatype, Identifier identifier, BinaryExp assignment) {
        this.datatype = datatype;
        this.identifier = identifier;
        this.assignment = assignment;
    }

    public Type getDatatype() {
        return datatype;
    }

}
