import javax.swing.*;
import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

public class Add_item_to_catalogue {
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

            if (title.isEmpty() || description.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        dialog,
                        "Item added to catalogue:\n" +
                                "Title: " + title + "\n" +
                                "Description: " + description + "\n" +
                                "Price: " + price + "\n" +
                                "Stock: " + stock + "\n" +
                                "Photo: " + (photoPath.isEmpty() ? "No photo uploaded" : photoPath),
                        "Item Added",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose(); // Close the dialog after submission
            }
        });

        dialog.add(panel);
        dialog.setLocationRelativeTo(parentFrame); // Center relative to the parent frame
        dialog.setVisible(true);
    }
}
