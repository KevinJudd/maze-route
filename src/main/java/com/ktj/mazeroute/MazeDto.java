package com.ktj.mazeroute;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Title: 	Lakana Java Architecture<br />
 * Company: 	Lakana, Inc.<br />
 * Copyright: 	Copyright 2015
 * </p>
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
