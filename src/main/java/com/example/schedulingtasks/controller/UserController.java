package com.example.schedulingtasks.controller;

import com.example.schedulingtasks.constant.RequestConstant;
import com.example.schedulingtasks.converter.NoteAndAccessLevelConverter;
import com.example.schedulingtasks.converter.NoteConverter;
import com.example.schedulingtasks.converter.UserConverter;
import com.example.schedulingtasks.converter.UserNoteConverter;
import com.example.schedulingtasks.domain.dto.*;
import com.example.schedulingtasks.domain.entity.Note;
import com.example.schedulingtasks.domain.entity.User;
import com.example.schedulingtasks.domain.entity.UserNote;
import com.example.schedulingtasks.service.UserService;
import com.example.schedulingtasks.util.RqUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "get")
    public List<User> getByFilter(@RequestBody UserRq user) {
        RqUtil.checkPage(user.getPage());
        return userService.getAllByFilter(UserConverter.toEntity(user.getFilter()), user.getPage());
    }

    @PostMapping(path = "save")
    public User save(@RequestBody UserDto user) {
        return userService.save(UserConverter.toEntity(user));
    }

    @GetMapping(path = "{id}")
    public User getById(@PathVariable(name = "id") Long id) {
        return userService.getById(id);
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        userService.delete(id);
    }


    @GetMapping(path = "notes")
    public List<NoteAndAccessLevel> allAccessibleNotes(
            @RequestParam(name = "page", defaultValue = RequestConstant.PAGE_STRING_DEFAULT) final Integer page,
            @RequestParam(name = "limit", defaultValue = RequestConstant.LIMIT_STRING_DEFAULT) final Integer limit
    ) {
        final List<UserNote> notes = userService.getNotesByUser(PageRequest.of(page, limit));
        return NoteAndAccessLevelConverter.toDtoList(notes);
    }

    @PostMapping(path = "notes")
    public List<NoteDto> updateNotes(@RequestBody final List<NoteDto> notes) {
        RqUtil.checkOnUpdate(notes);
        final List<Note> result = userService.updateNotes(NoteConverter.toEntityList(notes));
        return NoteConverter.toDtoList(result);
    }

    @PutMapping(path = "notes")
    public List<NoteDto> createNotes(@RequestBody final List<NoteDto> notes) {
        RqUtil.checkOnCreate(notes);
        final List<Note> createdNotes = userService.createNotes(NoteConverter.toEntityList(notes));
        return NoteConverter.toDtoList(createdNotes);
    }

    @PutMapping(path = "notes/authority")
    public void grantAuthority(@RequestBody final List<UserNoteRq> notes) {
        RqUtil.checkOnGrantAuthority(notes);
        userService.grantAuthority(UserNoteConverter.toEntityList(notes));
    }

    @DeleteMapping(path = "notes/authority")
    public void removeAuthority(@RequestBody final List<UserNoteRq> notes) {
        RqUtil.checkOnRemoveAuthority(notes);
        userService.removeAuthority(UserNoteConverter.toEntityList(notes));
    }

}
