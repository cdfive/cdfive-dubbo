package com.cdfive.learn.datastructure.tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Create a binary tree from Array.
 *
 * @author cdfive
 */
public class CreateBinaryTreeDemo {

    public static void main(String[] args) {
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

//        Node<Integer> rootNode = createTree(data.toArray(new Integer[0]));
        Node<Integer> rootNode = createTree2(data.toArray(new Integer[0]));

        System.out.println("done");
    }

    public static <T> Node<T> createTree(T[] data) {
        T first = data[0];
        Node rootNode = new Node(first);

        Queue<Node> queue = new LinkedList();
        queue.add(rootNode);

        for (int i = 1; i < data.length; i++) {
            Node node = new Node(data[i]);

            Node topNode = queue.peek();
            if (topNode != null) {
                if (topNode.getLeft() == null) {
                    topNode.setLeft(node);
                } else if (topNode.getRight() == null) {
                    topNode.setRight(node);
                    queue.poll();
                }
            }

            queue.add(node);
        }

        return rootNode;
    }

    public static <T> Node<T> createTree2(T[] data) {
        Node rootNode = null;

        Queue<Node> queue = new LinkedList();
        for (int i = 0; i < data.length; i++) {
            Node node = new Node(data[i]);

            if (i == 0) {
                rootNode = node;
            }

            Node topNode = queue.peek();
            if (topNode != null) {
                if (topNode.getLeft() == null) {
                    topNode.setLeft(node);
                } else if (topNode.getRight() == null) {
                    topNode.setRight(node);
                    queue.poll();
                }
            }

            queue.add(node);
        }

        return rootNode;
    }

    static class Node<T> {
        private T val;

        private Node left;

        private Node right;

        public Node(T val) {
            this.val = val;
        }

        public T getVal() {
            return val;
        }

        public void setVal(T val) {
            this.val = val;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}
