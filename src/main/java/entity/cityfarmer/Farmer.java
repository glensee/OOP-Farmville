package entity.cityfarmer;


/**
 * Farmer Entity Class
 */
public class Farmer implements Comparable<Farmer>{
    //attributes
    private String username;
    private int expAmount;
    private int gold;

    /**
     * General Constructor for New Farmer
     * @param username this User username
     */
    public Farmer(String username){
        this.username = username;
        this.expAmount = 0;
        this.gold = 50;
    }

    /**
     * General Constructor for Farmer
     * @param username username for this User
     * @param expAmount expAmount for this Farmer
     * @param gold gold for this Farmer
     */
    public Farmer(String username, int expAmount, int gold) {
        this.username = username;
        this.expAmount = expAmount;
        this.gold = gold;
        // for (int i=1; i<=5; i++){
        //     this.plotsOwned.add(new Plot(username, i, null, null));
        // }
    }

    /**
     * Getter Method: Get username for this Farmer
     * @return String username 
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter Method: Get Gold for this Farmer
     * @return gold for this Farmer
     */
    public int getGold() {
        return this.gold;
    }

    /**
     * Setter Method: Set gold for this Farmer
     * @param goldAmt gold for this Farmer
     */
    public void updateGold(int goldAmt){
        this.gold += goldAmt;
    }

    /**
     * Getter Method: get exp for this Farmer
     * @return xp for this Farmer
     */
    public int getExpAmount() {
        return this.expAmount;
    }

    /**
     * Setter Method: Set EXP for this Farmer
     * @param XP xp for this Farmer
     */
    public void addExpAmount(int XP){
        this.expAmount += XP;
    }

    /**
     * Getter Method: Get Rank for this Farmer
     * @return String Rank for this Farmer
     */
    public String getRank() {
        if (this.expAmount < 1000) {
            return "Novice";
        } else if (this.expAmount < 2500) {
            return "Apprentice";
        } else if (this.expAmount < 5000) {
            return "Journeyman";
        } else if (this.expAmount < 12000) {
            return "Grandmaster";
        } else {
            return "Legendary";
        }
    }

    @Override
    public int compareTo(Farmer o){
        int currentGold = this.gold;
        int compareGold = o.getGold();
        if(currentGold > compareGold){
            return 1;
        }
        else if(currentGold == compareGold){
            return 0;
        }
        else{
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Farmer) {
            Farmer farmer = (Farmer) obj;

            if (!(this.username.equals(farmer.getUsername()) ||
            this.gold == farmer.getGold() ||
            this.expAmount == farmer.getExpAmount())) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

}