package com.mycompany.hotelreservationgui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class HotelReservationGUI extends JFrame {

    // Hashing data structure:
    // Key = Reservation ID
    // Value = Reservation Object
    private HashMap<String, Reservation> reservationMap = new HashMap<>();

    // GUI Components
    private JTextField txtId, txtGuestName, txtRoomNo, txtDate;
    private JTable table;
    private DefaultTableModel tableModel;

    // Reservation Model Class
    static class Reservation {

        String reservationId;
        String guestName;
        String roomNumber;
        String date;

        public Reservation(
                String reservationId,
                String guestName,
                String roomNumber,
                String date) {

            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomNumber = roomNumber;
            this.date = date;
        }
    }

    public HotelReservationGUI() {

        setTitle("Hotel Reservation System");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // -----------------------------
        // Form Panel
        // -----------------------------

        JPanel panelForm =
                new JPanel(new GridLayout(5, 2, 5, 5));

        panelForm.setBorder(
                BorderFactory.createTitledBorder(
                        "Reservation Details"
                )
        );

        panelForm.add(new JLabel("Reservation ID:"));

        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Guest Name:"));

        txtGuestName = new JTextField();
        panelForm.add(txtGuestName);

        panelForm.add(new JLabel("Room Number:"));

        txtRoomNo = new JTextField();
        panelForm.add(txtRoomNo);

        panelForm.add(
                new JLabel("Check-in Date (YYYY-MM-DD):")
        );

        txtDate = new JTextField();
        panelForm.add(txtDate);

        add(panelForm, BorderLayout.NORTH);

        // -----------------------------
        // Table Display
        // -----------------------------

        tableModel =
                new DefaultTableModel(
                        new String[]{
                                "ID",
                                "Guest Name",
                                "Room No",
                                "Date"
                        },
                        0
                );

        table = new JTable(tableModel);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        // -----------------------------
        // Buttons Panel
        // -----------------------------

        JPanel panelButtons = new JPanel();

        JButton btnAdd =
                new JButton("Add");

        JButton btnEdit =
                new JButton("Edit");

        JButton btnDelete =
                new JButton("Delete");

        JButton btnClear =
                new JButton("Clear");

        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClear);

        add(
                panelButtons,
                BorderLayout.SOUTH
        );

        // -----------------------------
        // ADD Function
        // -----------------------------

        btnAdd.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String id =
                                txtId.getText().trim();

                        String name =
                                txtGuestName.getText().trim();

                        String room =
                                txtRoomNo.getText().trim();

                        String date =
                                txtDate.getText().trim();

                        if (id.isEmpty()
                                || name.isEmpty()
                                || room.isEmpty()
                                || date.isEmpty()) {

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Please fill out all fields!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            return;
                        }

                        if (reservationMap.containsKey(id)) {

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Reservation ID already exists!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            return;
                        }

                        Reservation res =
                                new Reservation(
                                        id,
                                        name,
                                        room,
                                        date
                                );

                        reservationMap.put(id, res);

                        refreshTable();
                        clearFields();

                        JOptionPane.showMessageDialog(
                                null,
                                "Reservation added successfully!"
                        );
                    }
                }
        );

        // -----------------------------
        // EDIT Function
        // -----------------------------

        btnEdit.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String id =
                                txtId.getText().trim();

                        if (id.isEmpty()) {

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Enter Reservation ID to edit!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            return;
                        }

                        if (!reservationMap.containsKey(id)) {

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Reservation ID not found!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            return;
                        }

                        String name =
                                txtGuestName.getText().trim();

                        String room =
                                txtRoomNo.getText().trim();

                        String date =
                                txtDate.getText().trim();

                        Reservation res =
                                reservationMap.get(id);

                        if (!name.isEmpty()) {
                            res.guestName = name;
                        }

                        if (!room.isEmpty()) {
                            res.roomNumber = room;
                        }

                        if (!date.isEmpty()) {
                            res.date = date;
                        }

                        reservationMap.put(id, res);

                        refreshTable();
                        clearFields();

                        JOptionPane.showMessageDialog(
                                null,
                                "Reservation updated successfully!"
                        );
                    }
                }
        );

        // -----------------------------
        // DELETE Function
        // -----------------------------

        btnDelete.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String id =
                                txtId.getText().trim();

                        if (id.isEmpty()
                                || !reservationMap.containsKey(id)) {

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Valid Reservation ID required to delete!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            return;
                        }

                        reservationMap.remove(id);

                        refreshTable();
                        clearFields();

                        JOptionPane.showMessageDialog(
                                null,
                                "Reservation deleted successfully!"
                        );
                    }
                }
        );

        // -----------------------------
        // CLEAR Form Fields
        // -----------------------------

        btnClear.addActionListener(
                e -> clearFields()
        );
    }

    // -----------------------------
    // DISPLAY / REFRESH Function
    // -----------------------------

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (Map.Entry<String, Reservation> entry
                : reservationMap.entrySet()) {

            Reservation r =
                    entry.getValue();

            tableModel.addRow(
                    new Object[]{
                            r.reservationId,
                            r.guestName,
                            r.roomNumber,
                            r.date
                    }
            );
        }
    }

    // -----------------------------
    // CLEAR Fields Function
    // -----------------------------

    private void clearFields() {

        txtId.setText("");
        txtGuestName.setText("");
        txtRoomNo.setText("");
        txtDate.setText("");
    }

    // -----------------------------
    // MAIN METHOD
    // -----------------------------

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new HotelReservationGUI().setVisible(true)
        );
    }
}