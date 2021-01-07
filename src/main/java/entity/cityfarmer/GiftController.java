package entity.cityfarmer;
import java.util.*;

import entity.friends.FriendDAO;

public class GiftController {

    private Farmer farmer;
    private GiftDAO giftDAO;
    private FarmerDAO farmerDAO;
    private FriendDAO friendDAO;

    public GiftController(Farmer farmer) {
        this.farmer =  farmer;
        this.giftDAO = new GiftDAO();
        this.farmerDAO = new FarmerDAO();
        this.friendDAO = new FriendDAO();
    }

    // Sends a gift
    public void send( String item, String sendee ) {
        String username = farmer.getUsername();
        List<Gift> gifts = giftDAO.retrieveTodayGifts(username);

        // Checks if the user has send more than 5 gifts on that day
        if (gifts.size() >= 5) {
            System.out.println("You may only send up to 5 gifts a day.");

        } else {
            int count = (gifts == null) ? 0 : gifts.size();
            String[] friends = sendee.split(",");
            boolean worked = true;
            for ( String friend : friends ) {

                // Double checks should the user exceed the gift sending in a single line
                if (count == 5) {
                    System.out.println("You may only send up to 5 gifts a day.");
                    worked = false;
                } else {

                    friend = friend.strip();

                    // Checks if the user is gifting to themselves
                    if ( friend.equals(username) ) {
                        System.out.println("You may not gift to yourself!");
                        worked = false;
                    // Checks if the user has already given a gift to that friend today
                    } else if (giftDAO.retrieveTodayGiftToFriend(username, friend) != null) {
                        System.out.println("You have already given a gift to " + friend + " today!");
                        worked = false;
                    // Checks if the friend is a user in the database
                    } else if (friendDAO.retrieveFriend(username,friend) == null) {
                        System.out.println(friend + " is not your friend");
                        worked = false;

                    } else {
                        // Sends the gift
                        Gift gift = new Gift(username,friend, item,null);
                        boolean sendIt = giftDAO.addGift(gift);

                        if (!sendIt) {
                            worked = false;
                        } else {
                            count++;
                        }
                    }
                }

            }
            // Prints the appropriate statement
            System.out.println((worked) ? "Gift posted to your friends' wall." : "There was an error in sending gifts to one or more of your friends" );
            System.out.println();
        }
    }
}