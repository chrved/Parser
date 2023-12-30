package chr.ved.parser;

import chr.ved.parser.exception.ParserException;
import chr.ved.parser.token.Token;

import java.util.Collections;
import java.util.List;
import java.util.Stack;



import static chr.ved.parser.token.TokenType.*;


public class Parser {
    private Stack<Token> tokens = new Stack<>();
    private Token lookahead;


    public void parse(List<Token> tokensToParse){
        this.tokens.clear();
        Collections.reverse(tokensToParse);
        this.tokens.addAll(tokensToParse);

        nextToken();

        expression();

        if (lookahead.getToken() != EPSILON) {
            throw new ParserException("Unexpected symbol "+lookahead.getToken()+" "+lookahead.getSequence()+" found");
        }
    }

    private void nextToken(){

        if (tokens.isEmpty()){
            lookahead = new Token(EPSILON, "");
        } else {
            lookahead = tokens.pop();
        }
//        log.info("Got a new token. {} {}",lookahead.getToken(),lookahead.getSequence());
    }

    private void expression() {
//        expression -> signed_term sum_op
//        log.info("In Expression. {} {}",lookahead.getToken(),lookahead.getSequence());
        signedTerm();
        sumOp();
    }
    private void signedTerm() {
//        log.info("In SignedTerm. {} {}",lookahead.getToken(),lookahead.getSequence());
        if (lookahead.getToken() == PLUSMINUS) {
//            log.info("FOUND MATCH. {} {}",lookahead.getToken(),lookahead.getSequence());
            //signedTerm -> PLUSMINUS term sum_op
            nextToken();
            term();
        } else {
            //signed_term -> term
            term();
        }

    }
    private void sumOp() {
//        log.info("In SumOp. {} {}",lookahead.getToken(),lookahead.getSequence());
        if (lookahead.getToken() == PLUSMINUS){
//            log.info("FOUND MATCH. {} {}",lookahead.getToken(),lookahead.getSequence());
            //sum_op -> PLUSMINUS term sum_op
            nextToken();
            term();
            sumOp();
        } else {
            //sum_op -> EPSILON
        }
    }

    private void term() {
//        log.info("In Term. {} {}",lookahead.getToken(),lookahead.getSequence());
        //term -> factor term_op
        factor();
        termOp();
    }

    private void termOp() {
//        log.info("In TermOp. {} {}",lookahead.getToken(),lookahead.getSequence());
        //term_op -> MULTDIV signed_factor term_op
        if(lookahead.getToken() == MULTDIV){
//            log.info("FOUND MATCH. {} {}",lookahead.getToken(),lookahead.getSequence());
            nextToken();
            signedFactor();
            termOp();
        } else {
            // term_op -> EPSILON
        }
    }
    private void factor() {
//        log.info("In Factor. {} {}",lookahead.getToken(),lookahead.getSequence());
        // factor -> argument factor_op
        argument();
        factorOp();
    }

    private void signedFactor() {
//        log.info("In SignedFactor. {} {}",lookahead.getToken(),lookahead.getSequence());
        if (lookahead.getToken() == PLUSMINUS) {
//            log.info("FOUND MATCH. {} {}",lookahead.getToken(),lookahead.getSequence());
            // signed_factor -> PLUSMINUS factor
            nextToken();
            factor();
        } else {
            // signed_factor -> factor
            factor();
        }
    }

    private void factorOp() {
//        log.info("In FactorOp. {} {}",lookahead.getToken(),lookahead.getSequence());
        if (lookahead.getToken() == RAISED) {
//            log.info("FOUND MATCH. {} {}",lookahead.getToken(),lookahead.getSequence());
            // factor_op -> RAISED expression
            nextToken();
            signedFactor();
        } else {
            // factor_op -> EPSILON
        }
    }

    private void argument() {
//        log.info("In Argument. {} {}",lookahead.getToken(),lookahead.getSequence());
        if (lookahead.getToken() == FUNCTION) {
//            log.info("FOUND MATCH. {} {}",lookahead.getToken(),lookahead.getSequence());
            // argument -> FUNCTION argument
            nextToken();
            argument();
        } else if (lookahead.getToken() == OPEN_BRACKET) {
//            log.info("FOUND MATCH. {} {}",lookahead.getToken(),lookahead.getSequence());
            // argument -> OPEN_BRACKET sum CLOSE_BRACKET
            nextToken();
            expression();

            if (lookahead.getToken() != CLOSE_BRACKET) {
                throw new ParserException("Closing brackets expected and " + lookahead.getSequence() + " found instead");
            }

            nextToken();
        } else {
            // argument -> value
            value();
        }
    }

    private void value() {
//        log.info("In Value. {} {}",lookahead.getToken(),lookahead.getSequence());
        if (lookahead.getToken() == NUMBER) {
//            log.info("FOUND MATCH. {} {}",lookahead.getToken(),lookahead.getSequence());
            // argument -> NUMBER
            nextToken();
        }else if (lookahead.getToken() == VARIABLE) {
//            log.info("FOUND MATCH. {} {}",lookahead.getToken(),lookahead.getSequence());
            // argument -> VARIABLE
            nextToken();
        } else {
            throw new ParserException("Unexpected symbol "+lookahead.getSequence()+" found");
        }
    }
}
