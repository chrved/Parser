package chr.ved.parser;

import chr.ved.parser.exception.ParserException;
import chr.ved.parser.token.Token;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

import static chr.ved.parser.token.TokenType.*;

@Slf4j
public class Parser {
    private LinkedList<Token> tokens = new LinkedList<>();
    private Token lookahead;


    public void parse(List<Token> tokensToParse) {
        this.tokens.clear();
        this.tokens.addAll(tokensToParse);
        this.lookahead = this.tokens.getFirst();

        expression();

        if (lookahead.getToken() != EPSILON) {
            throw new ParserException("Unexpected symbol: " + lookahead.getToken() + " " + lookahead.getValue() + " found");
        }
    }

    private void expression() {
//        expression -> signed_term sum_operator
        log.info("In Expression. {} {}", lookahead.getToken(), lookahead.getValue());
        signedTerm();
        sumOperator();
    }

    private void signedTerm() {
        log.info("In SignedTerm. {} {}", lookahead.getToken(), lookahead.getValue());
        if (lookahead.getToken() == MINUS) {
            log.info("Set opreator. {} {}", lookahead.getToken(), lookahead.getValue());
            //signedTerm -> MINUS term sum_op
            nextToken();
            term();
        } else {
            //signed_term -> term
            term();
        }

    }

    private void sumOperator() {
//        log.info("In sumOperator. {} {}", lookahead.getToken(), lookahead.getValue());
        if (lookahead.getToken() == PLUS || lookahead.getToken() == MINUS) {
            log.info("Set opreator. {} {}", lookahead.getToken(), lookahead.getValue());
            //sum_operator -> '+' | '-' term sum_operator
            nextToken();
            term();
            sumOperator();
        } else {
            //sum_op -> EPSILON
            log.info("Set to EPSILON. {} {}", lookahead.getToken(), lookahead.getValue());
        }
    }

    private void term() {
        log.info("In Term. {} {}", lookahead.getToken(), lookahead.getValue());
        //term -> factor term_operator
        signedFactor();
        termOperator();
    }

    private void termOperator() {
//        log.info("In termOperator. {} {}",lookahead.getToken(),lookahead.getValue());
//        term_operator -> '*' | '/' signed_factor term_operator
        if (lookahead.getToken() == MULTIPLY || lookahead.getToken() == DIVIDE) {
            log.info("Set value. {} {}", lookahead.getToken(), lookahead.getValue());
            nextToken();
            signedFactor();
            termOperator();
        } else {
            // term_op -> EPSILON
            log.info("Set to EPSILON. {} {}", lookahead.getToken(), lookahead.getValue());
        }
    }

    private void factor() {
//        log.info("In Factor. {} {}", lookahead.getToken(), lookahead.getValue());
        // factor -> argument factor_op
        argument();
        factorOperator();
    }

    private void signedFactor() {
        log.info("In SignedFactor. {} {}", lookahead.getToken(), lookahead.getValue());
        if (lookahead.getToken() == MINUS) {
            log.info("Set value. {} {}", lookahead.getToken(), lookahead.getValue());
            // signed_factor -> MINUS factor
            nextToken();
            factor();
        } else {
            // signed_factor -> factor
            factor();
        }
    }

    private void factorOperator() {
//        log.info("In factorOperator. {} {}", lookahead.getToken(), lookahead.getValue());
        if (lookahead.getToken() == RAISED) {
            log.info("Set value. {} {}", lookahead.getToken(), lookahead.getValue());
            // factor_op -> RAISED expression
            nextToken();
            signedFactor();
        } else {
            log.info("Set value. {} {}", lookahead.getToken(), lookahead.getValue());
            // factor_op -> EPSILON
        }
    }

    private void argument() {
        log.info("In Argument. {} {}", lookahead.getToken(), lookahead.getValue());
        if (lookahead.getToken() == FUNCTION) {
            log.info("Set argument. {} {}", lookahead.getToken(), lookahead.getValue());
            // argument -> FUNCTION argument
            nextToken();
            argument();
        } else if (lookahead.getToken() == OPEN_BRACKET) {
            log.info("Set argument. {} {}", lookahead.getToken(), lookahead.getValue());
            // argument -> OPEN_BRACKET sum CLOSE_BRACKET
            nextToken();
            expression();

            if (lookahead.getToken() != CLOSE_BRACKET) {
                throw new ParserException("Closing brackets expected and " + lookahead.getValue() + " found instead");
            }

            nextToken();
        } else {
            // argument -> value
            value();
        }
    }

    private void value() {
//        log.info("In Value. {} {}", lookahead.getToken(), lookahead.getValue());
        if (lookahead.getToken() == NUMBER) {
            log.info("Set value. {} {}", lookahead.getToken(), lookahead.getValue());
            // argument -> NUMBER
            nextToken();
        } else if (lookahead.getToken() == VARIABLE) {
            log.info("Set variable. {} {}", lookahead.getToken(), lookahead.getValue());
            // argument -> VARIABLE
            nextToken();
        } else {
            throw new ParserException("Unexpected symbol " + lookahead.getValue() + " found");
        }
    }


    private void nextToken() {
        tokens.pop();
        if (tokens.isEmpty()) {
            lookahead = new Token(EPSILON, "");
        } else {
            lookahead = tokens.getFirst();
        }

        log.info("Got a new token. {} {}", lookahead.getToken(), lookahead.getValue());
    }

}
