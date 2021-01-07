package entity.cityfarmer;

import java.sql.Timestamp;
import java.util.Random;

public class Plot {

    private String username;
    private int plotID;
    private String cropType;
    private int yield;
    private Timestamp timePlanted;

    public Plot(String username, int plotID, String cropType) {
        this.username = username;
        this.plotID = plotID;
        this.cropType = cropType;
        randomYield();
    }

    public Plot(String username, int plotID, String cropType, int yield, Timestamp timePlanted) {
        this.username = username;
        this.plotID = plotID;
        this.cropType = cropType;
        this.yield = yield;
        this.timePlanted = timePlanted;
    }

    public String getUsername() {
        return this.username;
    }

    public int getPlotID() {
        return this.plotID;
    }

    public String getCropType() {
        return this.cropType;
    }

    public Timestamp getTimePlanted() {
        return this.timePlanted;
    }

    public int getYield() {
        return yield;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    public void changeCropType(String cropType) {
        this.cropType = cropType;
        randomYield();
    }

    // Randomises the yield of that plot should it have a cropType
    private void randomYield() {
        Crop crop = CropDAO.retrieveCrops(cropType);

        Random rand = new Random();
        int max = crop.maxYield();
        int min = crop.minYield();

        yield = rand.nextInt(max - min) + min;
    }

    // Used to identify if it's wilted, ready for harvest or in-progress
    public String theTime(){
        Crop crop = CropDAO.retrieveCrops(cropType);
        int growthTime = crop.getGrowthTime();

        // Calculates the diff in minutes
        Timestamp now = new Timestamp(System.currentTimeMillis());
        int diffInMinutes = (int) ((now.getTime()/60000) - (timePlanted.getTime()/60000));

        // Checks if it's wilted/ready for harvest
        if (diffInMinutes > growthTime * 2) {
            return "wilted";
        } else if (diffInMinutes > growthTime) {
            return "100";
        }

        // Returns the progress of the crop
        int progress = (int)((diffInMinutes * 100.0f) / growthTime);

        return String.valueOf(progress);
    }


}