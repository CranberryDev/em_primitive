package com.example.schedulingtasks.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageRq {

    private Integer offset;
    private Integer limit;

}
