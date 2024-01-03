package chr.ved.parser.token;

import chr.ved.parser.exception.ParserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenParserTest {
    private TokenParser tokenParser;

    @BeforeEach
    void setup(){
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
    public void testPlusMinus(){
        tokenParser.parse(" 1+2-3+4");
        for (Token tok : tokenParser.getTokens()) {
            System.out.println("" + tok.getToken() + " \t\t" + tok.getValue());
        }
    }

    @Test
    public void testMultDiv(){
        tokenParser.parse(" 1*2/3*4");
        for (Token tok : tokenParser.getTokens()) {
            System.out.println("" + tok.getToken() + " \t\t" + tok.getValue());
        }
    }

    @Test
    public void testFuncVars(){
        tokenParser.parse(" cos(x) * (1 + var_12) ");
        for (Token tok : tokenParser.getTokens()) {
            System.out.println("" + tok.getToken() + " \t\t" + tok.getValue());
        }
    }

    @Test
    public void testExp(){
        tokenParser.parse(" cos(x^2) - 3^3");
        for (Token tok : tokenParser.getTokens()) {
            System.out.println("" + tok.getToken() + " \t\t" + tok.getValue());
        }
    }

    @Test
    public void testUnknown(){

        ParserException thrown = assertThrows(ParserException.class, () -> tokenParser.parse(" 1;1"));

        assertEquals("Unexpected character in input: ;1", thrown.getMessage());
    }
}