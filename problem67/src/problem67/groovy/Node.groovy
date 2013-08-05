package problem67.groovy

/**
 * User: son.c.to@gmail.com
 * Date: 6/22/13
 * Time: 5:39 AM
 */
class Node {
    Integer value
    Node leftParent
    Node rightParent
    Node left
    Node right

    Integer weight

    void depthFirstTraversal(Closure visitor) {
        if (left) {
            visitor(left)
            left.depthFirstTraversal(visitor)
        }
        if (right) {
            visitor(right)
            right.depthFirstTraversal(visitor)
        }
    }

    Integer calculateWeightedSum() {
        if (weight != null) {
            return weight
        }

        if (rightParent == null && leftParent == null) { //root node
            weight = value
        } else if (leftParent != null && rightParent == null) {
            weight = leftParent.calculateWeightedSum() + value
        } else if (rightParent != null && leftParent == null) {
            weight = rightParent.calculateWeightedSum() + value
        } else if (leftParent != null && rightParent != null) {
            weight = value + Math.max(leftParent.calculateWeightedSum(), rightParent.calculateWeightedSum())
        }

        return weight
    }

    boolean isLeaf() {
        left == null && right == null
    }

    String toString() {
        value as String
    }

}
