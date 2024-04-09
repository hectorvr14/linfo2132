package compiler.Parser;

import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    // Lookahead symbol
    private Symbol lookahead;

    // The lexer
    private Lexer lexer;

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        this.lookahead = lexer.getNextSymbol();
    }

    // Match method
    public Symbol match(String token, String attr) throws ParserException, IOException {
        if (attr == null) {
            if (!this.lookahead.getToken().equals(token)) {
                throw new ParserException("Line " + lookahead.getLineNumber() + " - Parsing error: token " + this.lookahead.getToken() + " doesn't match, expected " + token);
            }
            else {
                Symbol matchingSymbol = lookahead;
                lookahead = lexer.getNextSymbol();
                return matchingSymbol;
            }
        }
        else {
            if (!this.lookahead.getToken().equals(token)) {
                throw new ParserException("Line " + lookahead.getLineNumber() + " - Parsing error: token " + this.lookahead.getToken() + " doesn't match, expected " + token);
            }
            else if (!this.lookahead.getAttr().equals(attr)) {
                throw new ParserException("Line " + lookahead.getLineNumber() + " - Parsing error: attr " + this.lookahead.getAttr() + " doesn't match, expected " + attr);
            }
            else {
                Symbol matchingSymbol = lookahead;
                lookahead = lexer.getNextSymbol();
                return matchingSymbol;
            }
        }
    }

    public void parseProgram() throws IOException {
        ArrayList<Constant> constants = parseConstants();
    }

    public ArrayList<Constant> parseConstants() throws IOException {
        ArrayList<Constant> constants = new ArrayList<>();
        while(lookahead.getAttr() == "final") {
            constants.add(parseConstant());
        }
        return constants;
    }

    public Constant parseConstant() throws ParserException, IOException {
        // Match final keyword
        match("Keyword","final");
        // Match type
        Type type = parseType();
        // Match identifier
        Identifier id = parseIdentifier();
        // Match assignment
        match("AssignmentOperator", null);
        // Match the expression
        BinaryExp exp = parseExpression();
        return new Constant(type, id, exp);
    }

    public Type parseType() throws ParserException, IOException {
        Symbol type = match("Datatype", null);
        return new Type(type.getAttr());
    }

    public Identifier parseIdentifier() throws ParserException, IOException {
        Symbol type = match("Identifier", null);
        return new Identifier(type.getAttr());
    }

    public BinaryExp parseExpression() throws ParserException, IOException {
        return comparison();
    }

    private BinaryExp comparison() throws ParserException, IOException {

    }

    private

    public void parseStructsAndGlobals() {

    }

    public void parseProcedures() {

    }
}
