package entity.requests;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;

import entity.users.*;

public class RequestsDAOTest {

    RequestsDAO requestsDAO = new RequestsDAO();

    @Test
    public void givenUserRetrieveAllByUserTest() {
        User testUser = new User("apple", "Apple ONG");

        ArrayList<Requests> expected = new ArrayList<>();

        expected.add(new Requests("apple", "yado"));

        ArrayList<Requests> actual = requestsDAO.retrieveAllByUser(testUser);

        assertArrayEquals(expected.toArray(new Requests[expected.size()]), actual.toArray(new Requests[actual.size()]), "Arrays Mismatched: Actual not equal to Expected");
    }

    @Test
    public void givenSenderRetrieveAllBySenderTest() {
        User testSender = new User("apple", "Apple ONG");

        ArrayList<Requests> expected = new ArrayList<>();

        expected.add(new Requests("moe", "yado"));

        ArrayList<Requests> actual = requestsDAO.retrieveAllBySender(testSender);

        assertArrayEquals(expected.toArray(new Requests[expected.size()]), actual.toArray(new Requests[actual.size()]), "Arrays Mismatched: Actual not equal to Expected");
    }


    @Test
    public void givenRequestsSendTest() {
        Requests testRequestSent = new Requests("testuser1", "testuser2");
        Requests testRequestReceived = new Requests("testuser2", "testuser1");

        ArrayList<Requests> expected = new ArrayList<>();

        expected.add(testRequestReceived);

        try {
            requestsDAO.send(testRequestSent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Requests> actual = requestsDAO.retrieveAllBySender(new User("testuser1", "TESTER"));

        assertArrayEquals(expected.toArray(new Requests[expected.size()]), actual.toArray(new Requests[actual.size()]), "Request not send successfully by sender: testuser2");

    }

    @Disabled
    @Test 
    public void givenSameRequestsSendThrowsExceptionTest() {
        Requests testRequest = new Requests("testuser2", "testuser1");

        assertThrows(Exception.class, () -> {requestsDAO.send(testRequest);},
        "Exception is not caught");
    }

    @Test
    public void givenRequestsDeleteTest() {
        Requests testRequest = new Requests("testuser2", "testuser1");

        ArrayList<Requests> expected = new ArrayList<>();

        requestsDAO.delete(testRequest);

        ArrayList<Requests> actual = requestsDAO.retrieveAllBySender(new User("testuser1", "TESTER"));

        assertArrayEquals(expected.toArray(new Requests[expected.size()]), actual.toArray(new Requests[actual.size()]), "Arrays Mismatched: Actual not equal to Expected");
    }

}