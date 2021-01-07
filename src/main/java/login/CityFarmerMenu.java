package login;

import java.util.*;
import entity.interfaces.FarmerHeader;
import entity.users.*;
import entity.cityfarmer.*;

/**
 * CityFarmerMenu
 */
public class CityFarmerMenu implements FarmerHeader{
    private User user;
    private Farmer farmer;
    private HomeMenu menu;
    private Scanner sc;
    private String choice;


    public CityFarmerMenu (User user){
        this.user = user;
        this.menu = new HomeMenu(user);
        this.sc = new Scanner(System.in);

        FarmerDAO farmerDAO = new FarmerDAO();
        Farmer farmer = farmerDAO.getFarmerbyUsername(user.getUsername());
        this.farmer = farmer;
    }

    // COMPLETED
    public void start(){
        String fullName = user.getFullname();

        System.out.println(cityFarmerHeader);
        System.out.println("Welcome " + fullName + " !");
        System.out.println("Title: " + farmer.getRank() + "\t\tGold: " + farmer.getGold() + " gold");

        System.out.println();
        System.out.println(cityFarmerOptions);
        System.out.print(cityFarmerChoice);
        choice = sc.nextLine();

        switch (choice) {
            case "M":
                menu.start();
                break;
            case "1":
                myFarm();
                break;
            case "2":
                myStore();
                break;
            case "3":
                myInventory();
                break;
            case "4":
                visitFriend();
                break;
            case "5":
                sendGift();
                break;
            default:
                System.out.println("Invalid Choice!");
                start();
        }
    }

    public void myFarm(){

        System.out.println(farmlandHeader);
        System.out.println("Welcome " + user.getFullname() + " !");
        System.out.println("Title: " + farmer.getRank() + "\t\tGold: " + farmer.getGold() + " gold");

        System.out.println();
        MyFarmController mfc = new MyFarmController(user);
        VisitFriendController vfc = new VisitFriendController(farmer);
        TreeMap<String,Plot> plots = vfc.getPlots(farmer.getUsername());
        System.out.println("You have " + Integer.parseInt(plots.lastKey()) + " plots of land.");
        vfc.displayPlots(plots);
        System.out.print(farmlandChoice);
        choice = sc.nextLine();

        if (choice.equals("M")) {
            menu.start();
            return;
        } else if (choice.equals("F")) {
            start();
            return;
        } else if (choice.equals("H")) {
            mfc.myHarvest(farmer.getUsername(),plots);
        } else {
            try {
                Character action = choice.charAt(0);
                String num = choice.substring(1,2);
                Plot plot = plots.get(num);

                if (action == 'P' && plot != null) {
                    mfc.myPlant(plot);

                } else if ( action == 'C' && plot != null ) {
                    mfc.myClear(plot);

                } else {
                    System.out.println("Invalid Choice!");
                }

            } catch (Exception e) {
                System.out.println("Invalid Choice!");
            }
            myFarm();
            return;
        }
    }

    // COMPLETED
    public void myStore(){

        System.out.println(storeHeader);
        System.out.println("Welcome " + user.getFullname() + " !");
        System.out.println("Title: " + farmer.getRank() + "\t\tGold: " + farmer.getGold() + " gold");

        System.out.println();
        System.out.println("Seeds Available:");
        StoreController store = new StoreController(farmer);
        System.out.println(store.showStore());

        System.out.print(storeChoice);
        choice = sc.nextLine();
        int qty;
        String buy;
        switch (choice) {
            case "M":
                menu.start();
                break;
            case "F":
                start();
                break;
            case "1":
                System.out.print("Enter quantity > ");
                qty = Integer.parseInt(sc.nextLine());
                buy = store.purchase(1,qty);
                System.out.println(buy);
                System.out.println();
                myStore();
                break;
            case "2":
                System.out.print("Enter quantity > ");
                qty = Integer.parseInt(sc.nextLine());
                buy = store.purchase(2,qty);
                System.out.println(buy);
                System.out.println();
                myStore();
                break;
            case "3":
                System.out.print("Enter quantity > ");
                qty = Integer.parseInt(sc.nextLine());
                buy = store.purchase(3,qty);
                System.out.println(buy);
                System.out.println();
                myStore();
                break;
            case "4":
                System.out.print("Enter quantity > ");
                qty = Integer.parseInt(sc.nextLine());
                buy = store.purchase(4,qty);
                System.out.println(buy);
                System.out.println();
                myStore();
                break;
            default:
                System.out.println("Invalid Choice!");
                myStore();
        }
    }

    // COMPLETED
    public void myInventory(){

        System.out.println(inventoryHeader);
        System.out.println("Welcome " + user.getFullname() + " !");
        System.out.println("Title: " + farmer.getRank() + "\t\tGold: " + farmer.getGold() + " gold");

        System.out.println();

        InventoryDAO invDAO = new InventoryDAO();
        List<Inventory> inv = invDAO.getInventory(user.getUsername());
        System.out.println("My Seeds:");
        int count = 1;
        int total = 0;
        while (total < inv.size()) {
            if ( inv.get(total).getQuantity() != 0 ) {
                System.out.printf("%d. %d Bags of %s",count,inv.get(total).getQuantity(),inv.get(total).getItem());
                System.out.println();
                count++;
            }
            total++;
        }

        System.out.println();

        System.out.print(inventoryChoice);
        choice = sc.nextLine();

        switch (choice) {
            case "M":
                menu.start();
                break;
            case "F":
                start();
                break;
            default:
                System.out.println("Invalid Choice!");
                myInventory();
        }
    }

    // COMPLETED
    public void visitFriend(){

        System.out.println(inventoryHeader);
        System.out.println("Welcome " + user.getFullname() + " !");
        System.out.println("Title: " + farmer.getRank() + "\t\tGold: " + farmer.getGold() + " gold");

        System.out.println();

        System.out.println("My Friends:");

        VisitFriendController visitFriendController = new VisitFriendController(farmer);
        TreeMap<String,User> map = visitFriendController.viewFriends();
        for(Map.Entry<String,User> entry : map.entrySet()) {
            String key = entry.getKey();
            User value = entry.getValue();

            System.out.printf("%s. %s (%s)",key,value.getFullname(),value.getUsername());
            System.out.println();
        }

        System.out.println();

        System.out.print(inventoryChoice);

        choice = sc.nextLine();

        if (choice.equals("M")) {
            menu.start();
            return;
        } else if (choice.equals("F")) {
            start();
            return;
        } else {
            User user = map.get(choice);
            System.out.println();

            if (user == null) {
                System.out.println("Invalid Choice!");
            } else {
                visitFriendController.stealFriend(user);
            }
            visitFriend();
            return;
        }
    }

    // COMPLETED
    public void sendGift(){

        System.out.println(sendGiftHeader);
        System.out.println("Welcome " + user.getFullname() + " !");
        System.out.println("Title: " + farmer.getRank() + "\t\tGold: " + farmer.getGold() + " gold");

        System.out.println();

        System.out.println("Gifts Available:");

        System.out.println("1. 1 Bag of Papaya Seeds");
        System.out.println("2. 1 Bag of Pumpkin Seeds");
        System.out.println("3. 1 Bag of Sunflower Seeds");
        System.out.println("4. 1 Bag of Watermelon Seeds");
        System.out.print(sendGiftChoice);
        choice = sc.nextLine();
        GiftController giftController = new GiftController(farmer);
        String sendee;

        switch (choice) {
            case "R":
                start();
                break;
            case "1":
                System.out.print("Send to> ");
                sendee = sc.nextLine();
                giftController.send("Papaya", sendee);
                sendGift();
                break;
            case "2":
                System.out.print("Send to> ");
                sendee = sc.nextLine();
                giftController.send("Pumpkin", sendee);
                sendGift();
                break;
            case "3":
                System.out.print("Send to> ");
                sendee = sc.nextLine();
                giftController.send("Sunflower", sendee);
                sendGift();
                break;
            case "4":
                System.out.print("Send to> ");
                sendee = sc.nextLine();
                giftController.send("Watermelon", sendee);
                sendGift();
                break;
            default:
                System.out.println("Invalid Choice!");
                System.out.println();
                sendGift();
        }
    }

}