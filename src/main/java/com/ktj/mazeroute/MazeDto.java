package com.ktj.mazeroute;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Description:
 */
@ToString(includeFieldNames = true)
@NoArgsConstructor
@Data()
@EqualsAndHashCode()
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MazeDto {
    int stepsInRoute;
    Location[][] maze;
}
