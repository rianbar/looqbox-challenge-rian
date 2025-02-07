package com.looqbox.challenge.service.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.looqbox.challenge.model.response.PokemonName;

@Service
public class InMemoryCacheManager {
    private final List<PokemonName> pokemonNames = new ArrayList<>();
    
    public void saveAll(List<PokemonName> names) {
        pokemonNames.addAll(names);
    }

    public List<PokemonName> getAll() {
        return new ArrayList<>(pokemonNames);
    }

    public boolean contains(PokemonName name) {
        return pokemonNames.contains(name);
    }

    public void clear() {
        pokemonNames.clear();
    }
}
