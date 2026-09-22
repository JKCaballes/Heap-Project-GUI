package com.mycompany.restaurantheapsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.PriorityQueue;

// ==========================================
// FOOD ORDER CLASS
// ==========================================

class FoodOrder implements Comparable<FoodOrder> {

    private String customerName;
    private String foodItem;
    private int priorityTime;

    // Constructor
    public FoodOrder(
            String customerName,
            String foodItem,
            int priorityTime
    ) {

        this.customerName = customerName;
        this.foodItem = foodItem;
        this.priorityTime = priorityTime;
    }

    // Get customer name
    public String getCustomerName() {
        return customerName;
    }

    // Get food item
    public String getFoodItem() {
        return foodItem;
    }

    // Get preparation time
    public int getPriorityTime() {
        return priorityTime;
    }

    // ==========================================
    // MIN-HEAP COMPARISON
    // Lowest preparation time = highest priority
    // ==========================================

    @Override
    public int compareTo(FoodOrder other) {

        return Integer.compare(
                this.priorityTime,
                other.priorityTime
        );
    }
}


// ==========================================
// MAIN RESTAURANT HEAP SYSTEM
// ==========================================

public class RestaurantHeapSystem extends JFrame {

    // ==========================================
    // PRIORITY QUEUE / MIN-HEAP
    // ==========================================

    private PriorityQueue<FoodOrder> orderHeap;

    // ==========================================
    // GUI COMPONENTS
    // ==========================================

    private JTextField nameField;
    private JTextField itemField;
    private JTextField timeField;

    private JTable orderTable;

    private DefaultTableModel tableModel;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public RestaurantHeapSystem() {

        // Create Priority Queue
        orderHeap = new PriorityQueue<>();

        // Window title
        setTitle(
                "Restaurant Priority Order System (Heap-based)"
        );

        // Window size
        setSize(750, 500);

        // Close program when window is closed
        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        // Center window
        setLocationRelativeTo(null);

        // Main layout
        setLayout(
                new BorderLayout(10, 10)
        );


        // ==========================================
        // TOP PANEL - INPUT FORM
        // ==========================================

        JPanel inputPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                4,
                                8,
                                8
                        )
                );

        inputPanel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        inputPanel.setBackground(
                new Color(
                        245,
                        245,
                        245
                )
        );


        // Text fields
        nameField = new JTextField();

        itemField = new JTextField();

        timeField = new JTextField();


        // Labels
        inputPanel.add(
                new JLabel("Customer Name:")
        );

        inputPanel.add(
                new JLabel("Food Item:")
        );

        inputPanel.add(
                new JLabel(
                        "Prep Time (Mins - Heap Key):"
                )
        );

        inputPanel.add(
                new JLabel("Action:")
        );


        // Text fields
        inputPanel.add(nameField);

        inputPanel.add(itemField);

        inputPanel.add(timeField);


        // Add button
        JButton addButton =
                new JButton(
                        "Add Order (Push)"
                );

        addButton.setBackground(
                new Color(
                        46,
                        139,
                        87
                )
        );

        addButton.setForeground(
                Color.WHITE
        );

        inputPanel.add(addButton);


        // Add input panel to top
        add(
                inputPanel,
                BorderLayout.NORTH
        );


        // ==========================================
        // CENTER PANEL - ORDER TABLE
        // ==========================================

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "Customer",
                                "Food Item",
                                "Prep Time (Mins)"
                        },
                        0
                );

        orderTable =
                new JTable(tableModel);

        add(
                new JScrollPane(orderTable),
                BorderLayout.CENTER
        );


        // ==========================================
        // BOTTOM PANEL - PROCESS ORDER
        // ==========================================

        JPanel controlPanel =
                new JPanel();

        controlPanel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        // Process button
        JButton processButton =
                new JButton(
                        "Process Next Quick Order (Pop Heap)"
                );

        processButton.setBackground(
                new Color(
                        220,
                        20,
                        60
                )
        );

        processButton.setForeground(
                Color.WHITE
        );


        controlPanel.add(
                processButton
        );


        // Add bottom panel
        add(
                controlPanel,
                BorderLayout.SOUTH
        );


        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        addButton.addActionListener(
                e -> addOrderToHeap()
        );

        processButton.addActionListener(
                e -> processNextOrder()
        );
    }


    // ==========================================
    // ADD ORDER TO HEAP
    // ==========================================

    private void addOrderToHeap() {

        // Get input values
        String name =
                nameField.getText().trim();

        String item =
                itemField.getText().trim();

        String timeStr =
                timeField.getText().trim();


        // Check if fields are empty
        if (
                name.isEmpty()
                ||
                item.isEmpty()
                ||
                timeStr.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        try {

            // Convert preparation time to integer
            int time =
                    Integer.parseInt(timeStr);


            // Create new food order
            FoodOrder newOrder =
                    new FoodOrder(
                            name,
                            item,
                            time
                    );


            // Add order to PriorityQueue / Heap
            orderHeap.add(newOrder);


            // Refresh table
            refreshTable();


            // Clear input fields
            nameField.setText("");

            itemField.setText("");

            timeField.setText("");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Prep time must be a valid number!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // PROCESS NEXT ORDER
    // ==========================================

    private void processNextOrder() {

        // Check if heap is empty
        if (orderHeap.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No orders left in the queue!",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }


        // Remove minimum/root element
        FoodOrder nextOrder =
                orderHeap.poll();


        // Display processed order
        JOptionPane.showMessageDialog(
                this,

                "Now Preparing:\n"
                + "Customer: "
                + nextOrder.getCustomerName()
                + "\nItem: "
                + nextOrder.getFoodItem()
                + "\nPrep Time: "
                + nextOrder.getPriorityTime()
                + " mins",

                "Order Processed",

                JOptionPane.INFORMATION_MESSAGE
        );


        // Refresh table
        refreshTable();
    }


    // ==========================================
    // REFRESH TABLE
    // ==========================================

    private void refreshTable() {

        // Clear table
        tableModel.setRowCount(0);


        // Add current heap orders to table
        for (
                FoodOrder order
                : orderHeap
        ) {

            tableModel.addRow(
                    new Object[]{
                            order.getCustomerName(),
                            order.getFoodItem(),
                            order.getPriorityTime()
                    }
            );
        }
    }


    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    new RestaurantHeapSystem()
                            .setVisible(true);
                }
        );
    }
}