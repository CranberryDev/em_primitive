package com.example.schedulingtasks.converter;

import com.example.schedulingtasks.domain.dto.UserNoteRq;
import com.example.schedulingtasks.domain.entity.AccessLevel;
import com.example.schedulingtasks.domain.entity.Note;
import com.example.schedulingtasks.domain.entity.User;
import com.example.schedulingtasks.domain.entity.UserNote;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class UserNoteConverter {

    public static UserNote toEntity(final UserNoteRq from) {
        return new UserNote()
                .setNote(
                        new Note().setId(from.getNote())
                )
                .setUser(
                        new User().setId(from.getUser())
                )
                .setAccessLevel(
                        new AccessLevel().setValue(from.getAccessLevel())
                );
    }

    public static List<UserNote> toEntityList(final List<UserNoteRq> fromList) {
        return fromList.stream().map(UserNoteConverter::toEntity).collect(Collectors.toList());
    }

}
