package com.looqbox.challenge.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.looqbox.challenge.model.response.PokemonName;

@Component
public class PokemonFilter {

    public List<String> filterPokemons(List<PokemonName> pokemons, String query) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(".*" + Pattern.quote(query) + ".*", Pattern.CASE_INSENSITIVE);
        pokemons.forEach(pokemon -> {
            if (pattern.matcher(pokemon.getName()).matches()) result.add(pokemon.getName());
        });

        return result;
    }
}
