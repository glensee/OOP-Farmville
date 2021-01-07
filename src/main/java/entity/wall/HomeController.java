package entity.wall;

import entity.cityfarmer.*;
import entity.posts.*;
import entity.replies.*;
import entity.response.*;
import entity.users.*;

import java.util.*;
import java.text.*;

/**
 * Class responsible for logic of Home
 */
public class HomeController {
    private User user;
    public PostDAO postDAO;
    public ReplyDAO replyDAO;

    public HomeController(User user){
        this.user = user;
        this.postDAO = new PostDAO();
        this.replyDAO = new ReplyDAO();
    }

    /**
     * This method display Post by this User
     * @return HashMap of Key {@link Integer} and {@link String Post}
     */
    public HashMap<Integer, Post> displayPost() {
        Integer postCount = 1;

        GiftDAO giftDAO = new GiftDAO();

        ArrayList<Post> postList = postDAO.retrieveAllPostbyUser(user);
        ArrayList<Gift> pendingGifts = giftDAO.retrieveGiftSent(user.getUsername());
        for (Gift gift : pendingGifts) {
            String senderUsername = gift.getUsername();
            String content = "Here is a bag of " + gift.getGiftItem() + " seeds for you. - City Farmers";
            String dateString = gift.getTimestamp();
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try{
                Date timeStamp = dateformat.parse(dateString);
                Post giftPost = new Post(senderUsername,user.getUsername(),content,timeStamp);        
                postList.add(giftPost);
            }
            catch(ParseException e){
                continue;
            }

        }
        ArrayList<Post> shownList = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();

        for (Post post : postList) {
            dateList.add(post.getTime());
        }

        Collections.sort(dateList, Collections.reverseOrder());

        List<Date> latestDate = new ArrayList<>();

        if(dateList.size()>5){
            for (int i=0; i<5 ; i++) {
                Date current = dateList.get(i);
                latestDate.add(current);
            }
        }
        else{
            for (int i=0; i<dateList.size() ; i++) {
                Date current = dateList.get(i);
                latestDate.add(current);
            }
        }
        

        for (Post post : postList) {
            Date postDate = post.getTime();
            for (Date date : latestDate) {
                if(postDate.equals(date) && shownList.size() != 5){
                    shownList.add(post);
                    break;
                }
            }
        }

        HashMap<Integer, Post> postMap = new HashMap<>();

        if(shownList.isEmpty()){
            System.out.println("No post yet!");
        }

        for (Post post : shownList) {
            String postContent = post.getContent();
            String poster = post.getSender();
            Integer threadId = post.getThreadID();
            // System.out.println(poster);
            postMap.put(postCount, post);

            ArrayList<Reply> replies = replyDAO.retrieveReplies(threadId);
            Integer replyCount = 1;

            System.out.println(postCount + " " + poster + ": " + postContent);

            for (Reply reply : replies) {
                String replyContent = reply.getContent();
                String replyPoster = reply.getUsername();
                System.out.println("\t" + postCount + "." + replyCount + " " + replyPoster + ": " + replyContent);
                replyCount++;
            }
            postCount ++;
            System.out.println();

        }
        return postMap;
    }

    /**
     * This method display Friend Post of this User
     * @return HashMap of Key {@link Integer} and {@link Post}
     */
    public HashMap<Integer, Post> displayFriendPost() {
        Integer postCount = 1;

        ArrayList<Post> postList = postDAO.retrieveAllPostbyUser(user);
        ArrayList<Post> shownList = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();

        for (Post post : postList) {
            dateList.add(post.getTime());
        }

        Collections.sort(dateList, Collections.reverseOrder());

        List<Date> latestDate = new ArrayList<>();

        if(dateList.size()>5){
            for (int i=0; i<5 ; i++) {
                Date current = dateList.get(i);
                latestDate.add(current);
            }
        }
        else{
            for (int i=0; i<dateList.size() ; i++) {
                Date current = dateList.get(i);
                latestDate.add(current);
            }
        }

        for (Post post : postList) {
            Date postDate = post.getTime();
            for (Date date : latestDate) {
                if(postDate.equals(date) && shownList.size() != 5){
                    shownList.add(post);
                    break;
                }
            }
        }

        HashMap<Integer, Post> postMap = new HashMap<>();
        ResponseDAO responseDAO = new ResponseDAO();

        if(shownList.isEmpty()){
            System.out.println("No post yet!");
        }

        for (Post post : shownList) {
            String postContent = post.getContent();
            String poster = post.getSender();
            Integer threadId = post.getThreadID();
            ArrayList<Response> responseArr = responseDAO.retrieveAllResponsesByThread(threadId);
            Integer likeCount = 0;
            Integer dislikeCount = 0;
            for (Response response : responseArr) {
                if(response.getLikeDislike()){
                    likeCount++;
                }
                else{
                    dislikeCount++;
                }
            }
            // System.out.println(poster);
            postMap.put(postCount, post);

            ArrayList<Reply> replies = replyDAO.retrieveReplies(threadId);
            Integer replyCount = 1;

            System.out.println(postCount + " " + poster + ": " + postContent);
            System.out.println("\t[ " + likeCount + " like, " + dislikeCount + " dislike ]");

            for (Reply reply : replies) {
                String replyContent = reply.getContent();
                String replyPoster = reply.getUsername();
                System.out.println("\t" + postCount + "." + replyCount + " " + replyPoster + ": " + replyContent);
                replyCount++;
            }
            postCount ++;
            System.out.println();

        }
        return postMap;
    }

    /**
     *  Checks for tags and then remove tags from original postContent for every successful match of tagged username, it will also create a Post for the username tagged, 
     *  <p>
     *  Else, the invalid tag stays on the original postContent
     * @param postContent orignalPost Content
     * @param posterUsername poster Username
     */
	public void createPostAtOwnWall(String postContent,String posterUsername) {
        String[] checkArr = postContent.split(" ");

        System.out.println("starting createPost");
        System.out.println(postContent);

        if(postContent.indexOf('@') != -1){
        
            for (String string : checkArr) {
        
                if(string.indexOf('@') != -1){
                    String targetUsername = string.substring(1, string.length());
                    PostDAO postDAO = new PostDAO();
                    Post post = new Post(posterUsername,targetUsername,postContent);
                    postDAO.add(post);
                }
            }
            
            Post post = new Post(posterUsername,posterUsername,postContent);
            postDAO.add(post);
        } else {
            Post post = new Post(posterUsername,posterUsername,postContent);
            postDAO.add(post);
        } 
	}

    /**
     * Checks for tags and then remove tags from original postContent for every successful match of tagged username, 
     * it will also create a Post for the username tagged, 
     * <p>
     * else,
     * the invalid tag stays on the original postContent
     * @param postContent originalpostContent
     * @param posterUsername original postername
     * @param originalTargetUsername target username
     */
	public void createPostAtOtherWall(String postContent,String posterUsername, String originalTargetUsername) {
        String[] checkArr = postContent.split(" ");
        
        System.out.println("starting createPost");
        System.out.println(postContent);
        
        if(postContent.indexOf('@') != -1){
        
            for (String string : checkArr) {
        
                if(string.indexOf('@') != -1){
                    String targetUsername = string.substring(1, string.length());
                    PostDAO postDAO = new PostDAO();
                    Post post = new Post(posterUsername,targetUsername,postContent);
                    postDAO.add(post);
                }
        
            }
        
            Post post = new Post(posterUsername,posterUsername,postContent);
            postDAO.add(post);
        } else {
            Post post = new Post(posterUsername,originalTargetUsername,postContent);
            postDAO.add(post);
        } 
	}

}