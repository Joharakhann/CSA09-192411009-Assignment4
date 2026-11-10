package TASK3;

import javax.swing.*;
import java.awt.*;

public class LibrarySearchScreen extends JFrame {
    public LibrarySearchScreen() {
        super("Campus Library Search Screen");
        setLayout(new BorderLayout(10, 10));

        // 1. Search Row (North Container)
        JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlNorth.add(new JLabel("Search Book:"));
        pnlNorth.add(new JTextField(20));
        JComboBox<String> cmbCategory = new JComboBox<>(new String[]{
            "All Categories", "Computer Science", "Mathematics", "Electrical", "Fiction"
        });
        pnlNorth.add(cmbCategory);
        pnlNorth.add(new JButton("Search"));
        add(pnlNorth, BorderLayout.NORTH);

        // 2. Results Area (Center Container - Dynamic Growth)
        String[] columns = {"Book ID", "Title", "Author", "Status"};
        Object[][] data = {
            {"B101", "Core Java Volume I", "Cay S. Horstmann", "Available"},
            {"B102", "Effective Java", "Joshua Bloch", "Checked Out"},
            {"B103", "Design Patterns", "Erich Gamma", "Available"},
            {"B104", "Clean Code", "Robert C. Martin", "Available"},
            {"B105", "Data Structures & Algorithms", "Mark Allen Weiss", "Available"}
        };
        JTable tblResults = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(tblResults);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Control Buttons & Status Bar (South Container)
        JPanel pnlSouth = new JPanel(new BorderLayout());
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlButtons.add(new JButton("Prev"));
        pnlButtons.add(new JButton("Next"));
        pnlButtons.add(new JButton("Reserve"));

        JLabel lblStatus = new JLabel(" Ready - 5 books found in repository.");
        lblStatus.setBorder(BorderFactory.createEtchedBorder());

        pnlSouth.add(pnlButtons, BorderLayout.NORTH);
        pnlSouth.add(lblStatus, BorderLayout.SOUTH);
        add(pnlSouth, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 400);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySearchScreen().setVisible(true));
    }
}