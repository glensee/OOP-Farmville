package entity.replies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.*;

import entity.interfaces.*;

/**
 * Data Access Object for Reply Class Entity
 * <p>
 * Implements {@link ConnectionManager}
 */
public class ReplyDAO implements ConnectionManager {

    private String dbUsername = "root";
    private String dbPassword = "";

    /**
     * Retrieve a List of Replies by threadID of this Post
     * @param threadID threadID of Post
     * @return ArrayList of Reply
     */
    public ArrayList<Reply> retrieveReplies(int threadID){

        String sql = "SELECT * FROM replies WHERE thread_ID = ?";

        ArrayList<Reply> ReplyList = new ArrayList<>();

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1,threadID);

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String content = rs.getString("content");
                String[] timestamp = rs.getString("timestamp").split(" ");
                DateFormat dateformat = new SimpleDateFormat("hh:mm:ss.S");
                Date time = dateformat.parse(timestamp[1]);
                int replyID = rs.getInt("reply_ID");
                ReplyList.add(new Reply(threadID,replyID,username,content,time));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return ReplyList;
    }

    /**
     * This method add this Reply into the Database and returns true if successful
     * <p>
     * Otherwise return false
     * @param reply This Reply
     * @return boolean
     */
    public boolean add(Reply reply) {

        String sql = "INSERT INTO replies (thread_ID, username, content, reply_ID) VALUES (?, ?, ?, ?)";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, reply.getThreadID());
            stmt.setString(2,reply.getUsername());
            stmt.setString(3,reply.getContent());
            stmt.setInt(4,reply.getReplyID());

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
     * This method deletes this Reply from Database and returns true if successful
     * <p>
     * Otherwise return false
     * @param reply This Reply
     * @return boolean
     */
    public boolean deleteReply(Reply reply) {

        String sql = "DELETE FROM replies WHERE thread_ID = ? AND reply_ID = ?";

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, reply.getThreadID());
            stmt.setInt(2, reply.getReplyID());

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
     * This method returns true when Replies with ThreadID is deleted from Database
     * <p>
     * Otherwise, return false
     * @param reply This Reply 
     * @return boolean
     */
    public boolean deleteThread(Reply reply) {

        String sql = "DELETE FROM replies WHERE thread_ID = ?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, reply.getThreadID());

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