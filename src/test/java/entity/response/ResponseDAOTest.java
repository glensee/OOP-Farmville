package entity.response;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;

public class ResponseDAOTest {
    
    ResponseDAO responseDAO = new ResponseDAO();
    Response testResponse = new Response(99, "testuser", true);

    @Test
    public void givenThreadIDRetrieveAllResponsesByThreadTest() {
        int testThreadID = 1;

        ArrayList<Response> expected = new ArrayList<>();

        expected.add(new Response(1, "mike", true));
        expected.add(new Response(1, "yado", true));
        expected.add(new Response(1, "moe", false));
        expected.add(new Response(1, "dick", true));

        ArrayList<Response> actual = responseDAO.retrieveAllResponsesByThread(testThreadID);

        assertArrayEquals(expected.toArray(new Response[expected.size()]), actual.toArray(new Response[actual.size()]), "Arrays Mismatched: Actual not equal to Expected");

    }

    @Test
    public void givenResponseAddTest() {

        ArrayList<Response> expected = new ArrayList<>();

        expected.add(testResponse);

        try {
            responseDAO.add(testResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Response> actual = responseDAO.retrieveAllResponsesByThread(99);
        
        assertArrayEquals(expected.toArray(new Response[expected.size()]), actual.toArray(new Response[actual.size()]), "testuser threadID 99 was not added");
    }

    @Test
    public void givenResponseAddThrowsExceptionTest() {

        assertThrows(Exception.class, () -> {responseDAO.add(testResponse);}
        , "Exception is not caught");
        
    }

    @Test
    public void givenResponseDeleteTest() {

        ArrayList<Response> expected = new ArrayList<>();

        responseDAO.delete(testResponse);

        ArrayList<Response> actual = responseDAO.retrieveAllResponsesByThread(99);
        
        assertArrayEquals(expected.toArray(new Response[expected.size()]), actual.toArray(new Response[actual.size()]), "testuser threadID 99 was not added");
    }

}