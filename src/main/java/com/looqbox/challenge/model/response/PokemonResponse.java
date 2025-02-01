package com.looqbox.challenge.model.response;

import java.util.List;

public class PokemonResponse {
    private List<String> result;

    public PokemonResponse(List<String> result) {
        this.result = result;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
     
}
