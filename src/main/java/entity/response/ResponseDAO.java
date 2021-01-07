package entity.response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import entity.interfaces.*;

/**
 * Data Access Object for Response Entity Class
 */

public class ResponseDAO implements ConnectionManager{

    private String dbUsername = "root";
    private String dbPassword = "";

    /**
     * This method returns a list of Response given ThreadID
     * @param threadID threadID for list of this Response
     * @return ArrayList of Response
     */
    public ArrayList<Response> retrieveAllResponsesByThread(int threadID){

        String sql = "SELECT * FROM RESPONSE WHERE THREAD_ID=?";

        ArrayList<Response> ResponsesList = new ArrayList<>();

        int id = threadID;

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, id);

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String user = rs.getString("USERNAME");
                int thread = rs.getInt("THREAD_ID");
                boolean likeDislike = rs.getBoolean("LIKE_DISLIKE");

                ResponsesList.add(new Response(thread, user, likeDislike));
            }

            return ResponsesList;

        } catch (Exception e){
            e.printStackTrace();
        }

        return ResponsesList;
    }

    /**
     * This method returns true if this Response is added successfully into Database
     * 
     * Otherwise throw an Exception
     * @param response this Response
     * @return boolean
     * @throws Exception throws an Exception when add fails
     */
    public boolean add(Response response) throws Exception{

        String sql = "INSERT INTO RESPONSE VALUES(?,?,?)";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, response.getThreadID());
            stmt.setString(2, response.getUsername());
            stmt.setBoolean(3, response.getLikeDislike());

            // step 4: executes the query
            stmt.executeUpdate();

            return true;

        }catch (Exception e) {
            throw e;
        }

    }

    /**
     * This method returns true when deleting this Response from database is successful
     * <p>
     * Otherwise, return false
     * @param response this Response
     * @return boolean
     */
    public boolean delete(Response response) {

        String sql = "DELETE FROM response WHERE thread_id = ? AND username = ?";

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, response.getThreadID());
            stmt.setString(2, response.getUsername());

            // step 4: executes the query
            stmt.executeUpdate();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method updates this Response likeDislike in the database successfully and return true
     * <p>
     * Otherwise, return false
     * @param response this Response
     * @return boolean
     */
    public boolean update(Response response) {

        String sql = "UPDATE response SET like_dislike = ? WHERE thread_id = ? AND username = ?";

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setBoolean(1, response.getLikeDislike());
            stmt.setInt(2, response.getThreadID());
            stmt.setString(3, response.getUsername());

            // step 4: executes the query
            stmt.executeUpdate();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method returns likeDislike for this Response
     * @param username username for this Response
     * @param threadId ThreadID for this Response
     * @return boolean
     * @throws Exception thrown when there is an error in retrieving the likeDislike
     */
    public boolean check(String username, int threadId) throws Exception {

        String sql = "SELECT * FROM response WHERE thread_ID=? AND username=?";

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, threadId);
            stmt.setString(2, username);

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                boolean likeDislike = rs.getBoolean("LIKE_DISLIKE");
                return likeDislike;
            }

            throw new Exception("Error in Retrieving");
        }
        catch (Exception e) {
            throw e;
        }

    }
}