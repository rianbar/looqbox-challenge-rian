package com.looqbox.challenge.utils;

import java.util.List;
import java.util.function.BiPredicate;

import org.springframework.stereotype.Component;

import com.looqbox.challenge.constant.SortType;

@Component
public class PokemonSorter {

    // based on the sort type, this method calls the bubbleSortList method to sort the pokemons
    // with the correct comparator
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
    
    // this method uses the bubble sort algorithm to sort the pokemons, it receives a Bipredicate to compare the pokemons-
    // based on the sort type
    // BIG 0 notation: O(n^2) (medium case), the method receives a unordered list of pokemons and sorts them
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

    // here is the implementation of the alphabetical comparation, it compares the pokemons lexicographically-
    // if the first pokemon is lexicographically smaller than the second, it returns true, otherwise it returns false-
    // if the first pokemon is lexicographically equal to the second, this means that both strings are equal, so sort by length
    private boolean alphabeticalSort(String a, String b) {
        int min = Math.min(a.length(), b.length());
        for (int i = 0; i < min; i++) {
            if (a.charAt(i) < b.charAt(i)) return true;
            if (a.charAt(i) > b.charAt(i)) return false;
        }
        return a.length() > b.length();
    }

    // here is a simple implementation of the lenght comparation, it compares the pokemons based on their lenghts
    private boolean lenghtSort(String a, String b) {
        return a.length() < b.length();
    }   
}
