package problem67.groovy

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
        triangle = new Triangle(file: "test/data/smallTriangle.txt")
    }

    @Test
    void findMaxSum() {
        Integer maxSum = triangle.findMaxSum()
        assertEquals 27, maxSum
    }

    @Test
    void depthFirstTraversalSum() {
        Integer maxSum = 0
        triangle.depthFirstTraversal { groovy.util.Node n ->
            maxSum = Math.max(maxSum, n.calculateWeightedSum())
        }

        assertEquals 27, maxSum
    }

    @Test
    void depthFirstTraversalLeafNodesSum() {
        Integer maxSum = 0
        triangle.depthFirstTraversal { groovy.util.Node n ->
            if (n.isLeaf()) {
                maxSum = Math.max(maxSum, n.calculateWeightedSum())
            }
        }

        assertEquals 27, maxSum
    }

    @Test
    void testWeightedSum() {
        groovy.util.Node seven = triangle.left.right.left
        Integer leftParentWeight = seven.leftParent.calculateWeightedSum()
        Integer rightParentWeight = seven.rightParent.calculateWeightedSum()
        assertEquals 18, leftParentWeight
        assertEquals seven.calculateWeightedSum(), Math.max(leftParentWeight, rightParentWeight) + seven.value
    }

    @Test
    void testCreateGraph() {
        assertEquals 5, triangle.value

        assertEquals 9, triangle.left.value
        assertEquals null, triangle.left.leftParent

        assertEquals 5, triangle.left.rightParent.value

        assertEquals 6, triangle.right.value
        assertEquals 5, triangle.right.leftParent.value
        assertEquals 5, triangle.right.leftParent.value

        assertEquals 6, triangle.right.left.value
        assertEquals 6, triangle.right.left.rightParent.value
        assertEquals 9, triangle.right.left.leftParent.value

        assertEquals 4, triangle.left.left.value
        assertEquals null, triangle.left.left.leftParent
        assertEquals 9, triangle.left.left.rightParent.value


        assertEquals 6, triangle.left.right.value
        assertEquals 9, triangle.left.right.leftParent.value


        assertEquals 9, triangle.left.right.leftParent.value
        assertEquals 8, triangle.right.right.value

        assertEquals 0, triangle.left.left.left.value
        assertEquals null, triangle.left.left.left.leftParent
        assertEquals 4, triangle.left.left.left.rightParent.value

        assertEquals 7, triangle.left.left.right.value
        assertEquals triangle.left.left.right, triangle.right.left.left
        assertEquals 4, triangle.left.left.right.leftParent.value
        assertEquals 6, triangle.left.left.right.rightParent.value

        assertEquals 1, triangle.left.right.right.value
        assertEquals 6, triangle.left.right.right.leftParent.value
        assertEquals 8, triangle.left.right.right.rightParent.value
        assertEquals triangle.left.right.right.value, triangle.right.right.left.value

        assertEquals 5, triangle.right.right.right.value
        assertEquals 8, triangle.right.right.right.leftParent.value

        List<Integer> leafNodeValues = triangle.leafNodes.collect { groovy.util.Node n -> n.value }

        assertEquals([0, 7, 1, 5], leafNodeValues)
    }

    @Test
    void testGrid() {
        Map<List, groovy.util.Node> triangle = Triangle.createGridFrom(file: "triangle_test_4rows.txt")
        assertEquals 5, triangle[0, 0].value
        assertEquals 9, triangle[1, 0].value
        assertEquals 8, triangle[2, 2].value
        assertEquals 0, triangle[3, 0].value
        assertEquals 7, triangle[3, 1].value
    }


    @Test
    void errorHandling() {
        def badInputTestFiles = ["triangle_test_zero_rows.txt", "triangle_test_nonumeric.txt"]
        badInputTestFiles.each { String file ->
            try {
                Triangle.createGraphFrom(file: file)
            } catch (NumberFormatException ex) {
                println "${file} has have non-integer values. Cannot convert to Integer"
                println ex.message
            }
        }

        def (groovy.util.Node root, List leafNodes) = Triangle.createGraphFrom(file: "triangle_test_extra_lines_between_rows.txt")
        assertNotNull root
        assertNotNull leafNodes
        assertEquals 5, root.value
    }
}

