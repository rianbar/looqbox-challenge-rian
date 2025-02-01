package com.looqbox.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.looqbox.challenge.constant.SortType;
import com.looqbox.challenge.model.response.ApiResponse;
import com.looqbox.challenge.model.response.PokemonName;
import com.looqbox.challenge.model.response.PokemonResponse;
import com.looqbox.challenge.utils.SearchUtils;

@Service
public class PokemonService {
    
    PokeApiService pokeApi;
    SearchUtils utils;

    public PokemonService(PokeApiService pokeApiService, SearchUtils utils) {
        this.pokeApi = pokeApiService;
        this.utils = utils;
    }

    public PokemonResponse getPokemonService(String query, SortType sort) {
        ApiResponse response = pokeApi.retrieveAllPokemons();
        return getPokemonResponse(response, query, sort);
    }

    public String getPokemonHighlightsService(String param) {
        return param;
    }

    private PokemonResponse getPokemonResponse(ApiResponse response, String query, SortType sort) {
        List<PokemonName> result = query != null ? utils.filterPokemons(response.getResults(), query) : response.getResults();
        return new PokemonResponse(utils.sortPokemons(result, sort));     //RESULTS MAY BE A LIST OF POKEMON NAMES, NOT A OBJECT
    }
}
