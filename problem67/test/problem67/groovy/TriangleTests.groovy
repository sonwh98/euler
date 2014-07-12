package groovy

import org.junit.Before
import org.junit.Test
import static org.junit.Assert.*

/**
 * User: Sonny To son.c.to@gmail.com
 * Date: 6/20/13
 * Time: 10:26 PM
 */
class TriangleTests {
    Triangle smallTriangle

    @Before
    void setUp() {
        smallTriangle = new Triangle(file: "problem67/test/data/smallTriangle.txt") //https://projecteuler.net/problem=67
    }

    @Test
    void solutionToProblem67() {
        Triangle bigTriangle = new Triangle(file: "problem67/test/data/bigTriangle.txt")
        Integer solution = bigTriangle.findMaxSum()
        assertEquals 7273, solution
        println("solution is ${solution}")
    }

    @Test
    void findMaxSum() {
        Integer maxSum = smallTriangle.findMaxSum()
        assertEquals 23, maxSum
    }

    @Test
    void depthFirstTraversalSum() {
        Integer maxSum = 0
        smallTriangle.depthFirstTraversal { Node n ->
            maxSum = Math.max(maxSum, n.calculateWeightedSum())
        }

        assertEquals 23, maxSum
    }

    @Test
    void depthFirstTraversalLeafNodesSum() {
        Integer maxSum = 0
        smallTriangle.depthFirstTraversal { Node n ->
            if (n.isLeaf()) {
                maxSum = Math.max(maxSum, n.calculateWeightedSum())
            }
        }

        assertEquals 23, maxSum
    }

    @Test
    void testWeightedSum() {
        Node nine = smallTriangle.left.right.right
        Integer leftParentWeight = nine.leftParent.calculateWeightedSum()
        Integer rightParentWeight = nine.rightParent.calculateWeightedSum()
        assertEquals 14, leftParentWeight
        assertEquals nine.calculateWeightedSum(), Math.max(leftParentWeight, rightParentWeight) + nine.value
    }

    @Test
    void testCreateGraphFrom() {
        def (Node root, List<Node> leafNodes) = Triangle.createGraphFrom(file: "problem67/test/data/smallTriangle.txt")
        assertEquals 3, root.value

        assertEquals 7, root.left.value
        assertEquals null, root.left.leftParent

        assertEquals 3, root.left.rightParent.value

        assertEquals 4, root.right.value
        assertEquals 3, root.right.leftParent.value

        assertEquals 4, root.right.left.value
        assertEquals 4, root.right.left.rightParent.value
        assertEquals 7, root.right.left.leftParent.value

        assertEquals 2, root.left.left.value
        assertEquals null, root.left.left.leftParent
        assertEquals 7, root.left.left.rightParent.value


        assertEquals 4, root.left.right.value
        assertEquals 7, root.left.right.leftParent.value


        assertEquals 4, root.left.right.rightParent.value
        assertEquals 6, root.right.right.value

        assertEquals 8, root.left.left.left.value
        assertEquals null, root.left.left.left.leftParent
        assertEquals 2, root.left.left.left.rightParent.value

        assertEquals 5, root.left.left.right.value
        assertEquals root.left.left.right, root.right.left.left
        assertEquals 2, root.left.left.right.leftParent.value
        assertEquals 4, root.left.left.right.rightParent.value

        assertEquals 9, root.left.right.right.value
        assertEquals 4, root.left.right.right.leftParent.value
        assertEquals 6, root.left.right.right.rightParent.value
        assertEquals root.left.right.right.value, root.right.right.left.value

        assertEquals 3, root.right.right.right.value
        assertEquals 6, root.right.right.right.leftParent.value

        List<Integer> leafNodeValues = leafNodes.collect { Node n -> n.value }

        assertEquals([8, 5, 9, 3], leafNodeValues)
    }

    @Test
    void testGrid() {
        Map<List, Node> triangle = Triangle.createGridFrom(file: "problem67/test/data/smallTriangle.txt")
        assertEquals 3, triangle[0, 0].value
        assertEquals 7, triangle[1, 0].value
        assertEquals 6, triangle[2, 2].value
        assertEquals 8, triangle[3, 0].value
        assertEquals 5, triangle[3, 1].value
    }


}

