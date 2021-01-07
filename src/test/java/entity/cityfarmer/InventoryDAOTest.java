package entity.cityfarmer;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

public class InventoryDAOTest {

    String testUsername = "apple";
    InventoryDAO inventoryDAO = new InventoryDAO();

    @Disabled
    @Test
    public void givenUsernameGetInventoryTest() {
        ArrayList<Inventory> expected = new ArrayList<>();

        expected.add(new Inventory(testUsername, "Pumpkin", 20));
        expected.add(new Inventory(testUsername, "Papaya", 10));
        expected.add(new Inventory(testUsername, "Sunflower", 0));
        expected.add(new Inventory(testUsername, "Watermelon", 0));

        ArrayList<Inventory> actual = inventoryDAO.getInventory(testUsername);

        assertArrayEquals(expected.toArray(new Inventory[expected.size()]), 
        actual.toArray(new Inventory[actual.size()]), "Array Mismatch - Actual does not match Expected");

    }

    @Disabled
    @Test
    public void givenUsernameAndItemNameTest() {
        Inventory expected = new Inventory(testUsername, "Papaya", 10);

        String item = "Papaya";

        Inventory actual = inventoryDAO.getAnInventory(testUsername, item);

        assertEquals(expected, actual, "Item does not exist");

    }
    

}