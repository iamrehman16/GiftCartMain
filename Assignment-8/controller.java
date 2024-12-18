import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;

import java.awt.*;

public class controller {

    public controller() {
        // Example users (username -> {password, role})
        users.put("user123", new String[] { "pass123", "Host" });
        users.put("admin123", new String[] { "adminPass", "Admin" });
        users.put("guest123", new String[] { "guestPass", "Guest" });
    }

    private final Map<String, String[]> selectedItems = new HashMap<>();

    // Add an item to the registry
    public boolean addItem(String name, String price, int quantity, int availableQuantity) {
        if (quantity > 0 && quantity <= availableQuantity) {
            selectedItems.put(name,
                    new String[] { price, String.valueOf(quantity), String.valueOf(availableQuantity) });
            return true;
        }
        return false;
    }

    // Update the quantity of an existing item
    public boolean updateItem(String name, int newQuantity) {
        if (selectedItems.containsKey(name)) {
            String[] details = selectedItems.get(name);
            int availableQuantity = Integer.parseInt(details[2]);
            if (newQuantity > 0 && newQuantity <= availableQuantity) {
                selectedItems.put(name, new String[] { details[0], String.valueOf(newQuantity), details[2] });
                return true;
            }
        }
        return false;
    }

    // Delete an item from the registry
    public boolean deleteItem(String name) {
        return selectedItems.remove(name) != null;
    }

    // Get all items
    public Map<String, String[]> getAllItems() {
        return selectedItems;
    }

    // Search for items by name
    public List<String> searchItems(String query) {
        return selectedItems.entrySet().stream()
                .filter(entry -> entry.getKey().toLowerCase().contains(query.toLowerCase()))
                .map(entry -> formatItem(entry))
                .collect(Collectors.toList());
    }

    // Filter items by price
    public List<String> filterItems(String filter) {
        return selectedItems.entrySet().stream()
                .filter(entry -> {
                    int price = Integer.parseInt(entry.getValue()[0]);
                    return "Price < $15".equals(filter) && price < 15 ||
                            "Price >= $15".equals(filter) && price >= 15 ||
                            "All".equals(filter);
                })
                .map(this::formatItem)
                .collect(Collectors.toList());
    }

    // Format item for display
    private String formatItem(Map.Entry<String, String[]> entry) {
        return entry.getKey() + " - Price: $" + entry.getValue()[0] + " x " + entry.getValue()[1];
    }

    // signup validation
    private final Map<String, String[]> users = new HashMap<>();

    public String processSignup(String email, String phone, String username, String role, String confirmationCode) {
        if (email.isEmpty() || phone.isEmpty() || username.isEmpty() || confirmationCode.isEmpty()) {
            return "Error: Please fill all fields.";
        }

        if (users.containsKey(username)) {
            return "Error: Username already exists.";
        }

        users.put(username, new String[] { email, phone, role, confirmationCode });
        return "Signup Successful!\n" +
                "Email: " + email + "\n" +
                "Phone: " + phone + "\n" +
                "Username: " + username + "\n" +
                "Role: " + role + "\n" +
                "Confirmation Code: " + confirmationCode;
    }

    // Process login validation
    public String processLogin(String username, String password) {
        if (users.containsKey(username)) {
            String[] userDetails = users.get(username);
            if (userDetails[0].equals(password)) {
                return "Login Successful!";
            } else {
                return "Error: Invalid password.";
            }
        }
        return "Error: Username does not exist.";
    }

    // Get the role of the user
    public String getUserRole(String username) {
        return users.containsKey(username) ? users.get(username)[1] : null;
    }

    // A map to store catalogue items (title -> item details)
    private final Map<String, String[]> catalogue = new HashMap<>();

    /**
     * Adds an item to the catalogue.
     *
     * @param title       The title of the item.
     * @param description The description of the item.
     * @param price       The price of the item as a string.
     * @param stock       The stock quantity of the item.
     * @param photoPath   The file path of the item's photo.
     * @return A success or error message.
     */

    // add new items to catalogue
    public String addItemToCatalogue(String title, String description, String price, int stock, String photoPath) {
        // Validate input fields
        if (title == null || title.trim().isEmpty()) {
            return "Error: Title cannot be empty.";
        }
        if (description == null || description.trim().isEmpty()) {
            return "Error: Description cannot be empty.";
        }
        if (price == null || price.trim().isEmpty()) {
            return "Error: Price cannot be empty.";
        }
        try {
            double parsedPrice = Double.parseDouble(price);
            if (parsedPrice < 0) {
                return "Error: Price cannot be negative.";
            }
        } catch (NumberFormatException e) {
            return "Error: Invalid price format.";
        }

        if (stock < 0) {
            return "Error: Stock cannot be negative.";
        }

        // Add item to the catalogue
        catalogue.put(title, new String[] { description, price, String.valueOf(stock), photoPath });
        return "Item successfully added to the catalogue:\n" +
                "Title: " + title + "\n" +
                "Description: " + description + "\n" +
                "Price: $" + price + "\n" +
                "Stock: " + stock + "\n" +
                "Photo: " + (photoPath == null || photoPath.isEmpty() ? "No photo uploaded" : photoPath);
    }

    // adding dummy items for user to select for gifts
    private List<String[]> items = new ArrayList<>();
    private Map<String, JPanel> itemPanels = new HashMap<>();
    private int netTotal = 0;

    public void initializeItems() {
        items.add(new String[] { "Item 1", "100", "Description 1", "10" });
        items.add(new String[] { "Item 2", "150", "Description 2", "8" });
        items.add(new String[] { "Item 3", "200", "Description 3", "5" });
        items.add(new String[] { "Item 4", "250", "Description 4", "12" });
        items.add(new String[] { "Item 5", "250", "Description 5", "2" });
        items.add(new String[] { "Item 13", "200", "Description 13", "5" });
        items.add(new String[] { "Item 14", "250", "Description 14", "12" });
        items.add(new String[] { "Item 15", "250", "Description 15", "2" });
        for (String[] item : items) {
            JPanel itemPanel = createItemPanel(item);
            itemPanels.put(item[0], itemPanel);
        }
    }

    public List<String[]> getItems() {
        return items;
    }

    public Map<String, JPanel> getItemPanels() {
        return itemPanels;
    }

    public List<String[]> searchItemsfromRegistry(String query) {
        List<String[]> filteredItems = new ArrayList<>();
        for (String[] item : items) {
            if (item[0].toLowerCase().contains(query)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public int getNetTotal() {
        return netTotal;
    }

    private JPanel createItemPanel(String[] itemDetails) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout(5, 5));
        itemPanel.setPreferredSize(new Dimension(500, 120));
        itemPanel.setBorder(BorderFactory.createTitledBorder(itemDetails[0]));

        // Image Sub-box
        JPanel imageBox = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setIcon(new ImageIcon(
                new ImageIcon("placeholder.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        imageBox.add(imageLabel, BorderLayout.CENTER);
        imageBox.setBorder(new LineBorder(Color.GRAY, 2));

        JPanel detailsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel priceLabel = new JLabel("Price: " + itemDetails[1]);
        JLabel quantityLabel = new JLabel("Quantity:");
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.parseInt(itemDetails[3]), 1));
        JLabel descriptionLabel = new JLabel("Description: " + itemDetails[2]);
        JLabel remainingLabel = new JLabel("Remaining: " + itemDetails[3]);
        JLabel runningTotalLabel = new JLabel("Running Total: 0");
        JCheckBox selectCheckBox = new JCheckBox("Select");

        quantitySpinner.addChangeListener(e -> handleQuantityChange(e, itemDetails, quantitySpinner, runningTotalLabel,
                remainingLabel, selectCheckBox));
        selectCheckBox.addActionListener(e -> handleSelectionChange(itemDetails, quantitySpinner, runningTotalLabel,
                remainingLabel, selectCheckBox));

        detailsPanel.add(priceLabel);
        detailsPanel.add(quantityLabel);
        detailsPanel.add(quantitySpinner);
        detailsPanel.add(descriptionLabel);
        detailsPanel.add(remainingLabel);
        detailsPanel.add(runningTotalLabel);
        detailsPanel.add(selectCheckBox);

        itemPanel.add(imageBox, BorderLayout.WEST);
        itemPanel.add(detailsPanel, BorderLayout.CENTER);

        return itemPanel;
    }

    private void handleQuantityChange(ChangeEvent e, String[] itemDetails, JSpinner quantitySpinner,
            JLabel runningTotalLabel, JLabel remainingLabel, JCheckBox selectCheckBox) {
        if (!selectCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Select Item First.");
            return;
        }

        int quantity = (int) quantitySpinner.getValue();
        int remaining = Integer.parseInt(itemDetails[3]);
        if (quantity > remaining) {
            JOptionPane.showMessageDialog(null, "Max quantity achieved.");
            quantitySpinner.setValue(remaining);
        } else {
            int price = Integer.parseInt(itemDetails[1]);
            int runningTotal = quantity * price;
            runningTotalLabel.setText("Running Total: " + runningTotal);
            remainingLabel.setText("Remaining: " + (remaining - quantity));
            updateNetTotal();
        }
    }

    private void handleSelectionChange(String[] itemDetails, JSpinner quantitySpinner, JLabel runningTotalLabel,
            JLabel remainingLabel, JCheckBox selectCheckBox) {
        int quantity = (int) quantitySpinner.getValue();
        int price = Integer.parseInt(itemDetails[1]);

        if (selectCheckBox.isSelected()) {
            int runningTotal = quantity * price;
            runningTotalLabel.setText("Running Total: " + runningTotal);
            remainingLabel.setText("Remaining: " + (Integer.parseInt(itemDetails[3]) - quantity));
        } else {
            runningTotalLabel.setText("Running Total: 0");
            remainingLabel.setText("Remaining: " + itemDetails[3]);
        }
        updateNetTotal();
    }

    private void updateNetTotal() {
        netTotal = 0;
        for (JPanel panel : itemPanels.values()) {
            for (Component component : panel.getComponents()) {
                if (component instanceof JPanel) {
                    for (Component innerComponent : ((JPanel) component).getComponents()) {
                        if (innerComponent instanceof JLabel
                                && ((JLabel) innerComponent).getText().startsWith("Running Total:")) {
                            String text = ((JLabel) innerComponent).getText();
                            netTotal += Integer.parseInt(text.replace("Running Total: ", ""));
                        }
                    }
                }
            }
        }
    }

}
