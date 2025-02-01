package com.looqbox.challenge.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
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
                return bubbleSortList(pokemons, this::isGreater);
            case LENGHT:
                return bubbleSortList(pokemons, this::isGreaterByLenght);
            default:
                return pokemons;
        }
    }

    private List<PokemonName> bubbleSortList(List<PokemonName> pokemons, BiPredicate<String, String> compare) {
        int size = pokemons.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (compare.test(pokemons.get(j).getName(), pokemons.get(i).getName())) {
                    String aux = pokemons.get(i).getName();
                    pokemons.set(i, pokemons.get(j));
                    pokemons.set(j, new PokemonName(aux));
                }
            }   
        }
        return pokemons;
    }

    private boolean isGreater(String a, String b) {
        int min = Math.min(a.length(), b.length());
        for (int i = 0; i < min; i++) {
            if (a.charAt(i) < b.charAt(i)) return true;
            if (a.charAt(i) > b.charAt(i)) return false;
        }
        return a.length() > b.length();
    }

    private boolean isGreaterByLenght(String a, String b) {
        return a.length() < b.length();
    }
}
