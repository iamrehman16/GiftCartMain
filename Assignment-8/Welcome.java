import javax.swing.*;
import java.awt.*;
// import java.awt.event.ActionEvent;

public class Welcome {
    public static void main(String[] args) {
        // Create the main JFrame
        JFrame frame = new JFrame("Welcome to GiftCart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Use GridBagLayout to center components both vertically and horizontally
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to the GiftCart.", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        // welcomeLabel.setForeground(Color.BLUE);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(77, 182, 172));
        loginButton.addActionListener(e -> {
            Login.showLoginDialog(frame);
        });

        // Signup Button
        JButton signupButton = new JButton("Signup");
        signupButton.setBackground(new Color(77, 182, 172));
        signupButton.addActionListener(e -> {
            Signup.showSignupDialog(frame);
        });

        // Place welcomeLabel in the center
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Adds vertical spacing
        frame.add(welcomeLabel, gbc);

        // Place loginButton in the center
        gbc.gridy = 1;
        frame.add(loginButton, gbc);

        // Place signupButton in the center
        gbc.gridy = 2;
        frame.add(signupButton, gbc);

        // Ensure the window is centered on the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
