package com.example.schedulingtasks.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String state;
    private Integer number;
    private Double money;
    private Boolean isApplied;
    private Date date;
}
