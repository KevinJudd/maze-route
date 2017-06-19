package com.ktj.mazeroute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * <p>
 * Title: 	Lakana Java Architecture<br />
 * Company: 	Lakana, Inc.<br />
 * Copyright: 	Copyright 2015
 * </p>
 * Description:
 */
@RestController
@RequestMapping("/solve")
public class WebController {
    private Logger logger = LoggerFactory.getLogger(WebController.class);

    Converter converter = new Converter();

    @RequestMapping(value={"/demo"}, method=RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<MazeDto> solveTest1Maze(){
        return new ResponseEntity<MazeDto>(demoMaze(), HttpStatus.OK);
    }

    protected MazeDto demoMaze(){
        Location[][] locations = new Location[2][2];
        locations[0][0] = new Location(LocationStatus.start);
        locations[0][1] = new Location(LocationStatus.obstruction);
        locations[1][0] = new Location(LocationStatus.obstruction);
        locations[1][1] = new Location(LocationStatus.end);
        Maze maze = new Maze();
        maze.setMaze(locations);
        maze.setStepsInRoute(1);
        return converter.convert(maze);
    }
}
