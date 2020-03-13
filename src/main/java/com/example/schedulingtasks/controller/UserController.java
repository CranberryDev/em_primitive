package com.example.schedulingtasks.controller;

import com.example.schedulingtasks.constant.RequestConstant;
import com.example.schedulingtasks.converter.NoteConverter;
import com.example.schedulingtasks.converter.UserConverter;
import com.example.schedulingtasks.domain.dto.NoteDto;
import com.example.schedulingtasks.domain.dto.UserDto;
import com.example.schedulingtasks.domain.dto.UserRq;
import com.example.schedulingtasks.domain.entity.Note;
import com.example.schedulingtasks.domain.entity.User;
import com.example.schedulingtasks.service.UserService;
import com.example.schedulingtasks.util.RqUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public List<NoteDto> allAccessibleNotes(
            @RequestParam(name = "page", defaultValue = RequestConstant.PAGE_STRING_DEFAULT) final Integer page,
            @RequestParam(name = "limit", defaultValue = RequestConstant.LIMIT_STRING_DEFAULT) final Integer limit
    ) {
        final List<Note> notes = userService.getNotesByUser(PageRequest.of(page, limit));
        return NoteConverter.toDtoList(notes);
    }

    @PostMapping(path = "notes/update")
    public List<Object> updateNotes() {
        return new ArrayList<>();
    }

    @PutMapping(path = "notes/create")
    public List<Object> createNotes(@RequestBody final Object obj) {
        return null;
    }

    @DeleteMapping(path = "notes")
    public void deleteNotes() {

    }

}
