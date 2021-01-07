package entity.cityfarmer;

import java.util.*;

public class StoreController {
    private Farmer farmer;
    private List<Crop> crops;
    private FarmerDAO farmerDAO;
    private InventoryDAO inventoryDAO;

    public StoreController(Farmer farmer) {
        this.farmer = farmer;
        this.crops = CropDAO.retrieveAllCrops();
        this.farmerDAO = new FarmerDAO();
        this.inventoryDAO = new InventoryDAO();
    }

    // Displays the list of seeds available for purchase and its relevant details
    public String showStore() {
        String result = "";

        for (int i = 0 ; i < crops.size() ; i++) {
            Crop crop = crops.get(i);
            result += String.format("%d. %s\t- %d gold",i+1,crop.getCropName(),crop.getCost()) + "\n";
            int growthTime = crop.getGrowthTime();

            String time = (growthTime >= 60) ? growthTime / 60 + " hours" : growthTime + " mins";

            // Removes the 's' in mins if it is exactly an hour
            if (growthTime == 60) {
                time = time.substring(0, time.length() - 1);
            }
            result += "   Harvest in: " + time + "\n";
            result += "   XP Gained: " + crop.xp() + "\n";
        }
        return result;
    }

    public String purchase( int index , int qty ) {
        index --;
        Crop crop = crops.get(index);
        int cost = crop.getCost() * qty;
        String username = farmer.getUsername();
        String item = crop.getCropName();

        // Checks if farmer has enough gold to purchase the seeds
        if ( cost > farmer.getGold() ) return "Insufficient Gold!";

        boolean updateFarmer = farmerDAO.updateGold(username, farmer.getGold() - cost);
        farmer.updateGold(-cost);
        if (!updateFarmer) return "An error occurred during transaction.";

        Inventory inv = inventoryDAO.getAnInventory(username, item);

        boolean updateInv = inventoryDAO.updateInventory(username, item, inv.getQuantity() + qty);

        if (updateInv) {
            return qty + " bags of seeds purchased for " + cost + " gold.";
        } else {
            // Refunds the farmer their gold back should the transaction fail
            farmerDAO.updateGold(username, farmer.getGold());
            return "An error occured during transaction";
        }
    }
}