package com.example.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
