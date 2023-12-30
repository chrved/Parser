package chr.ved.parser.token;

public enum TokenType {
    EPSILON(0),
    PLUSMINUS(1),
    MULTDIV(2),
    RAISED(3),
    FUNCTION(4),
    OPEN_BRACKET(5),
    CLOSE_BRACKET(6),
    NUMBER(7),
    VARIABLE(8),
    WHITESPACE(9);

    private final int code;
    TokenType(int code){
        this.code = code;
    }
}
