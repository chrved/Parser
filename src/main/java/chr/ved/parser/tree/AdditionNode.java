package chr.ved.parser.tree;

public class AdditionNode extends SequenceNode {

    AdditionNode() { super();}
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
