package entity.wall;

import entity.posts.*;
import entity.replies.*;
import entity.response.*;
import entity.users.*;
import login.*;

import java.util.*;

/**
 * The class in charge of Viewing Thread
 */
public class ViewThread {

    private User user;
    private UserDAO userDAO;
    private ReplyDAO replyDAO;
    private ResponseDAO responseDAO;
    private PostDAO postDAO;

    /**
     * Genreal Constructer for Class
     * @param user this {@link User}
     */
    public ViewThread(User user) {
        this.user = user;
        this.userDAO = new UserDAO();
        this.replyDAO = new ReplyDAO();
        this.responseDAO = new ResponseDAO();
        this.postDAO = new PostDAO();
    }

    /**
     * This method views {@link Post} of targeted threadID of this {@link User}
     * @param target threadID
     * @param post {@link Post} of this {@link User}
     */
    public void viewThread(Integer target, Post post){
        int threadId = post.getThreadID();
        ArrayList<Reply> replies = replyDAO.retrieveReplies(threadId);
        Integer replyCount = 1;
        int likeCount = 0;

        ArrayList<Response> responses = responseDAO.retrieveAllResponsesByThread(threadId);

        System.out.println("== Social Magnet :: View a Thread ==");
        System.out.println("" + target +" " + post.getSender() + ": " + post.getContent());

        for (Reply reply : replies) {
            String replyContent = reply.getContent();
            String replyPoster = reply.getUsername();
            System.out.println("\t" + target + "." + replyCount + " " + replyPoster + ": " + replyContent);
            replyCount++;
        }

        System.out.println();

        System.out.println("Who likes this post:");

        for (Response response : responses) {

            if (response.getLikeDislike()) {
                likeCount++;
                User user = userDAO.getUserbyUsername(response.getUsername());
                System.out.println("\t" + likeCount + ". " + user.getFullname() + " (" + user.getUsername() + ")"); // missing full name, thinking we need userDAO to do this or we store fullname in Response Table
            }
        }

        System.out.println("Who dislikes this post:");

        for (Response response : responses) {
            if (!response.getLikeDislike()) {
                likeCount++;
                User user = userDAO.getUserbyUsername(response.getUsername());
                System.out.println("\t" + likeCount + ". " + user.getFullname() + " (" + user.getUsername() + ")"); // missing full name, thinking we need userDAO to do this or we store fullname in Response Table
            }
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("[M]ain | [K]ill | [R]eply | [L]ike | [D]islike > ");

        String userInput = sc.nextLine();

            do {

                switch (userInput) {
                    case "M":
                        HomeMenu homeMenu = new HomeMenu(user);
                        homeMenu.start();
                        break;
                    case "K":
                        deleteThread(post);
                        homeMenu = new HomeMenu(user);
                        homeMenu.start();
                        break;
                    case "R":
                        replyMessage(threadId, replyCount);
                        break;
                    case "L":
                        likeThread(user,post);
                        break;
                    case "D":
                        dislikeThread(user,post);
                        break;
                    default :
                        viewThread(target, post);
                }return;
            } while (userInput != "M");
    }

    /**
     * Allows the replying of Message by this {@link User}
     * @param threadId threadID of this {@link Post}
     * @param replyCount replyCount
     */
    private void replyMessage(int threadId, int replyCount) {
        System.out.print("Enter your reply message > ");
        Scanner sc = new Scanner(System.in);

        String replyContent = sc.nextLine();

        Reply reply = new Reply(threadId, replyCount+1, user.getUsername(), replyContent, null); // SQL side will help us add date

        if (replyDAO.add(reply)) {
            System.out.println("Your reply is posted successful!");
            System.out.println();
            HomeMenu homeMenu = new HomeMenu(user);
            homeMenu.start();
        } else {
            System.out.println("Something went wrong here");
            System.out.println();
            HomeMenu homeMenu = new HomeMenu(user);
            homeMenu.start();
        }
    }

    /**
     * if post.getCreator() is true then use masterDelete(), else use delete()
     * @param post {@link Post}
     */
    public void deleteThread(Post post){
        

        if(post.getSender().equals(user.getUsername())){
            postDAO.masterDelete(post);
        }
        else{
            postDAO.delete(post);
        }
    }

    public void likeThread(User user, Post post){
        int threadID = post.getThreadID();
        String username = post.getUsername();
        Response like = new Response(threadID, user.getUsername(), true);

        try{
            responseDAO.add(like);
            postDAO.incrementLike(post);
            // homeMenu.myWall();
            viewThread(1, post);
        }
        catch(Exception e) {
            try {
                if (responseDAO.check(username, threadID)) {
                    responseDAO.update(like);
                } else {
                    responseDAO.update(like);
                    postDAO.decrementDislike(post);
                    postDAO.incrementLike(post);
                }
            // homeMenu.myWall();
            viewThread(1, post);
            } catch (Exception g) {
                System.out.println(g.getMessage());
                return;
            }
        }
    }

    public void dislikeThread(User user, Post post){
        int threadID = post.getThreadID();
        String username = post.getUsername();
        Response dislike = new Response(threadID, user.getUsername(), false);
        try{
            responseDAO.add(dislike);
            postDAO.incrementDislike(post);
            // homeMenu.myWall();
            System.out.print("\033[H\033[2J"); // cls command in cmd
            viewThread(1, post);
        }
        catch(Exception e){
            try {
                if(responseDAO.check(username, threadID)){
                    responseDAO.update(dislike);
                    postDAO.decrementLike(post);
                    postDAO.incrementDislike(post);
                }
                else{
                    responseDAO.update(dislike);
                }
                // homeMenu.myWall();
                System.out.print("\033[H\033[2J"); // cls command in cmd
                viewThread(1, post);
            } catch (Exception g) {
                System.out.println(g.getMessage());
                return;
            }
        }


    }

}