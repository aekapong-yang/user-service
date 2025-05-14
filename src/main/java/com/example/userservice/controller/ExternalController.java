package com.example.userservice.controller;

import com.example.userservice.adaptor.jsonplaceholder.JsonPlaceHolderAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/externals")
public class ExternalController {
    private final JsonPlaceHolderAdaptor jsonPlaceHolderAdaptor;

    @GetMapping("{id}")
    public ResponseEntity<String> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonPlaceHolderAdaptor.getPostById(id));
    }
}
