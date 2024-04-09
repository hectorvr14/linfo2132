package compiler.Parser;

import java.util.ArrayList;

public class ForLoop extends Statement{

    // Expressions defining the loop
    private BinaryExp[] binaryExps;

    // Body of the loop
    private ArrayList<Statement> body;

}
