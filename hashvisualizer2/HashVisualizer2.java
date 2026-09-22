package com.mycompany.hashvisualizer2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HashVisualizer2 extends JFrame {

    private static final int TABLE_SIZE = 15;

    // Hashing strategy type
    enum HashingType {
        SEPARATE_CHAINING,
        QUADRATIC_PROBING,
        DOUBLE_HASHING
    }

    private HashingType currentType = HashingType.SEPARATE_CHAINING;

    // Storage representations
    private ArrayList<Integer>[] chainingTable;
    private Integer[] openAddressingTable;

    // GUI Components
    private HashCanvas canvas;
    private JTextField inputField;
    private JComboBox<String> techniqueSelector;
    private JLabel statusLabel;

    @SuppressWarnings("unchecked")
    public HashVisualizer2() {

        setTitle("Hashing Techniques Visualizer");
        setSize(900, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize Data Structures
        chainingTable = new ArrayList[TABLE_SIZE];

        for (int i = 0; i < TABLE_SIZE; i++) {
            chainingTable[i] = new ArrayList<>();
        }

        openAddressingTable = new Integer[TABLE_SIZE];

        // Layout setup
        setLayout(new BorderLayout());

        canvas = new HashCanvas();
        add(canvas, BorderLayout.CENTER);

        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
    }

    private JPanel createControlPanel() {

        JPanel panel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 10)
        );

        panel.setBackground(new Color(240, 240, 240));

        techniqueSelector = new JComboBox<>(
                new String[]{
                        "Separate Chaining",
                        "Quadratic Probing",
                        "Double Hashing"
                }
        );

        techniqueSelector.addActionListener(e -> switchTechnique());

        inputField = new JTextField(6);

        JButton insertButton = new JButton("Insert Key");
        insertButton.addActionListener(e -> handleInsertion());

        JButton resetButton = new JButton("Clear Table");
        resetButton.addActionListener(e -> resetTable());

        statusLabel = new JLabel(
                "Select a mode and enter an integer key."
        );

        statusLabel.setForeground(Color.BLUE);

        panel.add(new JLabel("Technique:"));
        panel.add(techniqueSelector);

        panel.add(new JLabel("Key (Integer):"));
        panel.add(inputField);

        panel.add(insertButton);
        panel.add(resetButton);

        panel.add(statusLabel);

        return panel;
    }

    private int primaryHash(int key) {
        return Math.abs(key) % TABLE_SIZE;
    }

    private int secondaryHash(int key) {

        // Must return a value co-prime with table size
        // A common formula:
        // R - (key % R), where R is a smaller prime.

        return 11 - (Math.abs(key) % 11);
    }

    private void handleInsertion() {

        String inputText = inputField.getText().trim();

        if (inputText.isEmpty()) {
            statusLabel.setText("Please enter a valid number!");
            return;
        }

        int key;

        try {
            key = Integer.parseInt(inputText);

        } catch (NumberFormatException e) {

            statusLabel.setText(
                    "Error: Enter integers only."
            );

            return;
        }

        int hash1 = primaryHash(key);

        switch (currentType) {

            case SEPARATE_CHAINING -> {

                chainingTable[hash1].add(key);

                statusLabel.setText(
                        "Inserted " + key +
                        " at Index " + hash1
                );
            }

            case QUADRATIC_PROBING -> {

                int i = 0;

                while (i < TABLE_SIZE) {

                    int index =
                            (hash1 + i * i) % TABLE_SIZE;

                    if (openAddressingTable[index] == null) {

                        openAddressingTable[index] = key;

                        statusLabel.setText(
                                "Inserted " + key +
                                " at Index " + index +
                                " (Probes: " + i + ")"
                        );

                        break;
                    }

                    i++;
                }

                if (i == TABLE_SIZE) {

                    statusLabel.setText(
                            "Table is Full / Probe loop limit reached!"
                    );
                }
            }

            case DOUBLE_HASHING -> {

                int hash2 = secondaryHash(key);

                int i = 0;

                while (i < TABLE_SIZE) {

                    int index =
                            (hash1 + i * hash2) % TABLE_SIZE;

                    if (openAddressingTable[index] == null) {

                        openAddressingTable[index] = key;

                        statusLabel.setText(
                                "Inserted " + key +
                                " at Index " + index +
                                " [H2 step = " + hash2 + "]"
                        );

                        break;
                    }

                    i++;
                }

                if (i == TABLE_SIZE) {

                    statusLabel.setText(
                            "Collision path failed to find open slot!"
                    );
                }
            }
        }

        inputField.setText("");
        canvas.repaint();
    }

    private void switchTechnique() {

        int index = techniqueSelector.getSelectedIndex();

        if (index == 0) {

            currentType = HashingType.SEPARATE_CHAINING;

        } else if (index == 1) {

            currentType = HashingType.QUADRATIC_PROBING;

        } else {

            currentType = HashingType.DOUBLE_HASHING;
        }

        resetTable();
    }

    private void resetTable() {

        for (int i = 0; i < TABLE_SIZE; i++) {

            chainingTable[i].clear();
            openAddressingTable[i] = null;
        }

        statusLabel.setText(
                "Table cleared. Switched to "
                + techniqueSelector.getSelectedItem()
        );

        canvas.repaint();
    }

    // Inner Canvas Component handling custom graph drawings
    private class HashCanvas extends JPanel {

        public HashCanvas() {
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setFont(
                    new Font("Arial", Font.BOLD, 16)
            );

            g2.drawString(
                    "Current Hashing Mode: "
                    + currentType.toString(),
                    30,
                    40
            );

            int startY = 80;
            int boxHeight = 35;
            int boxWidth = 60;
            int gap = 8;

            for (int i = 0; i < TABLE_SIZE; i++) {

                int y =
                        startY + i * (boxHeight + gap);

                // Draw Table Index labels
                g2.setColor(Color.DARK_GRAY);

                g2.setFont(
                        new Font("Arial", Font.PLAIN, 14)
                );

                g2.drawString(
                        "Index [" + i + "]:",
                        30,
                        y + 28
                );

                // Draw primary array slots
                g2.setColor(
                        new Color(220, 230, 242)
                );

                g2.fillRect(
                        110,
                        y,
                        boxWidth,
                        boxHeight
                );

                g2.setColor(Color.BLUE);

                g2.drawRect(
                        110,
                        y,
                        boxWidth,
                        boxHeight
                );

                // Draw structures matching the chosen algorithm
                if (currentType ==
                        HashingType.SEPARATE_CHAINING) {

                    ArrayList<Integer> chain =
                            chainingTable[i];

                    int currentX =
                            110 + boxWidth + 30;

                    for (int j = 0;
                         j < chain.size();
                         j++) {

                        // Draw connection link pointer arrows
                        g2.setColor(Color.RED);

                        g2.drawLine(
                                currentX - 30,
                                y + boxHeight / 2,
                                currentX,
                                y + boxHeight / 2
                        );

                        g2.drawLine(
                                currentX,
                                y + boxHeight / 2,
                                currentX - 8,
                                y + boxHeight / 2 - 5
                        );

                        g2.drawLine(
                                currentX,
                                y + boxHeight / 2,
                                currentX - 8,
                                y + boxHeight / 2 + 5
                        );

                        // Draw Linked element bucket node
                        g2.setColor(
                                new Color(230, 245, 230)
                        );

                        g2.fillRect(
                                currentX,
                                y,
                                boxWidth,
                                boxHeight
                        );

                        g2.setColor(
                                new Color(0, 128, 0)
                        );

                        g2.drawRect(
                                currentX,
                                y,
                                boxWidth,
                                boxHeight
                        );

                        g2.setColor(Color.BLACK);

                        g2.drawString(
                                String.valueOf(chain.get(j)),
                                currentX + 25,
                                y + 28
                        );

                        currentX += boxWidth + 30;
                    }

                } else {

                    // Open addressing styles
                    Integer val =
                            openAddressingTable[i];

                    if (val != null) {

                        g2.setColor(Color.BLACK);

                        g2.setFont(
                                new Font(
                                        "Arial",
                                        Font.BOLD,
                                        14
                                )
                        );

                        g2.drawString(
                                String.valueOf(val),
                                110 + 25,
                                y + 28
                        );

                    } else {

                        g2.setColor(Color.LIGHT_GRAY);

                        g2.drawString(
                                "null",
                                110 + 25,
                                y + 28
                        );
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new HashVisualizer2().setVisible(true)
        );
    }
}