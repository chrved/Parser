package chr.ved.parser.tree;

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
}
