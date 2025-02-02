package com.looqbox.challenge.utils;

import java.util.List;
import java.util.function.BiPredicate;

import org.springframework.stereotype.Component;

import com.looqbox.challenge.constant.SortType;

@Component
public class PokemonSorter {

    public List<String> sortPokemons(List<String> pokemons, SortType sort) {
        switch (sort) {
            case ALPHABETICAL:
                return bubbleSortList(pokemons, this::alphabeticalSort);
            case LENGTH:
                return bubbleSortList(pokemons, this::lenghtSort);
            default:
                return pokemons;
        }
    }
    
    private List<String> bubbleSortList(List<String> pokemons, BiPredicate<String, String> compare) {
        int size = pokemons.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (compare.test(pokemons.get(j), pokemons.get(i))) {
                    String aux = pokemons.get(i);
                    pokemons.set(i, pokemons.get(j));
                    pokemons.set(j, aux);
                }
            }   
        }
        return pokemons;
    }

    private boolean alphabeticalSort(String a, String b) {
        int min = Math.min(a.length(), b.length());
        for (int i = 0; i < min; i++) {
            if (a.charAt(i) < b.charAt(i)) return true;
            if (a.charAt(i) > b.charAt(i)) return false;
        }

        return a.length() > b.length();
    }

    private boolean lenghtSort(String a, String b) {
        return a.length() < b.length();
    }   
}
