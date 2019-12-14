package chr.ved.parser.tokenizer;

import org.junit.Test;

public class TokenizerTest {

    @Test
    public void test1(){
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.add("\\s",0);
        tokenizer.add("sin|cos|exp|ln|sqrt", 1); // function
        tokenizer.add("\\(", 2); // open bracket
        tokenizer.add("\\)", 3); // close bracket
        tokenizer.add("[+-]", 4); // plus or minus
        tokenizer.add("[*/]", 5); // mult or divide
        tokenizer.add("\\^", 6); // raised
        tokenizer.add("[0-9]+",7); // integer number
        tokenizer.add("[a-zA-Z][a-zA-Z0-9_]*", 8); // variable

        tokenizer.tokenize(" cos(x) * (1 + var_12) ");
        for (Token tok : tokenizer.getTokens()) {
            System.out.println("" + tok.token + " " + tok.sequence);
        }
    }

}