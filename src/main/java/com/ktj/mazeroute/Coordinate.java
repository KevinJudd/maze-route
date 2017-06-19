package com.ktj.mazeroute;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * </p>
 * Description:
 * This class describes a single Map Coordinate.
 * Row and Columnn follow Java convention and begin with zero.
 */
@ToString(includeFieldNames = true, exclude = { "logger"})
@NoArgsConstructor
@Data()
public class Coordinate {
    private Logger logger = LoggerFactory.getLogger(Coordinate.class);
    private int row;
    private int column;

    /**
     * row and column are actually java index; 0 based.
     * @param row
     * @param column
     */
    public Coordinate(int row, int column){
        this.row = row;
        this.column =column;
    }

    public boolean isNorthOOB(){
        return row ==0;
    }
    public boolean isEastOOB(int mazeNumberColumns){
        return column >= (mazeNumberColumns - 1);
    }
    public boolean isSouthOOB(int mazeNumberRows){
        return row >= (mazeNumberRows - 1);
    }
    public boolean isWestOOB(){
        return column == 0;
    }

    //  Check the OOB before running this!
    public Coordinate northernNeighbor(){
        return new Coordinate(row - 1, column);
    }
    // Check the OOB before running this!
    public Coordinate easternNeighbor(){
        return new Coordinate(row, column + 1);
    }
    // Check the OOB before running this!
    public Coordinate southernNeighbor(){
        return new Coordinate(row + 1, column);
    }
    // Check the OOB before running this!
    public Coordinate westernNeighbor(){
        return new Coordinate(row, column - 1);
    }

    // diagonal
    public Coordinate northEastNeighbor(){
        return new Coordinate(row - 1, column + 1);
    }
    public Coordinate southEastNeighbor(){
        return new Coordinate(row + 1, column + 1);
    }
    public Coordinate southWestNeighbor(){
        return new Coordinate(row + 1, column - 1);
    }
    public Coordinate northWestNeighbor(){
        return new Coordinate(row -1 , column - 1);
    }
}
