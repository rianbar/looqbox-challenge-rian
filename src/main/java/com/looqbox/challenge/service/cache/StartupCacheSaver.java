package com.looqbox.challenge.service.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.looqbox.challenge.model.response.ApiResponse;
import com.looqbox.challenge.service.PokeApiService;

@Component
public class StartupCacheSaver implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(StartupCacheSaver.class);
    
    private final PokeApiService pokeApi;
    private final InMemoryCacheManager cache;

    public StartupCacheSaver(PokeApiService pokeApi, InMemoryCacheManager cache) {
        this.pokeApi = pokeApi;
        this.cache = cache;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("starting initial in memory cache save!");
        ApiResponse response = pokeApi.retrieveAllPokemons();
        cache.saveAll(response.getResults());
    }
    
}
