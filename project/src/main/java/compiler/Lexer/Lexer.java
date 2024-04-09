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

    // Current line being read
    private int lineNumber;

    public Lexer(Reader input) {

        // Initialize the pushback reader
        this.back = new PushbackReader(input);

        // Initialize the symbols
        initStructures();

        // We start with the first line
        this.lineNumber = 1;
    }

    // Create the sets of keywords and datatypes
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

    }
    
    public Symbol getNextSymbol() throws IOException {

        Symbol symbol = null;

        // We get the next character
        int c = this.back.read();

        // End of file
        if(c == -1) {
            symbol = new Symbol("EOF","", this.lineNumber);
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
                symbol = new Symbol("Boolean", s, this.lineNumber);
            }
            else if(this.dataTypes.contains(s)) { // Datatypes
                symbol = new Symbol("Datatype", s, this.lineNumber);
            }
            else if(this.keywords.contains(s)) { // Keywords
                symbol = new Symbol("Keyword", s, this.lineNumber);
            }
            else { symbol = new Symbol("Identifier", s, this.lineNumber); } // Everything else is considered as an identifier
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
                        symbol = new Symbol("Error", s, this.lineNumber);
                    }
                    else { // No problem - float number
                        // Remember to unread the last character
                        back.unread(c);
                        symbol = new Symbol("Float", s, this.lineNumber);
                    }
                }
                else { // If it isn't a number - we will consider 3. as valid
                    // Unread the last character - and return float
                    back.unread(c);
                    symbol = new Symbol("Float", s, this.lineNumber);
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
                    symbol = new Symbol("Error", s, this.lineNumber);
                }
                else { // IntNumber number

                    // Unread the last character - and return integer
                    back.unread(c);
                    symbol = new Symbol("IntNumber", s, this.lineNumber);
                }
            }

            return symbol;
        }

        // Special single symbols - no need to look ahead, there is no confusion
        else if(c == '+') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("AddOperator", "", this.lineNumber);
            return symbol;
        }

        else if(c == '-') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("SubtractOperator", "", this.lineNumber);
            return symbol;
        }

        else if(c == '*') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("ProductOperator", "", this.lineNumber);
            return symbol;
        }

        else if(c == '%') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("ModuleOperator", "", this.lineNumber);
            return symbol;
        }

        else if(c == '(') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("LeftParenthesis", "", this.lineNumber);
            return symbol;
        }

        else if(c == ')') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("RightParenthesis", "", this.lineNumber);
            return symbol;
        }

        else if(c == '[') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("LeftBracket", "", this.lineNumber);
            return symbol;
        }

        else if(c == ']') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("RightBracket", "", this.lineNumber);
            return symbol;
        }

        else if(c == '{') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("LeftBrace", "", this.lineNumber);
            return symbol;
        }

        else if(c == '}') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("RightBrace", "", this.lineNumber);
            return symbol;
        }

        else if(c == '.') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("StructOperator", "", this.lineNumber);
            return symbol;
        }

        else if(c == ';') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("Semicolon", "", this.lineNumber);
            return symbol;
        }

        else if(c == ',') {
            String s = "";
            s = s + (char) c;
            symbol = new Symbol("Comma", "", this.lineNumber);
            return symbol;
        }

        // Special double symbols - we need to continue looking ahead
        else if(c == '=') {
            String s = "";
            s = s + (char) c;
            c = back.read();
            // Not an equal symbol - single special character
            if(c != '=') {
                back.unread(c);
                symbol = new Symbol("AssignmentOperator", "", this.lineNumber);
            }
            // Equal symbol - double special character
            else {
                s = s + (char) c;
                symbol = new Symbol("EqualOperator", "", this.lineNumber);
            }
            return symbol;
        }

        else if(c == '!') {
            String s = "";
            s = s + (char) c;
            c = back.read();
            // Not an equal symbol - identifier (the negation function)
            if(c != '=') {
                back.unread(c);
                symbol = new Symbol("Identifier", s, this.lineNumber);
            }
            // Equal symbol - double special character
            else {
                s = s + (char) c;
                symbol = new Symbol("DifferentOperator", "", this.lineNumber);
            }
            return symbol;
        }

        else if(c == '<') {
            String s = "";
            s = s + (char) c;
            c = back.read();
            // Not an equal symbol - single special character
            if(c != '=') {
                back.unread(c);
                symbol = new Symbol("LowerOperator", "", this.lineNumber);
            }
            // Equal symbol - double special character
            else {
                s = s + (char) c;
                symbol = new Symbol("LowerEqualOperator", "", this.lineNumber);
            }
            return symbol;
        }

        else if(c == '>') {
            String s = "";
            s = s + (char) c;
            c = back.read();
            // Not an equal symbol - single special character
            if(c != '=') {
                back.unread(c);
                symbol = new Symbol("GreaterOperator", "", this.lineNumber);
            }
            // Equal symbol - double special character
            else {
                s = s + (char) c;
                symbol = new Symbol("GreaterEqualOperator", "", this.lineNumber);
            }
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
                symbol = new Symbol("Error", s, this.lineNumber);
                return symbol;
            }
            // Logic AND
            else {
                s = s + (char) c;
                symbol = new Symbol("AndOperator", "", this.lineNumber);
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
                symbol = new Symbol("Error", s, this.lineNumber);
                return symbol;
            }
            // Logic OR
            else {
                s = s + (char) c;
                symbol = new Symbol("OrOperator", "", this.lineNumber);
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
                symbol = new Symbol("DivisionOperator", "", this.lineNumber);
                return symbol;
            }
            // Otherwise, comment - read until end of line (or file) and ignore, and also remember to increase line number
            else {
                while(c != '\n' && c != -1) {
                    c = back.read();
                }
                this.lineNumber++;
            }
        }

        // New line - increase line number
        else if(c == '\n') {
            symbol = new Symbol("NewLine", "", this.lineNumber);
            this.lineNumber++;
            return symbol;
        }

        // Whitespace - ignore 
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
                    symbol = new Symbol("Error", s, this.lineNumber);
                    return symbol;
                }
                s = s + (char) c;
                if(c == '"') break;
            }
            symbol = new Symbol("String", s, this.lineNumber);
            return symbol;
        }

        // All the other characters that are not recognised
        else {
            symbol = new Symbol("Error", "" + (char) c, this.lineNumber);
            return symbol;
        }

        return symbol;
    }

}
