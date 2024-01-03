package chr.ved.parser.tree.nodes;

import chr.ved.parser.tree.Node;
import chr.ved.parser.tree.NodeType;
import chr.ved.parser.tree.NodeVisitor;

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
            if (t.isPositive())
                sum += t.getExpression().getValue();
            else
                sum -= t.getExpression().getValue();
        }
        return sum;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
        for (Term t: terms)
            t.getExpression().accept(visitor);
    }
}
