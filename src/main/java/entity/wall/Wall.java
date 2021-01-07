package entity.wall;

import entity.users.*;
import entity.friends.*;
import entity.interfaces.WebHeader;
import login.HomeMenu;
import entity.posts.*;
import java.util.*;
import entity.cityfarmer.*;

/**
 * Wall Class
 */
public class Wall implements WebHeader{

    private User target;
    private User accesser;
    private HomeController homeCtrl;

    /**
     * General Constructor to create a Wall
     * @param target targeted {@link User}
     * @param accesser this {@link User}
     */
    public Wall(User target, User accesser){
        this.target = target;
        this.accesser = accesser;
        this.homeCtrl = new HomeController(accesser);
    }

    /**
     * When this {@link User} wishes to view his own wall
     */
    public void viewOwnWall() {

        FriendDAO friendDAO = new FriendDAO();
        FarmerDAO farmerDAO = new FarmerDAO();

        String username = target.getUsername();
        String fullName = target.getFullname();

        Farmer farmer = farmerDAO.getFarmerbyUsername(target.getUsername());
        String farmerRank = farmer.getRank();
        String rankString = "start";

        ArrayList<Friend> friendList = friendDAO.retrieveFriends(target);
        ArrayList<Farmer> farmerList = new ArrayList<>();

        for (Friend friend : friendList) {
            String friendUsername = friend.getFriendUsername();
            Farmer friendFarmer = farmerDAO.getFarmerbyUsername(friendUsername);
            farmerList.add(friendFarmer);
        }

        int rank = farmerList.size() + 1;

        for (Farmer friendFarmer : farmerList) {
            // System.out.println(friendFarmer.getGold());
            if(farmer.compareTo(friendFarmer) > 0 ){
                rank--;
            }
        }

        if (rank == 1) {
            rankString = "1st";
        }
        if (rank == 2) {
            rankString = "2nd";
        }
        if (rank == 3) {
            rankString = "3rd";
        }
        if (rank > 3 && rank < 21) {
            rankString = rank + "th";
        }
        
        if (rank >= 21) {
            rankString = Integer.toString(rank);
            String lastNum = rankString.substring(rankString.length()-1,rankString.length()-1);
            
            if (Integer.parseInt(lastNum) == 1) {
                rankString = rank + "st";

            } else if (Integer.parseInt(lastNum) == 2) {
                rankString = rank + "nd";

            } else if(Integer.parseInt(lastNum) == 3){
                rankString = rank + "rd";
            
            } else {
                rankString = rank + "th";
            }
        }

        System.out.println(wallMenuHeader);
        System.out.println("About " + username);
        System.out.println("Full Name: " + fullName);
        System.out.println(farmerRank + " Farmer, " + rankString + " Richest");
        System.out.println();

        HashMap<Integer, Post> postMap = homeCtrl.displayPost();

        Scanner sc = new Scanner(System.in);
        System.out.print(wallMenuOptions);
        String userInput = sc.nextLine();

        if (userInput.length() > 1){
            String action = userInput.substring(0, 1);
            Integer targetThread = Integer.parseInt(userInput.substring(1));
            // System.out.println(target);

            if (action.equals("T")) {
                ViewThread thread = new ViewThread(accesser);
                Post post = postMap.get(targetThread);
                if(post == null){
                    viewOwnWall();
                } else {
                   thread.viewThread(targetThread, post);
                }
            } else {
                viewOwnWall();
            }

        } else {

            switch (userInput) {
                
                case "M":
                    HomeMenu homeMenu = new HomeMenu(accesser);
                    homeMenu.start();
                    break;
                
                case "A":
                    GiftDAO giftDAO = new GiftDAO(); 
                    giftDAO.acceptAllGifts(username);
                    viewOwnWall();
                    break;
                
                case "P":
                    System.out.print("Enter your Post here > ");
                    String postContent = sc.nextLine();
                    homeCtrl.createPostAtOwnWall(postContent,username);
                    viewOwnWall();
                    break;
               
                default:
                    System.out.println("Invalid choice!");
                    viewOwnWall();
            }
        }

    }
    
    /**
     * This method allows this User to view another's wall
     */
    public void viewOtherWall() {

        String username = target.getUsername();

        System.out.println("== Social Magnet :: " + username + "'s Wall ==");
        System.out.println("Welcome " + accesser.getFullname() + "!");
        System.out.println();

        HomeController targetHomeCtrl = new HomeController(target);
        HashMap<Integer, Post> postMap = targetHomeCtrl.displayFriendPost();

        //display friends
        System.out.println(username + "'s Friend");
        Integer friendCount = 1;
        FriendDAO friendDAO = new FriendDAO();
        UserDAO userDAO = new UserDAO();

        ArrayList<Friend> targetFriends = friendDAO.retrieveFriends(target);
        ArrayList<Friend> accesserFriends = friendDAO.retrieveFriends(accesser);

        for (Friend friend : targetFriends) {
            String friendUsername = friend.getFriendUsername();
            User friendUserObj = userDAO.getUserbyUsername(friendUsername);
            String friendFullname = friendUserObj.getFullname();
            
            if (accesserFriends.contains(friend)) {
                System.out.println(friendCount +". " +  friendFullname + " ( Common Friend )");
                friendCount++;
            } else {
                System.out.println(friendCount +". " +  friendFullname);
                friendCount++;
            }
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("[M]ain | [T]hread | [P]ost >");
        String userInput = sc.nextLine();

        if (userInput.length() > 1) {
            String action = userInput.substring(0, 1);
            Integer targetThread = Integer.parseInt(userInput.substring(1));
            // System.out.println(target);

            if (action.equals("T")) {
                ViewThread thread = new ViewThread(accesser);
                Post post = postMap.get(targetThread);
                if (post == null) {
                    viewOtherWall();
                } else {
                   thread.viewThread(targetThread, post);
                }
            }
        } else {

            switch (userInput) {
                case "M":
                    HomeMenu homeMenu = new HomeMenu(accesser);
                    homeMenu.start();
                    break;
                
                case "P":
                    System.out.println("Enter your Post here > ");
                    String postContent = sc.nextLine();
                    homeCtrl.createPostAtOtherWall(postContent,accesser.getUsername(),username);
                    viewOtherWall();
                    break;
                
                default:
                    System.out.println("Invalid choice!");
                    viewOtherWall();
            }
        }
    }
}