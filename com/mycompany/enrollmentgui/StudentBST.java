package com.mycompany.enrollmentgui;

import javax.swing.table.DefaultTableModel;

// Binary Search Tree class
class StudentBST {

    StudentNode root;

    // Insert student
    public void insert(int id, String name, String course) {
        root = insertRec(root, id, name, course);
    }

    private StudentNode insertRec(
            StudentNode root,
            int id,
            String name,
            String course) {

        if (root == null) {
            return new StudentNode(id, name, course);
        }

        if (id < root.id) {
            root.left = insertRec(root.left, id, name, course);

        } else if (id > root.id) {
            root.right = insertRec(root.right, id, name, course);

        } else {
            // Update if ID already exists
            root.name = name;
            root.course = course;
        }

        return root;
    }

    // Delete student
    public void delete(int id) {
        root = deleteRec(root, id);
    }

    private StudentNode deleteRec(StudentNode root, int id) {

        if (root == null) {
            return root;
        }

        if (id < root.id) {
            root.left = deleteRec(root.left, id);

        } else if (id > root.id) {
            root.right = deleteRec(root.right, id);

        } else {

            // Node has no left child
            if (root.left == null) {
                return root.right;
            }

            // Node has no right child
            else if (root.right == null) {
                return root.left;
            }

            // Node has two children
            root.id = minValue(root.right);
            root.right = deleteRec(root.right, root.id);
        }

        return root;
    }

    // Find minimum value
    private int minValue(StudentNode root) {

        int minv = root.id;

        while (root.left != null) {
            minv = root.left.id;
            root = root.left;
        }

        return minv;
    }

    // Search student
    public StudentNode search(int id) {
        return searchRec(root, id);
    }

    private StudentNode searchRec(StudentNode root, int id) {

        if (root == null || root.id == id) {
            return root;
        }

        if (root.id > id) {
            return searchRec(root.left, id);
        }

        return searchRec(root.right, id);
    }

    // Display students using In-Order Traversal
    public void populateTable(DefaultTableModel model) {

        model.setRowCount(0);

        inorderRec(root, model);
    }

    private void inorderRec(
            StudentNode root,
            DefaultTableModel model) {

        if (root != null) {

            inorderRec(root.left, model);

            model.addRow(
                    new Object[]{
                        root.id,
                        root.name,
                        root.course
                    }
            );

            inorderRec(root.right, model);
        }
    }
}