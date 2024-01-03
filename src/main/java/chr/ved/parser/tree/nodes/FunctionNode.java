package chr.ved.parser.tree.nodes;

import chr.ved.parser.exception.EvaluationException;
import chr.ved.parser.tree.Node;
import chr.ved.parser.tree.NodeType;

public class FunctionNode extends SequenceNode {
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

    private int function;
    private Node argument;

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
        switch (function) {
            case SIN:  return Math.sin(argument.getValue());
            case COS:  return Math.cos(argument.getValue());
            case TAN:  return Math.tan(argument.getValue());
            case ASIN: return Math.asin(argument.getValue());
            case ACOS: return Math.acos(argument.getValue());
            case ATAN: return Math.atan(argument.getValue());
            case SQRT: return Math.sqrt(argument.getValue());
            case EXP:  return Math.exp(argument.getValue());
            case LN:   return Math.log(argument.getValue());
            case LOG:  return Math.log(argument.getValue())
                    * 0.43429448190325182765;
            case LOG2: return Math.log(argument.getValue())
                    * 1.442695040888963407360;
        }
        throw new EvaluationException("Invalid function id "+function+"!");
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

    public static String getAllFunctions() {
        return "sin|cos|tan|asin|acos|atan|sqrt|exp|ln|log|log2";
    }
}
