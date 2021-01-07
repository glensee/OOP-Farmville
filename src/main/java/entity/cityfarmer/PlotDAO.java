package entity.cityfarmer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entity.interfaces.ConnectionManager;

public class PlotDAO implements ConnectionManager {

    private String dbUsername = "root";
    private String dbPassword = "";

    // Retrieve plots of the farmer
    public ArrayList<Plot> getPlotbyUsername(String username) {

        ArrayList<Plot> plotList = new ArrayList<>();
        String sql = "SELECT * FROM plot WHERE username=? ORDER BY username, plot_id";

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
                plotList.add(new Plot(rs.getString("username"), rs.getInt("plot_ID"),
                    rs.getString("crop_type"), rs.getInt("yield"), rs.getTimestamp("timestamp")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return plotList;

    }

    // Plant a plot
    public boolean plantPlot(Plot plot) {
        String sql = "UPDATE plot SET crop_type = ?, timestamp = DEFAULT, yield = ? WHERE USERNAME = ? AND plot_id = ?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, plot.getCropType());
            stmt.setInt(2, plot.getYield());
            stmt.setString(3, plot.getUsername());
            stmt.setInt(4, plot.getPlotID());

            // step 4: executes the query
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Clear the plot
    public boolean clearPlot(Plot plot){

        String sql = "UPDATE plot SET crop_type = NULL, yield = 0 WHERE username = ? AND plot_id = ?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setString(1, plot.getUsername());
            stmt.setInt(2, plot.getPlotID());

            // step 4: executes the query
            stmt.executeUpdate();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    // Update the yield when it's stolen
    public boolean updateYield(Plot plot){

        String sql = "UPDATE plot SET yield = ? WHERE username = ? AND plot_ID = ?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // bind the parameters
            stmt.setInt(1, plot.getYield());
            stmt.setString(2, plot.getUsername());
            stmt.setInt(3, plot.getPlotID());

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