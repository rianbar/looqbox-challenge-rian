package com.looqbox.challenge.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class PokemonFilter {

    public List<String> filterPokemons(List<String> pokemons, String query) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(".*" + Pattern.quote(query) + ".*", Pattern.CASE_INSENSITIVE);
        pokemons.forEach(pokemon -> {
            if (pattern.matcher(pokemon).matches()) result.add(pokemon);
        });
        return result;
    }
}
