package com.mycompany.enrollmentgui;

// Student Node class for BST
class StudentNode {

    int id;
    String name;
    String course;

    StudentNode left, right;

    public StudentNode(int id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;

        left = right = null;
    }
}