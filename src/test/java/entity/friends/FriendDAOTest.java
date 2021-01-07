package entity.friends;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.*;

import entity.users.User;

public class FriendDAOTest {

     FriendDAO friendDAO = new FriendDAO();

     @Test
     public void givenUserRetrieveFriendTest() {

          User user = new User("apple", "Apple ONG");

          List<Friend> expected = new ArrayList<>();

          expected.add(new Friend("apple", "dick"));
          expected.add(new Friend("apple", "mike"));
           
          List<Friend> result = friendDAO.retrieveFriends(user);
          
          assertArrayEquals(expected.toArray(new Friend[expected.size()]), result.toArray(new Friend[result.size()]), "Array Mismatched, Result not equals to Expected");

     }

     @Test
     public void addTest() {

          User user = new User("apple", "Apple ONG");
          Friend friend = new Friend("apple", "testuser");

          friendDAO.add(friend);

          ArrayList<Friend> expected = new ArrayList<>();

          expected.add(new Friend("apple", "dick"));
          expected.add(new Friend("apple", "mike"));
          expected.add(new Friend("apple", "testuser"));

          ArrayList<Friend> result = friendDAO.retrieveFriends(user);
          
          assertArrayEquals((expected.toArray(new Friend[expected.size()])), (result.toArray(new Friend[result.size()])), "testuser was not found in the apple list of friends");

     }


     @Test
     public void deleteTest() {

          Friend friend = new Friend("apple", "testuser");
          User user = new User("apple", "Apple ONG");

          friendDAO.delete(friend);

          ArrayList<Friend> expected = new ArrayList<>();

          expected.add(new Friend("apple", "dick"));
          expected.add(new Friend("apple", "mike"));

          ArrayList<Friend> result = friendDAO.retrieveFriends(user);
          
          assertArrayEquals(expected.toArray(new Friend[expected.size()]), result.toArray(new Friend[result.size()]), "testuser was not removed");
     }

}