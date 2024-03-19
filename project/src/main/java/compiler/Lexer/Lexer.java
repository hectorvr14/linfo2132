// LINFO2132 - Project
// @authors - Héctor Varela and Paloma Bas
// UCLouvain - 2023/24

package compiler.Lexer;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

public class Lexer {

    // Pushback reader
    private PushbackReader back;

    // Set of keywords
    private Set<String> keywords;

    // Set of basic types
    private Set<String> dataTypes;

    // Set of single special symbols
    private Set<Character> singles;

    public Lexer(Reader input) {

        // Initialize the pushback reader
        this.back = new PushbackReader(input);

        // Initialize the symbols
        initStructures();
    }

    // Create the sets of keywords and symbols
    private void initStructures() {

        // Initialize the keywords
        this.keywords = new HashSet<>();

        this.keywords.add("free");
        this.keywords.add("final");
        this.keywords.add("struct");
        this.keywords.add("def");
        this.keywords.add("for");
        this.keywords.add("while");
        this.keywords.add("if");
        this.keywords.add("else");
        this.keywords.add("return");

        // Initialize the datatypes
        this.dataTypes = new HashSet<>();

        this.dataTypes.add("int");
        this.dataTypes.add("float");
        this.dataTypes.add("void");
        this.dataTypes.add("string");
        this.dataTypes.add("bool");

        // Initialize the single symbols
        this.singles = new HashSet<>();

        String symbols = "+-*%()[]{}.;,";
        char[] symb = symbols.toCharArray();
        for(int i = 0 ; i < symb.length; i++) {
            this.singles.add(symb[i]);
        }

    }
    
    public Symbol getNextSymbol() throws IOException {

        Symbol symbol = null;

        // We get the next character
        int c = this.back.read();

        // End of file
        if(c == -1) {
            symbol = new Symbol("EOF","");
            return symbol;
        }

        // Either identifier or keyword
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_') {
            String s = "";
            while(true) {
                s = s + (char) c;
                c = back.read();
                if(!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' || (c >= '0' && c <= '9'))) {
                    back.unread(c);
                    break;
                }
            }
            if(s.equals("true") || s.equals("false")) { // Booleans
                symbol = new Symbol("Boolean", s);
            }
            else if(this.dataTypes.contains(s)) { // Datatypes
                symbol = new Symbol("Datatype", s);
            }
            else if(this.keywords.contains(s)) { // Keywords
                symbol = new Symbol("Keyword", s);
            }
            else { symbol = new Symbol("Identifier", s); } // Everything else is considered as an identifier
            return symbol;
        }

        // Numbers
        else if((c >= '0' && c <= '9')) {
            String s = "";
            do { // While we keep reading digits
                s = s + (char) c;
                c = back.read();
            } while (c >= '0' && c <= '9');

            // Have we reached a dot?
            if(c == '.') {
                // Insert it in the string, and read next char
                s = s + (char) c;
                c = back.read();

                // Is the next char a digit? - if so, keep them coming until finished
                if(c >= '0' && c <= '9') {
                    do {
                        s = s + (char) c;
                        c = back.read();
                    } while (c >= '0' && c <= '9');

                    // What's the next character?
                    if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_') { // identifier kind of thing - error!
                        do {
                            s = s + (char) c;
                            c = back.read();
                        } while ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_');

                        // Unread the last character - and return error
                        back.unread(c);
                        symbol = new Symbol("Error", s);
                    }
                    else { // No problem - float number
                        // Remember to unread the last character
                        back.unread(c);
                        symbol = new Symbol("Float", s);
                    }
                }
                else { // If it isn't a number - we will consider 3. as valid
                    // Unread the last character - and return float
                    back.unread(c);
                    symbol = new Symbol("Float", s);
                }
            }
            else { // Not a dot
                if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_') { // identifier kind of thing - error!
                    do {
                        s = s + (char) c;
                        c = back.read();
                    } while ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_');

                    // Unread the last character - and return error
                    back.unread(c);
                    symbol = new Symbol("Error", s);
                }
                else { // Integer number

                    // Unread the last character - and return integer
                    back.unread(c);
                    symbol = new Symbol("Integer", s);
                }
            }

            return symbol;
        }

        // Special single symbols - no need to look ahead, there is no confusion
        else if(this.singles.contains((char) c)) {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("SpecialCharacter", s);
            return symbol;
        }

        // Special double symbols - we need to continue looking ahead
        else if((c == '=') || (c == '!') || (c == '<') || (c == '>')) {
            String s = "";
            s = s + (char) c;
            c = back.read();
            // Not an equal symbol - single special character
            if(c != '=') {
                back.unread(c);
            }
            // Equal symbol - double special character
            else {
                s = s + (char) c;
            }
            symbol = new Symbol("SpecialCharacter", s);
            return symbol;
        }

        // Logic AND &&
        else if((c == '&')) {
            String s = "";
            s = s + (char) c;
            c = back.read();
            // Not a logic AND - raise an error because single & not recognised
            if(c != '&') {
                back.unread(c);
                symbol = new Symbol("Error", s);
                return symbol;
            }
            // Logic AND
            else {
                s = s + (char) c;
                symbol = new Symbol("SpecialCharacter", s);
                return symbol;
            }
        }

        // Logic OR ||
        else if((c == '|')) {
            String s = "";
            s = s + (char) c;
            c = back.read();
            // Not a logic OR - raise an error because single | not recognised
            if(c != '|') {
                back.unread(c);
                symbol = new Symbol("Error", s);
                return symbol;
            }
            // Logic OR
            else {
                s = s + (char) c;
                symbol = new Symbol("SpecialCharacter", s);
                return symbol;
            }
        }

        // Comments or division
        else if((c == '/')) {
            String s = "";
            s = s + (char) c;
            // Read next character
            c = back.read();
            // If not another slash - division symbol
            if(c != '/') {
                symbol = new Symbol("SpecialCharacter", s);
                return symbol;
            }
            // Otherwise, comment - read until end of line (or file) and ignore
            else {
                while(c != '\n' && c != -1) {
                    c = back.read();
                }
            }
        }

        // New line
        else if(c == '\n') {
            symbol = new Symbol("NewLine", "");
            return symbol;
        }

        // Whitespace - ignore and return null
        else if(c == '\t' || c == ' ') {

        }

        // String - find opening quotation marks, and keep reading until the closing ones
        else if(c == '"'){
            String s = "";
            s = s + (char) c;
            while(true) {
                c = back.read();
                // Check for end of file or line - and raise and error if found (strings only in one line)
                if((c == -1) || (c == '\n')) {
                    symbol = new Symbol("Error", s);
                    return symbol;
                }
                s = s + (char) c;
                if(c == '"') break;
            }
            symbol = new Symbol("String", s);
            return symbol;
        }

        // All the other characters that are not recognised
        else {
            symbol = new Symbol("Error", "" + (char) c);
            return symbol;
        }

        return symbol;
    }

}
