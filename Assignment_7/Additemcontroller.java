import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Additemcontroller {
    private final Map<String, String[]> selectedItems = new HashMap<>();

    // Add an item to the registry
    public boolean addItem(String name, String price, int quantity, int availableQuantity) {
        if (quantity > 0 && quantity <= availableQuantity) {
            selectedItems.put(name, new String[]{price, String.valueOf(quantity), String.valueOf(availableQuantity)});
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
                selectedItems.put(name, new String[]{details[0], String.valueOf(newQuantity), details[2]});
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
}
