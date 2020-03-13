package com.example.schedulingtasks.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccessLevelEnum {
    OWNER("OWNER"),
    READ("READ"),
    WRITE("WRITE");

    private final String value;
}
