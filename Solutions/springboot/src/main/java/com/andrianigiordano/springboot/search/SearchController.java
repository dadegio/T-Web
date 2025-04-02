package com.andrianigiordano.springboot.search;

import com.andrianigiordano.springboot.actors.ActorsService;
import com.andrianigiordano.springboot.actors.Actors;
import com.andrianigiordano.springboot.movies.MoviesService;
import com.andrianigiordano.springboot.movies.Movies;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller per la gestione delle ricerche di attori e film.
 */
@RestController
public class SearchController {

    private final ActorsService actorsService;  // Servizio per la gestione degli attori
    private final MoviesService moviesService;  // Servizio per la gestione dei film

    /**
     * Costruttore del controller che inietta i servizi necessari.
     *
     * @param actorsService il servizio che gestisce la logica degli attori
     * @param moviesService il servizio che gestisce la logica dei film
     */
    public SearchController(ActorsService actorsService, MoviesService moviesService) {
        this.actorsService = actorsService;
        this.moviesService = moviesService;
    }

    /**
     * Endpoint per cercare attori in base a un nome.
     * Utilizza il servizio ActorsService per eseguire la ricerca.
     * La risposta è un oggetto JSON che contiene i risultati e la query.
     *
     * @param query la stringa di ricerca per gli attori
     * @return una mappa contenente i risultati della ricerca degli attori e la query
     */
    @GetMapping("/search-actors") // Mappatura per l'endpoint di ricerca degli attori
    public Map<String, Object> searchActors(@RequestParam("query") String query) {
        List<Actors> actorsResults = actorsService.searchActorsByName(query);

        Map<String, Object> response = new HashMap<>();
        response.put("actors", actorsResults);  // Aggiunge la lista degli attori trovati
        response.put("query", query);  // Aggiunge la query di ricerca

        return response; // Restituisce la risposta in formato JSON
    }

    /**
     * Endpoint per cercare film in base a un nome.
     * La risposta è un oggetto JSON che contiene i risultati e la query.
     *
     * @param query la stringa di ricerca per i film
     * @return una mappa contenente i risultati della ricerca dei film e la query
     */
    @GetMapping("/search-movies") // Mappatura per l'endpoint di ricerca dei film
    public Map<String, Object> searchMovies(@RequestParam("query") String query) {
        List<Movies> moviesResult = moviesService.searchMoviesByName(query);

        Map<String, Object> response = new HashMap<>();
        response.put("movies", moviesResult);  // Aggiunge la lista dei film trovati
        response.put("query", query);  // Aggiunge la query di ricerca

        return response; // Restituisce la risposta in formato JSON
    }
}
