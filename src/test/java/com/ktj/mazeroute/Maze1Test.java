package com.ktj.mazeroute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.*;

/**
 * <p>
 * Title: 	Lakana Java Architecture<br />
 * Company: 	Lakana, Inc.<br />
 * Copyright: 	Copyright 2015
 * </p>
 * Description:
 */
public class Maze1Test {
    private Logger logger = LoggerFactory.getLogger(Maze1Test.class);

    Maze maze;

    @BeforeSuite
    public void testBeforeSuite() throws IOException {
        File file = new File("src/test/resources/data/test-maze1.txt");
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
        assertEquals(maze.retrieveMazeLocation(new Coordinate(1,4)).getStatus(), LocationStatus.dead_end);

    }


}
