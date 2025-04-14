package com.andrianigiordano.springboot.movies;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movies", description = "API per la gestione dei film e dei relativi dettagli")
public class MoviesController {

    private final MoviesService movieService;

    public MoviesController(MoviesService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Recupera tutti i film con poster")
    @GetMapping("/get-all")
    public ResponseEntity<List<MovieDTO>> getMoviesWithPosters() {
        return ResponseEntity.ok(movieService.getAllMoviesWithPosters());
    }

    @Operation(summary = "Dettagli film per ID")
    @GetMapping("/get-movie-by-id")
    public ResponseEntity<MovieDTO> getMovieDetails(
            @Parameter(description = "ID del film") @RequestParam Long movieId) {
        return movieService.getMovieById(movieId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @Operation(summary = "Cerca film per nome")
    @GetMapping("/search-movies")
    public List<Movies> searchMoviesByName(
            @Parameter(description = "Nome del film da cercare") @RequestParam String name) {
        return movieService.searchMoviesByName(name);
    }

    @Operation(summary = "Top 100 film premiati agli Oscar")
    @GetMapping("/top100")
    public List<MovieDTO> oscarsTop100() {
        return movieService.oscarsTop100();
    }

    @Operation(summary = "Film in base agli attori")
    @GetMapping("/actors-home")
    public List<MovieDTO> getActorsHome() {
        return movieService.getActors();
    }

    @Operation(summary = "Film per la home page")
    @GetMapping("/get-home-movies")
    public List<MovieDTO> getHomeMovies() {
        return movieService.getPosters();
    }

    @Operation(summary = "Temi del film per ID")
    @GetMapping("/get-themes-by-id")
    public ResponseEntity<String> getThemesByMovieId(
            @Parameter(description = "ID del film") @RequestParam Long movieId) {
        String themes = movieService.getThemesByMovieId(movieId);
        return themes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(themes);
    }

    @Operation(summary = "Cast e crew del film per ID")
    @GetMapping("/get-crew-by-id")
    public ResponseEntity<String> getCrewByMovieId(
            @Parameter(description = "ID del film") @RequestParam Long movieId) {
        String crew = movieService.getCrewByMovieId(movieId);
        return crew.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(crew);
    }

    @Operation(summary = "Generi del film per ID")
    @GetMapping("/get-genres-by-id")
    public ResponseEntity<String> getGenresByMovieId(
            @Parameter(description = "ID del film") @RequestParam Long movieId) {
        String genres = movieService.getGenresByMovieId(movieId);
        return genres.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(genres);
    }

    @Operation(summary = "Paesi di produzione del film per ID")
    @GetMapping("/get-countries-by-id")
    public ResponseEntity<String> getCountriesByMovieId(
            @Parameter(description = "ID del film") @RequestParam Long movieId) {
        String countries = movieService.getCountriesByMovieId(movieId);
        return countries.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(countries);
    }

    @Operation(summary = "Lingue del film per ID")
    @GetMapping("/get-languages-by-id")
    public ResponseEntity<String> getLanguagesByMovieId(
            @Parameter(description = "ID del film") @RequestParam Long movieId) {
        String languages = movieService.getLanguagesByMovieId(movieId);
        return languages.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(languages);
    }

    @Operation(summary = "Studi di produzione del film per ID")
    @GetMapping("/get-studios-by-id")
    public ResponseEntity<String> getStudiosByMovieId(
            @Parameter(description = "ID del film") @RequestParam Long movieId) {
        String studios = movieService.getStudiosByMovieId(movieId);
        return studios.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(studios);
    }
}
