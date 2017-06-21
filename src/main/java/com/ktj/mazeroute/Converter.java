package com.ktj.mazeroute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 */
public class Converter {
    private Logger logger = LoggerFactory.getLogger(Converter.class);

    public MazeDto convert(Maze maze){
        MazeDto dto = new MazeDto();
        dto.setMaze(maze.getMaze());
        dto.setStepsInRoute(maze.getStepsInRoute());
        return dto;
    }
}
