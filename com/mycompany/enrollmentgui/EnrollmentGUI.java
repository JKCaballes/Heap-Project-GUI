package com.mycompany.enrollmentgui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main GUI Application
public class EnrollmentGUI extends JFrame {

    private StudentBST bst = new StudentBST();

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtCourse;

    private JTable table;
    private DefaultTableModel tableModel;

    public EnrollmentGUI() {

        setTitle("Enrollment System - Binary Search Tree");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================
        // FORM PANEL
        // =========================

        JPanel panelForm =
                new JPanel(new GridLayout(4, 2, 5, 5));

        panelForm.setBorder(
                BorderFactory.createTitledBorder(
                        "Student Details"
                )
        );

        panelForm.add(new JLabel("Student ID:"));

        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Student Name:"));

        txtName = new JTextField();
        panelForm.add(txtName);

        panelForm.add(new JLabel("Course:"));

        txtCourse = new JTextField();
        panelForm.add(txtCourse);

        // =========================
        // BUTTONS PANEL
        // =========================

        JPanel panelButtons =
                new JPanel(new FlowLayout());

        JButton btnAdd = new JButton("Add");
        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Delete");
        JButton btnDisplay =
                new JButton("Display (In-Order)");

        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        panelButtons.add(btnDisplay);

        // =========================
        // TABLE
        // =========================

        tableModel =
                new DefaultTableModel(
                        new String[]{
                            "ID",
                            "Name",
                            "Course"
                        },
                        0
                );

        table = new JTable(tableModel);

        JScrollPane scrollPane =
                new JScrollPane(table);

        // =========================
        // ADD COMPONENTS TO FRAME
        // =========================

        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // =========================
        // ADD BUTTON
        // =========================

        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    int id =
                            Integer.parseInt(
                                    txtId.getText().trim()
                            );

                    String name =
                            txtName.getText().trim();

                    String course =
                            txtCourse.getText().trim();

                    if (name.isEmpty() ||
                            course.isEmpty()) {

                        JOptionPane.showMessageDialog(
                                null,
                                "Fill all fields!"
                        );

                        return;
                    }

                    bst.insert(
                            id,
                            name,
                            course
                    );

                    bst.populateTable(tableModel);

                    clearFields();

                    JOptionPane.showMessageDialog(
                            null,
                            "Student Added Successfully!"
                    );

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid ID format!"
                    );
                }
            }
        });

        // =========================
        // EDIT BUTTON
        // =========================

        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    int id =
                            Integer.parseInt(
                                    txtId.getText().trim()
                            );

                    StudentNode node =
                            bst.search(id);

                    if (node != null) {

                        node.name =
                                txtName.getText().trim();

                        node.course =
                                txtCourse.getText().trim();

                        bst.populateTable(tableModel);

                        clearFields();

                        JOptionPane.showMessageDialog(
                                null,
                                "Student Updated Successfully!"
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                null,
                                "Student ID not found!"
                        );
                    }

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Enter valid ID to edit!"
                    );
                }
            }
        });

        // =========================
        // DELETE BUTTON
        // =========================

        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    int id =
                            Integer.parseInt(
                                    txtId.getText().trim()
                            );

                    if (bst.search(id) != null) {

                        bst.delete(id);

                        bst.populateTable(tableModel);

                        clearFields();

                        JOptionPane.showMessageDialog(
                                null,
                                "Student Deleted Successfully!"
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                null,
                                "ID not found!"
                        );
                    }

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Enter valid ID to delete!"
                    );
                }
            }
        });

        // =========================
        // DISPLAY BUTTON
        // =========================

        btnDisplay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                bst.populateTable(tableModel);
            }
        });
    }

    // =========================
    // CLEAR INPUT FIELDS
    // =========================

    private void clearFields() {

        txtId.setText("");
        txtName.setText("");
        txtCourse.setText("");
    }

    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                new Runnable() {

                    @Override
                    public void run() {

                        new EnrollmentGUI()
                                .setVisible(true);
                    }
                }
        );
    }
}