import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class manageRegistry {
    public static void main(String[] args) {
        // Create controller
        controller controller = new controller();

        // Create main frame
        JFrame frame = new JFrame("Registry");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(245, 240, 245));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Catalog Panel
        JPanel catalogPanel = new JPanel();
        catalogPanel.setLayout(new BoxLayout(catalogPanel, BoxLayout.Y_AXIS));
        catalogPanel.setBackground(new Color(245, 240, 245));

        // Sample catalog items
        String[][] items = {
                { "Teddy Bear", "20", "6", "path/to/teddy_bear.png" },
                { "Coffee Mug", "15", "5", "path/to/coffee_mug.png" },
                { "Photo Frame", "10", "8", "path/to/photo_frame.png" }
        };

        for (String[] item : items) {
            JPanel itemPanel = new JPanel(new BorderLayout(10, 10));
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));

            // Item details
            JLabel nameLabel = new JLabel("Name: " + item[0]);
            JLabel priceLabel = new JLabel("Price: $" + item[1]);
            JLabel quantityLabel = new JLabel("Available: " + item[2]);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.parseInt(item[2]), 1);
            JSpinner quantitySpinner = new JSpinner(spinnerModel);

            JButton addButton = new JButton("Add");
            addButton.setBackground(new Color(77, 182, 172));
            addButton.setForeground(Color.WHITE);
            addButton.setFocusPainted(false);
            addButton.setFont(new Font("Arial", Font.BOLD, 12));

            addButton.addActionListener(e -> {
                int quantity = (int) quantitySpinner.getValue();
                if (controller.addItem(item[0], item[1], quantity, Integer.parseInt(item[2]))) {
                    JOptionPane.showMessageDialog(frame, "Added to registry: " + item[0] + " x " + quantity);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JPanel actionPanel = new JPanel();
            actionPanel.add(new JLabel("Qty: "));
            actionPanel.add(quantitySpinner);
            actionPanel.add(addButton);

            JPanel detailsPanel = new JPanel(new GridLayout(3, 1));
            detailsPanel.add(nameLabel);
            detailsPanel.add(priceLabel);
            detailsPanel.add(quantityLabel);

            itemPanel.add(detailsPanel, BorderLayout.CENTER);
            itemPanel.add(actionPanel, BorderLayout.EAST);

            catalogPanel.add(itemPanel);
            catalogPanel.add(Box.createVerticalStrut(10)); // Add spacing between items

            // Adding a line separator between items
            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setBackground(new Color(230, 230, 230));
            separator.setForeground(new Color(200, 200, 200));
            catalogPanel.add(separator);
        }

        JScrollPane catalogScrollPane = new JScrollPane(catalogPanel);
        catalogScrollPane.setBorder(BorderFactory.createTitledBorder("Available Items"));

        // View Registry Button
        JButton viewRegistryButton = new JButton("View Registry");
        viewRegistryButton.setBackground(new Color(77, 182, 172));
        viewRegistryButton.setForeground(Color.WHITE);
        viewRegistryButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewRegistryButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(viewRegistryButton);

        viewRegistryButton.addActionListener(e -> {
            // Create Registry Frame
            JFrame registryFrame = new JFrame("Gift Registry");
            registryFrame.setSize(800, 600);
            registryFrame.setLayout(new BorderLayout(10, 10));

            // Registry Model and List
            DefaultListModel<String> registryModel = new DefaultListModel<>();
            JList<String> registryList = new JList<>(registryModel);
            registryList.setFont(new Font("Arial", Font.PLAIN, 14));

            // Populate registry list from controller
            controller.getAllItems().forEach((name, details) -> registryModel
                    .addElement(name + " - Price: $" + details[0] + " x " + details[1]));

            // Search Field
            JTextField searchField = new JTextField(10);
            searchField.setFont(new Font("Arial", Font.PLAIN, 14));
            JButton searchButton = new JButton("Search");
            searchButton.setBackground(new Color(77, 182, 172));
            searchButton.setForeground(Color.WHITE);
            searchButton.setFont(new Font("Arial", Font.BOLD, 12));

            searchButton.addActionListener(event -> {
                String query = searchField.getText();
                List<String> results = controller.searchItems(query);
                registryModel.clear();
                results.forEach(registryModel::addElement);
            });

            // Update Button
            JButton updateButton = new JButton("Update");
            updateButton.setBackground(new Color(77, 182, 172));
            updateButton.setForeground(Color.WHITE);
            updateButton.setFont(new Font("Arial", Font.BOLD, 12));
            updateButton.addActionListener(event -> {
                String selectedValue = registryList.getSelectedValue();
                if (selectedValue != null) {
                    String itemName = selectedValue.split(" - ")[0];
                    String[] itemDetails = controller.getAllItems().get(itemName);

                    String newQty = JOptionPane.showInputDialog(registryFrame,
                            "Enter new quantity for " + itemName + ":");
                    try {
                        int qty = Integer.parseInt(newQty);
                        if (controller.updateItem(itemName, qty)) {
                            registryModel.setElementAt(itemName + " - Price: $" + itemDetails[0] + " x " + qty,
                                    registryList.getSelectedIndex());
                            JOptionPane.showMessageDialog(registryFrame, "Item updated successfully.");
                        } else {
                            JOptionPane.showMessageDialog(registryFrame, "Invalid quantity.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(registryFrame, "Invalid quantity.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(registryFrame, "Please select an item to update.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            // Delete Button
            JButton deleteButton = new JButton("Delete");
            deleteButton.setBackground(new Color(239, 83, 80));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
            deleteButton.addActionListener(event -> {
                String selectedValue = registryList.getSelectedValue();
                if (selectedValue != null) {
                    String itemName = selectedValue.split(" - ")[0];
                    if (controller.deleteItem(itemName)) {
                        registryModel.removeElement(selectedValue);
                        JOptionPane.showMessageDialog(registryFrame, "Item deleted.");
                    } else {
                        JOptionPane.showMessageDialog(registryFrame, "Error deleting item.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(registryFrame, "Please select an item to delete.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            // Exit Button
            JButton exitButton = new JButton("Exit");
            exitButton.setBackground(new Color(239, 83, 80));
            exitButton.setForeground(Color.WHITE);
            exitButton.setFont(new Font("Arial", Font.BOLD, 12));
            exitButton.addActionListener(event -> registryFrame.dispose());

            // Top Panel for Controls
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            topPanel.add(searchField);
            topPanel.add(searchButton);

            // Bottom Panel for Actions
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottomPanel.add(updateButton);
            bottomPanel.add(deleteButton);
            bottomPanel.add(exitButton);

            registryFrame.add(topPanel, BorderLayout.NORTH);
            registryFrame.add(new JScrollPane(registryList), BorderLayout.CENTER);
            registryFrame.add(bottomPanel, BorderLayout.SOUTH);

            registryFrame.setVisible(true);
        });

        // Main frame components
        frame.add(catalogScrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}

// Mock Controller Class
class Additemcontroller {
    private final Map<String, String[]> registry = new HashMap<>();

    public boolean addItem(String name, String price, int quantity, int available) {
        if (quantity > 0 && quantity <= available) {
            registry.put(name, new String[] { price, String.valueOf(quantity) });
            return true;
        }
        return false;
    }

    public boolean updateItem(String name, int quantity) {
        if (registry.containsKey(name) && quantity > 0) {
            registry.get(name)[1] = String.valueOf(quantity);
            return true;
        }
        return false;
    }

    public boolean deleteItem(String name) {
        return registry.remove(name) != null;
    }

    public Map<String, String[]> getAllItems() {
        return registry;
    }

    public List<String> searchItems(String query) {
        return registry.entrySet().stream()
                .filter(entry -> entry.getKey().toLowerCase().contains(query.toLowerCase()))
                .map(entry -> entry.getKey() + " - Price: $" + entry.getValue()[0] + " x " + entry.getValue()[1])
                .collect(Collectors.toList());
    }
}
