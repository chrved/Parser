package chr.ved.parser;

import chr.ved.parser.exception.ParserException;
import chr.ved.parser.grammar.TokenType;
import chr.ved.parser.tokenizer.Tokenizer;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void parse() {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.add("\\s", TokenType.WHITESPACE);
        tokenizer.add("sin|cos|exp|ln|sqrt", TokenType.FUNCTION); // function
        tokenizer.add("\\(", TokenType.OPEN_BRACKET); // open bracket
        tokenizer.add("\\)", TokenType.CLOSE_BRACKET); // close bracket
        tokenizer.add("[+-]", TokenType.PLUSMINUS); // plus or minus
        tokenizer.add("[*/]", TokenType.MULTDIV); // mult or divide
        tokenizer.add("\\^", TokenType.RAISED); // raised
        tokenizer.add("[0-9]+", TokenType.NUMBER); // integer number
        tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", TokenType.VARIABLE); // variable

        tokenizer.tokenize(" cos(x) * (1 + var_12)   ");

        Parser parser = new Parser();
        parser.parse(tokenizer.getTokens());

    }

    @Test(expected = ParserException.class)
    public void parse1() {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.add("\\s", TokenType.WHITESPACE);
        tokenizer.add("sin|cos|exp|ln|sqrt", TokenType.FUNCTION); // function
        tokenizer.add("\\(", TokenType.OPEN_BRACKET); // open bracket
        tokenizer.add("\\)", TokenType.CLOSE_BRACKET); // close bracket
        tokenizer.add("[+-]", TokenType.PLUSMINUS); // plus or minus
        tokenizer.add("[*/]", TokenType.MULTDIV); // mult or divide
        tokenizer.add("\\^", TokenType.RAISED); // raised
        tokenizer.add("[0-9]+", TokenType.NUMBER); // integer number
        tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", TokenType.VARIABLE); // variable

        tokenizer.tokenize(" cos(x) * (1 * var_12   ");

        Parser parser = new Parser();
        parser.parse(tokenizer.getTokens());

    }
}