package com.jdbc.basic.schedule.domain;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    private String userId;
    private String password;
}
