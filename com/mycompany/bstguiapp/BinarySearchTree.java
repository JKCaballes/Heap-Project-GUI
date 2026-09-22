package com.mycompany.bstguiapp;

class BinarySearchTree {
    Node root;

    public BinarySearchTree() {
        root = null;
    }

    // Insert a key
    public void insert(int key) {
        root = insertRec(root, key);
    }

    private Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key < root.key) {
            root.left = insertRec(root.left, key);
        } else if (key > root.key) {
            root.right = insertRec(root.right, key);
        }

        return root;
    }

    // Delete a key
    public void delete(int key) {
        root = deleteRec(root, key);
    }

    private Node deleteRec(Node root, int key) {
        if (root == null) {
            return root;
        }

        if (key < root.key) {
            root.left = deleteRec(root.left, key);
        } else if (key > root.key) {
            root.right = deleteRec(root.right, key);
        } else {

            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children
            root.key = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.key);
        }

        return root;
    }

    private int minValue(Node root) {
        int minv = root.key;

        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }

        return minv;
    }

    // Preorder Traversal
    public String getPreorder() {
        StringBuilder sb = new StringBuilder();
        preorderRec(root, sb);
        return sb.toString().trim();
    }

    private void preorderRec(Node root, StringBuilder sb) {
        if (root != null) {
            sb.append(root.key).append(" ");
            preorderRec(root.left, sb);
            preorderRec(root.right, sb);
        }
    }

    // Inorder Traversal
    public String getInorder() {
        StringBuilder sb = new StringBuilder();
        inorderRec(root, sb);
        return sb.toString().trim();
    }

    private void inorderRec(Node root, StringBuilder sb) {
        if (root != null) {
            inorderRec(root.left, sb);
            sb.append(root.key).append(" ");
            inorderRec(root.right, sb);
        }
    }

    // Postorder Traversal
    public String getPostorder() {
        StringBuilder sb = new StringBuilder();
        postorderRec(root, sb);
        return sb.toString().trim();
    }

    private void postorderRec(Node root, StringBuilder sb) {
        if (root != null) {
            postorderRec(root.left, sb);
            postorderRec(root.right, sb);
            sb.append(root.key).append(" ");
        }
    }
}