package com.mycompany.heapgui2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HeapGUI2 extends JFrame {

    private final HeapEngine heapEngine;
    private final HeapVisualPanel visualPanel;
    private final JTextField inputField;
    private final JComboBox<String> typeComboBox;

    public HeapGUI2() {

        // =========================
        // HEAP ENGINE
        // =========================

        heapEngine = new HeapEngine(true); // Default to Min-Heap

        // =========================
        // WINDOW SETTINGS
        // =========================

        setTitle("Min/Max Heap Visualizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =========================
        // CONTROL PANEL
        // =========================

        JPanel controlPanel = new JPanel();

        controlPanel.setBackground(
                new Color(240, 242, 245)
        );

        typeComboBox =
                new JComboBox<>(
                        new String[]{
                            "Min-Heap",
                            "Max-Heap"
                        }
                );

        inputField = new JTextField(5);

        JButton insertButton =
                new JButton("Insert");

        JButton deleteButton =
                new JButton("Delete Root");

        JButton clearButton =
                new JButton("Clear");

        controlPanel.add(
                new JLabel("Heap Type:")
        );

        controlPanel.add(typeComboBox);

        controlPanel.add(
                new JLabel("Value:")
        );

        controlPanel.add(inputField);

        controlPanel.add(insertButton);

        controlPanel.add(deleteButton);

        controlPanel.add(clearButton);

        add(
                controlPanel,
                BorderLayout.NORTH
        );

        // =========================
        // VISUALIZATION PANEL
        // =========================

        visualPanel =
                new HeapVisualPanel();

        add(
                new JScrollPane(visualPanel),
                BorderLayout.CENTER
        );

        // =========================
        // HEAP TYPE SELECTION
        // =========================

        typeComboBox.addActionListener(e -> {

            boolean isMinHeap =
                    typeComboBox.getSelectedIndex() == 0;

            heapEngine.setHeapType(isMinHeap);

            visualPanel.repaint();
        });

        // =========================
        // INSERT BUTTON
        // =========================

        insertButton.addActionListener(
                e -> handleInsert()
        );

        // Pressing ENTER also inserts
        inputField.addActionListener(
                e -> handleInsert()
        );

        // =========================
        // DELETE ROOT BUTTON
        // =========================

        deleteButton.addActionListener(e -> {

            try {

                int removed =
                        heapEngine.extractRoot();

                JOptionPane.showMessageDialog(
                        this,
                        "Extracted root element: "
                                + removed
                );

                visualPanel.repaint();

            } catch (IllegalStateException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // =========================
        // CLEAR BUTTON
        // =========================

        clearButton.addActionListener(e -> {

            heapEngine.clear();

            visualPanel.repaint();
        });
    }

    // =========================
    // INSERT METHOD
    // =========================

    private void handleInsert() {

        try {

            int val =
                    Integer.parseInt(
                            inputField.getText().trim()
                    );

            heapEngine.insert(val);

            inputField.setText("");

            inputField.requestFocus();

            visualPanel.repaint();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid integer.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ==========================================
    // BACKEND: HEAP IMPLEMENTATION ENGINE
    // ==========================================

    private static class HeapEngine {

        private final ArrayList<Integer> heap =
                new ArrayList<>();

        private boolean isMinHeap;

        public HeapEngine(boolean isMinHeap) {

            this.isMinHeap = isMinHeap;
        }

        // =========================
        // CHANGE HEAP TYPE
        // =========================

        public void setHeapType(boolean isMinHeap) {

            if (this.isMinHeap != isMinHeap) {

                this.isMinHeap = isMinHeap;

                rebuildHeap();
            }
        }

        // =========================
        // GET HEAP DATA
        // =========================

        public ArrayList<Integer> getHeapData() {

            return heap;
        }

        // =========================
        // CLEAR HEAP
        // =========================

        public void clear() {

            heap.clear();
        }

        // =========================
        // INSERT VALUE
        // =========================

        public void insert(int val) {

            heap.add(val);

            siftUp(heap.size() - 1);
        }

        // =========================
        // DELETE ROOT
        // =========================

        public int extractRoot() {

            if (heap.isEmpty()) {

                throw new IllegalStateException(
                        "Heap is empty!"
                );
            }

            int rootVal =
                    heap.get(0);

            int lastVal =
                    heap.remove(
                            heap.size() - 1
                    );

            if (!heap.isEmpty()) {

                heap.set(0, lastVal);

                siftDown(0);
            }

            return rootVal;
        }

        // =========================
        // REBUILD HEAP
        // =========================

        private void rebuildHeap() {

            for (
                    int i = (heap.size() / 2) - 1;
                    i >= 0;
                    i--
            ) {

                siftDown(i);
            }
        }

        // =========================
        // SIFT UP
        // =========================

        private void siftUp(int index) {

            while (index > 0) {

                int parent =
                        (index - 1) / 2;

                if (
                        compare(
                                heap.get(index),
                                heap.get(parent)
                        )
                ) {

                    swap(index, parent);

                    index = parent;

                } else {

                    break;
                }
            }
        }

        // =========================
        // SIFT DOWN
        // =========================

        private void siftDown(int index) {

            int size =
                    heap.size();

            while (
                    (2 * index + 1) < size
            ) {

                int left =
                        2 * index + 1;

                int right =
                        2 * index + 2;

                int target =
                        left;

                if (
                        right < size
                        &&
                        compare(
                                heap.get(right),
                                heap.get(left)
                        )
                ) {

                    target = right;
                }

                if (
                        compare(
                                heap.get(target),
                                heap.get(index)
                        )
                ) {

                    swap(index, target);

                    index = target;

                } else {

                    break;
                }
            }
        }

        // =========================
        // COMPARE VALUES
        // =========================

        private boolean compare(
                int child,
                int parent
        ) {

            return isMinHeap
                    ? child < parent
                    : child > parent;
        }

        // =========================
        // SWAP VALUES
        // =========================

        private void swap(
                int i,
                int j
        ) {

            int temp =
                    heap.get(i);

            heap.set(
                    i,
                    heap.get(j)
            );

            heap.set(
                    j,
                    temp
            );
        }
    }

    // ==========================================
    // FRONTEND: CUSTOM PAINT PANEL
    // ==========================================

    private class HeapVisualPanel
            extends JPanel {

        private final int nodeRadius = 20;

        private final int rowHeight = 60;

        public HeapVisualPanel() {

            setBackground(Color.WHITE);

            setPreferredSize(
                    new Dimension(
                            800,
                            500
                    )
            );
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g;

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            ArrayList<Integer> data =
                    heapEngine.getHeapData();

            // =========================
            // EMPTY HEAP
            // =========================

            if (data.isEmpty()) {

                g2.setColor(
                        Color.LIGHT_GRAY
                );

                g2.setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                16
                        )
                );

                g2.drawString(
                        "Heap Empty. Insert nodes to visualize.",
                        getWidth() / 2 - 130,
                        getHeight() / 2
                );

                return;
            }

            // =========================
            // DRAW ROOT NODE
            // =========================

            drawNode(
                    g2,
                    0,
                    getWidth() / 2,
                    40,
                    getWidth() / 4,
                    data
            );
        }

        // =========================
        // DRAW TREE NODE
        // =========================

        private void drawNode(
                Graphics2D g2,
                int index,
                int x,
                int y,
                int hGap,
                ArrayList<Integer> data
        ) {

            if (index >= data.size()) {

                return;
            }

            int leftChild =
                    2 * index + 1;

            int rightChild =
                    2 * index + 2;

            // =========================
            // DRAW CONNECTION LINES
            // =========================

            g2.setColor(
                    new Color(
                            150,
                            150,
                            150
                    )
            );

            g2.setStroke(
                    new BasicStroke(2)
            );

            // Left child
            if (leftChild < data.size()) {

                g2.drawLine(
                        x,
                        y,
                        x - hGap,
                        y + rowHeight
                );

                drawNode(
                        g2,
                        leftChild,
                        x - hGap,
                        y + rowHeight,
                        hGap / 2,
                        data
                );
            }

            // Right child
            if (rightChild < data.size()) {

                g2.drawLine(
                        x,
                        y,
                        x + hGap,
                        y + rowHeight
                );

                drawNode(
                        g2,
                        rightChild,
                        x + hGap,
                        y + rowHeight,
                        hGap / 2,
                        data
                );
            }

            // =========================
            // DRAW NODE CIRCLE
            // =========================

            g2.setColor(
                    typeComboBox.getSelectedIndex() == 0
                            ? new Color(
                                    70,
                                    130,
                                    180
                            )
                            : new Color(
                                    220,
                                    53,
                                    69
                            )
            );

            g2.fillOval(
                    x - nodeRadius,
                    y - nodeRadius,
                    nodeRadius * 2,
                    nodeRadius * 2
            );

            // =========================
            // NODE BORDER
            // =========================

            g2.setColor(
                    Color.DARK_GRAY
            );

            g2.drawOval(
                    x - nodeRadius,
                    y - nodeRadius,
                    nodeRadius * 2,
                    nodeRadius * 2
            );

            // =========================
            // NODE VALUE
            // =========================

            g2.setColor(
                    Color.WHITE
            );

            g2.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            13
                    )
            );

            String valStr =
                    String.valueOf(
                            data.get(index)
                    );

            FontMetrics fm =
                    g2.getFontMetrics();

            int textX =
                    x
                    - fm.stringWidth(valStr) / 2;

            int textY =
                    y
                    + fm.getAscent() / 2
                    - 2;

            g2.drawString(
                    valStr,
                    textX,
                    textY
            );

            // =========================
            // ARRAY INDEX
            // =========================

            g2.setColor(
                    Color.GRAY
            );

            g2.setFont(
                    new Font(
                            "SansSerif",
                            Font.PLAIN,
                            10
                    )
            );

            g2.drawString(
                    "[" + index + "]",
                    x - 8,
                    y - nodeRadius - 4
            );
        }
    }

    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new HeapGUI2().setVisible(true)
        );
    }
}