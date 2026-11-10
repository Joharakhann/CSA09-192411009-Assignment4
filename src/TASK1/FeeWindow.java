package TASK1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FeeWindow extends JFrame {
    private final JTextField txtRegNo = new JTextField(15);
    private final JTextField txtAmount = new JTextField(10);
    private final JButton btnPay = new JButton("Pay");
    private final JButton btnClear = new JButton("Clear");
    private final JButton btnReceipt = new JButton("Receipt");
    private final FeeService feeService = new FeeService();

    public FeeWindow() {
        super("Student Fee Payment");
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        add(new JLabel("Reg No:")); add(txtRegNo);
        add(new JLabel("Amount:")); add(txtAmount);
        add(btnPay); add(btnClear); add(btnReceipt);

        // Registered dedicated handlers
        btnPay.addActionListener(this::handlePay);
        btnClear.addActionListener(e -> clearFields());
        btnReceipt.addActionListener(e -> JOptionPane.showMessageDialog(this, "Receipt feature pending."));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void handlePay(ActionEvent e) {
        String regNo = txtRegNo.getText();
        String amount = txtAmount.getText();
        btnPay.setEnabled(false);

        // Execute database work asynchronously off the EDT using SwingWorker
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return feeService.processPayment(regNo, amount);
            }

            @Override
            protected void done() {
                btnPay.setEnabled(true);
                try {
                    boolean success = get();
                    if (success) {
                        JOptionPane.showMessageDialog(FeeWindow.this, "Payment Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    }
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    JOptionPane.showMessageDialog(FeeWindow.this, cause.getMessage(), "Payment Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void clearFields() {
        txtRegNo.setText("");
        txtAmount.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FeeWindow().setVisible(true));
    }
}