package com.example.schedulingtasks.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccessLevelEnum {
    OWNER("OWNER"),
    READ("READ"),
    WRITE("WRITE"),
    PUBLIC_READ("PUBLIC_READ"),
    PUBLIC_WRITE("PUBLIC_WRITE");

    private final String value;
}
