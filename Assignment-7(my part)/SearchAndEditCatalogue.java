import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SearchAndEditCatalogue {
    // Data structure to store catalogue items
    private static final ArrayList<Item> catalogue = new ArrayList<>();

    public static void main(String[] args) {
        // Sample data
        catalogue.add(new Item("1", "Item A", "Description of Item A", "10.0", 50, "photoA.jpg"));
        catalogue.add(new Item("2", "Item B", "Description of Item B", "15.5", 30, "photoB.jpg"));
        catalogue.add(new Item("3", "Item C", "Description of Item C", "20.0", 10, "photoC.jpg"));
        catalogue.add(new Item("4", "Item D", "Description of Item A", "10.0", 50, "photoA.jpg"));
        catalogue.add(new Item("5", "Item A", "Description of Item A", "10.0", 50, "photoA.jpg"));
        catalogue.add(new Item("4", "Super Item A", "Super Description A", "25.0", 40, "superA.jpg"));

        // Create the frame
        JFrame frame = new JFrame("Catalogue");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        // Add a heading
        JLabel headingLabel = new JLabel("Search Catalogue", JLabel.CENTER);
        headingLabel.setFont(new Font("Serif", Font.BOLD, 18));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(headingLabel, BorderLayout.NORTH);

        // Create a panel for the search bar
        JPanel searchPanel = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Search by ID or Title:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton addButton = new JButton("Add Item");
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(addButton);
        frame.add(searchPanel, BorderLayout.NORTH);

        // Create a scrollable panel for the results
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); // Reduce horizontal padding
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE)); // Limit the width to 400px
        resultsPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        containerPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(containerPanel, BorderLayout.CENTER);

        // Add action listener to the search button
        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim().toLowerCase(); // Case-insensitive search
            resultsPanel.removeAll(); // Clear previous results

            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an ID or Title to search.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean found = false;
            for (Item item : catalogue) {
                if (item.getId().equalsIgnoreCase(query) || item.getTitle().toLowerCase().contains(query)) {
                    found = true;

                    // Create a panel for each matching item
                    JPanel itemPanel = new JPanel();
                    itemPanel.setLayout(new BorderLayout());
                    itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    itemPanel.setBackground(Color.WHITE);
                    itemPanel.setPreferredSize(new Dimension(500, 70));

                    JLabel itemLabel = new JLabel(
                            "<html><b>ID:</b> " + item.getId() + "<br><b>Title:</b> " + item.getTitle()
                                    + "<br><b>Price:</b> $" + item.getPrice()+"<br><b>Description:"+item.getDescription() + " <br> <b>Stock:</b> " + item.getStock()
                                    + "</html>");
                    itemPanel.add(itemLabel, BorderLayout.CENTER);

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    JButton editButton = new JButton("Edit");
                    JButton deleteButton = new JButton("Delete");
                    buttonPanel.add(editButton);
                    buttonPanel.add(deleteButton);

                    itemPanel.add(buttonPanel, BorderLayout.EAST);

                    // Add action listeners for edit and delete buttons
                    editButton.addActionListener(event -> {
                        JDialog editDialog = new JDialog(frame, "Edit Item", true);
                        editDialog.setSize(400, 300);
                        editDialog.setLayout(new BorderLayout());
                    
                        // Main panel with padding and visual enhancements
                        JPanel formPanel = new JPanel(new GridBagLayout());
                        formPanel.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createEmptyBorder(20, 20, 20, 20),  // Outer padding
                                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)  // Subtle border
                        ));
                        formPanel.setBackground(Color.WHITE);
                    
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(10, 10, 10, 10);  // Consistent padding between components
                        gbc.fill = GridBagConstraints.HORIZONTAL;
                    
                        int row = 0;
                    
                        // Title
                        gbc.gridx = 0;
                        gbc.gridy = row;
                        formPanel.add(new JLabel("Title:"), gbc);
                        gbc.gridx = 1;
                        JTextField titleField = new JTextField(item.getTitle());
                        formPanel.add(titleField, gbc);
                        row++;
                    
                        // Description
                        gbc.gridx = 0;
                        gbc.gridy = row;
                        formPanel.add(new JLabel("Description:"), gbc);
                        gbc.gridx = 1;
                        JTextArea descriptionField = new JTextArea(item.getDescription(), 3, 20);
                        descriptionField.setLineWrap(true);
                        descriptionField.setWrapStyleWord(true);
                        JScrollPane descriptionScroll = new JScrollPane(descriptionField);
                        formPanel.add(descriptionScroll, gbc);
                        row++;
                    
                        // Price
                        gbc.gridx = 0;
                        gbc.gridy = row;
                        formPanel.add(new JLabel("Price:"), gbc);
                        gbc.gridx = 1;
                        JTextField priceField = new JTextField(item.getPrice());
                        formPanel.add(priceField, gbc);
                        row++;
                    
                        // Stock
                        gbc.gridx = 0;
                        gbc.gridy = row;
                        formPanel.add(new JLabel("Stock:"), gbc);
                        gbc.gridx = 1;
                        JSpinner stockSpinner = new JSpinner(new SpinnerNumberModel(item.getStock(), 0, 1000, 1));
                        formPanel.add(stockSpinner, gbc);
                        row++;
                    
                        // Save Button
                        gbc.gridx = 1;
                        gbc.gridy = row;
                        gbc.anchor = GridBagConstraints.EAST;
                        JButton saveButton = new JButton("Save");
                        formPanel.add(saveButton, gbc);
                    
                        saveButton.addActionListener(saveEvent -> {
                            String newTitle = titleField.getText().trim();
                            String newDescription = descriptionField.getText().trim();
                            String newPrice = priceField.getText().trim();
                            int newStock = (int) stockSpinner.getValue();
                    
                            if (newTitle.isEmpty() || newDescription.isEmpty() || newPrice.isEmpty()) {
                                JOptionPane.showMessageDialog(editDialog, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                    
                            item.title = newTitle;
                            item.description = newDescription;
                            item.price = newPrice;
                            item.stock = newStock;
                    
                            JOptionPane.showMessageDialog(editDialog, "Item updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            editDialog.dispose();
                    
                            searchButton.doClick();  // Refresh search results
                        });
                    
                        editDialog.add(formPanel, BorderLayout.CENTER);
                        editDialog.setLocationRelativeTo(frame);  // Center the dialog relative to main frame
                        editDialog.setVisible(true);
                    });
                    
                    

                    deleteButton.addActionListener(event -> {
                        int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this item?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    
                        if (confirmation == JOptionPane.YES_OPTION) {
                            catalogue.remove(item);
                            JOptionPane.showMessageDialog(frame, "Item deleted successfully!", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                            searchButton.doClick();  // Refresh search results
                        }
                    });
                    

                    resultsPanel.add(itemPanel);
                    resultsPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between items
                }
            }

            if (!found) {
                JLabel noResultsLabel = new JLabel("No matching items found.");
                noResultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultsPanel.add(noResultsLabel);
            }

            resultsPanel.revalidate();
            resultsPanel.repaint();
        });

        addButton.addActionListener(e -> {
            Add_item_to_catalogue.showAddItemDialog(frame);
        });
        
        // Display the frame
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // Item class to represent catalogue items
    static class Item {
        private final String id;
        private String title;
        private String description;
        private String price;
        private int stock;
        private String photoPath;

        public Item(String id, String title, String description, String price, int stock, String photoPath) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.price = price;
            this.stock = stock;
            this.photoPath = photoPath;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Title: " + title;
        }
    }
}
