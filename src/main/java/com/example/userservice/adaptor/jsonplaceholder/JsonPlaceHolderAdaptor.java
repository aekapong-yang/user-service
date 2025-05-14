package com.example.userservice.adaptor.jsonplaceholder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class JsonPlaceHolderAdaptor {
    private final RestClient restClient;

    public String getPostById(Long id) {
        return restClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/" + id)
                .retrieve()
                .body(String.class);
    }
}
