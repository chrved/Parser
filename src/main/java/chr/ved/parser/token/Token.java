package chr.ved.parser.token;

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
