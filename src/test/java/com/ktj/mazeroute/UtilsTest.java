package com.ktj.mazeroute;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.*;

/**
 *  * Description:
 */
public class UtilsTest {
    private Logger logger = LoggerFactory.getLogger(UtilsTest.class);
    private Location[][] locations;
    private Location[][] locationsBad;   // will have no start

    @BeforeSuite
    public void testBeforeSuite() throws IOException {
        File file = new File("src/test/resources/data/test-maze1.txt");
        assertTrue(file.exists(), String.format("cannot find file %s", file.getAbsolutePath()));
        locations = Utils.buildALocationArray(FileUtils.readLines(file, "UTF-8"));
        assertNotNull(file);
        assertEquals(locations.length, 8);
        assertEquals(locations[0].length, 10);

        locationsBad = new Location[2][2];
        locationsBad[0][0] = new Location(LocationStatus.obstruction);
        locationsBad[0][1] = new Location(LocationStatus.end);
        locationsBad[1][0] = new Location(LocationStatus.obstruction);
        locationsBad[1][1] = new Location(LocationStatus.obstruction);
    }

    @Test
    public void testFileUploadGoodCalc() throws IOException {
        assertEquals(locations[0][0].getStatus(), LocationStatus.obstruction);
        for (int i = 0; i < locations[0].length; i++){
            assertEquals(locations[0][i].getStatus(), LocationStatus.obstruction, String.format("problem with start location idx %d",i));  // whole first row is obstructions.
            assertEquals(locations[7][i].getStatus(), LocationStatus.obstruction, String.format("problem with end location idx %d", i));
        }
        assertEquals(locations[1][1].getStatus(), LocationStatus.start);
        assertEquals(locations[4][8].getStatus(), LocationStatus.end);
    }

    @Test
    public void testIsValid(){
        assertTrue(Utils.validateLocationArray(locations));
        assertFalse(Utils.validateLocationArray(locationsBad));
    }

    @Test
    public void testFinders(){
        assertEquals(Utils.findStart(locations), new Coordinate(1,1));
        assertEquals(Utils.findEnd(locations), new Coordinate(4,8));
    }

}
