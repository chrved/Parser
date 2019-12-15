package chr.ved.parser.tokenizer;

import chr.ved.parser.grammar.TokenType;

import java.util.regex.Pattern;

public class TokenInfo {
    public final Pattern regex;
    public final TokenType token;

    public TokenInfo(Pattern regex, TokenType token) {
        this.regex = regex;
        this.token = token;
    }
}
