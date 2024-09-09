package binary_search_tree;

import java.util.LinkedList;
import java.util.Queue;

public class BSTNodeConst {
    int value;
    BSTNodeConst leftChild;
    BSTNodeConst rightChild;

    public BSTNodeConst(int val) {
        this.value = val;
        this.leftChild = null;
        this.rightChild = null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BSTNodeConst getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BSTNodeConst leftChild) {
        this.leftChild = leftChild;
    }

    public BSTNodeConst getRightChild() {
        return rightChild;
    }

    public void setRightChild(BSTNodeConst rightChild) {
        this.rightChild = rightChild;
    }

    public static BSTNodeConst insert(int value, BSTNodeConst current) {
        if (current == null) {
            BSTNodeConst newNode = new BSTNodeConst(value);
            return newNode;
        } else {
            if (value < current.value)
                current.leftChild = insert(value, current.leftChild);
            else
                current.rightChild = insert(value, current.rightChild);
        }
        return current;
    }

    public static boolean search(int key, BSTNodeConst current) {
        if (current == null)
            return false;
        if (current.value == key)
            return true;
        else {
            if (key < current.value)
                return search(key, current.leftChild);
            else
                return search(key, current.rightChild);
        }
    }


    // return element if exists
    public BSTNodeConst levelOrder(BSTNodeConst current, int toFind) {
        if (current == null)
            return null;
        Queue<BSTNodeConst> q = new LinkedList<BSTNodeConst>();
        q.add(current);
        while (q.isEmpty() == false) {
            BSTNodeConst temp = q.remove();
            if (temp.value == toFind) return temp;

            if (temp.leftChild != null)
                q.add(temp.leftChild);
            if (temp.rightChild != null)
                q.add(temp.rightChild);
        }
        return null;
    }
}