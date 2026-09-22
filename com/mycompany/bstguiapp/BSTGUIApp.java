package com.mycompany.bstguiapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class BSTGUIApp extends JFrame {

    private BinarySearchTree bst;
    private JTextField inputField;
    private JLabel preorderLabel, inorderLabel, postorderLabel;

    public BSTGUIApp() {
        bst = new BinarySearchTree();
        createUI();
    }

    private void createUI() {

        setTitle("BST Traversal Utility");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Control Panel
        JPanel controlPanel = new JPanel(new FlowLayout());

        JLabel inputLabel = new JLabel("Enter Integer:");
        inputField = new JTextField(6);

        JButton insertButton = new JButton("Insert");
        JButton deleteButton = new JButton("Delete");

        controlPanel.add(inputLabel);
        controlPanel.add(inputField);
        controlPanel.add(insertButton);
        controlPanel.add(deleteButton);

        add(controlPanel, BorderLayout.NORTH);

        // Center Display Panel
        JPanel displayPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        displayPanel.setBorder(
            BorderFactory.createTitledBorder("Tree Traversals")
        );

        preorderLabel = new JLabel("Preorder: ");
        inorderLabel = new JLabel("Inorder: ");
        postorderLabel = new JLabel("Postorder: ");

        Font labelFont = new Font("Arial", Font.BOLD, 13);

        preorderLabel.setFont(labelFont);
        inorderLabel.setFont(labelFont);
        postorderLabel.setFont(labelFont);

        displayPanel.add(preorderLabel);
        displayPanel.add(inorderLabel);
        displayPanel.add(postorderLabel);

        add(displayPanel, BorderLayout.CENTER);

        // Insert Button
        insertButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                handleAction(true);
            }
        });

        // Delete Button
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                handleAction(false);
            }
        });
    }

    private void handleAction(boolean isInsert) {

        try {
            int value = Integer.parseInt(
                inputField.getText().trim()
            );

            if (isInsert) {
                bst.insert(value);
            } else {
                bst.delete(value);
            }

            updateTraversalDisplay();

            inputField.setText("");
            inputField.requestFocus();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Please enter a valid integer.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateTraversalDisplay() {

        preorderLabel.setText(
            "Preorder: " + bst.getPreorder()
        );

        inorderLabel.setText(
            "Inorder: " + bst.getInorder()
        );

        postorderLabel.setText(
            "Postorder: " + bst.getPostorder()
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new BSTGUIApp().setVisible(true);
            }
        });
    }
}