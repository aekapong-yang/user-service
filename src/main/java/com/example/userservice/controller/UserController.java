package com.example.userservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @GetMapping
    public String getUserAll() {
        return "get users all";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable long id) {
        return "user " + id;
    }

    @PutMapping("/{id}")
    public String updateById(@PathVariable long id) {
        return "update user id " + id;
    }

    @PostMapping()
    public String createById() {
        return "create user";
    }
}
