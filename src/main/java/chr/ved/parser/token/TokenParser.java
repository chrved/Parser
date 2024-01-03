package chr.ved.parser.token;

import chr.ved.parser.exception.ParserException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenParser {
    private final List<TokenInfo> tokenInfos;
    @Getter
    private List<Token> tokens;

    public TokenParser() {
        tokenInfos = new ArrayList<>();
        tokens = new ArrayList<>();
    }

    public void parse(String str) {
        String s = str;
        s = s.trim();
        tokens.clear();

        while (!s.isEmpty()){
            boolean match = false;
            for (TokenInfo info : tokenInfos) {
                Matcher m = info.regex.matcher(s);
                if (m.find()) {
                    match = true;

                    String tok = m.group().trim();
                    if(!tok.isEmpty()){
                        tokens.add(new Token(info.token, tok));
                    }


                    s = m.replaceFirst("");
                    break;
                }
            }
            if (!match) throw new ParserException("Unexpected character in input: "+s);
        }

    }

    public void addTokenType(TokenType token, String regex)  {
        tokenInfos.add(new TokenInfo(Pattern.compile("^("+regex+")"), token));
    }

}
