package com.ktj.mazeroute;

/**
 * Description:
 * The Status of a location.  Within a map route, any location can have only one status.
 */
public enum     LocationStatus {
    start, end, open, step, dead_end, obstruction;

    public static LocationStatus translateFileToken(char token){
      if (token == '#'){
            return obstruction;
        }
        if (token == '.'){
            return open;
        }
        if (token == 'A'){
            return start;
        }
        if (token == 'B'){
            return end;
        }
        throw new IllegalArgumentException(String.format("Cannot translateFileToken of {}", token));
    }

    public static String toFileToken(LocationStatus status){
        if (status == null){
            throw new IllegalArgumentException("Unable to translate toFileToken for a null Location Status");
        }
        if (status == start){
            return "A";
        }
        if (status == end){
            return "B";
        }
        if (status == open){
            return ".";
        }
        if (status == step){
            return "S";
        }
        if (status == dead_end){
            return "/";
        }
        if (status == obstruction){
            return "#";
        }
        return status.toString();
    }
}
