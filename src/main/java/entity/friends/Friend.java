package entity.friends;

/**
 * Friend Entity Class
 */
public class Friend {
    private String username;
    private String friendUsername;

    /**
     * Create a Friend Object
     * 
     * @param username Username of User
     * @param friendUsername Username of Friend
     */
    public Friend(String username,String friendUsername){
        this.username = username;
        this.friendUsername = friendUsername;
    }

    /** 
     * Getter Method: Get the User's username
     * @return {@link String} username of User
    */
    public String getUsername(){
        return this.username;
    }

    /** 
     * Getter Method: Get the Friend's username
     * @return {@link String} username of Friend
    */
    public String getFriendUsername(){
        return this.friendUsername;
    }

    /**
     * Overrides equals method() from {@link Object}
     * 
     * The method returns true if obj is instanceof Friend and this username is the same
     * as the obj's username, this.friendUsername is the same as the obj's friendUsername. 
     * <p>
     * Otherwise, return false
     */
    @Override
    public boolean equals(Object obj) {
        boolean flag = true;
        if (obj instanceof Friend) {
            Friend friend = (Friend) obj;

            if (!(this.username.equals(friend.getUsername()))) {
                flag = false;
            } 
            if (!(this.friendUsername.equals(friend.getFriendUsername()))) {
                flag = false;
            }
        } else {
            flag = false;
        }

        return flag;

    }
}