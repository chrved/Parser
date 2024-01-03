package chr.ved.parser.tree.nodes;

import chr.ved.parser.tree.Node;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Term {
    private final boolean positive;
    private final Node expression;

    public Term(boolean positive, Node expression) {
        this.positive = positive;
        this.expression = expression;
    }
}
