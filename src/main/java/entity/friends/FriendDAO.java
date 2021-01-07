package entity.friends;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import entity.interfaces.*;
import entity.users.*;

/**
 * Data Access Object Class for Friend Entity
 * 
 * Extends {@link ConnectionManager} for String type attributes needed for JDBC Connector to MySQL Database
 */
public class FriendDAO implements ConnectionManager {

    private String dbUsername = "root";
    private String dbPassword = "";

    /**
     * This method returns a list of Friends belonging to this User 
     * @param user {@link User}
     * @return An ArrayList of Friend belonging to this User
     */
    public ArrayList<Friend> retrieveFriends(User user){

        String sql = "SELECT * FROM friend WHERE username = ?";

        ArrayList<Friend> friendList = new ArrayList<>();

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1,user.getUsername());

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String friendUsername = rs.getString("friend_username");
                friendList.add(new Friend(user.getUsername(),friendUsername));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return friendList;
    }

    /**
     * This method retrieve a Friend of this User
     * 
     * @param username String username of this User
     * @param friend String username of this User's Friend
     * @return {@link Friend} belonging to this User
     */
    public Friend retrieveFriend(String username, String friend){

        String sql = "SELECT * FROM friend WHERE username = ? AND friend_username = ?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, username);
            stmt.setString(2, friend);

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Friend(rs.getString("username"),rs.getString("friend_username"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method returns true if this User successfully added a Friend and the Friend Database is updated
     * successfully.
     * 
     * <p>
     * Otherwise return false
     * 
     * @param friend {@link Friend} of this User
     * @return boolean
     */
    public boolean add(Friend friend){

        String sql = "INSERT INTO friend VALUES(?,?)";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt1 = conn.prepareStatement(sql);
            PreparedStatement stmt2 = conn.prepareStatement(sql);

            // bind the parameters
            stmt1.setString(1, friend.getUsername());
            stmt1.setString(2, friend.getFriendUsername());
            stmt2.setString(1, friend.getFriendUsername());
            stmt2.setString(2, friend.getUsername());

            // step 4: executes the query
            stmt1.executeUpdate();
            stmt2.executeUpdate();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * This method returns true if this User successfully deleted a Friend and the Friend Database is updated
     * successfully.
     * 
     * <p>
     * Otherwise return false
     * 
     * @param friend {@link Friend} of this User
     * @return boolean
     */
    public boolean delete(Friend friend) {

        String sql = "DELETE FROM friend WHERE username = ? AND friend_username = ?";

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt1 = conn.prepareStatement(sql);
            PreparedStatement stmt2 = conn.prepareStatement(sql);

            // bind the parameters
            stmt1.setString(1, friend.getUsername());
            stmt1.setString(2, friend.getFriendUsername());
            stmt2.setString(1, friend.getFriendUsername());
            stmt2.setString(2, friend.getUsername());

            // step 4: executes the query
            stmt1.executeUpdate();
            stmt2.executeUpdate();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}