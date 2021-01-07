package entity.cityfarmer;

import java.util.*;
import login.*;
import entity.interfaces.FarmerHeader;
import entity.users.*;

public class MyFarmController implements FarmerHeader {

    private PlotDAO plotDAO;
    private FarmerDAO farmerDAO;
    private InventoryDAO inventoryDAO;
    private HomeMenu home;
    private Farmer farmer;

    public MyFarmController(User user) {
        this.home = new HomeMenu(user);
        this.plotDAO = new PlotDAO();
        this.farmerDAO = new FarmerDAO();
        this.inventoryDAO = new InventoryDAO();
        this.farmer = farmerDAO.getFarmerbyUsername(user.getUsername());
    }

    // Plants the plot
    public void myPlant(Plot plot) {
        System.out.println();

        // Checks if it is not empty, wilted, in-progress or ready for harvest
        if ( plot.getCropType() != null ) {

            String theTime = plot.theTime();
            if ( theTime.equals("wilted") ) {
                System.out.println("Your crop has wilted, please clear it before planting a new crop.");
            } else if ( theTime.equals("100") ) {
                System.out.println("Please harvest your plot before planting a new crop");
            } else {
                System.out.println("Your plot currently has a crop, you may plant a new crop once it is cleared/harvested.");
            }
        } else {

            System.out.println("Select the crop:");
            ArrayList<Inventory> inventories = inventoryDAO.getInventory(plot.getUsername());
            int count = 1;
            int total = 0;
            TreeMap<Integer,Inventory> inv = new TreeMap<>();

            // Prints out the inventory available while placing it in a TreeMap
            while (total < inventories.size()) {
                Inventory myInv = inventories.get(total);
                if ( myInv.getQuantity() != 0 ) {
                    System.out.println(count + ". " + myInv.getItem());
                    inv.put( count , myInv );
                    count++;
                }
                total++;
            }

            System.out.print(farmlandHarvest);

            Scanner sc = new Scanner(System.in);
            String choice = sc.nextLine();

            // Checks what input is entered by the user
            try {
                if ( choice.equals("M") ) {
                    home.start();
                    return;

                } else if ( choice.equals("F") ) {
                    System.out.println();
                    return;

                } else {
                    int n = Integer.parseInt(choice);

                    // Checks the integer if it is inside the TreeMap
                    Inventory item = inv.get(n);
                    if (item == null) {
                        System.out.println("Invalid choice!");
                        return;
                    }

                    // Removes 1 from the inventory and plants the plot
                    inventoryDAO.updateInventory(item.getUsername(),item.getItem(),item.getQuantity() - 1);
                    plot.changeCropType(item.getItem());
                    System.out.println( (plotDAO.plantPlot(plot)) ? item.getItem() + " planted on plot " + plot.getPlotID() + "." : "There was an error in planting the crop");

                }
            } catch (Exception e) {
                System.out.println("Invalid choice!");
            }
        }
        System.out.println();
    }

    // Clears the crop
    public void myClear(Plot plot) {
        // Checks if it's empty
        if (plot.getCropType() == null) {
            System.out.println("The plot is empty, there is nothing to clear.");
        // Checks if it's wilted
        } else if ( !plot.theTime().equals("wilted") ){
            System.out.println("The plot has yet to wilt.");
        // Checks if the farmer has 5 gold to clear the crop
        } else {
            int gold = farmer.getGold();
            if (gold < 5) {
                System.out.println("You do not have enough money to clear the wilted crop");
            // Remove 5 gold and clear the plot
            } else if (farmerDAO.updateGold(farmer.getUsername(), gold - 5) && plotDAO.clearPlot(plot)){
                farmer.updateGold(-5);
                System.out.println("Plot " + plot.getPlotID() + " has been cleared.");
            }
        }
        System.out.println();
    }

    // Harvest the crop
    public void myHarvest(String username, TreeMap<String,Plot> plots) {
        int size = Integer.parseInt(plots.lastKey());
        int totalGold = 0;
        int totalXP = 0;
        String crops = "";
        PlotDAO plotDAO = new PlotDAO();

        for ( int i = 1 ; i <= size ; i++ ) {
            Plot plot = plots.get(""+i);
            // Ignores the crop if it's empty, in-progress or wilted
            if ( plot.getCropType() == null || !plot.theTime().equals("100")) {
                continue;
            }

            // Add it to the total gold & xp alongside the print statement
            Crop crop = CropDAO.retrieveCrops(plot.getCropType());
            totalGold += crop.salePrice() * plot.getYield();
            totalXP += crop.xp() * plot.getYield();
            crops += " " + plot.getYield() + " " + plot.getCropType() + ",";
            plotDAO.clearPlot(plot);
        }

        // Update the farmer's gold and xp
        farmer.addExpAmount(totalXP);
        farmer.updateGold(totalGold);
        farmerDAO.updateEXP(username, farmer.getExpAmount() + totalXP);
        farmerDAO.updateGold(username, farmer.getGold() + totalGold);

        // Prints the appropriate statement, it returns nothing if no crops were harvested
        crops = (crops.isEmpty()) ? " nothing" : crops.substring(0,crops.length()-1);
        String result = String.format("You have harvested%s for %d XP, and %d gold.", crops, totalXP,totalGold);
        System.out.println(result);
        System.out.println();
    }
}