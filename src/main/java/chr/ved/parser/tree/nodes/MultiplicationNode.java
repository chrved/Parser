package chr.ved.parser.tree.nodes;

import chr.ved.parser.tree.Node;
import chr.ved.parser.tree.NodeType;

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
            if (t.positive)
                prod *= t.expression.getValue();
            else
                prod /= t.expression.getValue();
        }
        return prod;
    }
}
