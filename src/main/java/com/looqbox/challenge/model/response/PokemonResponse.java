package com.looqbox.challenge.model.response;

import java.util.List;

public class PokemonResponse {
    private List<PokemonName> result;

    public PokemonResponse(List<PokemonName> result) {
        this.result = result;
    }

    public List<PokemonName> getResult() {
        return result;
    }

    public void setResult(List<PokemonName> result) {
        this.result = result;
    }
     
}
