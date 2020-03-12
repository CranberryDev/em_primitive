package com.example.schedulingtasks.converter;

import com.example.schedulingtasks.domain.dto.UserDto;
import com.example.schedulingtasks.domain.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {

    public static User toEntity(UserDto dto) {
        return new User()
                .setId(dto.getId())
                .setIsApplied(dto.getIsApplied())
                .setMoney(dto.getMoney())
                .setName(dto.getName())
                .setNumber(dto.getNumber())
                .setState(dto.getState())
                .setDate(dto.getDate());
    }

}
