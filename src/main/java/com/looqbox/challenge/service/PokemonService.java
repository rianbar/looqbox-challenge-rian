package com.looqbox.challenge.service;

import java.util.List;
import java.util.Optional;

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
        return this.fetchPokemons()
            .map(response -> sortPokemons(response, query, sort))
            .get();
    }

    public PokemonResponse<HighlightResponse> getPokemonHighlightsService(String query, SortType sort) {
        return this.fetchPokemons()
            .map(response -> sortPokemons(response, query, sort))
            .map(sorted -> formatter.toHighlightResponse(sorted.getResult(), query))
            .map(PokemonResponse::new)
            .get();

    }

    private Optional<ApiResponse> fetchPokemons() {
        return Optional.ofNullable(pokeApi.retrieveAllPokemons());
    }

    private PokemonResponse<String> sortPokemons(ApiResponse response, String query, SortType sort) {
        return Optional.ofNullable(query)
            .map(q -> filter.filterPokemons(response.getResults(), q))
            .map(result -> sorter.sortPokemons(result, sort))
            .map(PokemonResponse::new)
            .orElseGet(() -> {
                List<String> format = formatter.toStringList(response.getResults());
                return new PokemonResponse<>(sorter.sortPokemons(format, sort));
            });
    }
}
