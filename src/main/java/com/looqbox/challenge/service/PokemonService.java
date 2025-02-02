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
        return sortPokemons(this.fetchPokemons().get(), query, sort);
    }

    public PokemonResponse<HighlightResponse> getPokemonHighlightsService(String query, SortType sort) {
        PokemonResponse<String> sortedPokemons = sortPokemons(this.fetchPokemons().get(), query, sort);
        return new PokemonResponse<>(formatter.toHighlightResponse(sortedPokemons.getResult(), query));
    }

    private Optional<ApiResponse> fetchPokemons() {
        return Optional.ofNullable(pokeApi.retrieveAllPokemons());
    }

    private PokemonResponse<String> sortPokemons(ApiResponse response, String query, SortType sort) {
        List<String> result = Optional.ofNullable(query)
            .map(q -> filter.filterPokemons(response.getResults(), q))
            .orElse(formatter.toStringList(response.getResults()));
            
        return new PokemonResponse<>(sorter.sortPokemons(result, sort));
    }
}
