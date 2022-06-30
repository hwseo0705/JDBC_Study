package com.jdbc.basic.schedule.domain;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Schedule {

    private int scheduleId;
    private String category;
    private String scheduleName;
    private String dateTime;
    private String location;
    private String note;

}
