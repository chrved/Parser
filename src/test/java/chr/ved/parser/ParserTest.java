package chr.ved.parser;

import chr.ved.parser.token.TokenParser;
import chr.ved.parser.token.TokenType;
import chr.ved.parser.tree.Node;
import chr.ved.parser.tree.SetVariable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private TokenParser tokenParser;
    private Parser parser;

    @BeforeEach
    void setUp() {
        System.out.println("setup");
        parser = new Parser();
        tokenParser = new TokenParser();
        tokenParser.addTokenType(TokenType.WHITESPACE, "\\s");
        tokenParser.addTokenType(TokenType.FUNCTION, "sin|cos|exp|ln|sqrt"); // function
        tokenParser.addTokenType(TokenType.OPEN_BRACKET, "\\("); // open bracket
        tokenParser.addTokenType(TokenType.CLOSE_BRACKET, "\\)"); // close bracket
        tokenParser.addTokenType(TokenType.PLUS, "[+]"); // plus or minus
        tokenParser.addTokenType(TokenType.MINUS, "[-]"); // plus or minus
        tokenParser.addTokenType(TokenType.MULTIPLY, "[*]"); // mult or divide
        tokenParser.addTokenType(TokenType.DIVIDE, "/"); // mult or divide
        tokenParser.addTokenType(TokenType.RAISED, "\\^"); // raised
        tokenParser.addTokenType(TokenType.NUMBER, "[0-9]+"); // integer number
        tokenParser.addTokenType(TokenType.VARIABLE, "[a-zA-Z][a-zA-Z0-9_]*"); // variable
    }


    @ParameterizedTest
    @CsvSource({
            " 1+2 , 3",
            " -1-2 , -3",
            " 1*2 , 2",
            "(2+2)*4 , 16",
            "2+2*4 , 10",
            "3*2^4 + sqrt(1+3), 50"
    })
    void parse(String input, String expected) {
        tokenParser.parse(input);
        Node expr = parser.parse(tokenParser.getTokens());
        assertEquals(Double.parseDouble(expected), expr.getValue());
    }

    @Test
    void parse_1pVar() {
        tokenParser.parse("1-x");
        Node expr = parser.parse(tokenParser.getTokens());
        expr.accept(new SetVariable("x", 3));
        assertEquals(-2, expr.getValue());
    }

    @Test
    void parse_Exp() {
        tokenParser.parse("x^2");
        Node expr = parser.parse(tokenParser.getTokens());
        expr.accept(new SetVariable("x", 3));
        assertEquals(9, expr.getValue());
    }

    @Test
    void parse_fun() {
        tokenParser.parse("sin(pi/2)");
        Node expr = parser.parse(tokenParser.getTokens());
        expr.accept(new SetVariable("pi", Math.PI));
        assertEquals(1, expr.getValue());
    }
}