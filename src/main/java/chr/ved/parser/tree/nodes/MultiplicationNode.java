package chr.ved.parser.tree.nodes;

import chr.ved.parser.tree.Node;
import chr.ved.parser.tree.NodeType;
import chr.ved.parser.tree.NodeVisitor;

public class MultiplicationNode extends SequenceNode {

    public MultiplicationNode() { super(); }

    public MultiplicationNode(Node node, boolean positive) {
        super(node, positive);
    }
    @Override
    public NodeType getType() {
        return NodeType.MULTIPLICATION;
    }

    @Override
    public double getValue() {
        double prod = 1.0;
        for (Term t : terms) {
            if (t.isPositive())
                prod *= t.getExpression().getValue();
            else
                prod /= t.getExpression().getValue();
        }
        return prod;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
        for (Term t: terms)
            t.getExpression().accept(visitor);
    }
}
