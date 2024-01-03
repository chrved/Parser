package chr.ved.parser.tree.nodes;

import chr.ved.parser.tree.Node;
import chr.ved.parser.tree.NodeType;
import chr.ved.parser.tree.NodeVisitor;

public class ConstantNode implements Node {
    private final double value;

    public ConstantNode(String value) {
        this.value = Double.parseDouble(value);
    }

    @Override
    public NodeType getType() {
        return NodeType.CONSTANT;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
