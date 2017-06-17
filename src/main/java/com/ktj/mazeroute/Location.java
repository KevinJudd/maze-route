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
 */
@ToString(includeFieldNames = true, exclude = {"logger"})
@NoArgsConstructor
@Data()

public class Location {
    private Logger logger = LoggerFactory.getLogger(Location.class);
    private LocationStatus status;
    public Location(LocationStatus status){
        this.status = status;
    }
}
