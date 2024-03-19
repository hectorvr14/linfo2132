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

    // Constructor
    public Symbol(String token, String attribute) {
        this.token = token;
        this.attr = attribute;
    }

    public String getToken() {
        return token;
    }

    public String getAttr() {
        return attr;
    }

    // How to print it
    @Override
    public String toString(){
        return "< " + this.token + ", " + this.attr + " >";
    }
}
