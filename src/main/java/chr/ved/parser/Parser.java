package chr.ved.parser;

import chr.ved.parser.exception.ParserException;
import chr.ved.parser.token.Token;
import chr.ved.parser.token.TokenType;
import chr.ved.parser.tree.Node;
import chr.ved.parser.tree.NodeType;
import chr.ved.parser.tree.nodes.*;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static chr.ved.parser.token.TokenType.CLOSE_BRACKET;
import static chr.ved.parser.token.TokenType.EPSILON;

@Slf4j
public class Parser {
    private final LinkedList<Token> tokens = new LinkedList<>();
    private Token lookahead;


    public Node parse(List<Token> tokensToParse) {
        this.tokens.clear();
        this.tokens.addAll(tokensToParse);
        this.lookahead = this.tokens.getFirst();

        Node expression = expression();

        if (lookahead.getToken() != EPSILON) {
            throw new ParserException("Unexpected symbol: " + lookahead.getToken() + " " + lookahead.getValue() + " found");
        }

        return expression;
    }

    private Node expression() {
        Node sTerm = signedTerm();
        return sumOperator(sTerm);
    }

    private Node sumOperator(Node expr) {
        switch (lookahead.getToken()) {
            case MINUS, PLUS -> {
                log.info("Set sumOperator. {} {}", lookahead.getToken(), lookahead.getValue());
                AdditionNode sum;
                if (expr.getType() == NodeType.ADDITION)
                    sum = (AdditionNode) expr;
                else
                    sum = new AdditionNode(expr, true);

                boolean positive = lookahead.getValue().equals("+");

                nextToken();
                Node term = term();
                sum.add(term, positive);
                return sumOperator(sum);
            }
            default -> {
                return expr;
            }
        }
    }

    private Node signedTerm() {
        switch (lookahead.getToken()) {
            case PLUS, MINUS -> {
                log.info("Set signedTerm. {} {}", lookahead.getToken(), lookahead.getValue());
                boolean positive = lookahead.getValue().equals("+");
                nextToken();

                Node term = term();
                if (positive) {
                    return term;
                }
                return new AdditionNode(term, false);
            }
            default -> {
                return term();
            }
        }
    }

    private Node termOperator(Node expr) {
        switch (lookahead.getToken()) {
            case MULTIPLY, DIVIDE -> {
                log.info("Set termOperator. {} {}", lookahead.getToken(), lookahead.getValue());
                MultiplicationNode prod;
                if (expr.getType() == NodeType.MULTIPLICATION) {
                    prod = (MultiplicationNode) expr;
                } else {
                    prod = new MultiplicationNode(expr, true);
                }

                boolean positive = lookahead.getValue().equals("*");
                nextToken();
                Node fac = signedFactor();

                prod.add(fac, positive);

                return termOperator(prod);
            }
            default -> {
                return expr;
            }
        }
    }

    private Node term() {
        Node fac = factor();
        return termOperator(fac);
    }

    private Node signedFactor() {
        switch (lookahead.getToken()) {
            case MINUS, PLUS -> {
                log.info("Set signedFactor. {} {}", lookahead.getToken(), lookahead.getValue());
                boolean positive = lookahead.getValue().equals("+");
                nextToken();
                Node fac = factor();
                if (positive) {
                    return fac;
                }
                return new AdditionNode(fac, false);
            }
            default -> {
                return factor();
            }
        }
    }

    private Node factor() {
        Node arg = argument();
        return factorOperator(arg);
    }

    private Node factorOperator(Node expr) {
        if (Objects.requireNonNull(lookahead.getToken()) == TokenType.RAISED) {
            log.info("Set factorOperator. {} {}", lookahead.getToken(), lookahead.getValue());
            nextToken();
            Node sigFactor = signedFactor();

            return new ExponentiationNode(expr, sigFactor);
        }
        return expr;
    }

    private Node argument() {
        switch (lookahead.getToken()) {
            case FUNCTION -> {
                log.info("Set argument. {} {}", lookahead.getToken(), lookahead.getValue());
                int function = FunctionNode.stringToFunction(lookahead.getValue());
                nextToken();
                Node expression = argument();
                return new FunctionNode(function, expression);
            }
            case OPEN_BRACKET -> {
                log.info("Set argument. {} {}", lookahead.getToken(), lookahead.getValue());
                nextToken();
                Node expr = expression();

                if (lookahead.getToken() != CLOSE_BRACKET) {
                    throw new ParserException("Closing brackets expected and " + lookahead.getValue() + " found instead");
                }

                nextToken();
                return expr;
            }
            default -> {
                return value();
            }
        }
    }

    private Node value() {
        switch (lookahead.getToken()) {
            case NUMBER -> {
                log.info("Set value. {} {}", lookahead.getToken(), lookahead.getValue());
                Node constNode = new ConstantNode(lookahead.getValue());
                nextToken();
                return constNode;
            }
            case VARIABLE -> {
                log.info("Set value. {} {}", lookahead.getToken(), lookahead.getValue());
                Node varNode = new VariableNode(lookahead.getValue());
                nextToken();
                return varNode;
            }
            default -> {
                if (lookahead.getToken() == EPSILON) {
                    throw new ParserException("Unexpected end of input");
                } else {
                    throw new ParserException("Unexpected symbol " + lookahead.getValue() + " found");
                }
            }
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
