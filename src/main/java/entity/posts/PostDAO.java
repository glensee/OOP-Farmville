package entity.posts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.*;

import entity.interfaces.*;
import entity.users.*;

/**
 * Data Access Object for Post Entity Class
 * 
 * Implements {@link ConnectionManager}
 */
public class PostDAO implements ConnectionManager {

    private String dbUsername = "root";
    private String dbPassword = "";
    
    
    /**
     * This method is called to retrieve an ArrayList of Post by this User
     * @param user This User retrieve his list of Post
     * @return ArrayList of Post by this User
     */
    public ArrayList<Post> retrieveAllPostbyUser(User user) {

        String username = user.getUsername();

        String sql = "SELECT * FROM posts WHERE username=? ORDER BY `TIMESTAMP` DESC";

        ArrayList<Post> PostList = new ArrayList<>();

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
                String sender = rs.getString("sender");
                int threadID = rs.getInt("thread_ID");
                String content = rs.getString("content");
                String likes = rs.getString("likes");
                String dislikes = rs.getString("dislikes");
                String timestamp = rs.getString("timestamp");
                DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date time = dateformat.parse(timestamp);

                PostList.add(new Post(sender, username, threadID, content, likes, dislikes, time));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return PostList;
    }

    /**
     * This method retrieved an ArrayList of Post by username
     * @param username username of User
     * @return ArrayList of Post by this username
     */
    public ArrayList<Post> retrieveAllPostbyUser(String username) {

        String sql = "SELECT * FROM posts WHERE username=? ORDER BY `TIMESTAMP` DESC";

        ArrayList<Post> PostList = new ArrayList<>();

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
                String sender = rs.getString("sender");
                int threadID = rs.getInt("thread_ID");
                String content = rs.getString("content");
                String likes = rs.getString("likes");
                String dislikes = rs.getString("dislikes");
                String timestamp = rs.getString("timestamp");
                DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date time = dateformat.parse(timestamp);

                PostList.add(new Post(sender, username, threadID, content, likes, dislikes, time));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return PostList;
    }

    /**
     * This method is called to add Post.
     * <p>
     * Returns true when this Post is added successfully and the database is updated successfully.
     * <p>
     * Otherwise returns false
     * @param post This Post that we want to add
     * @return boolean
     */
    public boolean add(Post post){

        String sql = "INSERT INTO posts (sender, username, content, likes, dislikes) VALUES (?, ?, ?, 0, 0)";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, post.getSender());
            stmt.setString(2, post.getUsername());
            stmt.setString(3, post.getContent());

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
     * This method is called to delete Post.
     * <p>
     * Returns true when this Post is deleted successfully and the database is updated successfully.
     * <p>
     * Otherwise returns false
     * @param post This Post that we want to delete
     * @return boolean
     */
    public boolean delete(Post post) {

        String sql = "DELETE FROM posts WHERE username = ? AND thread_ID = ?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, post.getUsername());
            stmt.setInt(2, post.getThreadID());

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
     * This method is called to delete all Post, Reply and Response where threadID is this Post's ThreadID 
     * <p>
     * Returns true when this Post, related Reply and responses are deleted successfully
     * and the database is updated successfully.
     * <p>
     * Otherwise returns false
     * @param post This Post that we want to delete
     * @return boolean
     */
    public boolean masterDelete(Post post) {

        String sql = "DELETE FROM posts WHERE thread_ID = ?";
        String sql2 = "DELETE FROM replies WHERE thread_ID = ?";
        String sql3 = "DELETE FROM response WHERE thread_ID = ?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            PreparedStatement stmt3 = conn.prepareStatement(sql3);

            // bind the parameters
            int threadId = post.getThreadID();
            stmt.setInt(1, threadId);
            stmt2.setInt(1, threadId);
            stmt3.setInt(1, threadId);

            // step 4: executes the query
            stmt.executeUpdate();
            stmt2.executeUpdate();
            stmt3.executeUpdate();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is called to increase Like count for this Post
     * @param post Post where Like is increased
     * @return boolean
     */
    public boolean incrementLike(Post post){

        String sql = "UPDATE posts SET likes=? WHERE thread_ID=?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, Integer.parseInt(post.getLikes()) + 1);
            stmt.setInt(2, post.getThreadID());

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
     * This method increase the Dislike Count for this Post
     * @param post This Post where Dislike Count is increased
     * @return boolean
     */
    public boolean incrementDislike(Post post){

        String sql = "UPDATE posts SET dislikes=? WHERE thread_ID=?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, Integer.parseInt(post.getDislikes()) + 1);
            stmt.setInt(2, post.getThreadID());

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
     * This method decrease the Like for this Post
     * @param post This Post where Like is decreased
     * @return boolean
     */
    public boolean decrementLike(Post post){

        String sql = "UPDATE posts SET likes=? WHERE thread_ID=?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, Integer.parseInt(post.getLikes()) - 1);
            stmt.setInt(2, post.getThreadID());

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
     * This method is called to decrease dislike count for this Post
     * @param post This Post where dislike is decreased
     * @return boolean
     */
    public boolean decrementDislike(Post post){

        String sql = "UPDATE posts SET dislikes=? WHERE thread_ID=?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, Integer.parseInt(post.getDislikes()) - 1);
            stmt.setInt(2, post.getThreadID());

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