package entity.friends;
import entity.users.*;
import entity.requests.*;
import entity.wall.*;
import java.util.*;

/**
 * This class handles all the logic for My Friend Page of Social Magnet
 */
public class FriendController {

    private FriendDAO friendDAO;
    private RequestsDAO requestsDAO;
    private User user;

    /**
     * General Constructor for FriendController
     * <p>
     * Creates a FriendController that handles what User should see.
     * 
     * Takes in 
     * @param user User Object part of FriendController Attribute
     */
    public FriendController(User user){
        FriendDAO friendDAO = new FriendDAO();
        RequestsDAO requestsDAO = new RequestsDAO();
        this.requestsDAO = requestsDAO;
        this.friendDAO = friendDAO;
        this.user = user;
    }


    /**
     * This method return list of friends of {@link User}
     * 
     * @return HashMap of key Integer type and value of String type of Friends beloing to User
     */
    public HashMap<Integer,String> displayFriends(){
        User user = this.user;
        Integer count = 1;

        HashMap<Integer,String> countMap = new HashMap<Integer,String>();
        ArrayList<Friend> myFriends = friendDAO.retrieveFriends(user);
        ArrayList<Requests> myRequests = requestsDAO.retrieveAllByUser(user);

        for (Friend friend : myFriends) {
            String friendName = friend.getFriendUsername();
            countMap.put(count,friendName);
            System.out.println(count+ ". " + friendName);
            count++;
        }

        System.out.println("My Requests: ");
        for (Requests requests : myRequests) {
            String senderUsername = requests.getOtherParty();
            System.out.println(count+ ". " + senderUsername);
            countMap.put(count,senderUsername);
            count++;
        }

        return countMap;

    }

    /**
     * This method is called when User wants to unfriend another User
     * 
     * @param username User's Username
     */
    public void unfriend(String username){
        FriendDAO friendDAO = this.friendDAO;
        User user = this.user;
        String unfrienderName = user.getUsername();
        Friend targetFriend = new Friend(unfrienderName,username);
        friendDAO.delete(targetFriend);
    }


    /**
     * This method is called when User wished to request another User to befriend with
     * 
     * @param targetUsername - {@link String} Username of another User who User wishes to befriend with
     * @throws Exception - Throws an Exception when the request fails
     */
    public void request(String targetUsername) throws Exception {
        User user = this.user;
        String senderUsername = user.getUsername();
        Requests request = new Requests(senderUsername,targetUsername);

        try {
            requestsDAO.send(request);
        } catch(Exception e) {
            throw e;
        }
        
    }

    /**
     * This method is called when User accepts Sender's Friend Requests
     * 
     * @param senderUsername Username who sent the Friend Requests
     */
    public void accept(String senderUsername){
        String accepterUsername = user.getUsername();

        Friend newFriend = new Friend(accepterUsername, senderUsername);
        Requests oldRequest = new Requests(accepterUsername,senderUsername);
        requestsDAO.delete(oldRequest);
        friendDAO.add(newFriend);
    }

    /**
     * This method is used when User rejects the Sender's Friend Request
     * @param senderUsername Username who sent the Friend Requests
     */
    public void reject(String senderUsername){
        String receivingUsername = user.getUsername();
        System.out.println(senderUsername);
        System.out.println(receivingUsername);

        Requests rejectedRequest = new Requests(receivingUsername,senderUsername);
        requestsDAO.delete(rejectedRequest);
    }

    /**
     * This method is used when User views user's friend's wall
     * 
     * @param user - User we are logged in as.
     * @param target - User that we wish to see the wall of.
     */
    public void view(User user, User target){
        Wall wall = new Wall(target,user);
        wall.viewOtherWall();
    }
}