package com.andrianigiordano.springboot.search;

import com.andrianigiordano.springboot.actors.ActorsService;
import com.andrianigiordano.springboot.actors.Actors;
import com.andrianigiordano.springboot.movies.MoviesService;
import com.andrianigiordano.springboot.movies.Movies;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller per la gestione delle ricerche di attori e film.
 */
@RestController
@Tag(name = "Search", description = "Ricerca attori e film tramite stringa")
public class SearchController {

    private final ActorsService actorsService;
    private final MoviesService moviesService;

    public SearchController(ActorsService actorsService, MoviesService moviesService) {
        this.actorsService = actorsService;
        this.moviesService = moviesService;
    }

    /**
     * Ricerca attori per nome.
     *
     * @param query la stringa di ricerca
     * @return risultati della ricerca e query
     */
    @Operation(summary = "Cerca attori", description = "Cerca attori tramite una stringa di testo")
    @GetMapping("/search-actors")
    public Map<String, Object> searchActors(
            @Parameter(description = "Testo da cercare nel nome degli attori") @RequestParam("query") String query) {

        List<Actors> actorsResults = actorsService.searchActorsByName(query);

        Map<String, Object> response = new HashMap<>();
        response.put("actors", actorsResults);
        response.put("query", query);

        return response;
    }

    /**
     * Ricerca film per nome.
     *
     * @param query la stringa di ricerca
     * @return risultati della ricerca e query
     */
    @Operation(summary = "Cerca film", description = "Cerca film tramite una stringa di testo")
    @GetMapping("/search-movies")
    public Map<String, Object> searchMovies(
            @Parameter(description = "Testo da cercare nel nome dei film") @RequestParam("query") String query) {

        List<Movies> moviesResult = moviesService.searchMoviesByName(query);

        Map<String, Object> response = new HashMap<>();
        response.put("movies", moviesResult);
        response.put("query", query);

        return response;
    }
}
