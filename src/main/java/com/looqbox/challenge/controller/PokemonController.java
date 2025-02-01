package com.looqbox.challenge.controller;

import org.springframework.web.bind.annotation.RestController;

import com.looqbox.challenge.constant.SortType;
import com.looqbox.challenge.model.response.PokemonResponse;
import com.looqbox.challenge.service.PokemonService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("pokemons")
public class PokemonController {

    PokemonService service;

    public PokemonController(PokemonService pokemonService) {
        this.service = pokemonService;
    }

    @GetMapping
    public ResponseEntity<PokemonResponse> getPokemons(
            @RequestParam(required = false) String query, 
            @RequestParam(defaultValue = "ALPHABETICAL") SortType sort) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getPokemonService(query, sort));
    }

    @GetMapping("highlight")
    public ResponseEntity<String> getPokemonHighlights(@RequestParam String param) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getPokemonHighlightsService(param));
    }
}
