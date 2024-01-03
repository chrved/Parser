package chr.ved.parser.tree;

import chr.ved.parser.tree.nodes.*;

public class SetVariable implements NodeVisitor {
    private final String name;
    private final double value;

    public SetVariable(String name, double value) {
        super();
        this.name = name;
        this.value = value;
    }
    @Override
    public void visit(VariableNode node) {
        if (node.getName().equals(name))
            node.setValue(value);
    }

    @Override
    public void visit(ConstantNode node) {}

    @Override
    public void visit(AdditionNode node) {}

    @Override
    public void visit(MultiplicationNode node) {}

    @Override
    public void visit(ExponentiationNode node) {}

    @Override
    public void visit(FunctionNode node) {}
}
