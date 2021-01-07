package login;

import entity.users.*;
import entity.controllers.NewsFeedController;
import entity.friends.*;
import entity.interfaces.WebHeader;
import entity.wall.*;
import entity.posts.*;

import java.util.*;

/**
 * This class is responsible for displaying the Main Menu Page after User logs in
 * <p>
 * This class implements {@link WebHeader} which contains all the Strings of the Header
 * and Options to be printed
 */
public class HomeMenu implements WebHeader {

    private User user;
    private Scanner sc;

    /* 
    For us to do any logic related stuff,
    to separate UI and Input surface from logic controls
    that involves interaction with DAO classes (below)
    */

    private NewsFeedController nfc;
    private FriendController friendCtrl;

    /**
     * HomeMenu Constructor
     * <p>
     * Creates an HomeMenu Object Instance which handles display of Main Menu User Interface for Users who
     * logged in
     * 
     * @param user User Object created from {@link LoginController}
     * 
     */
    public HomeMenu(User user){
        this.user = user;
        this.nfc = new NewsFeedController(user);
        this.friendCtrl = new FriendController(user);
        this.sc = new Scanner(System.in);
    }

    /**
     * Starts the Display of the Main Menu Page
     */
    public void start(){
        Integer choice;
        String fullName = user.getFullname();

        try{
            System.out.println();
            System.out.println(welcomeMenuHeader);
            System.out.println("Welcome " + fullName + " !");
            System.out.println(welcomeMenuOptions);
            System.out.print(selectChoice);

            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    newsFeed();
                    break;
                case 2:
                    viewWall(user,user);
                    break;
                case 3:
                    myFriends();
                    break;
                case 4:
                    cityFarmer();
                    break;
                case 5:
                    System.out.println("You are logged out");
                    StartMenu menu = new StartMenu();
                    menu.start();
                    break;
                default:
                    System.out.println("Enter choice between 1 & 5");
                    start();
            }

        } catch(InputMismatchException e) {
            System.out.println("Invalid choice!");
            sc.next();
            start();
        }
    }

    /**
     * Displays newsFeed for User and calls on {@link NewsFeedController} method to display Post for User
     */
    public void newsFeed(){
        System.out.println();
        System.out.println(newsFeedWall); //header for the news feed

        ArrayList<Post> postList = nfc.displayPost();
        if(postList.isEmpty()){
            System.out.println("No post on your newsfeed yet! Go make some friends you loner.");
        }

        System.out.print(newsFeedOption);
        String userInput = sc.next();

        if (userInput.length() > 1){
            String action = userInput.substring(0, 1);
            Integer target = Integer.parseInt(userInput.substring(1));
            // System.out.println(target);

            if (action.equals("T")) {
                ViewThread thread = new ViewThread(user);
                // System.out.println(target); -- For Debugging
                
                try {
                    Post post = postList.get(target-1);
                    thread.viewThread(target, post);

                } catch(IndexOutOfBoundsException e) {
                    newsFeed();
                }

            } else {
                newsFeed();
            }

        } else if (userInput.equals("M")){
            start();

        } else {
            newsFeed();
        }
    }

    /**
     * This method generates the Wall of User. It creates a {@link Wall} object using the parameters target 
     * and accesser to create the Wall belonging to the person
     * 
     * @param target User Object who's wall is to be displayed
     * @param accesser User Object who is looking at the wall
     */
    public void viewWall(User target, User accesser) {
        Wall wall = new Wall(target,accesser);
        System.out.println();
        wall.viewOwnWall();
    }

    /**
     * This method displays the User's Friends on the screen and allows the User to input options
     * given by the Menu for this display
     */
    public void myFriends(){
        System.out.println();
        System.out.println(friendsWallHeader);
        System.out.println("Welcome " + user.getFullname() + " !");
        System.out.println("My Friends: ");

        HashMap<Integer,String> countMap = friendCtrl.displayFriends();
        System.out.println();

        System.out.print(friendsWallChoice);
        String userInput = sc.next();

        String action;
        String targetUsername;

        if (userInput.length() > 1) {

            targetUsername = userInput.substring(1);
            Integer targetNumber = Integer.valueOf(targetUsername);
            // System.out.println(targetUsername); Debugging

            targetUsername = countMap.get(targetNumber);
            UserDAO userDAO = new UserDAO();
            User target = userDAO.getUserbyUsername(targetUsername);
            
            if (target == null) {
                myFriends();
            }
            
            action = userInput.substring(0,1);

            switch(action) {
                
                case "M":
                    start();
                    break;

                case "U":
                    friendCtrl.unfriend(targetUsername);
                    myFriends();
                    break;

                case "A":
                    friendCtrl.accept(targetUsername);
                    myFriends();
                    break;

                case "R":
                    friendCtrl.reject(targetUsername);
                    myFriends();
                    break;

                case "V":
                    friendCtrl.view(user,target);
                    break;

                default:
                    System.out.println("Invalid choice!");
                    myFriends();
            }

        } else {

            action = userInput;

            switch (action) {

                case "M":
                    start();
                    break;

                case "Q":
                    System.out.print("Enter the username > ");
                    
                    String givenUsername = sc.next();
                    FriendDAO friendDAO = new FriendDAO();
                    ArrayList<Friend> friendList = friendDAO.retrieveFriends(user);
                    
                    for (Friend friend : friendList) {
                        String name = friend.getFriendUsername();
                        
                        if(name.equals(givenUsername)){
                            System.out.println("You are already friends with this user!");
                            myFriends();
                            break;
                        }
                    } 
                    
                    try {
                        friendCtrl.request(givenUsername);
                        myFriends();
                        break;
                    } catch(Exception e) {
                        System.out.println("You have already sent that user a request!");
                        myFriends();
                        break;
                    }
                    
                default:
                    System.out.println("Invalid choice!");
                    myFriends();

            }

        }
    }

    /**
     * Creates a {@link CityFarmerMenu} Object which handles user interface for CityFarmer Features
     * 
     */
    public void cityFarmer(){
        System.out.println();
        CityFarmerMenu cfm = new CityFarmerMenu(user);
        cfm.start();
    }

}