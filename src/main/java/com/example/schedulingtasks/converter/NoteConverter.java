package com.example.schedulingtasks.converter;

import com.example.schedulingtasks.domain.dto.NoteDto;
import com.example.schedulingtasks.domain.entity.Note;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NoteConverter {

    public static NoteDto toDto(final Note note) {
        return new NoteDto()
                .setId(note.getId())
                .setText(note.getText());
    }

    public static List<NoteDto> toDtoList(final List<Note> notes) {
        return notes.stream().map(NoteConverter::toDto).collect(Collectors.toList());
    }

    public static Note toEntity(final NoteDto note) {
        return new Note()
                .setId(note.getId())
                .setText(note.getText());
    }

    public static List<Note> toEntityList(final List<NoteDto> notes) {
        return notes.stream().map(NoteConverter::toEntity).collect(Collectors.toList());
    }
}
