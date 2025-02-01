package com.looqbox.challenge.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.looqbox.challenge.constant.SortType;
import com.looqbox.challenge.model.response.PokemonName;

@Component
public class SearchUtils {

    public List<PokemonName> filterPokemons(List<PokemonName> pokemons, String query) {
        List<PokemonName> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(".*" + Pattern.quote(query) + ".*", Pattern.CASE_INSENSITIVE);
        for (PokemonName pokemon: pokemons) {
            if (pattern.matcher(pokemon.getName()).matches()) result.add(pokemon);
        }
        return result;
    }

    public List<PokemonName> sortPokemons(List<PokemonName> pokemons, SortType sort) { //try to clean this method
        switch (sort) {
            case ALPHABETICAL:
                return sortPokemonsBy(pokemons, Comparator.naturalOrder());
            case LENGHT:
                return sortPokemonsBy(pokemons, Comparator.comparing(String::length));
            default:
                return pokemons;
        }
    }

    private List<PokemonName> sortPokemonsBy(List<PokemonName> pokemons, Comparator<String> comparator) {
        int size = pokemons.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (comparator.compare(pokemons.get(j).getName(), pokemons.get(i).getName()) < 0) { // i cant use comparator, REMOVE FROM HERE
                    String aux = pokemons.get(i).getName();
                    pokemons.set(i, pokemons.get(j));
                    pokemons.set(j, new PokemonName(aux));
                }
            }   
        }
        return pokemons;
    }
}
