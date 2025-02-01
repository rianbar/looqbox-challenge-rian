package com.looqbox.challenge.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.looqbox.challenge.model.response.ApiResponse;

@Service
public class PokeApiService {

    @Value("${api.pokemon.url}")
    private String url;

    public ApiResponse retrieveAllPokemons() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, ApiResponse.class);
    }
}
