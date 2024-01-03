package chr.ved.parser.tree;

import chr.ved.parser.tree.nodes.*;

public interface NodeVisitor {
    void visit(VariableNode node);
    void visit(ConstantNode node);
    void visit(AdditionNode node);
    void visit(MultiplicationNode node);
    void visit(ExponentiationNode node);
    void visit(FunctionNode node);
}
