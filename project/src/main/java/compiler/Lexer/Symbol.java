// LINFO2132 - Project
// @authors - Héctor Varela and Paloma Bas
// UCLouvain - 2023/24

package compiler.Lexer;

// A symbol needs two attributes - token and attribute

public class Symbol {

    // Type
    private String token;

    // Attribute
    private String attr;

    // Número de liña do símbolo
    private int lineNumber;

    // Constructor
    public Symbol(String token, String attribute, int lineNumber) {
        this.token = token;
        this.attr = attribute;
        this.lineNumber = lineNumber;
    }

    public String getToken() {
        return token;
    }

    public String getAttr() {
        return attr;
    }

    public int getLineNumber() { return lineNumber; }

    // How to print it
    @Override
    public String toString(){
        return "< " + this.token + ", " + this.attr + " >";
    }
}
