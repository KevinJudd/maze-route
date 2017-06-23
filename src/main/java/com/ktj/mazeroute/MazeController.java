package com.ktj.mazeroute;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 * Description:
 */
@RestController
public class MazeController {
    private Logger logger = LoggerFactory.getLogger(MazeController.class);

    Converter converter = new Converter();

    @CrossOrigin
    @RequestMapping(value = "/demo", produces = "application/json")
    public MazeDto displayDemoMaze(){
        return demoMaze();
    }

    protected MazeDto demoMaze(){
        logger.info("In demoMaze()");
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

    @CrossOrigin
    @RequestMapping(value = "/solve/{fileNumber}", produces = "application/json")
    public MazeDto solveFileMaze(HttpServletRequest request, @PathVariable String fileNumber) throws URISyntaxException {

        File mazeFile = null;
        switch (fileNumber) {
            case "1":
                mazeFile = retrieveFile("/data/maze1.txt");
                break;
            case "2":
                mazeFile = retrieveFile("/data/maze2.txt");
                break;
            case "3":
                mazeFile = retrieveFile("/data/maze3.txt");
                break;
            default:
                String errorMessage = String.format("Invalid fileNumber (%s) entered!  Cannot process.  Exiting!", fileNumber);
                logger.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
        }
        if (mazeFile.exists()){
            logger.info("Processing file %s", mazeFile.getAbsolutePath());
        } else {
            String errorMessage = String.format("Cannot find file %s!", mazeFile.getAbsolutePath());
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        Maze maze = new Maze(mazeFile);
        boolean found = maze.findRoute();
        return converter.convert(maze);
    }

    private File retrieveFile(String fileName) throws URISyntaxException {
        File classpathFile = new File(this.getClass().getResource(fileName).toURI());
        if (classpathFile.exists()){
            logger.info("found file {}", classpathFile.getAbsolutePath());
        } else {
            logger.error("Unable to find file at {}", fileName);
            throw new IllegalArgumentException("Unable to find file at " + fileName);
        }
        return classpathFile;
    }
}
