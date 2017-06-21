package com.ktj.mazeroute;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * </p>
 * Description:
 */
@ToString(includeFieldNames = true, exclude = {"logger"})
@NoArgsConstructor
@Data()
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {
    private LocationStatus status;
    public Location(LocationStatus status){
        this.status = status;
    }
}
