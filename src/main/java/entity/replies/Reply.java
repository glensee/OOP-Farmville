package entity.replies;

import java.util.Date;

/**
 * Reply Entity Class
 */
public class Reply {
    private int threadID;
    private int replyID;
    private String username;
    private String content;
    private Date timestamp;

    /**
     * General Constructor for Reply Object
     * @param threadID threadID for this Reply Object
     * @param replyID replyID for this Reply Object
     * @param username username for this Reply Object
     * @param content Content for this Reply Object
     * @param timestamp Time for this Reply Object
     */
    public Reply(int threadID, int replyID, String username,String content, Date timestamp){
        this.threadID = threadID;
        this.replyID = replyID;
        this.username = username;
        this.content = content;
        this.timestamp = timestamp;
    }

    /**
     * Getter Method: Get ThreadID for this Reply
     * @return int ThreadID
     */
    public int getThreadID() {
        return this.threadID;
    }

    /**
     * Getter Method: Get ReplyID for this Reply
     * @return int ReplyID
     */
    public int getReplyID() {
        return this.replyID;
    }

    /**
     * Getter Method: Get username for this Reply
     * @return String username for this Reply
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Getter Method: Get Content for this Reply
     * @return String Content for this Reply
     */
    public String getContent(){
        return this.content;
    }

    /**
     * Getter Method: Get Date for this Reply
     * @return {@link Date} for this Reply
     */
    public Date getTime() {
        return this.timestamp;
    }

    /**
     * Overrides equals(Object obj) inherited from {@link Object}
     */
    @Override
    public boolean equals(Object obj) {
        boolean flag = true;

        if (obj instanceof Reply) {
            Reply reply = (Reply) obj;
            
            if (!(this.threadID == reply.getThreadID() || 
            this.replyID == reply.getReplyID() || 
            this.username.equals(reply.getUsername()) ||
            this.content.equals(reply.getContent()) || 
            this.timestamp.equals(reply.getTime()))) {
                flag = false;
            }
        } else {
            flag = false;
        }

        return flag;
        
    }
}