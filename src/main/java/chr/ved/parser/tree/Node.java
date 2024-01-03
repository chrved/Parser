package chr.ved.parser.tree;

public interface Node {
    NodeType getType();
    double getValue();

    void accept(NodeVisitor visitor);
}
