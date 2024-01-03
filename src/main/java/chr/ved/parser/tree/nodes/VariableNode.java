package chr.ved.parser.tree.nodes;

import chr.ved.parser.exception.EvaluationException;
import chr.ved.parser.tree.Node;
import chr.ved.parser.tree.NodeType;
import chr.ved.parser.tree.NodeVisitor;
import lombok.Getter;

public class VariableNode implements Node {

    @Getter
    private final String name;
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

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
