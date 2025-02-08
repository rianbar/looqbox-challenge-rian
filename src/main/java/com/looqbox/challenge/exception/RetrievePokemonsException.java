package com.looqbox.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RetrievePokemonsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "The service could not retrieve pokemons list";

    public RetrievePokemonsException() {
        super(DEFAULT_MESSAGE);
    }
}
