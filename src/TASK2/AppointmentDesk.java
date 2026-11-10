package TASK2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentDesk extends JFrame {
    private final JTextField txtPatientId = new JTextField(15);
    private final JComboBox<String> cmbDepartment = new JComboBox<>(new String[]{"Cardiology", "Orthopedics", "Pediatrics"});
    private final JComboBox<String> cmbDoctor = new JComboBox<>();
    private final JComboBox<String> cmbTimeSlot = new JComboBox<>(new String[]{"09:00 AM", "11:00 AM", "02:00 PM", "04:00 PM"});
    private final JButton btnBook = new JButton("Book Appointment");
    private final JLabel lblStatus = new JLabel(" Select details to book an appointment.");

    private final Map<String, List<String>> doctorMap = new HashMap<>();
    private boolean isBookingInProgress = false;

    public AppointmentDesk() {
        super("Hospital Outpatient Appointment Desk");
        initData();

        setLayout(new BorderLayout(10, 10));

        // Form Panel (North)
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnlForm.add(new JLabel("Patient ID (PAT-XXXX):")); pnlForm.add(txtPatientId);
        pnlForm.add(new JLabel("Department:")); pnlForm.add(cmbDepartment);
        pnlForm.add(new JLabel("Doctor:")); pnlForm.add(cmbDoctor);
        pnlForm.add(new JLabel("Time Slot:")); pnlForm.add(cmbTimeSlot);
        add(pnlForm, BorderLayout.NORTH);

        // Button Panel (Center)
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlButton.add(btnBook);
        add(pnlButton, BorderLayout.CENTER);

        // Status Panel (South)
        JPanel pnlStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlStatus.setBorder(BorderFactory.createEtchedBorder());
        pnlStatus.add(lblStatus);
        add(pnlStatus, BorderLayout.SOUTH);

        // Listeners
        cmbDepartment.addActionListener(e -> updateDoctors());
        btnBook.addActionListener(e -> bookAppointment());

        // Window Closing Confirmation
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (isBookingInProgress || !txtPatientId.getText().trim().isEmpty()) {
                    int choice = JOptionPane.showConfirmDialog(
                            AppointmentDesk.this,
                            "A booking/entry is currently in progress. Are you sure you want to exit?",
                            "Confirm Exit",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (choice == JOptionPane.YES_OPTION) {
                        dispose();
                    }
                } else {
                    dispose();
                }
            }
        });

        updateDoctors(); // Initialize doctor dropdown for default department
        pack();
        setLocationRelativeTo(null);
    }

    private void initData() {
        doctorMap.put("Cardiology", Arrays.asList("Dr. Sharma", "Dr. Mehta"));
        doctorMap.put("Orthopedics", Arrays.asList("Dr. Kapoor", "Dr. Verma"));
        doctorMap.put("Pediatrics", Arrays.asList("Dr. Ananya", "Dr. Rajesh"));
    }

    private void updateDoctors() {
        String selectedDept = (String) cmbDepartment.getSelectedItem();
        cmbDoctor.removeAllItems();
        if (selectedDept != null && doctorMap.containsKey(selectedDept)) {
            for (String doc : doctorMap.get(selectedDept)) {
                cmbDoctor.addItem(doc);
            }
        }
    }

    private void bookAppointment() {
        String patientId = txtPatientId.getText().trim();
        String doc = (String) cmbDoctor.getSelectedItem();
        String slot = (String) cmbTimeSlot.getSelectedItem();

        if (patientId.isEmpty() || !patientId.matches("^PAT-\\d{4}$")) {
            lblStatus.setText(" Error: Patient ID must follow format PAT-XXXX (e.g. PAT-1001).");
            JOptionPane.showMessageDialog(this, "Invalid Patient ID format! Expected format: PAT-XXXX", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (doc == null || slot == null) {
            lblStatus.setText(" Error: Please select a valid Doctor and Time Slot.");
            return;
        }

        isBookingInProgress = true;
        lblStatus.setText(" Booking appointment for " + patientId + " with " + doc + " at " + slot + "...");

        // Simulate booking execution
        JOptionPane.showMessageDialog(this, "Appointment Booked Successfully!\nPatient: " + patientId + "\nDoctor: " + doc + "\nSlot: " + slot, "Success", JOptionPane.INFORMATION_MESSAGE);
        lblStatus.setText(" Booking confirmed for " + patientId + ".");
        txtPatientId.setText("");
        isBookingInProgress = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppointmentDesk().setVisible(true));
    }
}