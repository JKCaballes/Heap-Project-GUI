package com.mycompany.binarytreegui2;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

class TreePanel extends JPanel {

    private final BinarySearchTree tree;
    private final int radius = 20;
    private final int vGap = 50;

    public TreePanel(BinarySearchTree tree) {
        this.tree = tree;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (tree.root != null) {
            // Enable Anti-Aliasing
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            drawTree(
                g2,
                tree.root,
                getWidth() / 2,
                40,
                getWidth() / 4
            );
        }
    }

    private void drawTree(
        Graphics2D g,
        Node node,
        int x,
        int y,
        int hGap
    ) {

        // Draw links to children first
        if (node.left != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x - hGap, y + vGap);

            drawTree(
                g,
                node.left,
                x - hGap,
                y + vGap,
                hGap / 2
            );
        }

        if (node.right != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x + hGap, y + vGap);

            drawTree(
                g,
                node.right,
                x + hGap,
                y + vGap,
                hGap / 2
            );
        }

        // Draw the node circle
        g.setColor(new Color(144, 238, 144));
        g.fillOval(
            x - radius,
            y - radius,
            2 * radius,
            2 * radius
        );

        g.setColor(Color.BLACK);
        g.drawOval(
            x - radius,
            y - radius,
            2 * radius,
            2 * radius
        );

        // Draw the text inside the node
        String valueStr = String.valueOf(node.key);
        FontMetrics fm = g.getFontMetrics();

        int textX = x - fm.stringWidth(valueStr) / 2;
        int textY = y + fm.getAscent() / 2 - 2;

        g.drawString(valueStr, textX, textY);
    }
}