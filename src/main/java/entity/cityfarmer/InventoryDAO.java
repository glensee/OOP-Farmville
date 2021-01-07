package entity.cityfarmer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entity.interfaces.ConnectionManager;

public class InventoryDAO implements ConnectionManager {

    private String dbUsername = "root";
    private String dbPassword = "";

    // Returns a list of inventory
    public ArrayList<Inventory> getInventory(String username) {

        String sql = "select * from Inventory where username=?";

        ArrayList<Inventory> inv = new ArrayList<>();

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
                inv.add(new Inventory(rs.getString("username"), rs.getString("item"), rs.getInt("quantity")));
            }

            return inv;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inv;

    }

    // Returns an inventory object
    public Inventory getAnInventory(String username, String item) {

        String sql = "SELECT * FROM Inventory WHERE username=? AND item=?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, username);
            stmt.setString(2, item);

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return new Inventory(rs.getString("username"), rs.getString("item"), rs.getInt("quantity"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    // Adds an inventory object to the database
    public boolean addInventory(String username) {

        String sql = "INSERT INTO INVENTORY VALUES (?,'Papaya',0), (?,'Pumpkin',0), (?,'Sunflower',0), (?,'Watermelon',0)";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, username);
            stmt.setString(4, username);

            // step 4: executes the query
            stmt.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Updates the inventory object with the specified quantity
    public boolean updateInventory(String username, String item, int change) {

        String sql = "UPDATE INVENTORY SET QUANTITY = ? WHERE USERNAME = ? AND ITEM = ?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, change);
            stmt.setString(2, username);
            stmt.setString(3, item);

            // step 4: executes the query
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



}