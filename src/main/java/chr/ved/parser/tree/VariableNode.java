package chr.ved.parser.tree;

import chr.ved.parser.exception.EvaluationException;

public class VariableNode implements Node {

    private String name;
    private Double value;

    public VariableNode(String name) {
        this.name = name;
        value = null;
    }

    public void setValue(double val) {
        this.value = val;
    }

    @Override
    public NodeType getType() {
        return NodeType.VARIABLE;
    }

    @Override
    public double getValue() {
        if (value == null) {
            throw new EvaluationException("Variable '"
                    + name + "' was not initialized.");
        }
        return value;
    }
}
