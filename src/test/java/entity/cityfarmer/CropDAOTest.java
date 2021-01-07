package entity.cityfarmer;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.*;

public class CropDAOTest {

    @Test
    public void retrieveAllCropsTest() {

        ArrayList<Crop> expected = new ArrayList<>();

        expected.add(new Crop("Papaya", 20, 30, 8, 75, 100, 15));
        expected.add(new Crop("Pumpkin", 30, 60, 5, 5, 200, 20));
        expected.add(new Crop("Sunflower", 40, 120, 20, 15, 20, 40));
        expected.add(new Crop("Watermelon", 50, 240, 1, 5, 800, 10));

        ArrayList<Crop> actual = CropDAO.retrieveAllCrops();

        assertArrayEquals(expected.toArray(new Crop[expected.size()]), 
        actual.toArray(new Crop[actual.size()]), "Array Mismatch - Actual not the same as Expected");

    }

    @Test
    public void givenCropNameRetrieveCropTest() {

        String testCropName = "Sunflower";

        Crop testCrop = new Crop("Sunflower", 40, 120, 20, 15, 20, 40);

        Crop actualCrop = CropDAO.retrieveCrops(testCropName);

        assertEquals(testCrop, actualCrop, "Sunflower is not found");
    }


    @Test
    public void givenCropNameFailToRetrieveCropTest() {
        String testCropName = "Mango";

        Crop testCrop = new Crop("Mango", 40, 120, 20, 15, 20, 40);

        Crop actualCrop = CropDAO.retrieveCrops(testCropName);

        assertNotEquals(testCrop, actualCrop, "Mango is not in the database!");
    }

}