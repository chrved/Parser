package chr.ved.parser.tree.nodes;

import chr.ved.parser.tree.Node;
import chr.ved.parser.tree.NodeType;
import chr.ved.parser.tree.NodeVisitor;

public class ExponentiationNode implements Node {

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

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
        base.accept(visitor);
        exponent.accept(visitor);
    }
}
