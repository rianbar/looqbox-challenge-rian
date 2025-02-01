package com.looqbox.challenge.model.response;

import java.util.List;

public class ApiResponse {
    private List<PokemonName> results;

    public List<PokemonName> getResults() {
        return results;
    }

    public void setResult(List<PokemonName> results) {
        this.results = results;
    }
}
