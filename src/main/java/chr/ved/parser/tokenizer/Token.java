package chr.ved.parser.tokenizer;

import chr.ved.parser.grammar.TokenType;
import lombok.Getter;

@Getter
public class Token {

    private final TokenType token;
    private final String sequence;

    public Token(TokenType token, String sequence) {
        this.token = token;
        this.sequence = sequence;
    }
}
