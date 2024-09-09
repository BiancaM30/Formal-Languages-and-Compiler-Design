package binary_search_tree;

import java.util.LinkedList;
import java.util.Queue;

public class BSTNodeConst {
    String value;
    BSTNodeConst leftChild;
    BSTNodeConst rightChild;

    public BSTNodeConst(String val) {
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

    public static BSTNodeConst insert(String value, BSTNodeConst current) {
        if (current == null) {
            BSTNodeConst newNode = new BSTNodeConst(value);
            return newNode;
        } else {
            try {
                double x = Double.parseDouble(value);
                if (x < Double.parseDouble(current.value))
                    current.leftChild = insert(value, current.leftChild);
                else
                    current.rightChild = insert(value, current.rightChild);
            } catch (Exception ex) {
                System.out.println("Is not double!!!");
            }
        }
        return current;
    }

    public static boolean search(String key, BSTNodeConst current) {
        if (current == null)
            return false;
        if (current.value.equals(key))
            return true;
        else {
            try {
                double x = Double.parseDouble(key);
                if (x < Double.parseDouble(current.value))
                    return search(key, current.leftChild);
                else
                    return search(key, current.rightChild);
            } catch (Exception ex) {
                System.out.println("Is not double!!!");
                return false;
            }
        }
    }


    // return element if exists
    public BSTNodeConst levelOrder(BSTNodeConst current, String toFind) {
        if (current == null)
            return null;
        Queue<BSTNodeConst> q = new LinkedList<BSTNodeConst>();
        q.add(current);
        while (q.isEmpty() == false) {
            BSTNodeConst temp = q.remove();
            //if (temp.value.equals(toFind)) return temp;
            if(toFind.contains("b") || temp.value.contains("b")) return temp;
            else if (Double.parseDouble(toFind) == Double.parseDouble(temp.value)) return temp;
            if (temp.leftChild != null)
                q.add(temp.leftChild);
            if (temp.rightChild != null)
                q.add(temp.rightChild);
        }
        return null;
    }
}