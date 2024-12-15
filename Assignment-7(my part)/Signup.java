import javax.swing.*;
import java.awt.*;

public class Signup {
    public static void showSignupDialog(JFrame parentFrame) {
        // Create a modal dialog
        JDialog signupDialog = new JDialog(parentFrame, "Signup to GiftCart", true);
        signupDialog.setSize(400, 400);
        signupDialog.setLayout(new GridBagLayout());

        // Create a panel to hold the signup form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(300, 300));
        formPanel.setMaximumSize(new Dimension(300, 300));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)));

        // GridBagConstraints to align components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        // Email Label and Text Field
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(15);
        formPanel.add(emailField, gbc);
        row++;

        // Phone Number Label and Text Field
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Phone Number:"), gbc);

        gbc.gridx = 1;
        JTextField phoneField = new JTextField(15);
        formPanel.add(phoneField, gbc);
        row++;

        // Username Label and Text Field
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);
        row++;

        // User Role Label and ComboBox
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        String[] roles = {"Host", "Guest", "Admin"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        formPanel.add(roleBox, gbc);
        row++;

        // Confirmation Code Label and Text Field
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Confirmation Code:"), gbc);

        gbc.gridx = 1;
        JTextField confirmationField = new JTextField(15);
        formPanel.add(confirmationField, gbc);
        row++;

        // Signup Button
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JButton signupButton = new JButton("Signup");
        formPanel.add(signupButton, gbc);

        // Action listener for the signup button
        signupButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String username = usernameField.getText().trim();
            String role = (String) roleBox.getSelectedItem();
            String confirmationCode = confirmationField.getText().trim();

            if (email.isEmpty() || phone.isEmpty() || username.isEmpty() || confirmationCode.isEmpty()) {
                JOptionPane.showMessageDialog(signupDialog, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        signupDialog,
                        "Signup Successful!\n" +
                                "Email: " + email + "\n" +
                                "Phone: " + phone + "\n" +
                                "Username: " + username + "\n" +
                                "Role: " + role + "\n" +
                                "Confirmation Code: " + confirmationCode,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                signupDialog.dispose(); // Close the dialog after submission
                parentFrame.dispose();
                SwingUtilities.invokeLater(() -> SearchAndEditCatalogue.main(new String[]{}));
            }
        });

        // Center the signup panel in the dialog
        signupDialog.add(formPanel);
        signupDialog.setLocationRelativeTo(parentFrame); // Center the dialog relative to the parent frame
        signupDialog.setVisible(true);
    }}