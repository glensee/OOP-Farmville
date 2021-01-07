package entity.cityfarmer;

public class Inventory {

    // Each Farmer has an Inventory which contains Multiple Items (Stored in an Array)
    private String username;
    private String item;
    private int quantity;

    public Inventory(String username, String item, int quantity) {
        this.username = username;
        this.item = item;
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

}