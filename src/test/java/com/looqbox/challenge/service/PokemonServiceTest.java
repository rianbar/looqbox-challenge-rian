package com.looqbox.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.looqbox.challenge.constant.SortType;
import com.looqbox.challenge.model.response.ApiResponse;
import com.looqbox.challenge.model.response.HighlightResponse;
import com.looqbox.challenge.model.response.PokemonName;
import com.looqbox.challenge.model.response.PokemonResponse;
import com.looqbox.challenge.utils.PokemonFilter;
import com.looqbox.challenge.utils.PokemonFormatter;
import com.looqbox.challenge.utils.PokemonSorter;

@SpringBootTest
class PokemonServiceTest {

    @InjectMocks
    private PokemonService pokemonService;

    @Mock
    private PokeApiService pokeApi;

    @Mock
    private PokemonSorter sorter;

    @Mock
    private PokemonFilter filter;

    @Mock
    private PokemonFormatter formatter;

    ApiResponse apiResponse;

    List<PokemonName> results;

    @BeforeEach
    void setUp() {
        apiResponse = new ApiResponse();
        results = List.of(new PokemonName("bulbasaur"), new PokemonName("ivysaur"));
        apiResponse.setResult(results);
    }

    @Test
    void testGetPokemonServiceWithQuery() {

        when(pokeApi.retrieveAllPokemons()).thenReturn(apiResponse);
        when(filter.filterPokemons(results, "bul")).thenReturn(List.of("bulbasaur"));
        when(sorter.sortPokemons(List.of("bulbasaur"), SortType.ALPHABETICAL)).thenReturn(List.of("bulbasaur"));

        PokemonResponse<String> response = pokemonService.getPokemonService("bul", SortType.ALPHABETICAL);

        assertTrue(response.getResult().size() > 0); 
    }

    @Test
    void testGetPokemonServiceWithoutQuery() {

        when(pokeApi.retrieveAllPokemons()).thenReturn(apiResponse);
        when(formatter.toStringList(results)).thenReturn(List.of("bulbasaur", "ivysaur"));
        when(sorter.sortPokemons(List.of("bulbasaur", "ivysaur"), SortType.ALPHABETICAL))
            .thenReturn(List.of("bulbasaur", "ivysaur"));

        PokemonResponse<String> response = pokemonService.getPokemonService(null, SortType.ALPHABETICAL);

        assertNotNull(response);
        assertEquals(List.of("bulbasaur", "ivysaur"), response.getResult());
    }

    @Test
    void testGetPokemonHighLightServiceWithQuery() {

        List<HighlightResponse> highlight = List.of(new HighlightResponse("bulbasaur", "<pre>bul</pre>basaur"));

        when(pokeApi.retrieveAllPokemons()).thenReturn(apiResponse);
        when(filter.filterPokemons(results, "bul")).thenReturn(List.of("bulbasaur"));
        when(sorter.sortPokemons(List.of("bulbasaur"), SortType.ALPHABETICAL))
            .thenReturn(List.of("bulbasaur"));
        when(formatter.toHighlightResponse(List.of("bulbasaur"), "bul"))
            .thenReturn(highlight);

        PokemonResponse<HighlightResponse> response = pokemonService.getPokemonHighlightsService("bul", SortType.ALPHABETICAL);

        assertTrue(response.getResult().size() > 0); 
    }

    @Test
    void testGetPokemonHighLightServiceWithoutQuery() {

        List<HighlightResponse> highlight = List.of(new HighlightResponse("bulbasaur", "bulbasaur"));

        when(pokeApi.retrieveAllPokemons()).thenReturn(apiResponse);
        when(formatter.toStringList(results)).thenReturn(List.of("bulbasaur"));
        when(sorter.sortPokemons(List.of("bulbasaur"), SortType.ALPHABETICAL))
            .thenReturn(List.of("bulbasaur"));
        when(formatter.toHighlightResponse(List.of("bulbasaur"), null))
            .thenReturn(highlight);

        PokemonResponse<HighlightResponse> response = pokemonService.getPokemonHighlightsService(null, SortType.ALPHABETICAL);

        assertTrue(response.getResult().size() > 0); 
    }
}
