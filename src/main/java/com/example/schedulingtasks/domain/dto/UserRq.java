package com.example.schedulingtasks.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRq {

    private UserDto filter;
    private PageRq page;

}
