import javax.swing.*;
import java.awt.*;

public class Login {
    private static final String DUMMY_USERNAME = "user123";
    private static final String DUMMY_PASSWORD = "pass123";

    public static void showLoginDialog(JFrame parentFrame) {
        // Create a modal dialog
        JDialog loginDialog = new JDialog(parentFrame, "Login to GiftCart", true);
        loginDialog.setSize(400, 300);
        loginDialog.setLayout(new GridBagLayout());

        // Create a panel to hold the login form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(300, 200));  // Keep it compact
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
        formPanel.add(loginButton, gbc);

        // Action listener for the login button
        loginButton.addActionListener(e -> {
            String enteredUsername = usernameField.getText().trim();
            String enteredPassword = new String(passwordField.getPassword());

            if (enteredUsername.equals(DUMMY_USERNAME) && enteredPassword.equals(DUMMY_PASSWORD)) {
                JOptionPane.showMessageDialog(loginDialog, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loginDialog.dispose(); // Close the dialog on success
                parentFrame.dispose();
                SwingUtilities.invokeLater(() -> SearchAndEditCatalogue.main(new String[]{}));
            } else {
                JOptionPane.showMessageDialog(loginDialog, "Invalid Username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add the form panel to the dialog
        loginDialog.add(formPanel);
        loginDialog.setLocationRelativeTo(parentFrame); // Center the dialog relative to the parent frame
        loginDialog.setVisible(true);
    }
}
