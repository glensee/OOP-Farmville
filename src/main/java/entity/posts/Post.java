package entity.posts;

import java.util.Date;

/**
 * Post Entity Class
 */
public class Post {
    private String sender;
    private String username;
    private int threadID;
    private String content;
    private String likes;
    private String dislikes;
    private Date timestamp;

    /**
     * General Constructor for Post Object
     * 
     * @param sender username of Sender of this Post
     * @param username username of this User
     * @param threadID ThreadID of this Post
     * @param content Content of this Post
     * @param likes Number of Likes for this Post
     * @param dislikes Number of Dislikes for this Post
     * @param timestamp Time Posted of this Post
     */
    public Post(String sender, String username, int threadID, String content, String likes, String dislikes, Date timestamp) {
        this(sender,username,content, timestamp);
        this.threadID = threadID;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    /**
     * Specific Constructor for Post that only has Sender and User's username and Content
     * @param sender username of sender of this Post
     * @param username username of User of this Post
     * @param content Content for this Post
     */
    public Post(String sender, String username, String content) {
        this.sender = sender;
        this.username = username;
        this.content = content;
    }

    /**
     * General Constructor for Post with Time Posted
     * @param sender username of Sender of this Post
     * @param username username of User of this Post
     * @param content Content for this Post
     * @param timestamp Time posted for this Post
     */
    public Post(String sender, String username, String content, Date timestamp) {
        this(sender, username, content);
        this.timestamp = timestamp;
    }

    /**
     * Getter method: Get Username of Sender
     * @return username of Sender
     */
    public String getSender() {
        return this.sender;
    }

    /**
     * Getter method: Get Username of User
     * @return username of User
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter method: Get ThreadID of this Post
     * @return ThreadID of this Post
     */
    public int getThreadID() {
        return this.threadID;
    }

    /**
     * Getter method: Get Content of this Post
     * @return Content of this Post
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Getter method: Get Likes of this Post
     * @return Likes for this Post
     */
    public String getLikes() {
        return this.likes;
    }

    /**
     * Getter method: Get Dislikes for this Post
     * @return Dislikes for this Post
     */
    public String getDislikes() {
        return this.dislikes;
    }

    /**
     * Getter method: Get Time Posted for this Post
     * @return {@link Date} of this Post
     */
    public Date getTime() {
        return this.timestamp;
    }


    /**
     * This method returns true if this timestamp of this Post happens after timestamp of Post
     * <p>
     * Otherwise returns false
     * 
     * @param post Post to be compared with this Post
     * @return boolean
     */
    public boolean compareTimeTo(Post post) {
        if (this.timestamp.after(post.getTime())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        boolean flag = true;

        if (obj instanceof Post) {
            Post post = (Post) obj;

            if (!(this.sender.equals(post.getSender()) ||
            this.username.equals(post.getUsername()) ||
            this.threadID == post.getThreadID() || 
            this.content.equals(post.getContent()) || 
            this.likes.equals(post.getLikes()) ||
            this.dislikes.equals(post.getDislikes())
            // timeStamp is not compared because we insert based on time
            )) {
                flag = false; 
            }
        } else {
            flag = false;
        }

        return flag;
        
    }

}