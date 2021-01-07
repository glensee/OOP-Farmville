package entity.interfaces;

/**
 * FarmerHeader Interface to store all the Strings  to be printed out and makes it easier to edit and change
 * accordingly
 */
public interface FarmerHeader {

        // For Farmers
        String cityFarmerHeader = "== Social Magnet :: City Farmers ==";

        String cityFarmerOptions = "1. My Farmland \n2. My Store \n3. My Inventory \n4. Visit Friend \n5. Send Gift";

        String cityFarmerChoice = "[M]ain | Enter your choice > ";

        // For Farmland
        String farmlandHeader = "== Social Magnet :: City Farmers :: My Farmland ==";

        String farmlandChoice = "[M]ain | City [F]armers | [P]lant | [C]lear | [H]arvest > ";

        String farmlandHarvest = "[M]ain | City [F]armers | Select Choice > ";

        // Store
        String storeHeader = "== Social Magnet :: City Farmers :: My Store ==";

        String storeChoice = "[M]ain | City [F]armers | Select choice > ";

        // Inventory
        String inventoryHeader = "== Social Magnet :: City Farmers ==";

        String inventoryChoice = "[M]ain | City [F]armers | Select choice > ";

        // Vist Friend
        String visitFriendHeader = "== Social Magnet :: City Farmers :: Visit Friend ==";

        String visitFriendChoice = "[M]ain | City [F]armers Main | Select choice > ";

        String visitFriendSteal = "[M]ain | City [F]armers Main | [S]teal > ";

        // Send Gift

        String sendGiftHeader = "== Social Magnet :: City Farmers :: Send Gift ==";

        String sendGiftChoice = "[R]eturn to main | Select choice > ";
}