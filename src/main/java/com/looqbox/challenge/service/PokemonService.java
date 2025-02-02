package com.looqbox.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.looqbox.challenge.constant.SortType;
import com.looqbox.challenge.model.response.ApiResponse;
import com.looqbox.challenge.model.response.HighlightResponse;
import com.looqbox.challenge.model.response.PokemonResponse;
import com.looqbox.challenge.utils.PokemonFilter;
import com.looqbox.challenge.utils.PokemonFormatter;
import com.looqbox.challenge.utils.PokemonSorter;

@Service
public class PokemonService {
    
    private final PokeApiService pokeApi;
    private final PokemonSorter sorter;
    private final PokemonFilter filter;
    private final PokemonFormatter formatter;

    public PokemonService(PokeApiService pokeApiService, PokemonSorter sorter, PokemonFilter filter, PokemonFormatter formatter) {
        this.pokeApi = pokeApiService;
        this.sorter = sorter;
        this.filter = filter;
        this.formatter = formatter; 
    }

    public PokemonResponse<String> getPokemonService(String query, SortType sort) {
        ApiResponse response = pokeApi.retrieveAllPokemons();
        return orderPokemons(response, query, sort);
    }

    public PokemonResponse<HighlightResponse> getPokemonHighlightsService(String query, SortType sort) {
        ApiResponse response = pokeApi.retrieveAllPokemons();
        PokemonResponse<String> orderedPokemons = orderPokemons(response, query, sort);
        return new PokemonResponse<>(formatter.toHighlightResponse(orderedPokemons.getResult(), query));
    }

    private PokemonResponse<String> orderPokemons(ApiResponse response, String query, SortType sort) {
        List<String> result = (query != null) 
            ? filter.filterPokemons(response.getResults(), query) 
            : formatter.toStringList(response.getResults());
            
        return new PokemonResponse<>(sorter.sortPokemons(result, sort));
    }
}
