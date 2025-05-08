package com.example.userservice.controller;

import com.example.userservice.dto.ApiResponse;
import com.example.userservice.dto.user.GetUserAllRequest;
import com.example.userservice.dto.user.GetUserAllResponse;
import com.example.userservice.dto.user.GetUserByIdResponse;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<GetUserAllResponse>> getUserAll(GetUserAllRequest request) {
        return userService.getUserAll(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetUserByIdResponse>> getUserById(
            @PathVariable long id) {
        return userService.getUserById(id);
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
