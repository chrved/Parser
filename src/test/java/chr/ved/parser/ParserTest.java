package chr.ved.parser;

import chr.ved.parser.token.TokenParser;
import chr.ved.parser.token.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private TokenParser tokenParser;

    @BeforeEach
    void setUp() {
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

    @Test
    void parse_1p2() {
        tokenParser.parse("1+2");
        Parser p = new Parser();
        p.parse(tokenParser.getTokens());
    }

    @Test
    void parse_minus1p2() {
        tokenParser.parse("-1+-2");
        Parser p = new Parser();
        p.parse(tokenParser.getTokens());

    }

    @Test
    void parse_1t2() {
        tokenParser.parse("1*2");
        Parser p = new Parser();
        p.parse(tokenParser.getTokens());

    }

    @Test
    void parse_1pVar() {
        tokenParser.parse("1-x");
        Parser p = new Parser();
        p.parse(tokenParser.getTokens());

    }

    @Test
    void parse_Exp() {
        tokenParser.parse("x^2");
        Parser p = new Parser();
        p.parse(tokenParser.getTokens());
    }

    @Test
    void parse_Bracets() {
        tokenParser.parse("(2+2)*4");
        Parser p = new Parser();
        p.parse(tokenParser.getTokens());
    }

    @Test
    void parse_Functions() {
        tokenParser.parse("3*2^4 + sqrt(1+3)");
        Parser p = new Parser();
        p.parse(tokenParser.getTokens());
    }
}