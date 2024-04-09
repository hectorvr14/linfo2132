// LINFO2132 - Project
// @authors - Héctor Varela and Paloma Bas
// UCLouvain - 2023/24

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;

public class TestLexer {

    @Test
    public void testIdentifier1() throws IOException { // Good
        String input = "variable";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Identifier", symbol.getToken());
        assertEquals("variable", symbol.getAttr());
    }

    @Test
    public void testIdentifier2() throws IOException { // Good
        String input = "_23a4_5";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Identifier", symbol.getToken());
        assertEquals("_23a4_5", symbol.getAttr());
    }

    @Test
    public void testIdentifier3() throws IOException { // Good
        String input = "a_234a928";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Identifier", symbol.getToken());
        assertEquals("a_234a928", symbol.getAttr());
    }

    @Test
    public void testIdentifier4() throws IOException { // Good
        String input = "_____";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Identifier", symbol.getToken());
        assertEquals("_____", symbol.getAttr());
    }

    @Test
    public void testIdentifier5() throws IOException { // Bad
        String input = "variable-1";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Identifier", symbol.getToken());
        assertEquals("variable-1", symbol.getAttr());
    }

    @Test
    public void testIdentifier6() throws IOException { // Bad
        String input = "57a2983";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Identifier", symbol.getToken());
        assertEquals("57a2983", symbol.getAttr());
    }

    @Test
    public void testInteger1() throws IOException { // Good
        String input = "12357097952";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("IntNumber", symbol.getToken());
        assertEquals("12357097952", symbol.getAttr());
    }

    @Test
    public void testInteger2() throws IOException { // Good
        String input = "0000003252342342";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("IntNumber", symbol.getToken());
        assertEquals("0000003252342342", symbol.getAttr());
    }

    @Test
    public void testInteger3() throws IOException { // Bad
        String input = "0000003252342342.a";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("IntNumber", symbol.getToken());
        assertEquals("0000003252342342.a", symbol.getAttr());
    }

    @Test
    public void testFloat1() throws IOException { // Good
        String input = "3928375982733.923857928375823";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Float", symbol.getToken());
        assertEquals("3928375982733.923857928375823", symbol.getAttr());
    }

    @Test
    public void testFloat2() throws IOException { // Good
        String input = "3928375982733.";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Float", symbol.getToken());
        assertEquals("3928375982733.", symbol.getAttr());
    }

    @Test
    public void testFloat3() throws IOException { // Bad
        String input = ".234324";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Float", symbol.getToken());
        assertEquals(".234324", symbol.getAttr());
    }

    @Test
    public void testFloat4() throws IOException { // Good
        String input = "29835.asd";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Float", symbol.getToken());
        assertEquals("29835.", symbol.getAttr());
    }

    @Test
    public void testString1() throws IOException { // Good
        String input = "\"Hello, World!\"";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("String", symbol.getToken());
        assertEquals("\"Hello, World!\"", symbol.getAttr());
    }

    @Test
    public void testKeyword() throws IOException {
        String input = "for";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Keyword", symbol.getToken());
        assertEquals("for", symbol.getAttr());
    }

    @Test
    public void testSpecialCharacter() throws IOException {
        String input = "+";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("SpecialCharacter", symbol.getToken());
        assertEquals("+", symbol.getAttr());
    }

    @Test
    public void testLogicAnd() throws IOException {
        String input = "&&";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("SpecialCharacter", symbol.getToken());
        assertEquals("&&", symbol.getAttr());
    }

    @Test
    public void testLogicOr() throws IOException {
        String input = "||";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("SpecialCharacter", symbol.getToken());
        assertEquals("||", symbol.getAttr());
    }

    @Test
    public void testComment() throws IOException {
        String input = "// This is a comment\n";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertNull(symbol);
    }

    @Test
    public void testInvalidSymbol() throws IOException {
        String input = "$";
        Lexer lexer = new Lexer(new StringReader(input));
        Symbol symbol = lexer.getNextSymbol();
        assertEquals("Error", symbol.getToken());
        assertEquals("$", symbol.getAttr());
    }

}
