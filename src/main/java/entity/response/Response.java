package entity.response;

/**
 * Response Entity Class
 */
public class Response {
    private Integer threadID;
    private String username;
    private boolean likeDislike;

    /**
     * General Constructor for Response
     * @param threadID threadID for this Response
     * @param username username for this Response
     * @param likeDislike likeDislike for this Response
     */
    public Response(Integer threadID,String username, boolean likeDislike){
        this.threadID = threadID;
        this.username = username;
        this.likeDislike = likeDislike;
    }

    /**
     * Getter method: Get LikeDislike for this Response
     * @return boolean
     */
    public boolean getLikeDislike(){
        return this.likeDislike;
    }

    /**
     * Getter method: Get Username for this Response
     * @return String username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Getter Method: Get ThreadID for this Response
     * @return int ThreadID
     */
    public int getThreadID(){
        return threadID;
    }

    @Override
    public boolean equals(Object obj) {
        boolean flag = true;
 
        if (obj instanceof Response) {
            Response response = (Response) obj;

            if (!(this.threadID == response.getThreadID() || 
            this.likeDislike == response.getLikeDislike() || 
            this.username.equals(response.getUsername()))) {
                flag = false;
            }
        } else {
            flag = false;
        }

        return flag;
    }
}