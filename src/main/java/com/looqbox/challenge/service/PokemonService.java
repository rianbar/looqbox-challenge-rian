package com.looqbox.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.looqbox.challenge.constant.SortType;
import com.looqbox.challenge.model.response.ApiResponse;
import com.looqbox.challenge.model.response.HighlightResponse;
import com.looqbox.challenge.model.response.PokemonResponse;
import com.looqbox.challenge.utils.PokemonUtils;

@Service
public class PokemonService {
    
    PokeApiService pokeApi;
    PokemonUtils utils;

    public PokemonService(PokeApiService pokeApiService, PokemonUtils utils) {
        this.pokeApi = pokeApiService;
        this.utils = utils;
    }

    public PokemonResponse<String> getPokemonService(String query, SortType sort) {
        ApiResponse response = pokeApi.retrieveAllPokemons();
        return orderPokemons(response, query, sort);
    }

    public PokemonResponse<HighlightResponse> getPokemonHighlightsService(String query, SortType sort) {
        ApiResponse response = pokeApi.retrieveAllPokemons();
        PokemonResponse<String> orderedPokemons = orderPokemons(response, query, sort);
        return new PokemonResponse<HighlightResponse>(utils.toHihglightResponse(orderedPokemons.getResult(), query));
    }

    private PokemonResponse<String> orderPokemons(ApiResponse response, String query, SortType sort) {
        List<String> result = query != null ? 
            utils.filterPokemons(response.getResults(), query) : utils.toStringList(response.getResults());
        return new PokemonResponse<String>(utils.sortPokemons(result, sort));
    }
}
