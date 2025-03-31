package com.andrianigiordano.springboot.search;

import com.andrianigiordano.springboot.actors.ActorsService;
import com.andrianigiordano.springboot.actors.Actors;
import com.andrianigiordano.springboot.movies.MoviesService;
import com.andrianigiordano.springboot.movies.Movies;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller per la gestione delle ricerche di attori e film.
 * Espone due endpoint per effettuare ricerche in base al nome.
 */
@RestController // Indica che questo controller gestisce risposte in formato JSON.
public class SearchController {

    private final ActorsService actorsService; // Servizio per la ricerca degli attori
    private final MoviesService moviesService; // Servizio per la ricerca dei film

    /**
     * Costruttore per l'inizializzazione dei servizi.
     *
     * @param actorsService Servizio per la ricerca degli attori.
     * @param moviesService Servizio per la ricerca dei film.
     */
    public SearchController(ActorsService actorsService, MoviesService moviesService) {
        this.actorsService = actorsService;
        this.moviesService = moviesService;
    }

    /**
     * Endpoint per la ricerca degli attori in base al nome.
     *
     * @param query Nome o parte del nome dell'attore da cercare.
     * @return Una mappa contenente i risultati della ricerca e la query effettuata.
     */
    @GetMapping("/search-actors")
    public Map<String, Object> searchActors(@RequestParam("query") String query) {
        List<Actors> actorsResults = actorsService.searchActorsByName(query); // Cerca attori con il nome specificato

        Map<String, Object> response = new HashMap<>();
        response.put("actors", actorsResults); // Aggiunge i risultati alla risposta JSON
        response.put("query", query); // Aggiunge la query originale alla risposta

        return response; // Restituisce la risposta in formato JSON
    }

    /**
     * Endpoint per la ricerca dei film in base al nome.
     *
     * @param query Nome o parte del nome del film da cercare.
     * @return Una mappa contenente i risultati della ricerca e la query effettuata.
     */
    @GetMapping("/search-movies")
    public Map<String, Object> searchMovies(@RequestParam("query") String query) {
        List<Movies> moviesResult = moviesService.searchMoviesByName(query); // Cerca film con il nome specificato

        Map<String, Object> response = new HashMap<>();
        response.put("movies", moviesResult); // Aggiunge i risultati alla risposta JSON
        response.put("query", query); // Aggiunge la query originale alla risposta

        return response; // Restituisce la risposta in formato JSON
    }
}
