package compiler.Parser;

import java.util.ArrayList;

public class ProcedureCall extends Operand {

    private String identifier;

    private ArrayList<BinaryExp> params;

    public ProcedureCall(String identifier, ArrayList<BinaryExp> params) {
        super();
        this.identifier = identifier;
        this.params = params;
    }

    public String toString() {
        String call = "Procedure call: " + identifier + "\n";
        for(int i = 0; i < params.size(); i++) {
            call += "- " + params.get(i).toString() + "\n";
        }
        return call;
    }

    public boolean equals(Object o) {
        ProcedureCall pc =  (ProcedureCall) o;
        return identifier.equals(pc.identifier) && params.equals(pc.params);
    }

}
