import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class giftSelection {
    private static controller giftController = new controller();
    private static JPanel itemsPanel;
    private static JLabel netTotalLabel;
    private static Map<String, JPanel> itemPanels;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(giftSelection::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Gift Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Search Bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField("Search...");

        JButton searchButton = new JButton("Search");
        JButton homeButton = new JButton("Back");
        searchButton.setBackground(new Color(77, 182, 172));
        homeButton.setBackground(new Color(77, 182, 172));

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        searchPanel.add(homeButton, BorderLayout.WEST);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Items Panel
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new BorderLayout());
        netTotalLabel = new JLabel("Net Total: 0");
        JButton payNowButton = new JButton("Pay Now");
        payNowButton.setBackground(new Color(77, 182, 172));

        footerPanel.add(netTotalLabel, BorderLayout.WEST);
        footerPanel.add(payNowButton, BorderLayout.EAST);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Initialize items and panels
        giftController.initializeItems();
        itemPanels = giftController.getItemPanels();
        updateItemsPanel(giftController.getItems());

        // Search Button Action Listener
        searchButton.addActionListener(e -> {
            String query = searchField.getText().toLowerCase();
            List<String[]> filteredItems = giftController.searchItemsfromRegistry(query);
            if (filteredItems.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No items found.");
            } else {
                updateItemsPanel(filteredItems);
            }
        });

        // Home Button Action Listener
        homeButton.addActionListener(e -> updateItemsPanel(giftController.getItems()));

        // Pay Now Button Action Listener
        payNowButton.addActionListener(e -> {
            PaymentHandler.showPaymentDialog(frame, giftController.getNetTotal());
        });

        frame.add(mainPanel);

        // Set visible at the end
        frame.setVisible(true);
    }

    // update items in the selected list
    private static void updateItemsPanel(List<String[]> itemList) {
        itemsPanel.removeAll();
        for (String[] item : itemList) {
            itemsPanel.add(itemPanels.get(item[0]));
        }
        itemsPanel.revalidate();
        itemsPanel.repaint();
        updateNetTotalLabel();
    }

    // update net total for payment
    private static void updateNetTotalLabel() {
        netTotalLabel.setText("Net Total: " + giftController.getNetTotal());
    }
};
