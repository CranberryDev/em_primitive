package com.example.schedulingtasks.controller;

import com.example.schedulingtasks.converter.UserConverter;
import com.example.schedulingtasks.domain.dto.UserDto;
import com.example.schedulingtasks.domain.dto.UserRq;
import com.example.schedulingtasks.domain.entity.User;
import com.example.schedulingtasks.service.UserService;
import com.example.schedulingtasks.util.RqUtil;
import lombok.RequiredArgsConstructor;
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

}
