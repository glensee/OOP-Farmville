package entity.cityfarmer;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class GiftDAOTest {

    GiftDAO giftDAO = new GiftDAO();
    String testUsername = "apple";
    

    @Test
    public void givenUsernameRetrieveAllGiftsTest() {
        
        ArrayList<Gift> expected = new ArrayList<>();

        expected.add(new Gift(testUsername, "mike", "Papaya", null));
        expected.add(new Gift(testUsername, "yado", "Watermelon", null));


        ArrayList<Gift> actual = giftDAO.retrieveAllGifts(testUsername);

        assertArrayEquals(expected.toArray(new Gift[expected.size()]), 
        actual.toArray(new Gift[actual.size()]), "Array Mismatched - Actual not the same as Expected");
    }

    @Test
    public void givenUsernameRetrieveGiftSent() {

        ArrayList<Gift> expected = new ArrayList<>();

        
        expected.add(new Gift("yado", testUsername, "Watermelon", null));

        ArrayList<Gift> actual = giftDAO.retrieveGiftSent(testUsername);

        assertArrayEquals(expected.toArray(new Gift[expected.size()]), 
        actual.toArray(new Gift[actual.size()]), "Array Mismatched - Actual not the same as Expected");
    }

    @Disabled("Will need to match Timestamp")
    @Test
    public void givenUsernameRetrieveTodayGifts() {
        
        ArrayList<Gift> expected = new ArrayList<>();

        ArrayList<Gift> actual = giftDAO.retrieveTodayGifts(testUsername);

        assertArrayEquals(expected.toArray(new Gift[expected.size()]), 
        actual.toArray(new Gift[actual.size()]), "Array Mismatched - Actual not the same as Expected");
    }

    @Disabled("Will need to match Timestamp")
    @Test
    public void givenUsernameAndFriendUsernameRetrieveTodayGiftToFriendTest() {

        String testFriendUsername = "moe";

        Gift expected = new Gift(testUsername, testFriendUsername, "Watermelon", null);

        Gift actual = giftDAO.retrieveTodayGiftToFriend(testUsername, testFriendUsername);

        assertEquals(expected, actual, "No such Gift Exists");

    }

    @Disabled("Will need to match Timestamp")
    @Test
    public void givenUsernameAndFriendUsernameRetrieveLatestGiftTest() {

        String testFriendUsername = "moe";

        Gift expected = new Gift(testUsername, testFriendUsername, "Watermelon", null);

        Gift actual = giftDAO.retrieveLatestGift(testUsername, testFriendUsername);

        assertEquals(expected, actual, "No such Gift Exists");

    }

    @Test
    public void givenGiftAddGiftTest() {

        Gift testGift = new Gift("test1", "test2", "Papaya", null);

        ArrayList<Gift> expected = new ArrayList<>();

        expected.add(testGift);

        giftDAO.addGift(testGift);

        ArrayList<Gift> actual = giftDAO.retrieveAllGifts("test1");

        assertArrayEquals(expected.toArray(new Gift[expected.size()]), 
        actual.toArray(new Gift[actual.size()]), "Test Gift not added");

    }

    @Test
    public void givenUsernameAcceptAllGiftTest() {

        ArrayList<Gift> expected = new ArrayList<>();

        giftDAO.addGift(new Gift("test3", "test2", "Watermelon", null));
        giftDAO.addGift(new Gift("test4", "test2", "Watermelon", null));
        giftDAO.addGift(new Gift("test5", "test2", "Papaya", null));

        giftDAO.acceptAllGifts("test2");

        ArrayList<Gift> actual = giftDAO.retrieveAllGifts("test2");

        assertArrayEquals(expected.toArray(new Gift[expected.size()]), 
        actual.toArray(new Gift[actual.size()]), "Test Gift not added");
    }

    


}