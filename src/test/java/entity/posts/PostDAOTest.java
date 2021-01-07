package entity.posts;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.*;

import entity.users.*;

public class PostDAOTest {

    PostDAO postDAO = new PostDAO();

    @Test
    public void givenUserRetrieveAllPostTest() {
        User testUser = new User("apple", "Apple ONG");

        ArrayList<Post> expected = new ArrayList<>();

        expected.add(new Post("apple", "apple", 1, "Hello World", "3", "1", new Date(1000L)));
        expected.add(new Post("apple", "dick", 3, "Hello Dick", "0", "0", new Date(1000L)));
        expected.add(new Post("apple", "moe", 4, "Hello Moe", "0", "0", new Date(1000L)));
        expected.add(new Post("apple", "john", 5, "Hello John", "0", "0", new Date(1000L)));
        expected.add(new Post("apple", "apple", 7, "Hello Sixth Post", "0", "0", new Date(1000L)));

        ArrayList<Post> actual = postDAO.retrieveAllPostbyUser(testUser);

        assertArrayEquals(expected.toArray(new Post[expected.size()]), 
        actual.toArray(new Post[actual.size()]), "Arrays Mismatched - Actual not the same as Expected");

    }

    @Test
    public void givenUsernameRetrieveAllPostTest() {
        String testUsername = "john";

        ArrayList<Post> expected = new ArrayList<>();

        expected.add(new Post("apple", testUsername, 5, "Hello John", "3", "1", new Date(1000L)));

        ArrayList<Post> actual = postDAO.retrieveAllPostbyUser(testUsername);

        assertArrayEquals(expected.toArray(new Post[expected.size()]), 
        actual.toArray(new Post[actual.size()]), "Arrays Mismatched - Actual not the same as Expected");

    }

    @Disabled("Skipped as ThreadID is autoincrement")
    @Test
    public void givenPostAddTest() {
        Post testPost = new Post("testuser1", "testuser2", 99, "TEST", "0", "0", new Date(1000L));

        ArrayList<Post> expected = new ArrayList<>();

        expected.add(testPost);

        postDAO.add(testPost);

        ArrayList<Post> actual = postDAO.retrieveAllPostbyUser("testuser2");

        assertArrayEquals(expected.toArray(new Post[expected.size()]), 
        actual.toArray(new Post[actual.size()]), "Add Post is unsuccessful");

    }

    @Disabled("Skipped as ThreadID is autoincrement")
    @Test
    public void givenPostDeleteTest() {
        Post testPost = new Post("testuser1", "testuser2", 11, "TEST", "0", "0", new Date(1000L));

        ArrayList<Post> expected = new ArrayList<>();

        postDAO.delete(testPost);

        ArrayList<Post> actual = postDAO.retrieveAllPostbyUser("testuser2");

        assertArrayEquals(expected.toArray(new Post[expected.size()]), 
        actual.toArray(new Post[actual.size()]), "Delete Post is unsuccessful");
    }

    @Disabled("Skipped as we will test this on as part of a broader test case")
    @Test
    public void givenPostMasterDeleteTest() {

        Post testPost = new Post("testuser1", "testuser1", 99, "TEST", "0", "0", new Date(1000L));

        ArrayList<Post> expected = new ArrayList<>();
        

        postDAO.masterDelete(testPost);

        ArrayList<Post> actual = postDAO.retrieveAllPostbyUser("testuser1");

        assertArrayEquals(expected.toArray(new Post[expected.size()]), 
        actual.toArray(new Post[actual.size()]), "Master Delete Post is unsuccessful");
    }

    @Test
    public void givenPostIncrementLikeTest() {
        Post testPost = new Post("apple", "moe", 4, "Hello Moe", "1", "0", new Date(1000L));

        ArrayList<Post> expected = new ArrayList<>();

        expected.add(testPost);

        postDAO.incrementLike(testPost);

        ArrayList<Post> actual = postDAO.retrieveAllPostbyUser(testPost.getUsername());

        assertArrayEquals(expected.toArray(new Post[expected.size()]), 
        actual.toArray(new Post[actual.size()]), "Liking Post is unsuccessful");

    }

    @Test
    public void givenPostIncrementDisLikeTest() {
        Post testPost = new Post("apple", "moe", 4, "Hello Moe", "1", "1", new Date(1000L));

        ArrayList<Post> expected = new ArrayList<>();

        expected.add(testPost);

        postDAO.incrementDislike(testPost);

        ArrayList<Post> actual = postDAO.retrieveAllPostbyUser(testPost.getUsername());

        assertArrayEquals(expected.toArray(new Post[expected.size()]), 
        actual.toArray(new Post[actual.size()]), "Disliking Post is unsuccessful");

    }

    @Test
    public void givenPostDecrementLikeTest() {
        Post testPost = new Post("apple", "moe", 4, "Hello Moe", "0", "1", new Date(1000L));

        ArrayList<Post> expected = new ArrayList<>();

        expected.add(testPost);

        postDAO.incrementLike(testPost);

        ArrayList<Post> actual = postDAO.retrieveAllPostbyUser(testPost.getUsername());

        assertArrayEquals(expected.toArray(new Post[expected.size()]), 
        actual.toArray(new Post[actual.size()]), "Removing like Post is unsuccessful");
    }

    @Test
    public void givenPostDecrementDislikeTest() {
        Post testPost = new Post("apple", "moe", 4, "Hello Moe", "0", "0", new Date(1000L));

        ArrayList<Post> expected = new ArrayList<>();

        expected.add(testPost);

        postDAO.incrementLike(testPost);

        ArrayList<Post> actual = postDAO.retrieveAllPostbyUser(testPost.getUsername());

        assertArrayEquals(expected.toArray(new Post[expected.size()]), 
        actual.toArray(new Post[actual.size()]), "Removing dislike Post is unsuccessful");
    }

}