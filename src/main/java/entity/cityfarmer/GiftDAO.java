package entity.cityfarmer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;

import entity.interfaces.*;

public class GiftDAO implements ConnectionManager {

    private String dbUsername = "root";
    private String dbPassword = "";

    // Returns a list of gifts
    public ArrayList<Gift> retrieveAllGifts(String username) {

        String sql = "SELECT * FROM GIFT WHERE USERNAME=? AND ACCEPTANCE=FALSE";

        ArrayList<Gift> GiftList = new ArrayList<>();

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, username);

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username1 = rs.getString("USERNAME");
                String friendUsername = rs.getString("FRIEND_USERNAME");
                String giftItem = rs.getString("GIFT_ITEM");
                String[] timestamp = rs.getString("TIMESTAMP").split(" ");

                GiftList.add(new Gift(username1, friendUsername, giftItem, timestamp[0]));
            }

            return GiftList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return GiftList;
    }

    // Returns a list of gifts sent
    public ArrayList<Gift> retrieveGiftSent(String username) {

        String sql = "SELECT * FROM GIFT WHERE FRIEND_USERNAME=? AND ACCEPTANCE=FALSE";

        ArrayList<Gift> GiftList = new ArrayList<>();

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, username);

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username1 = rs.getString("USERNAME");
                String friendUsername = rs.getString("FRIEND_USERNAME");
                String giftItem = rs.getString("GIFT_ITEM");
                String timestamp = rs.getString("timestamp");

                GiftList.add(new Gift(username1, friendUsername, giftItem, timestamp));
            }

            return GiftList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return GiftList;
    }

    // Returns a list of gifts sent for today
    public ArrayList<Gift> retrieveTodayGifts(String username) {

        String sql = "SELECT * FROM GIFT WHERE USERNAME=? AND ACCEPTANCE=FALSE AND DATE(TIMESTAMP) = ?";

        ArrayList<Gift> GiftList = new ArrayList<>();

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, username);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            stmt.setString(2, dtf.format(now));

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username1 = rs.getString("USERNAME");
                String friendUsername = rs.getString("FRIEND_USERNAME");
                String giftItem = rs.getString("GIFT_ITEM");
                String[] timestamp = rs.getString("TIMESTAMP").split(" ");

                GiftList.add(new Gift(username1, friendUsername, giftItem, timestamp[0]));
            }

            return GiftList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return GiftList;
    }

    // Returns the gift object sent to a friend today
    public Gift retrieveTodayGiftToFriend(String username, String friend) {

        String sql = "SELECT * FROM GIFT WHERE USERNAME=? AND FRIEND_USERNAME=? AND DATE(TIMESTAMP) = ?";

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
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            stmt.setString(3, dtf.format(now));

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String username1 = rs.getString("USERNAME");
                String friendUsername = rs.getString("FRIEND_USERNAME");
                String giftItem = rs.getString("GIFT_ITEM");
                String[] timestamp = rs.getString("TIMESTAMP").split(" ");

                return new Gift(username1, friendUsername, giftItem, timestamp[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Returns the latest gift sent by a friend
    public Gift retrieveLatestGift(String username, String friend) {

        String sql = "SELECT * FROM GIFT WHERE USERNAME=? AND ACCEPTANCE=FALSE AND friend_username=? ORDER BY timestamp desc";

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
                String username1 = rs.getString("USERNAME");
                String friendUsername = rs.getString("FRIEND_USERNAME");
                String giftItem = rs.getString("GIFT_ITEM");
                String[] timestamp = rs.getString("TIMESTAMP").split(" ");

                return new Gift(username1, friendUsername, giftItem, timestamp[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Adds a gift object to the database
    public boolean addGift(Gift gift){

        String sql = "INSERT INTO gift VALUES(?,?,?,FALSE,DEFAULT)";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, gift.getUsername());
            stmt.setString(2, gift.getFriendUsername());
            stmt.setString(3, gift.getGiftItem());

            // step 4: executes the query
            stmt.executeUpdate();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    // Accepts all the gifts sent to the user
    public boolean acceptAllGifts(String username){

        String sql = "UPDATE gift SET acceptance=TRUE WHERE friend_username=?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, username);

            // step 4: executes the query
            stmt.executeUpdate();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }
}