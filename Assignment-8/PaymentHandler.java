// package com.giftshop.payment;

import javax.swing.*;
import java.awt.*;

public class PaymentHandler {

    public static void showPaymentDialog(JFrame parentFrame, int netTotal) {
        if (netTotal <= 0) {
            JOptionPane.showMessageDialog(parentFrame, "No items selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel paymentPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField cardNumberField = new JTextField();
        JTextField cvcField = new JTextField();
        JTextField expiryField = new JTextField();

        paymentPanel.add(new JLabel("Card Number:"));
        paymentPanel.add(cardNumberField);
        paymentPanel.add(new JLabel("CVC:"));
        paymentPanel.add(cvcField);
        paymentPanel.add(new JLabel("Expiry Date (MM/YY):"));
        paymentPanel.add(expiryField);

        int result = JOptionPane.showConfirmDialog(parentFrame, paymentPanel, "Enter Payment Information",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String cardNumber = cardNumberField.getText();
            String cvc = cvcField.getText();
            String expiry = expiryField.getText();

            if (validatePaymentInfo(cardNumber, cvc, expiry)) {
                JOptionPane.showMessageDialog(parentFrame, "Payment Successful!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Invalid Payment Information.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static boolean validatePaymentInfo(String cardNumber, String cvc, String expiry) {
        if (!cardNumber.matches("\\d{16}")) {
            return false;
        }
        if (!cvc.matches("\\d{3}")) {
            return false;
        }
        if (!expiry.matches("\\d{2}/\\d{2}")) {
            return false;
        }
        return true;
    }
}
