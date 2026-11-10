package TASK1;

public class FeeService {
    public boolean processPayment(String regNo, String amountStr) throws Exception {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration Number cannot be empty.");
        }
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format. Please enter a number.");
        }

        // Simulated database delay and success response
        Thread.sleep(500); 
        return true; 
    }
}