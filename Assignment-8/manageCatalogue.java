import javax.swing.*;
import java.awt.*;

public class manageCatalogue {
    private static final controller catalogueController = new controller();

    public static void showAddItemDialog(JFrame parentFrame) {
        // Create a modal dialog
        JDialog dialog = new JDialog(parentFrame, "New Item", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridBagLayout()); // Center the panel

        // Create a panel for the form
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setPreferredSize(new Dimension(400, 300));

        // Title
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        panel.add(titleLabel);
        panel.add(titleField);

        // Description
        JLabel descriptionLabel = new JLabel("Description:");
        JTextArea descriptionField = new JTextArea(5, 20);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        descriptionField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(descriptionLabel);
        panel.add(descriptionField);

        // Price
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();
        panel.add(priceLabel);
        panel.add(priceField);

        // Stock
        JLabel stockLabel = new JLabel("Stock:");
        JSpinner stockSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        panel.add(stockLabel);
        panel.add(stockSpinner);

        // Add Photo
        JLabel photoLabel = new JLabel("Add photo:");
        JButton photoButton = new JButton("Upload Photo");
        JLabel photoPathLabel = new JLabel();
        panel.add(photoLabel);
        panel.add(photoButton);

        photoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(dialog);
            if (result == JFileChooser.APPROVE_OPTION) {
                photoPathLabel.setText(fileChooser.getSelectedFile().getPath());
            }
        });

        // Submit Button
        JButton submitButton = new JButton("Add+");
        panel.add(new JLabel());
        panel.add(submitButton);

        submitButton.addActionListener(e -> {
            String title = titleField.getText();
            String description = descriptionField.getText();
            String price = priceField.getText();
            int stock = (int) stockSpinner.getValue();
            String photoPath = photoPathLabel.getText();

            // Pass the data to the controller for processing
            String result = catalogueController.addItemToCatalogue(title, description, price, stock, photoPath);

            // Display the result returned from the controller
            if (result.startsWith("Error:")) {
                JOptionPane.showMessageDialog(dialog, result, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialog, result, "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose(); // Close the dialog after successful submission
            }
        });

        dialog.add(panel);
        dialog.setLocationRelativeTo(parentFrame); // Center relative to the parent frame
        dialog.setVisible(true);
    }
}
