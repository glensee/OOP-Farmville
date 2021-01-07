package entity.requests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import entity.interfaces.*;
import entity.users.*;

/**
 * Data Access Object for Requests Entity
 */
public class RequestsDAO implements ConnectionManager {

    private String dbUsername = "root";
    private String dbPassword = "";

    /**
     * This method retrieves list of Requests sent to this User
     * @param user this User
     * @return ArrayList of Requests
     */
    public ArrayList<Requests> retrieveAllByUser(User user){

        String username = user.getUsername();

        String sql = "SELECT * FROM requests WHERE USERNAME=?";

        ArrayList<Requests> RequestsList = new ArrayList<>();

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

            while (rs.next()){
                String userName = rs.getString("USERNAME");
                String sender = rs.getString("SENDER");

                RequestsList.add(new Requests(userName, sender));
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return RequestsList;
    }

    /**
     * This method returns list of Requests sent by this User
     * @param user this User
     * @return ArrayList of Requests
     */
    public ArrayList<Requests> retrieveAllBySender(User user){

        String username = user.getUsername();

        String sql = "SELECT * FROM requests WHERE SENDER = ?";

        ArrayList<Requests> RequestsList = new ArrayList<>();

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

            while (rs.next()){
                String userName = rs.getString("USERNAME");
                String sender = rs.getString("SENDER");

                RequestsList.add(new Requests(userName, sender));
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return RequestsList;
    }

    /**
     * This method sends Requests from this username to SenderUsername
     * @param request This Requests
     * @return boolean
     * @throws Exception when a duplicate Requests is send again
     */
    public boolean send(Requests request) throws Exception {

        String sql = "INSERT INTO requests VALUES(?,?)";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, request.getOtherParty());
            stmt.setString(2, request.getUsername());

            // step 4: executes the query
            stmt.executeUpdate();

            return true;

        } catch (Exception e) {
            throw e;
        }

        
    }

    /***
     * This method deletes this Requests from SenderUsername to this username 
     * @param request this Request
     * @return boolean
     */
    public boolean delete(Requests request) {

        String sql = "DELETE FROM requests WHERE username = ? AND sender = ?";

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, request.getUsername());
            stmt.setString(2, request.getOtherParty());

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
