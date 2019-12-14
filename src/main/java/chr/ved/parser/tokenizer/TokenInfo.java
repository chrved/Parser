package chr.ved.parser.tokenizer;

import java.util.regex.Pattern;

public class TokenInfo {
    public final Pattern regex;
    public final int token;

    public TokenInfo(Pattern regex, int token) {
        this.regex = regex;
        this.token = token;
    }
}
