package com.ktj.mazeroute;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * </p>
 * Description:
 */
@ToString(includeFieldNames = true, exclude = { "logger", "maze"})
@NoArgsConstructor
@Data()
public class Maze {
    private Logger logger = LoggerFactory.getLogger(Maze.class);

    private Location[][] maze;
    private Coordinate begin;
    private Coordinate end;
    private int numberRows;
    private int numberColumns;

    public Maze(File file){
        try {
            List<String> mazeList = FileUtils.readLines(file, "UTF8" );
            maze = Utils.buildALocationArray(mazeList);
            begin = Utils.findStart(maze);
            end = Utils.findEnd(maze);
            if (!Utils.validateLocationArray(maze)){
                throw new IllegalStateException(String.format("The maze built from file {} is invalid!", file.getAbsolutePath()));
            }
            int[] dimens = Utils.calculateDimensions(maze);
            numberRows = dimens[0];
            numberColumns = dimens[1];
        } catch (IOException e) {
            logger.error("Error instantiating Maze with file ()", file.getAbsolutePath(), e);
        }
    }

    public Location retrieveLocation(Coordinate coordinate){
        return maze[coordinate.getRow()][coordinate.getColumn()];
    }

    public void findRoute(){
        walk(begin);
    }
    /**
     * Recursive method.  Given a good location, find the next step and go there.
     * Update the maze with your route as you go.
     * We have to walk N, E, S, or W into a coordinate that is OPEN.
     * @param coordinate
     * @return
     */
    public Coordinate walk(Coordinate coordinate) {
        // first find if this coordinate is next to the End.  If so, we are done.
        if (!coordinate.isNorthOOB() && retrieveLocation(coordinate.northernNeighbor()).getStatus().equals(LocationStatus.end)){
            //   we're done
            return coordinate;
        }
        if (!coordinate.isEastOOB(numberColumns) && retrieveLocation(coordinate.easternNeighbor()).getStatus().equals(LocationStatus.end)){
            return coordinate;
        }
        if (!coordinate.isSouthOOB(numberRows) && retrieveLocation(coordinate.southernNeighbor()).getStatus().equals(LocationStatus.end)){
           return coordinate;
        }
        if (!coordinate.isWestOOB() && retrieveLocation(coordinate.westernNeighbor()).getStatus().equals(LocationStatus.end)) {
            return coordinate;
        }

        // see if this coordinate is diagonal from an end, and if we can move into an Open Coordinate next to it.
        if (!coordinate.isNorthOOB() && !coordinate.isEastOOB(numberColumns) && retrieveLocation(coordinate.northEastNeighbor()).getStatus().equals(LocationStatus.end)){
            if (retrieveLocation(coordinate.northernNeighbor()).getStatus().equals(LocationStatus.open)){
                retrieveLocation(coordinate).setStatus(LocationStatus.step);
                walk(coordinate.northernNeighbor());
                return coordinate;
            }
            if (retrieveLocation(coordinate.easternNeighbor()).getStatus().equals(LocationStatus.open)){
                retrieveLocation(coordinate).setStatus(LocationStatus.step);
                walk(coordinate.northernNeighbor());
                return coordinate;
            }
        }
               // then move N, E, S, or W into an open coordinate
            //TODO:  remove dummy return
        return null;
    }
}
