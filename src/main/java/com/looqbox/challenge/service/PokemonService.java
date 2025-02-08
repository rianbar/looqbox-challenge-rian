package com.looqbox.challenge.service;

import java.util.List;

import com.looqbox.challenge.exception.RetrievePokemonsException;
import org.springframework.stereotype.Service;

import com.looqbox.challenge.constant.SortType;
import com.looqbox.challenge.model.response.HighlightResponse;
import com.looqbox.challenge.model.response.PokemonResponse;
import com.looqbox.challenge.service.cache.InMemoryCacheManager;
import com.looqbox.challenge.utils.PokemonFilter;
import com.looqbox.challenge.utils.PokemonFormatter;
import com.looqbox.challenge.utils.PokemonSorter;

import static java.util.Optional.ofNullable;

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
        return ofNullable(cache.getAll())
            .map(formatter::toStringList)
            .map(names -> sortPokemons(names, query, sort))
            .map(PokemonResponse::new)
            .orElseThrow(RetrievePokemonsException::new);
    }

    public PokemonResponse<HighlightResponse> getPokemonHighlightsService(String query, SortType sort) {
        return ofNullable(cache.getAll())
            .map(formatter::toStringList)
            .map(names -> sortPokemons(names, query, sort))
            .map(list -> formatter.toHighlightResponse(list, query))
            .map(PokemonResponse::new)
            .orElseThrow(RetrievePokemonsException::new);
    }

    private List<String> sortPokemons(List<String> names, String query, SortType sort) {
        return ofNullable(query)
            .map(q -> filter.filterPokemons(names, q))
            .map(pokemons -> sorter.sortPokemons(pokemons, sort))
            .orElse(sorter.sortPokemons(names, sort));
    }
}
