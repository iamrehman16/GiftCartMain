import javax.swing.*;
import java.awt.*;

public class Login {
    private static final controller loginController = new controller();

    public static void showLoginDialog(JFrame parentFrame) {
        // Create a modal dialog
        JDialog loginDialog = new JDialog(parentFrame, "Login to GiftCart", true);
        loginDialog.setSize(400, 300);
        loginDialog.setLayout(new GridBagLayout());

        // Create a panel to hold the login form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(300, 200)); // Keep it compact
        formPanel.setMaximumSize(new Dimension(300, 200));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)));

        // GridBagConstraints to align components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        // Username Label and Text Field
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);
        row++;

        // Password Label and Text Field
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);
        row++;

        // Login Button
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(77, 182, 172));
        formPanel.add(loginButton, gbc);

        // Action listener for the login button
        loginButton.addActionListener(e -> {
            String enteredUsername = usernameField.getText().trim();
            String enteredPassword = new String(passwordField.getPassword());

            // Pass data to controller for validation
            String loginResult = loginController.processLogin(enteredUsername, enteredPassword);

            // Handle controller's response
            if (loginResult.startsWith("Error:")) {
                JOptionPane.showMessageDialog(loginDialog, loginResult, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(loginDialog, loginResult, "Success", JOptionPane.INFORMATION_MESSAGE);
                loginDialog.dispose(); // Close the dialog on success
                parentFrame.dispose();

                // Navigate to respective functionality based on user role
                String role = loginController.getUserRole(enteredUsername);
                if ("Admin".equals(role)) {
                    SwingUtilities.invokeLater(() -> SearchAndEditCatalogue.main(new String[] {}));
                } else if ("Host".equals(role)) {
                    SwingUtilities.invokeLater(() -> manageRegistry.main(new String[] {}));
                } else {
                    SwingUtilities.invokeLater(() -> giftSelection.main(new String[] {}));
                }
            }
        });

        // Add the form panel to the dialog
        loginDialog.add(formPanel);
        loginDialog.setLocationRelativeTo(parentFrame); // Center the dialog relative to the parent frame
        loginDialog.setVisible(true);
    }
}
