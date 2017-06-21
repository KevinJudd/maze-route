package com.ktj.mazeroute;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Description:
 */
public class MazeEzTest {
    private Logger logger = LoggerFactory.getLogger(MazeEzTest.class);

    Maze maze;

    @BeforeSuite
    public void testBeforeSuite() throws IOException {
        File file = new File("src/test/resources/data/test-mazeNE-SW.txt");
        assertTrue(file.exists(), String.format("cannot find file %s", file.getAbsolutePath()));
        maze = new Maze(file);
        assertNotNull(maze);
        assertEquals(maze.getNumberColumns(), 10);
        assertEquals(maze.getNumberRows(), 8);
    }

    @Test
    public void walkTheMaze(){
        boolean foundIt = maze.findRoute();
        assertTrue(foundIt);
        Utils.printAMaze(maze.getMaze());
    }


}
