package chr.ved.parser.tree.nodes;

import chr.ved.parser.tree.Node;
import org.junit.jupiter.api.Test;

import static chr.ved.parser.tree.nodes.FunctionNode.SQRT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SequenceNodeTest {

    @Test
    void ett() {
        //3*2^4 + sqrt(1+3)

        //1+3
        AdditionNode innerSum = new AdditionNode();
        innerSum.add(new ConstantNode("1"), true);
        innerSum.add(new ConstantNode("3"), true);

        //sqrt(1+3)
        Node sqrt = new FunctionNode(SQRT, innerSum);

        //2^4
        Node expo = new ExponentiationNode(
                new ConstantNode("2"),
                new ConstantNode("4"));

        //3*2^4
        MultiplicationNode prod = new MultiplicationNode();
        prod.add(new ConstantNode("3"), true);
        prod.add(expo, true);

        //3*2^4 + sqrt(1+3)
        AdditionNode expression = new AdditionNode();
        expression.add(prod, true);
        expression.add(sqrt, true);

        assertEquals(50.0, expression.getValue());
    }

}