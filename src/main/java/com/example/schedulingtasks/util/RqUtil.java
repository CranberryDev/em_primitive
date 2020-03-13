package com.example.schedulingtasks.util;

import com.example.schedulingtasks.domain.dto.NoteDto;
import com.example.schedulingtasks.domain.dto.PageRq;
import com.example.schedulingtasks.domain.dto.UserNoteRq;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Objects;


@NoArgsConstructor
public class RqUtil {

    public static void checkPage(final PageRq page) {
        if(Objects.isNull(page) || Objects.isNull(page.getLimit()) || Objects.isNull(page.getOffset())) {
            throw new RuntimeException();
        }
    }

    public static PageRequest from(final Integer offset, final Integer limit) {
        if(Objects.isNull(offset) || Objects.isNull(limit)) {
            throw new RuntimeException();
        }
        if(offset < 0 || limit < 0) {
            throw new RuntimeException();
        }
        if(limit.equals(0)) {
            throw new RuntimeException();
        }
        if(offset.equals(0)) {
            return PageRequest.of(0, limit);
        }

        final Integer page = offset / limit;

        return PageRequest.of(page, limit);
    }

    public static void checkOnCreate(final List<NoteDto> notes) {
        if(notes.stream().anyMatch(note -> Objects.nonNull(note.getId()))) {
            throw new RuntimeException();
        }
    }

    public static void checkOnUpdate(final List<NoteDto> notes) {
        if(notes.stream().anyMatch(note -> Objects.isNull(note.getId()))) {
            throw new RuntimeException();
        }
    }

    public static void checkOnGrantAuthority(final List<UserNoteRq> userNotes) {
        if(userNotes.stream().anyMatch(RqUtil::isBadParamForGrantAuthority)) {
            throw new RuntimeException();
        }
    }

    public static void checkOnRemoveAuthority(final List<UserNoteRq> userNotes) {
        if(userNotes.stream().anyMatch(RqUtil::isBadParamForRemoveAuthority)) {
            throw new RuntimeException();
        }
    }

    private static boolean isBadParamForGrantAuthority(final UserNoteRq userNote) {
        if(Objects.isNull(userNote)) {
            return true;
        }
        if(Objects.isNull(userNote.getNote()) || Objects.isNull(userNote.getUser())) {
            return true;
        }
        return Objects.isNull(userNote.getAccessLevel()) || Objects.isNull(userNote.getAccessLevel().getValue());
    }

    private static boolean isBadParamForRemoveAuthority(final UserNoteRq userNote) {
        return Objects.isNull(userNote.getUser()) || Objects.isNull(userNote.getNote());
    }



}
