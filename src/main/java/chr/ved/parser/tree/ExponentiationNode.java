package chr.ved.parser.tree;

public class ExponentiationNode extends SequenceNode {

    private final Node base;
    private final Node exponent;

    public ExponentiationNode(Node base, Node exponent) {
        this.base = base;
        this.exponent = exponent;
    }
    @Override
    public NodeType getType() {
        return NodeType.EXPONENTIATION;
    }

    @Override
    public double getValue() {
        return Math.pow(base.getValue(), exponent.getValue());
    }
}
