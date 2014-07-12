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
    Triangle triangle

    @Before
    void setUp() {
        triangle = new Triangle(file: "problem67/test/data/smallTriangle.txt") //https://projecteuler.net/problem=67
    }

    @Test
    void findMaxSum() {
        Integer maxSum = triangle.findMaxSum()
        assertEquals 23, maxSum
    }

    @Test
    void depthFirstTraversalSum() {
        Integer maxSum = 0
        triangle.depthFirstTraversal { Node n ->
            maxSum = Math.max(maxSum, n.calculateWeightedSum())
        }

        assertEquals 23, maxSum
    }

    @Test
    void depthFirstTraversalLeafNodesSum() {
        Integer maxSum = 0
        triangle.depthFirstTraversal { Node n ->
            if (n.isLeaf()) {
                maxSum = Math.max(maxSum, n.calculateWeightedSum())
            }
        }

        assertEquals 23, maxSum
    }

    @Test
    void testWeightedSum() {
        Node nine = triangle.left.right.right
        Integer leftParentWeight = nine.leftParent.calculateWeightedSum()
        Integer rightParentWeight = nine.rightParent.calculateWeightedSum()
        assertEquals 14, leftParentWeight
        assertEquals nine.calculateWeightedSum(), Math.max(leftParentWeight, rightParentWeight) + nine.value
    }

    @Test
    void testCreateGraph() {
        assertEquals 3, triangle.value

        assertEquals 7, triangle.left.value
        assertEquals null, triangle.left.leftParent

        assertEquals 3, triangle.left.rightParent.value

        assertEquals 4, triangle.right.value
        assertEquals 3, triangle.right.leftParent.value

        assertEquals 4, triangle.right.left.value
        assertEquals 4, triangle.right.left.rightParent.value
        assertEquals 7, triangle.right.left.leftParent.value

        assertEquals 2, triangle.left.left.value
        assertEquals null, triangle.left.left.leftParent
        assertEquals 7, triangle.left.left.rightParent.value


        assertEquals 4, triangle.left.right.value
        assertEquals 7, triangle.left.right.leftParent.value


        assertEquals 4, triangle.left.right.rightParent.value
        assertEquals 6, triangle.right.right.value

        assertEquals 8, triangle.left.left.left.value
        assertEquals null, triangle.left.left.left.leftParent
        assertEquals 2, triangle.left.left.left.rightParent.value

        assertEquals 5, triangle.left.left.right.value
        assertEquals triangle.left.left.right, triangle.right.left.left
        assertEquals 2, triangle.left.left.right.leftParent.value
        assertEquals 4, triangle.left.left.right.rightParent.value

        assertEquals 9, triangle.left.right.right.value
        assertEquals 4, triangle.left.right.right.leftParent.value
        assertEquals 6, triangle.left.right.right.rightParent.value
        assertEquals triangle.left.right.right.value, triangle.right.right.left.value

        assertEquals 3, triangle.right.right.right.value
        assertEquals 6, triangle.right.right.right.leftParent.value

        List<Integer> leafNodeValues = triangle.leafNodes.collect { Node n -> n.value }

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

