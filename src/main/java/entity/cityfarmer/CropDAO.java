package entity.cityfarmer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import entity.interfaces.*;

/**
 * Data Access Object for Crop Entity Class
 */
public class CropDAO implements ConnectionManager {

    private static String dbUsername = "root";
    private static String dbPassword = "";

    /**
     * Returns list of Crops
     * @return ArrayList of Crops
     */
    public static ArrayList<Crop> retrieveAllCrops() {

        String sql = "SELECT * FROM CROP";

        ArrayList<Crop> CropList = new ArrayList<>();

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String cropName = rs.getString("CROPNAME");
                int cost = rs.getInt("COST");
                int growthTime = rs.getInt("GROWTH_TIME");
                int xp = rs.getInt("XP");
                int minYield = rs.getInt("MINYIELD");
                int maxYield = rs.getInt("MAXYIELD");
                int salePrice = rs.getInt("SALE_PRICE");

                CropList.add(new Crop(cropName, cost, growthTime, xp, minYield, maxYield, salePrice));
            }

            return CropList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CropList;
    }

    /**
     * Returns List of Crop pf specific cropType
     * @param cropType String cropType of this Crop
     * @return this Crop
     */
    public static Crop retrieveCrops(String cropType) {

        String sql = "SELECT * FROM CROP WHERE cropname = ?";

        try {

            // step 1: Loads the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();

            // step 2: Gets a connection to the database
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // step 3: Prepare the SQL to be sent to the database
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, cropType);
            // step 4: executes the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String cropName = rs.getString("CROPNAME");
                int cost = rs.getInt("COST");
                int growthTime = rs.getInt("GROWTH_TIME");
                int xp = rs.getInt("XP");
                int minYield = rs.getInt("MINYIELD");
                int maxYield = rs.getInt("MAXYIELD");
                int salePrice = rs.getInt("SALE_PRICE");

                return new Crop(cropName, cost, growthTime, xp, minYield, maxYield, salePrice);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}