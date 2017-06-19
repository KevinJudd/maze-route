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
    private int stepsInRoute;

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

    public Location retrieveMazeLocation(Coordinate coordinate){
        return maze[coordinate.getRow()][coordinate.getColumn()];
    }
    public void updateMazeLocationStatus(Coordinate coordinate, LocationStatus status){
        logger.info("Set coordinate {} to status {}", coordinate.toString(), status.toString());
        maze[coordinate.getRow()][coordinate.getColumn()].setStatus(status);
    }

    public boolean findRoute(){
        boolean foundRoute = walk(begin);
        if (foundRoute){
            stepsInRoute = Utils.countSteps(maze);
        }
        return foundRoute;
    }
    /**
     * Recursive method.  Given a good location, find the next step and go there.
     * Update the maze with your route as you go.
     * We have to walk N, E, S, or W into a coordinate that is OPEN.
     * return of true from recursion means this step was a step on a valid route.
     * Return of false means this step does not get us to the end.  If we have another option for a step take it.
     *     If there are no other options for steps, Mark this Coordinate as DEAD END and return false.
     * @param coordinate
     * @return
     */
    public boolean walk(Coordinate coordinate) {

        if (isAbuttingTheEnd(coordinate)){
            //  we found the end; this is a good route!
            return true;
        }
        Coordinate chosenStep = null;
        boolean foundEnd = false;

        while (foundEnd == false) {
            chosenStep = checkDiagonalsForEnd(coordinate);

            if (chosenStep == null) {
                chosenStep = moveTowardEnd(coordinate);
            }

            if (chosenStep == null) {
                chosenStep = pickAStep(coordinate);
            }

            if (chosenStep == null) {
                // there are no available steps.  We are at a dead end.
                if (isMazeCoordinateLocationStatus(coordinate, LocationStatus.step)) {
                    // only update location status if status is open.  What else could it be?  Start!
                    updateMazeLocationStatus(coordinate, LocationStatus.dead_end);
                } else {
                    logger.info("We have no available steps from coordinate {}; do not update current status of {}",
                            coordinate.toString(), retrieveMazeLocation(coordinate).getStatus().toString()  );
                }
                return false;  // signals to the calling methods in the recursion stack that this is a dead end.
            } else {
                updateMazeLocationStatus(chosenStep, LocationStatus.step);   // this may get overridden later to a dead_end.
                foundEnd = walk(chosenStep);
            }
        }  //  end of while loop.  We know here that this coordinate is a step toward the solution OR a step in a dead end.
        return foundEnd;
    }

    protected boolean isAbuttingTheEnd(Coordinate coordinate){
        // first find if this coordinate is next to the End.  If so, we are done.
        if (!coordinate.isNorthOOB() && retrieveMazeLocation(coordinate.northernNeighbor()).getStatus().equals(LocationStatus.end)){
            //   we're done
            return true;
        }
        if (!coordinate.isEastOOB(numberColumns) && retrieveMazeLocation(coordinate.easternNeighbor()).getStatus().equals(LocationStatus.end)){
            return true;
        }
        if (!coordinate.isSouthOOB(numberRows) && retrieveMazeLocation(coordinate.southernNeighbor()).getStatus().equals(LocationStatus.end)){
           return true;
        }
        if (!coordinate.isWestOOB() && retrieveMazeLocation(coordinate.westernNeighbor()).getStatus().equals(LocationStatus.end)) {
            return true;
        }
        return false;
    }

    /**
     * If there is a diagonal coordinate that is the end, return an adjacent coordinate.  Else return null.
     * @param coordinate
     * @return
     */
    protected Coordinate checkDiagonalsForEnd(Coordinate coordinate){
        // see if this coordinate is diagonal from an end, and if we can move into an Open Coordinate next to it.
        //  check northEast neighbor
        if (!coordinate.isNorthOOB()
                && !coordinate.isEastOOB(numberColumns)
                && retrieveMazeLocation(coordinate.northEastNeighbor()).getStatus().equals(LocationStatus.end)){
            if (isMazeCoordinateLocationStatusOpen(coordinate.northernNeighbor())){
                return coordinate.northernNeighbor();
            }
            if (isMazeCoordinateLocationStatusOpen(coordinate.easternNeighbor())){
                return coordinate.easternNeighbor();
            }
        }
        //  check southEast neighbor
        if (!coordinate.isEastOOB(numberColumns)
                && (!coordinate.isSouthOOB(numberRows))
                && retrieveMazeLocation(coordinate.southEastNeighbor()).getStatus().equals(LocationStatus.end)){
            if (isMazeCoordinateLocationStatusOpen(coordinate.easternNeighbor())){
                return coordinate.easternNeighbor();
            }
            if (isMazeCoordinateLocationStatusOpen(coordinate.southernNeighbor())){
                return coordinate.southernNeighbor();
            }
        }
        //  check southWest neighbor
        if (!coordinate.isSouthOOB(numberRows)
                && (!coordinate.isWestOOB())
                && (retrieveMazeLocation(coordinate.southWestNeighbor()).getStatus().equals(LocationStatus.end))){
            if (isMazeCoordinateLocationStatusOpen(coordinate.southernNeighbor())){
                return coordinate.southernNeighbor();
            }
            if (isMazeCoordinateLocationStatusOpen(coordinate.westernNeighbor())){
                return coordinate.westernNeighbor();
            }
        }
        //  check northWest neighbor
        if (!coordinate.isWestOOB()
                && (!coordinate.isNorthOOB())
                && (retrieveMazeLocation(coordinate.northWestNeighbor()).getStatus().equals(LocationStatus.end))){
            if (isMazeCoordinateLocationStatusOpen(coordinate.northernNeighbor())){
                return coordinate.northernNeighbor();
            }
            if (isMazeCoordinateLocationStatusOpen(coordinate.westernNeighbor())){
                return coordinate.westernNeighbor();
            }
        }
        return null;
    }

    protected Coordinate moveTowardEnd(Coordinate coordinate){
        int horizontalShift = Math.abs(coordinate.getColumn() - end.getColumn());
        int verticalShift = Math.abs(coordinate.getRow() - end.getRow());

        if (verticalShift < horizontalShift){
            if (coordinate.getColumn() > end.getColumn()){
                // move west is preference
                if (isMazeCoordinateLocationStatusOpen(coordinate.westernNeighbor())){
                    return coordinate.westernNeighbor();
                }
            } else {
                // move east
                if (isMazeCoordinateLocationStatusOpen(coordinate.easternNeighbor())){
                    return coordinate.easternNeighbor();
                }
            }
        }
        if (horizontalShift < verticalShift){
            if (coordinate.getRow() < end.getRow()){
                // south
                if (isMazeCoordinateLocationStatusOpen(coordinate.southernNeighbor())){
                    return coordinate.southernNeighbor();
                }
            } else {
                if (isMazeCoordinateLocationStatusOpen(coordinate.northernNeighbor())){
                    return coordinate.northernNeighbor();
                }
            }
        }

        if (coordinate.getRow() < end.getRow()){
            // south
            if (isMazeCoordinateLocationStatusOpen(coordinate.southernNeighbor())){
                return coordinate.southernNeighbor();
            }
        } else {
            if (isMazeCoordinateLocationStatusOpen(coordinate.northernNeighbor())){
                return coordinate.northernNeighbor();
            }
        }
        if (coordinate.getColumn() > end.getColumn()){
            // move west is preference
            if (isMazeCoordinateLocationStatusOpen(coordinate.westernNeighbor())){
                return coordinate.westernNeighbor();
            }
        } else {
            // move east
            if (isMazeCoordinateLocationStatusOpen(coordinate.easternNeighbor())){
                return coordinate.easternNeighbor();
            }
        }
        return null;
    }

    /**
     * Having inspected all steps that might be preferable, randomly choose any available step.
     * If none available, return null
     * @param coordinate
     * @return
     */
    protected Coordinate pickAStep(Coordinate coordinate){
        //  Move to north, east, south, west; find the first one that is OPEN
        // try North
        if (isMazeCoordinateLocationStatusOpen(coordinate.northernNeighbor())){
            return coordinate.northernNeighbor();
        }
        // try East
        if (isMazeCoordinateLocationStatusOpen(coordinate.easternNeighbor())){
            return coordinate.easternNeighbor();
        }
        // try South
        if (isMazeCoordinateLocationStatusOpen(coordinate.southernNeighbor())){
            return coordinate.southernNeighbor();
        }
        // try West
        if (isMazeCoordinateLocationStatusOpen(coordinate.westernNeighbor())){
            return coordinate.westernNeighbor();
        }
        return null;
    }

    protected boolean isMazeCoordinateLocationStatus(Coordinate coordinate, LocationStatus locationStatus){
        return retrieveMazeLocation(coordinate).getStatus().equals(locationStatus);
    }
    protected boolean isMazeCoordinateLocationStatusOpen(Coordinate coordinate){
        return isMazeCoordinateLocationStatus(coordinate, LocationStatus.open);
    }

}
