package entity.users;

/**
 * User Entity Class
 */
public class User {
    private String username;
    private String fullname;

    /**
     * General Constructor for User
     * @param username username for this User
     * @param fullname fullname for this User
     */
    public User(String username, String fullname){
        this.username = username;
        this.fullname = fullname;
    }

    /**
     * Getter Method: get this User username
     * @return String username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Getter Method: get this User Fullname
     * @return String Fullname
     */
    public String getFullname(){
        return this.fullname;
    }

    /**
     * Overloaded Method for equals() inherited from {@link Object}
     * @param o User
     * @return boolean
     */
    public boolean equals(User o){
        String originalUsername = this.username;
        String originalFullname = this.fullname;
        String compareUsername = o.getUsername();
        String compareFullname = o.getFullname();
        if(originalFullname.equals(compareFullname) && originalUsername.equals(compareUsername)){
            return true;
        }
        return false;
    }
}