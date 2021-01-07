package entity.cityfarmer;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.sql.Timestamp;
import java.util.*;

public class PlotDAOTest {

    String testUsername = "apple";
    PlotDAO plotDAO = new PlotDAO();

    ArrayList<Plot> expected = new ArrayList<>();

    @Disabled("Timestamp Logic")
    @Test
    public void givenUsernameGetPlotTest() {

        ArrayList<Plot> actual = plotDAO.getPlotbyUsername(testUsername);

        expected.add(new Plot(testUsername, 1, null, 0, null));
        expected.add(new Plot(testUsername, 2, null, 0, null));
        expected.add(new Plot(testUsername, 3, null, 0, null));
        expected.add(new Plot(testUsername, 4, null, 0, null));
        expected.add(new Plot(testUsername, 5, null, 0, null));

        assertArrayEquals(expected.toArray(new Plot[expected.size()]), 
        actual.toArray(new Plot[actual.size()]), "Array Mismatched - Actual is not the same as Expected");

        for (int i = 0; i < expected.size(); i++) {
            expected.remove(i);
        }

    }

    @Disabled("Timestamp logic")
    @Test
    public void givenPlotPlantPlotTest() {
        Plot testPlot = new Plot(testUsername, 3, "Papaya", 0, new Timestamp(10000L));

        expected.add(new Plot(testUsername, 1, null, 0, null));
        expected.add(new Plot(testUsername, 2, null, 0, null));
        expected.add(new Plot(testUsername, 3, "Papaya", 0, new Timestamp(10000L)));
        expected.add(new Plot(testUsername, 4, null, 0, null));
        expected.add(new Plot(testUsername, 5, null, 0, null));

        plotDAO.plantPlot(testPlot);

        ArrayList<Plot> actual = plotDAO.getPlotbyUsername(testUsername);

        assertArrayEquals(expected.toArray(new Plot[expected.size()]), 
        actual.toArray(new Plot[actual.size()]), "Unable to update Plot of apple");

        for (int i = 0; i < expected.size(); i++) {
            expected.remove(i);
        }
    }

    @Disabled("Timestamp Logic")
    @Test
    public void givenPlotClearPlotTest() {
        Plot testPlot = new Plot(testUsername, 3, null, 0, null);

        expected.add(new Plot(testUsername, 1, null, 0, null));
        expected.add(new Plot(testUsername, 2, null, 0, null));
        expected.add(new Plot(testUsername, 3, null, 0, null));
        expected.add(new Plot(testUsername, 4, null, 0, null));
        expected.add(new Plot(testUsername, 5, null, 0, null));

        plotDAO.clearPlot(testPlot);

        ArrayList<Plot> actual = plotDAO.getPlotbyUsername(testUsername);

        assertArrayEquals(expected.toArray(new Plot[expected.size()]), 
        actual.toArray(new Plot[actual.size()]), "Unable to clear Plot of apple");

        for (int i = 0; i < expected.size(); i++) {
            expected.remove(i);
        }
    }

    @Disabled("Timestamp Logic")
    @Test
    public void givenPlotUpdateYieldTest() {
        Plot testPlot = new Plot(testUsername, 3, null, 30, null);

        expected.add(new Plot(testUsername, 1, null, 0, null));
        expected.add(new Plot(testUsername, 2, null, 0, null));
        expected.add(new Plot(testUsername, 3, null, 30, null));
        expected.add(new Plot(testUsername, 4, null, 0, null));
        expected.add(new Plot(testUsername, 5, null, 0, null));

        plotDAO.updateYield(testPlot);

        ArrayList<Plot> actual = plotDAO.getPlotbyUsername(testUsername);

        assertArrayEquals(expected.toArray(new Plot[expected.size()]), 
        actual.toArray(new Plot[actual.size()]), "Unable to update yield for Plot of apple");
    }



}