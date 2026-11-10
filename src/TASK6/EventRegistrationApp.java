package TASK6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class EventRegistrationApp extends JFrame {
    private final JTextField txtStudentId = new JTextField(10);
    private final JTextField txtName = new JTextField(12);
    private final JComboBox<String> cmbEvent = new JComboBox<>(new String[]{"Coding Contest", "Web Dev Hackathon", "Paper Presentation"});
    private final JComboBox<String> cmbFilter = new JComboBox<>(new String[]{"All Events", "Coding Contest", "Web Dev Hackathon", "Paper Presentation"});
    private final JButton btnRegister = new JButton("Register");
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Reg No", "Name", "Event", "Date"}, 0);
    private final JTable table = new JTable(tableModel);

    // In-memory database simulation array list for immediate verification
    private static final java.util.List<Object[]> mockDb = new java.util.ArrayList<>();

    public EventRegistrationApp() {
        super("Campus Event Registration Management");
        setLayout(new BorderLayout(10, 10));

        // 1. MenuBar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemExit);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);

        // 2. Input Form (North Container)
        JPanel pnlForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        pnlForm.setBorder(BorderFactory.createTitledBorder("New Event Registration"));
        pnlForm.add(new JLabel("Student ID:")); pnlForm.add(txtStudentId);
        pnlForm.add(new JLabel("Name:")); pnlForm.add(txtName);
        pnlForm.add(new JLabel("Event:")); pnlForm.add(cmbEvent);
        pnlForm.add(btnRegister);
        add(pnlForm, BorderLayout.NORTH);

        // 3. Center Table & Filter Controls
        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlFilter.add(new JLabel("Filter by Event:"));
        pnlFilter.add(cmbFilter);
        pnlCenter.add(pnlFilter, BorderLayout.NORTH);
        pnlCenter.add(new JScrollPane(table), BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // 4. Event Handlers
        btnRegister.addActionListener(e -> registerStudent());
        cmbFilter.addActionListener(e -> loadRegistrations());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 450);
        setLocationRelativeTo(null);

        // Populate sample data
        mockDb.add(new Object[]{"192411009", "Johara", "Coding Contest", new java.util.Date().toString()});
        loadRegistrations();
    }

    private void registerStudent() {
        String studentId = txtStudentId.getText().trim();
        String name = txtName.getText().trim();
        String eventName = (String) cmbEvent.getSelectedItem();

        if (studentId.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Duplicate Check
        for (Object[] row : mockDb) {
            if (row[0].toString().equalsIgnoreCase(studentId) && row[2].toString().equals(eventName)) {
                JOptionPane.showMessageDialog(this, "Duplicate Registration: Student is already registered for this event!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        btnRegister.setEnabled(false);
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                Thread.sleep(300); // Simulate asynchronous database save
                mockDb.add(new Object[]{studentId, name, eventName, new java.util.Date().toString()});
                return true;
            }

            @Override
            protected void done() {
                btnRegister.setEnabled(true);
                JOptionPane.showMessageDialog(EventRegistrationApp.this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                txtStudentId.setText("");
                txtName.setText("");
                loadRegistrations();
            }
        }.execute();
    }

    private void loadRegistrations() {
        String selectedFilter = (String) cmbFilter.getSelectedItem();
        tableModel.setRowCount(0);

        for (Object[] row : mockDb) {
            if ("All Events".equals(selectedFilter) || row[2].toString().equals(selectedFilter)) {
                tableModel.addRow(row);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventRegistrationApp().setVisible(true));
    }
}