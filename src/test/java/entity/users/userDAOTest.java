package entity.users;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import entity.users.UserDAO;
import entity.users.User;

public class userDAOTest {

     UserDAO userDAO = new UserDAO();

     @Test
     public void givenUsernamePasswordAuthenticateTest() {

        String username = "apple";
        String password = "apple123";

        Boolean actual = userDAO.authenticate(username, password);
          
        assertTrue(actual,"Result not equals to true, expected true");

     }

     @Test
     public void givenUsernameGetUser() {

        String username = "apple";

        User expected = new User("apple", "Apple ANG");
        User actualUser = userDAO.getUserbyUsername(username);
        Boolean actual = actualUser.equals(expected);
        
        assertTrue(actual, "Actual User does not equals to Expected User");

     }


     @Test
     public void addTest() {

        String username = "Bob";
        String password = "supersecurepassword";
        String fullName = "Bob AttackUrServer";

        Boolean actual = userDAO.add(username,password,fullName);

        assertTrue(actual, "Result not equals to true, expected true");
     }

}