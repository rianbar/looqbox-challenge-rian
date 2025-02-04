package com.looqbox.challenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.looqbox.challenge.constant.SortType;
import com.looqbox.challenge.model.response.HighlightResponse;
import com.looqbox.challenge.model.response.PokemonName;
import com.looqbox.challenge.model.response.PokemonResponse;
import com.looqbox.challenge.service.cache.InMemoryCacheManager;
import com.looqbox.challenge.utils.PokemonFilter;
import com.looqbox.challenge.utils.PokemonFormatter;
import com.looqbox.challenge.utils.PokemonSorter;

@Service
public class PokemonService {
    
    private final InMemoryCacheManager cache;
    private final PokemonSorter sorter;
    private final PokemonFilter filter;
    private final PokemonFormatter formatter;

    public PokemonService(InMemoryCacheManager cache, PokemonSorter sorter, PokemonFilter filter, PokemonFormatter formatter) {
        this.cache = cache;
        this.sorter = sorter;
        this.filter = filter;
        this.formatter = formatter; 
    }

    public PokemonResponse<String> getPokemonService(String query, SortType sort) {
        return sortPokemons(cache.getAll(), query, sort);
    }

    public PokemonResponse<HighlightResponse> getPokemonHighlightsService(String query, SortType sort) {
        PokemonResponse<String> sortedPokemons = sortPokemons(cache.getAll(), query, sort);
        return new PokemonResponse<>(formatter.toHighlightResponse(sortedPokemons.getResult(), query));
    }

    private PokemonResponse<String> sortPokemons(List<PokemonName> response, String query, SortType sort) {
        List<String> result = Optional.ofNullable(query)
            .map(q -> filter.filterPokemons(response, q))
            .orElse(formatter.toStringList(response));
            
        return new PokemonResponse<>(sorter.sortPokemons(result, sort));
    }
}
