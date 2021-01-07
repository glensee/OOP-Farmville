package entity.replies;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class RepliesDAOTest {

    ReplyDAO replyDAO = new ReplyDAO();

    @Test // Need to review this test
    public void givenThreadIDRetrieveRepliesTest() {
        int testThreadID = 1;

        ArrayList<Reply> actual = replyDAO.retrieveReplies(testThreadID);

        assertEquals(4, actual.size(), "Number of Replies received is not the same");

    }

    public void givenReplyAddTest() {
        Reply testReply = new Reply(99, 1, "testuser", "this is a test", new Date(10000L));

        ArrayList<Reply> expected = new ArrayList<>();
        expected.add(testReply);

        replyDAO.add(testReply);

        ArrayList<Reply> actual = replyDAO.retrieveReplies(99);

        assertArrayEquals(expected.toArray(new Reply[expected.size()]),
        actual.toArray(new Reply[actual.size()]), "New reply by testuser was not added successfully.");
    }

    @Test
    public void givenReplyDeleteReplyTest() {
        Reply testReply = new Reply(99, 1, "testuser", "this is a test", new Date(10000L));

        ArrayList<Reply> expected = new ArrayList<>();

        replyDAO.deleteReply(testReply);

        ArrayList<Reply> actual = replyDAO.retrieveReplies(99);

        assertArrayEquals(expected.toArray(new Reply[expected.size()]),
        actual.toArray(new Reply[actual.size()]), "Reply by testuser was not deleted successfully.");
    }

    @Test
    public void givenReplydeleteThreadTest() {
        Reply testReply = new Reply(99, 1, "testuser", "this is a test", new Date(10000L));

        ArrayList<Reply> expected = new ArrayList<>();

        expected.add(testReply);

        replyDAO.add(testReply);

        ArrayList<Reply> actual = replyDAO.retrieveReplies(99);

        assertArrayEquals(expected.toArray(new Reply[expected.size()]),
        actual.toArray(new Reply[actual.size()]), "New reply by testuser was not added successfully.");

        replyDAO.deleteThread(testReply);
        
        expected.remove(testReply);

        actual = replyDAO.retrieveReplies(99);

        assertArrayEquals(expected.toArray(new Reply[expected.size()]),
        actual.toArray(new Reply[actual.size()]), "Reply by testuser was not deleted successfully.");
    }
}