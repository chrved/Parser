package chr.ved.parser.tree.nodes;

import chr.ved.parser.exception.EvaluationException;
import chr.ved.parser.tree.Node;
import chr.ved.parser.tree.NodeType;
import chr.ved.parser.tree.NodeVisitor;

public class FunctionNode implements Node {
    public static final int SIN = 1;
    public static final int COS = 2;
    public static final int TAN = 3;

    public static final int ASIN = 4;
    public static final int ACOS = 5;
    public static final int ATAN = 6;

    public static final int SQRT = 7;
    public static final int EXP = 8;

    public static final int LN = 9;
    public static final int LOG = 10;
    public static final int LOG2 = 11;

    private final int function;
    private final Node argument;

    public FunctionNode(int function, Node argument) {
        super();
        this.function = function;
        this.argument = argument;
    }
    @Override
    public NodeType getType() {
        return NodeType.FUNCTION;
    }

    @Override
    public double getValue() {
        return switch (function) {
            case SIN -> Math.sin(argument.getValue());
            case COS -> Math.cos(argument.getValue());
            case TAN -> Math.tan(argument.getValue());
            case ASIN -> Math.asin(argument.getValue());
            case ACOS -> Math.acos(argument.getValue());
            case ATAN -> Math.atan(argument.getValue());
            case SQRT -> Math.sqrt(argument.getValue());
            case EXP -> Math.exp(argument.getValue());
            case LN -> Math.log(argument.getValue());
            case LOG -> Math.log(argument.getValue())
                    * 0.43429448190325182765;
            case LOG2 -> Math.log(argument.getValue())
                    * 1.442695040888963407360;
            default -> throw new EvaluationException("Invalid function id " + function + "!");
        };
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
        argument.accept(visitor);
    }

    public static int stringToFunction(String str) {
        if (str.equals("sin")) return FunctionNode.SIN;
        if (str.equals("cos")) return FunctionNode.COS;
        if (str.equals("tan")) return FunctionNode.TAN;

        if (str.equals("asin")) return FunctionNode.ASIN;
        if (str.equals("acos")) return FunctionNode.ACOS;
        if (str.equals("atan")) return FunctionNode.ATAN;

        if (str.equals("sqrt")) return FunctionNode.SQRT;
        if (str.equals("exp")) return FunctionNode.EXP;

        if (str.equals("ln")) return FunctionNode.LN;
        if (str.equals("log")) return FunctionNode.LOG;
        if (str.equals("log2")) return FunctionNode.LOG2;

        throw new EvaluationException("Unexpected Function "+str+" found!");
    }
}
