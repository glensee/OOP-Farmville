package entity.cityfarmer;

import java.util.*;
import entity.interfaces.FarmerHeader;
import login.*;
import entity.friends.*;
import entity.users.*;

public class VisitFriendController implements FarmerHeader{

    private FriendDAO friendDAO;
    private FarmerDAO farmerDAO;
    private UserDAO userDAO;
    private Farmer farmer;
    private String username;
    private HomeMenu home;
    private Scanner sc;
    private User user;
    private PlotDAO plotDAO;
    private StealPlotDAO stealPlotDAO;
    private InventoryDAO inventoryDAO;
    private CityFarmerMenu cfm;

    public VisitFriendController(Farmer farmer){
        this.friendDAO = new FriendDAO();
        this.farmerDAO = new FarmerDAO();
        this.userDAO = new UserDAO();
        this.farmer = farmer;
        this.username = farmer.getUsername();
        this.sc = new Scanner(System.in);
        this.user = userDAO.getUserbyUsername(username);
        this.home = new HomeMenu(user);
        this.plotDAO = new PlotDAO();
        this.stealPlotDAO = new StealPlotDAO();
        this.inventoryDAO = new InventoryDAO();
        this.cfm = new CityFarmerMenu(user);
    }

    public TreeMap<String,User> viewFriends() {
        TreeMap<String,User> map = new TreeMap<>();

        List<Friend> friends = friendDAO.retrieveFriends(user);
        int count = 1;

        for (Friend friend : friends) {
            User user = userDAO.getUserbyUsername(friend.getFriendUsername());
            map.put(count+"",user);
            count++;
        }

        return map;
    }

    public void stealFriend(User friend){

        Farmer friendFarmer = farmerDAO.getFarmerbyUsername(friend.getUsername());

        System.out.println("Name: " + friend.getFullname());
        System.out.println("Title: " + friendFarmer.getRank());
        System.out.println("Gold: " + friendFarmer.getGold());
        TreeMap<String,Plot> plots = getPlots(friend.getUsername());
        displayPlots(plots);
        System.out.print(visitFriendSteal);

        String choice = sc.nextLine();

        switch (choice) {
            case "M":
                home.start();
                break;
            case "F":
                System.out.println();
                cfm.start();
                break;
            case "S":
                steal(username, plots);
                stealFriend(friend);
                break;
            default:
                System.out.println("Invalid Choice!");
                stealFriend(friend);
        }
    }

    public TreeMap<String,Plot> getPlots(String username) {
        ArrayList<Plot> plots = plotDAO.getPlotbyUsername(username);

        TreeMap<String,Plot> result = new TreeMap<>();

        for ( int i = 0 ; i < plots.size() ; i++ ) {
            result.put(i+1 + "",plots.get(i));
        }

        return result;
    }

    public void displayPlots(TreeMap<String,Plot> plots) {
        int size = Integer.parseInt(plots.lastKey());

        for ( int i = 1 ; i <= size ; i++ ) {
            Plot plot = plots.get(""+i);
            String crop = plot.getCropType();
            String output = "<empty>";
            if (crop != null) {
                String time = plot.theTime();
                output = crop + "\t" + displayHash(time);
            }


            System.out.println(""+i + ". " + output);
        }
    }

    private String displayHash(String time) {
        String result = "[";

        if (time.equals("wilted")) {
            result += "  wilted  ]";
        } else {
            int progress = Integer.parseInt(time);

            for (int i = 10 ; i <= 100 ; i += 10 ) {
                result += (i <= progress) ? "#" : "-";
            }

            result += "] " + time + "%";
        }
        return result;
    }

    public void steal(String username,TreeMap<String,Plot> plots) {
        int size = Integer.parseInt(plots.lastKey());
        int totalGold = 0;
        int totalXP = 0;
        String cropStolen = "";

        for ( int i = 1 ; i <= size ; i++ ) {
            Plot plot = plots.get(""+i);
            int amtStolen = stealPlotDAO.amountStolen(plot.getUsername(),i);

            // Checks if the plot has been stolen before, is not empty, is ready for harvesting and has < 20% of its yield stolen
            if ( stealPlotDAO.beenStolen(plot.getUsername(),username,i) || plot.getCropType() == null || !plot.theTime().equals("100") || amtStolen >= 20) {
                continue;
            }

            // Randomises the steal and takes the min of that and the remaining qty available to steal
            Random rand = new Random();
            int potentialSteal = rand.nextInt(4) + 1;
            int steal = Math.min(potentialSteal, 20 - amtStolen);
            stealPlotDAO.add(plot.getUsername(),username,i,steal);

            // Calculate and update the plot yield
            int numStolen = (int) ( steal * plot.getYield() / 100.0f );
            int remainingYield = plot.getYield() - numStolen;
            plot.setYield(remainingYield);
            plotDAO.updateYield(plot);

            // Calculate the gold & xp, add it to the string
            Crop crop = CropDAO.retrieveCrops(plot.getCropType());
            totalGold += crop.salePrice() * numStolen;
            totalXP += crop.xp() * numStolen;
            cropStolen += " " + numStolen + " " + plot.getCropType() + ",";
        }

        // Updates the database + the Farmer's attributes
        farmerDAO.updateEXP(username, farmer.getExpAmount() + totalXP);
        farmerDAO.updateGold(username, farmer.getGold() + totalGold);
        farmer.updateGold(totalGold);
        farmer.addExpAmount(totalXP);

        // Prints the appropriate statement, nothing if none was stolen
        cropStolen = (cropStolen.isEmpty()) ? " nothing" : cropStolen.substring(0,cropStolen.length()-1);
        String result = String.format("You have successfully stolen%s for %d XP, and %d gold.", cropStolen, totalXP,totalGold);
        System.out.println(result);
        System.out.println();
    }
}