package entity.cityfarmer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entity.interfaces.ConnectionManager;

/**
 * Data Acess Object for Farmer Entity Class
 * 
 * <p>
 * Implements {@link ConnectionManager}
 */

public class FarmerDAO implements ConnectionManager {

    private String dbUsername = "root";
    private String dbPassword = "";

    // Retrieve farmer by their username
    public Farmer getFarmerbyUsername(String username) {

        String sql = "select * from Farmer where username=?";

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

            if (rs.next()) {
                return new Farmer(rs.getString("username"), rs.getInt("expAmt"), rs.getInt("gold"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    // Add a farmer
    public boolean add(String username) {

        String sql = "INSERT INTO farmer (username, expAmt, gold) VALUES (?, 0, 50)";

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    // Update the farmer's gold
    public boolean updateGold(String username, int amount) {

        String sql = "UPDATE farmer SET gold = ? WHERE username = ?";

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, amount);
            stmt.setString(2, username);

            // step 4: executes the query
            stmt.executeUpdate();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update the farmer's exp
    public boolean updateEXP(String username, int amount) {

        String sql = "UPDATE farmer SET expamt = ? WHERE username = ?";

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, amount);
            stmt.setString(2, username);

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