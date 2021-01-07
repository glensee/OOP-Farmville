package entity.cityfarmer;

/**
 * Crop Entity Class
 */
public class Crop {
    private String cropName;
    private int cost;
    private int growthTime;
    private int xp;
    private int minYield;
    private int maxYield;
    private int salePrice;

    /**
     * General Constructor for Crop
     * @param cropName cropName for this Crop
     * @param cost cost for this Crop
     * @param growthTime growthTime for this Crop
     * @param xp EXP for this Crop
     * @param minYield minYield for this crop
     * @param maxYield maxYield for this crop
     * @param salePrice salesPrice for this crop
     */
    public Crop(String cropName, int cost, int growthTime, int xp, int minYield, int maxYield, int salePrice){
        this.cropName = cropName;
        this.cost = cost;
        this.growthTime = growthTime;
        this.xp = xp;
        this.minYield = minYield;
        this.maxYield = maxYield;
        this.salePrice = salePrice;
    }

    /**
     * Getter Method: Get Crop Name for this Crop
     * @return String cropName
     */
    public String getCropName(){
        return this.cropName;
    }

    /**
     * Getter Method: Get Cost for this Crop
     * @return cost for this Crop
     */ 
    public int getCost(){
        return this.cost;
    }

    /**
     * Getter Method: Get Growth Time for this Crop
     * @return growthTime for this crop
     */
    public int getGrowthTime(){
        return this.growthTime;
    }

    /**
     * Getter Method: Get Exp for this Crop
     * @return xp for this Crop
     */
    public int xp(){
        return this.xp;
    }

    /**
     * Getter Method: Get minYield for this Crop
     * @return minYield for this Crop
     */
    public int minYield(){
        return this.minYield;
    }

    /**
     * Getter Method: Get maxYield for this Crop
     * @return maxYield for this Crop
     */
    public int maxYield(){
        return this.maxYield;
    }

    /**
     * Getter Method: Get Sales Price for this Crop
     * @return salesPrice for this Crop
     */
    public int salePrice(){
        return this.salePrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Crop) {
            Crop crop = (Crop) obj;

            if (!(this.cropName.equals(crop.getCropName()) || 
            this.cost == crop.getCost() || 
            this.growthTime == crop.getGrowthTime() ||
            this.maxYield == crop.maxYield() || 
            this.minYield == crop.minYield() ||
            this.xp == crop.xp() ||
            this.salePrice == crop.salePrice())) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }
}