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
import com.looqbox.challenge.service.cache.InMemoryCacheManager;
import com.looqbox.challenge.utils.PokemonFilter;
import com.looqbox.challenge.utils.PokemonFormatter;
import com.looqbox.challenge.utils.PokemonSorter;

@SpringBootTest
class PokemonServiceTest {

    @InjectMocks
    private PokemonService pokemonService;

    @Mock
    private InMemoryCacheManager cache;

    @Mock
    private PokemonSorter sorter;

    @Mock
    private PokemonFilter filter;

    @Mock
    private PokemonFormatter formatter;

    List<PokemonName> results;
    List<String> pokemons;

    @BeforeEach
    void setUp() {
        ApiResponse apiResponse = new ApiResponse();
        results = List.of(new PokemonName("bulbasaur"), new PokemonName("ivysaur"));
        pokemons = List.of("bulbasaur", "ivysaur");
        apiResponse.setResult(results);
    }

    @Test
    void testGetPokemonServiceWithQuery() {

        List<String> bulbasaurList = List.of("bulbasaur");

        when(cache.getAll()).thenReturn(results);
        when(formatter.toStringList(results)).thenReturn(pokemons);
        when(filter.filterPokemons(pokemons, "bul")).thenReturn(bulbasaurList);
        when(sorter.sortPokemons(bulbasaurList, SortType.ALPHABETICAL)).thenReturn(bulbasaurList);

        PokemonResponse<String> response = pokemonService.getPokemonService("bul", SortType.ALPHABETICAL);

        assertTrue(response.getResult().size() > 0); 
    }

    @Test
    void testGetPokemonServiceWithoutQuery() {

        when(cache.getAll()).thenReturn(results);
        when(formatter.toStringList(results)).thenReturn(pokemons);
        when(sorter.sortPokemons(pokemons, SortType.ALPHABETICAL)).thenReturn(pokemons);

        PokemonResponse<String> response = pokemonService.getPokemonService(null, SortType.ALPHABETICAL);

        assertNotNull(response);
        assertEquals(pokemons, response.getResult());
    }

    @Test
    void testGetPokemonHighLightServiceWithQuery() {

        List<HighlightResponse> highlight = List.of(new HighlightResponse("bulbasaur", "<pre>bul</pre>basaur"));
        List<String> bulbasaurList = List.of("bulbasaur");

        when(cache.getAll()).thenReturn(results);
        when(formatter.toStringList(results)).thenReturn(pokemons);
        when(filter.filterPokemons(pokemons, "bul")).thenReturn(bulbasaurList);
        when(sorter.sortPokemons(bulbasaurList, SortType.ALPHABETICAL)).thenReturn(bulbasaurList);
        when(formatter.toHighlightResponse(bulbasaurList, "bul")).thenReturn(highlight);

        PokemonResponse<HighlightResponse> response = pokemonService.getPokemonHighlightsService("bul", SortType.ALPHABETICAL);

        assertTrue(response.getResult().size() > 0); 
    }

    @Test
    void testGetPokemonHighLightServiceWithoutQuery() {

        List<HighlightResponse> highlight = List.of(new HighlightResponse("bulbasaur", "bulbasaur"));
        List<String> bulbasaurList = List.of("bulbasaur");

        when(cache.getAll()).thenReturn(results);
        when(formatter.toStringList(results)).thenReturn(bulbasaurList);
        when(sorter.sortPokemons(bulbasaurList, SortType.ALPHABETICAL)).thenReturn(bulbasaurList);
        when(formatter.toHighlightResponse(bulbasaurList, null)).thenReturn(highlight);

        PokemonResponse<HighlightResponse> response = pokemonService.getPokemonHighlightsService(null, SortType.ALPHABETICAL);

        assertTrue(response.getResult().size() > 0); 
    }
}
