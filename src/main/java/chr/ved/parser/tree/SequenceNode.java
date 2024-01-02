package chr.ved.parser.tree;

import java.util.LinkedList;

public abstract class SequenceNode implements Node {

    protected LinkedList<Term> terms;

    public SequenceNode() {
        this.terms = new LinkedList<>();
    }

    public SequenceNode(Node node, boolean positive) {
        this.terms = new LinkedList<>();
        this.terms.add(new Term(positive, node));
    }

    public void add(Node node, boolean positive) {
        this.terms.add(new Term(positive, node));
    }
}
