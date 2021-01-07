package entity.cityfarmer;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FarmerDAOTest {

    FarmerDAO farmerDAO = new FarmerDAO();

    @Test
    public void givenUsernameGetFarmerTest() {
        String username = "moe";

        Farmer expected = new Farmer("moe", 15000, 2000);

        Farmer actual = farmerDAO.getFarmerbyUsername(username);

        assertEquals(expected, actual, "Farmer moe is not found!");

    }

    String testUsername = "joe";

    @Test
    public void givenUsernameAddTest() {

        Farmer expected = new Farmer("joe", 0, 50);

        farmerDAO.add(testUsername);

        Farmer actual = farmerDAO.getFarmerbyUsername(testUsername);

        assertEquals(expected, actual, "Farmer joe is not found after adding");
    }

    @Test
    public void givenUsernameAndGoldAmtUpdateGoldTest() {
        int goldAmt = 1000;

        Farmer expected = new Farmer(testUsername, 0, 1000);

        farmerDAO.updateGold(testUsername, goldAmt);

        Farmer actual = farmerDAO.getFarmerbyUsername(testUsername);

        assertEquals(expected, actual, "The amount of Gold for joe is not updated");
    }

    @Test
    public void givenUsernameAndExpAmtUpdateExpTest() {
        int expAmt = 2000;

        Farmer expected = new Farmer(testUsername, 2000, 1000);

        farmerDAO.updateEXP(testUsername, expAmt);

        Farmer actual = farmerDAO.getFarmerbyUsername(testUsername);

        assertEquals(expected, actual, "The amount of Exp for joe is not updated");
    }

}