package com.example.schedulingtasks.converter;

import com.example.schedulingtasks.domain.dto.NoteAndAccessLevel;
import com.example.schedulingtasks.domain.entity.UserNote;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NoteAndAccessLevelConverter {

    public static NoteAndAccessLevel toDto(final UserNote userNote) {
        return new NoteAndAccessLevel()
                .setAccessLevel(userNote.getAccessLevel().getValue())
                .setNote(
                        NoteConverter.toDto(userNote.getNote())
                );
    }

    public static List<NoteAndAccessLevel> toDtoList(final List<UserNote> userNotes) {
        return userNotes.stream().map(NoteAndAccessLevelConverter::toDto).collect(Collectors.toList());
    }

}
