package com.looqbox.challenge.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.looqbox.challenge.model.response.HighlightResponse;
import com.looqbox.challenge.model.response.PokemonName;

@Component
public class PokemonFormatter {

    public List<HighlightResponse> toHighlightResponse(List<String> pokemons, String query) {
        List<HighlightResponse> result = new ArrayList<>();
        for (String pokemon: pokemons) {
            result.add(new HighlightResponse(pokemon, pokemon.replaceAll("(?i)" + query, "<pre>$0</pre>")));
        }

        return result;
    }

    public List<String> toStringList(List<PokemonName> pokemons) {
        List<String> result = new ArrayList<>();
        for (PokemonName pokemon: pokemons) {
            result.add(pokemon.getName());
        }

        return result;
    }  
}
