package entity.users;

import entity.cityfarmer.FarmerDAO;
import entity.cityfarmer.InventoryDAO;
import entity.interfaces.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Data Access Object for User Entity Class
 * <p>
 * Implements {@link ConnectionManager}
 */
public class UserDAO implements ConnectionManager {

    private String dbUsername = "root";
    private String dbPassword = "";

    /**
     * This method authenticates User on Login
     * @param username username of User
     * @param password pwd of User
     * @return boolean
     */
    public boolean authenticate(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ? && pwd =?";
        
        try {
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();
            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs==null) {
                return false;
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * This method retrieves this User with given username
     * @param givenUsername String username
     * @return this User
     */
    public User getUserbyUsername(String givenUsername) {

        String sql = "SELECT * FROM USERS WHERE USERNAME=?";

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();
            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, givenUsername);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getString("username"), rs.getString("full_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method adds new User into Database and returns true if successful
     * <p>
     * Otherwise return false
     * @param givenUsername {@link String} username
     * @param givenPassword {@link String} password 
     * @param givenFullname {@link String} fullName
     * @return boolean
     */
    public boolean add(String givenUsername, String givenPassword, String givenFullname){

        String sql = "INSERT INTO users (username,pwd,full_name) VALUES (?, ?, ?)";

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();
            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, givenUsername);
            stmt.setString(2, givenPassword);
            stmt.setString(3, givenFullname);
            // step 4: executes the query
            stmt.executeUpdate();

            FarmerDAO farmerDAO = new FarmerDAO();
            farmerDAO.add(givenUsername);

            InventoryDAO inventoryDAO = new InventoryDAO();
            inventoryDAO.addInventory(givenUsername);

            return true;

        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Error with registration.");
        }

        return false;
    }

}