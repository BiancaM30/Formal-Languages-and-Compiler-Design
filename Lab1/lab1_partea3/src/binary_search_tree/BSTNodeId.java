package binary_search_tree;

import java.util.LinkedList;
import java.util.Queue;

public class BSTNodeId {
     String value;
    BSTNodeId leftChild;
    BSTNodeId rightChild;

    public BSTNodeId(String val) {
        this.value = val;
        this.leftChild = null;
        this.rightChild = null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BSTNodeId getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BSTNodeId leftChild) {
        this.leftChild = leftChild;
    }

    public BSTNodeId getRightChild() {
        return rightChild;
    }

    public void setRightChild(BSTNodeId rightChild) {
        this.rightChild = rightChild;
    }

    public static BSTNodeId insert(String value, BSTNodeId current) {
        if (current == null) {
            BSTNodeId newNode = new BSTNodeId(value);
            return newNode;
        } else {
            if (value.compareTo(current.value)<0)
                current.leftChild = insert(value, current.leftChild);
            else
                current.rightChild = insert(value, current.rightChild);
        }
        return current;
    }

    public static boolean search(String key, BSTNodeId current) {
        if (current == null)
            return false;
        if (current.value.equals(key))
            return true;
        else {
            if (key.compareTo(current.value)<0)
                return search(key, current.leftChild);
            else
                return search(key, current.rightChild);
        }
    }


    // return element if exists
    public BSTNodeId levelOrder(BSTNodeId current, String toFind) {
        if (current == null)
            return null;
        Queue<BSTNodeId> q = new LinkedList<BSTNodeId>();
        q.add(current);
        while (q.isEmpty() == false) {
            BSTNodeId temp = q.remove();
            if (temp.value.equals(toFind)) return temp;

            if (temp.leftChild != null)
                q.add(temp.leftChild);
            if (temp.rightChild != null)
                q.add(temp.rightChild);
        }
        return null;
    }
}