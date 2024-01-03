package chr.ved.parser.tree.nodes;

import chr.ved.parser.tree.Node;
import chr.ved.parser.tree.NodeType;

public class AdditionNode extends SequenceNode {

    public AdditionNode() { super();}
    public AdditionNode(Node node, boolean positive) {
        super(node, positive);
    }
    @Override
    public NodeType getType() {
        return NodeType.ADDITION;
    }

    @Override
    public double getValue() {
        double sum = 0.0;
        for (Term t : terms) {
            if (t.positive)
                sum += t.expression.getValue();
            else
                sum -= t.expression.getValue();
        }
        return sum;
    }
}
