package groovy

/**
 * User: son.c.to@gmail.com
 * Date: 6/24/13
 * Time: 8:52 PM
 */
class Triangle {
    @Delegate
    Node root
    List<Node> leafNodes

    Triangle(Map params) {
        (root, leafNodes) = createGraphFrom(file: params.file)
    }

    Integer findMaxSum() {
        leafNodes.inject(0) { Integer maxSum, Node n ->
            Math.max(maxSum, n.calculateWeightedSum())
        }
    }

    static Map createGridFrom(Map map) {
        String triangleFile = map.file
        List<List<Integer>> triangle = new File(triangleFile).collect { String line ->
            line = line.trim()
            if (!line.empty) {
                line.split(/\s+/).collect { it.toInteger() }
            }
        }.findAll()

        Map grid = [:]
        for (int row = 0; row < triangle.size(); row++) {
            for (int col = 0; col < triangle[row].size(); col++) {
                grid[row, col] = new Node(value: triangle[row][col])
            }
        }
        return grid
    }

    static List createGraphFrom(Map params) {
        String triangleFile = params.file
        Map triangle = createGridFrom(file: triangleFile)
        List leafNodes = []
        Set coordinates = triangle.keySet()
        coordinates.each { List tuple ->
            int row = tuple[0]
            int col = tuple[1]
            Node node = triangle[tuple]
            if (col == 0) {
                node.leftParent = null
            } else {
                node.leftParent = triangle[row - 1, Math.max(col - 1, 0)]
            }
            node.rightParent = triangle[row - 1, col]
            node.left = triangle[row + 1, col]
            node.right = triangle[row + 1, col + 1]
            if (node.isLeaf()) {
                leafNodes << node
            }
        }
        Node root = triangle[0, 0]
        return [root, leafNodes]
    }
}
