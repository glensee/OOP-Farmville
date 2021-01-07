package entity.cityfarmer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entity.interfaces.ConnectionManager;

public class StealPlotDAO implements ConnectionManager {

    // The database has a triggers that removes the records of it being stolen once the plot has been cleared/harvested
    private String dbUsername = "root";
    private String dbPassword = "";

    // Checks if the friend has stolen before
    public boolean beenStolen(String username, String friend, int plot_ID) {

        String sql = "SELECT * FROM stealplot WHERE username=? AND friend_username=? AND plot_id=?";

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
            stmt.setInt(3,plot_ID);

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    // Checks the total amount that has been stolen from that specific plot
    public int amountStolen(String username, int plot_ID) {
        int amount = 0;
        String sql = "SELECT * FROM stealplot WHERE username=? AND plot_id=?";

        try {
            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, username);
            stmt.setInt(2,plot_ID);

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                amount += rs.getInt("stolen");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return amount;
    }

    // Add the stealer into the database once stolen
    public boolean add(String username, String friend_username, int plot_id, int stolen){

        String sql = "INSERT INTO stealplot VALUES(?,?,?,?)";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, username);
            stmt.setString(2, friend_username);
            stmt.setInt(3, plot_id);
            stmt.setInt(4, stolen);

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