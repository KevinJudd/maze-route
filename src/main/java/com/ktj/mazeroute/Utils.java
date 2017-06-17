package com.ktj.mazeroute;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>
 * Title: 	Lakana Java Architecture<br />
 * Company: 	Lakana, Inc.<br />
 * Copyright: 	Copyright 2015
 * </p>
 * Description:
 */
public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static Location[][] buildALocationArray(List<String> list){
        int rows =  list.size();
        int columns = 0;
        // first get the dimensions so we can initialize the array.
        for (String line: list){
            if (line.length() == 0){
                logger.warn("Ignoring 0 length line from file.");
                continue;
            }
            if (columns ==  0){
                columns = line.length();
            }
            if (columns != line.length()){
                logger.warn("Maze input lines have different lengths{} and {}!!", columns, line.length());
            }
        }
        Location[][] locArray = new Location[rows][columns];

        // load it up!
        int row = 0;
        for (String line: list){
            for (int tokenCol = 0; tokenCol < line.length(); tokenCol++){
                locArray[row][tokenCol] = new Location(LocationStatus.translateFileToken(line.charAt(tokenCol)));
            }
            row+=1;
        }
        return locArray;
    }

    public static boolean validateLocationArray(Location[][] locationArray){
        int starts = 0;
        int ends = 0;

        for (int row=0;  row < locationArray.length; row ++){
            Location[] rowOfLocs = locationArray[row];
            for (int col = 0; col < rowOfLocs.length; col++){
                if (rowOfLocs[col].getStatus().equals(LocationStatus.start) ){
                    starts+= 1;
                } else {
                    if (rowOfLocs[col].getStatus().equals(LocationStatus.end) ){
                        ends += 1;
                    }
                }
            }
        }

        if ((starts == 1) && (ends == 1) ){
            return true;
        }
        return false;
    }

    public static Coordinate findStart(Location[][] locationArray){

            for (int row=0;  row < locationArray.length; row ++){
                Location[] rowOfLocs = locationArray[row];
                for (int col = 0; col < rowOfLocs.length; col++){
                    if (rowOfLocs[col].getStatus().equals(LocationStatus.start)){
                        return new Coordinate(row, col);
                    }
                }
            }
        throw new IllegalStateException("No start is defined!");
    }

    public static Coordinate findEnd(Location[][] locationArray){

            for (int row=0;  row < locationArray.length; row ++){
                Location[] rowOfLocs = locationArray[row];
                for (int col = 0; col < rowOfLocs.length; col++){
                    if (rowOfLocs[col].getStatus().equals(LocationStatus.end )){
                        return new Coordinate(row, col);
                    }
                }
            }
        throw new IllegalStateException("No end is defined!");
    }

    /**
     * return an array of ints giving number of  rows and number of columsn of this array.
     * @param locationArray
     * @return
     */
    public static int[] calculateDimensions(Location[][] locationArray){
        int[] dimens = new int[2];
        dimens[0] = locationArray.length;
        dimens[1] = locationArray[0].length;
        return dimens;

    }
}
