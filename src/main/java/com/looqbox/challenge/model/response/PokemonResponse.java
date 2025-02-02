package com.looqbox.challenge.model.response;

import java.util.List;

public class PokemonResponse<T> {
    private List<T> result;

    public PokemonResponse(List<T> result) {
        this.result = result;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
