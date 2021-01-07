package entity.controllers;

import java.util.*;

import entity.friends.Friend;
import entity.friends.FriendDAO;
import entity.posts.Post;
import entity.posts.PostDAO;
import entity.replies.Reply;
import entity.replies.ReplyDAO;
import entity.users.User;

/**
 * This class handles the Logic of feature News Feed for Social Magnet
 */
public class NewsFeedController {

    private User user;
    private PostDAO postDAO;
    private ReplyDAO replyDAO;
    private FriendDAO friendDAO;

    /**
     * Class Constructor
     * <p>
     * Takes in {@link User} object and creates an user interface for User
     * @param user User Object from HomeMenu
     */
    public NewsFeedController(User user) {
        this.postDAO = new PostDAO();
        this.user = user;
        this.replyDAO = new ReplyDAO();
        this.friendDAO = new FriendDAO();
    }


    /**
     * Handles the Posts to be displayed on HomeMenu when called.
     * 
     * @return ArrayList of {@link Post}
     */
	public ArrayList<Post> displayPost() {
        int displayCount = 1;

        ArrayList<Post> postList = new ArrayList<>();
        ArrayList<Post> listofAllPost = new ArrayList<>();

        ArrayList<Friend> friendList = friendDAO.retrieveFriends(user);
        ArrayList<Post> myPost = postDAO.retrieveAllPostbyUser(user);

        Integer numberOfPostFromFriend = 0;

        //adds all of friends post post array
        if (!friendList.isEmpty()) {

            for (Friend friend : friendList) {
                ArrayList<Post> friendsPosts = postDAO.retrieveAllPostbyUser(friend.getFriendUsername());
                numberOfPostFromFriend += friendsPosts.size();

                for (Post post : friendsPosts) {
                    listofAllPost.add(post);
                }

            }
        }

        if (!myPost.isEmpty()) {
            for (Post post : myPost) {
                listofAllPost.add(post);
            }

        }

        // System.out.println("Starting the loop here"); //debug tool
        List<Date> dateList = new ArrayList<>();
        
        for (Post post : listofAllPost) {
            dateList.add(post.getTime());
        }

        Collections.sort(dateList, Collections.reverseOrder());
        
        List<Date> latestDate = new ArrayList<>();

        if (dateList.size() > 5) {
            for (int i=0; i<5 ; i++) {
                Date current = dateList.get(i);
                latestDate.add(current);
            }

        } else {
            for (int i=0; i<dateList.size() ; i++) {
                Date current = dateList.get(i);
                latestDate.add(current);
            }
        }
        
        // System.out.println(latestDate);

        for (Date date : latestDate) {
            
            for(Post post : listofAllPost){
                
                Date postDate = post.getTime();
                
                if(postDate.compareTo(date) == 0){
                    postList.add(post);
                    break;
                }
            }
        }

        for (int i = 0; i < postList.size(); i++) {
            Post post = postList.get(i);
            int likecount;
            int dislikecount;

            if (post.getLikes() != null) {
                likecount = Integer.parseInt(post.getLikes());
            } else {
                likecount = 0;
            }
            
            if(post.getLikes() != null){
                dislikecount = Integer.parseInt(post.getDislikes());
            } else {
                dislikecount = 0;
            }

            String like = " like";
            String dislike = " dislike";
            
            if (likecount > 1) {
                like = " likes";
            }

            if (dislikecount > 1) {
                dislike = " dislikes";
            }

            System.out.println("" + displayCount + " " + post.getSender() + ": " + post.getContent());
            System.out.println("[ " + likecount + like + ", " + dislikecount + dislike + " ]");
            
            ArrayList<Reply> replies = replyDAO.retrieveReplies(post.getThreadID());
            int replyCount = 1;
            
            for (Reply reply : replies) {
                String replyContent = reply.getContent();
                String replyPoster = reply.getUsername();
                
                System.out.println("  " + displayCount + "." + replyCount + " " + replyPoster + ": " + replyContent);
                replyCount++;
            }

            displayCount++;
            System.out.println();
        }

        return postList;
	}
}