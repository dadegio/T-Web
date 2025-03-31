package com.andrianigiordano.springboot.movies;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per la gestione dei film.
 * Fornisce API REST per ottenere informazioni sui film, tra cui dettagli, attori, generi e altro.
 */
@RestController
@RequestMapping("/movies")  // Definisce il percorso base per tutte le richieste al controller
public class MoviesController {

    private final MoviesService movieService;  // Servizio per la gestione della logica dei film

    /**
     * Costruttore che inizializza il servizio per la gestione dei film.
     *
     * @param movieService il servizio per la gestione dei film
     */
    public MoviesController(MoviesService movieService) {
        this.movieService = movieService;
    }

    /**
     * Recupera tutti i film con i rispettivi poster.
     *
     * @return una lista di film con poster, racchiusa in una ResponseEntity
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<MovieDTO>> getMoviesWithPosters() {
        return ResponseEntity.ok(movieService.getAllMoviesWithPosters());
    }

    /**
     * Recupera i dettagli di un film dato il suo ID.
     *
     * @param movieId l'ID del film da cercare
     * @return il film corrispondente se trovato, altrimenti un errore 404
     */
    @GetMapping("/get-movie-by-id")
    public ResponseEntity<MovieDTO> getMovieDetails(@RequestParam Long movieId) {
        return movieService.getMovieById(movieId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * Cerca film per nome.
     *
     * @param name il nome del film da cercare
     * @return una lista di film che corrispondono al nome specificato
     */
    @GetMapping("/search-movies")
    public List<Movies> searchMoviesByName(@RequestParam String name) {
        return movieService.searchMoviesByName(name);
    }

    /**
     * Recupera la lista dei 100 migliori film premiati agli Oscar.
     *
     * @return una lista di 100 film
     */
    @GetMapping("/top100")
    public List<MovieDTO> oscarsTop100() {
        return movieService.oscarsTop100();
    }

    /**
     * Recupera una lista di film in base agli attori.
     *
     * @return una lista di film basati sugli attori presenti
     */
    @GetMapping("/actors-home")
    public List<MovieDTO> getActorsHome() {
        return movieService.getActors();
    }

    /**
     * Recupera i film principali per la home page.
     *
     * @return una lista di film con i relativi poster
     */
    @GetMapping("/get-home-movies")
    public List<MovieDTO> getHomeMovies() {
        return movieService.getPosters();
    }

    /**
     * Recupera i temi associati a un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return i temi del film se presenti, altrimenti un errore 404
     */
    @GetMapping("/get-themes-by-id")
    public ResponseEntity<String> getThemesByMovieId(@RequestParam Long movieId) {
        String themes = movieService.getThemesByMovieId(movieId);
        if (themes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(themes);
    }

    /**
     * Recupera il cast e la troupe di un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return il cast e la troupe del film se presenti, altrimenti un errore 404
     */
    @GetMapping("/get-crew-by-id")
    public ResponseEntity<String> getCrewByMovieId(@RequestParam Long movieId) {
        String crew = movieService.getCrewByMovieId(movieId);
        if (crew.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(crew);
    }

    /**
     * Recupera i generi di un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return i generi del film se presenti, altrimenti un errore 404
     */
    @GetMapping("/get-genres-by-id")
    public ResponseEntity<String> getGenresByMovieId(@RequestParam Long movieId) {
        String genres = movieService.getGenresByMovieId(movieId);
        if (genres.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(genres);
    }

    /**
     * Recupera i paesi di produzione di un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return i paesi del film se presenti, altrimenti un errore 404
     */
    @GetMapping("/get-countries-by-id")
    public ResponseEntity<String> getCountriesByMovieId(@RequestParam Long movieId) {
        String countries = movieService.getCountriesByMovieId(movieId);
        if (countries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(countries);
    }

    /**
     * Recupera le lingue in cui è disponibile un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return le lingue del film se presenti, altrimenti un errore 404
     */
    @GetMapping("/get-languages-by-id")
    public ResponseEntity<String> getLanguagesByMovieId(@RequestParam Long movieId) {
        String languages = movieService.getLanguagesByMovieId(movieId);
        if (languages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(languages);
    }

    /**
     * Recupera gli studi di produzione di un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return gli studi del film se presenti, altrimenti un errore 404
     */
    @GetMapping("/get-studios-by-id")
    public ResponseEntity<String> getStudiosByMovieId(@RequestParam Long movieId) {
        String studios = movieService.getStudiosByMovieId(movieId);
        if (studios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studios);
    }
}
