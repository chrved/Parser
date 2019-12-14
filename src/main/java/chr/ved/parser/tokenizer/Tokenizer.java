package chr.ved.parser.tokenizer;

import chr.ved.parser.exception.ParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private List<TokenInfo> tokenInfos;
    private List<Token> tokens;

    public Tokenizer() {
        tokenInfos = new ArrayList<>();
        tokens = new ArrayList<>();
    }

    public void tokenize(String str) {
        String s = str;
        tokens.clear();

        while (!s.equals("")){
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

    public void add(String regex, int token) {
        tokenInfos.add(new TokenInfo(Pattern.compile("^("+regex+")"), token));
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
