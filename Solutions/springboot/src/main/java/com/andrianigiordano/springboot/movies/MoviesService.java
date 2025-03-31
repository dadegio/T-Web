package com.andrianigiordano.springboot.movies;

import com.andrianigiordano.springboot.countries.CountriesRepository;
import com.andrianigiordano.springboot.crew.CrewRepository;
import com.andrianigiordano.springboot.genres.GenresRepository;
import com.andrianigiordano.springboot.languages.LanguagesRepository;
import com.andrianigiordano.springboot.studios.StudiosRepository;
import com.andrianigiordano.springboot.themes.ThemesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Servizio per la gestione dei film.
 * Questo servizio fornisce metodi per ottenere informazioni sui film, come dettagli, poster, attori e premi.
 */
@Service
public class MoviesService {

    // Repository per l'accesso ai dati dei film e delle loro proprietà correlate
    @Autowired
    private MoviesRepository movieRepository;
    @Autowired
    private ThemesRepository themesRepository;
    @Autowired
    private CrewRepository crewRepository;
    @Autowired
    private GenresRepository genresRepository;
    @Autowired
    private CountriesRepository countriesRepository;
    @Autowired
    private LanguagesRepository languagesRepository;
    @Autowired
    private StudiosRepository studiosRepository;

    /**
     * Recupera tutti i film con i rispettivi poster.
     *
     * @return una lista di oggetti MovieDTO contenenti informazioni sui film con i poster
     */
    public List<MovieDTO> getAllMoviesWithPosters() {
        return movieRepository.findAllWithPosters()
                .stream()
                .map(movie -> new MovieDTO(
                        movie.getId(),
                        movie.getName(),
                        movie.getDate(),
                        movie.getTagline(),
                        movie.getDescription(),
                        movie.getPosterUrl(),
                        movie.getRating(),
                        movie.getMinute()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Recupera i 100 migliori film premiati agli Oscar nel 2024.
     *
     * @return una lista di oggetti MovieDTO con i dettagli dei film vincitori degli Oscar
     */
    public List<MovieDTO> oscarsTop100() {
        return movieRepository.getOscarsIn2024()
                .stream()
                .map(movie -> new MovieDTO(
                        movie.getId(),
                        movie.getName(),
                        movie.getDate(),
                        movie.getTagline(),
                        movie.getDescription(),
                        movie.getPosterUrl(),
                        movie.getRating(),
                        movie.getMinute()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Recupera una lista di film basati sugli attori.
     *
     * @return una lista di oggetti MovieDTO contenenti film con attori specifici
     */
    public List<MovieDTO> getActors() {
        return movieRepository.getActors()
                .stream()
                .map(movie -> new MovieDTO(
                        movie.getId(),
                        movie.getName(),
                        movie.getDate(),
                        movie.getTagline(),
                        movie.getDescription(),
                        movie.getPosterUrl(),
                        movie.getRating(),
                        movie.getMinute()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Recupera i dettagli di un film dato il suo ID.
     *
     * @param id l'ID del film da cercare
     * @return un Optional contenente il MovieDTO del film se trovato, altrimenti vuoto
     */
    public Optional<MovieDTO> getMovieById(Long id) {
        return movieRepository.findMovieById(id)
                .map(movie -> new MovieDTO(
                        movie.getId(),
                        movie.getName(),
                        movie.getDate(),
                        movie.getTagline(),
                        movie.getDescription(),
                        movie.getPosterUrl(),
                        movie.getRating(),
                        movie.getMinute()
                ));
    }

    /**
     * Recupera i film principali con i loro poster.
     *
     * @return una lista di oggetti MovieDTO con i poster dei film
     */
    public List<MovieDTO> getPosters() {
        return movieRepository.getAllPosters()
                .stream()
                .map(movie -> new MovieDTO(
                        movie.getId(),
                        movie.getName(),
                        movie.getDate(),
                        movie.getTagline(),
                        movie.getDescription(),
                        movie.getPosterUrl(),
                        movie.getRating(),
                        movie.getMinute()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Cerca film per nome con un limite massimo di 100 risultati.
     *
     * @param name il nome del film da cercare
     * @return una lista di oggetti Movies contenenti i film trovati
     */
    public List<Movies> searchMoviesByName(String name) {
        PageRequest pageable = PageRequest.of(0, 100); // Offset 0, massimo 100 risultati
        return movieRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    /**
     * Recupera i temi associati a un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return una stringa contenente i temi del film, se presenti
     */
    public String getThemesByMovieId(Long movieId) {
        return themesRepository.findByMovieId(movieId);
    }

    /**
     * Recupera il cast e la troupe di un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return una stringa contenente il cast e la troupe del film, se presenti
     */
    public String getCrewByMovieId(Long movieId) {
        return crewRepository.findByMovieId(movieId);
    }

    /**
     * Recupera i generi di un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return una stringa contenente i generi del film, se presenti
     */
    public String getGenresByMovieId(Long movieId) {
        return genresRepository.findByMovieId(movieId);
    }

    /**
     * Recupera i paesi di produzione di un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return una stringa contenente i paesi del film, se presenti
     */
    public String getCountriesByMovieId(Long movieId) {
        return countriesRepository.findByMovieId(movieId);
    }

    /**
     * Recupera le lingue in cui è disponibile un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return una stringa contenente le lingue del film, se presenti
     */
    public String getLanguagesByMovieId(Long movieId) {
        return languagesRepository.findByMovieId(movieId);
    }

    /**
     * Recupera gli studi di produzione di un film dato il suo ID.
     *
     * @param movieId l'ID del film
     * @return una stringa contenente gli studi del film, se presenti
     */
    public String getStudiosByMovieId(Long movieId) {
        return studiosRepository.findByMovieId(movieId);
    }
}
