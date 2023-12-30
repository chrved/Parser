package chr.ved.parser.token;

import org.junit.jupiter.api.Test;

public class TokenParserTest {

    @Test
    public void test1(){
        TokenParser tokenParser = new TokenParser();
        tokenParser.add("\\s", TokenType.WHITESPACE);
        tokenParser.add("sin|cos|exp|ln|sqrt", TokenType.FUNCTION); // function
        tokenParser.add("\\(", TokenType.OPEN_BRACKET); // open bracket
        tokenParser.add("\\)", TokenType.CLOSE_BRACKET); // close bracket
        tokenParser.add("[+-]", TokenType.PLUSMINUS); // plus or minus
        tokenParser.add("[*/]", TokenType.MULTDIV); // mult or divide
        tokenParser.add("\\^", TokenType.RAISED); // raised
        tokenParser.add("[0-9]+", TokenType.NUMBER); // integer number
        tokenParser.add("[a-zA-Z][a-zA-Z0-9_]*", TokenType.VARIABLE); // variable

        tokenParser.tokenize(" cos(x) * (1 + var_12)   ");
        for (Token tok : tokenParser.getTokens()) {
            System.out.println("" + tok.getToken() + " \t\t" + tok.getSequence());
        }
    }

}