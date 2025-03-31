package com.andrianigiordano.springboot.movies;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository per la gestione delle operazioni di accesso ai dati dei film.
 */
@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {

    /**
     * Cerca i film il cui nome contiene una determinata stringa, ignorando maiuscole e minuscole.
     * La ricerca è limitata a un numero massimo di risultati specificato dal Pageable.
     *
     * @param name     Il nome (o parte del nome) del film da cercare.
     * @param pageable L'oggetto Pageable per limitare il numero di risultati.
     * @return Una lista di film corrispondenti alla ricerca.
     */
    @Query("SELECT DISTINCT m FROM Movies m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Movies> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    /**
     * Recupera tutti i film con i relativi poster, limitando il risultato a 100 elementi.
     * Viene utilizzata una LEFT JOIN FETCH per caricare i poster associati ai film.
     *
     * @return Una lista di film con i relativi poster.
     */
    @Query("SELECT m FROM Movies m LEFT JOIN FETCH m.poster ORDER BY m.id limit 100")
    List<Movies> findAllWithPosters();

    /**
     * Recupera un film specifico dato il suo ID, includendo il poster associato.
     *
     * @param id L'ID del film da cercare.
     * @return Un Optional contenente il film se trovato, altrimenti vuoto.
     */
    @Query("SELECT m FROM Movies m LEFT JOIN FETCH m.poster WHERE m.id = :id")
    Optional<Movies> findMovieById(@Param("id") Long id);

    /**
     * Recupera i film che hanno partecipato agli Oscar nel 2024.
     * Viene considerata solo la data di uscita a partire dal 2015.
     *
     * @return Una lista di film che hanno partecipato agli Oscar nel 2024.
     */
    @Query("SELECT DISTINCT m FROM Movies m LEFT JOIN FETCH m.poster WHERE m.date >= 2015")
    List<Movies> getOscarsIn2024();

    /**
     * Recupera i film che contengono attori specifici e che sono stati rilasciati dal 2020 in poi.
     *
     * @return Una lista di film con attori specifici rilasciati dal 2020.
     */
    @Query("SELECT DISTINCT m FROM Movies m LEFT JOIN FETCH m.poster p WHERE m.date >= 2020")
    List<Movies> getActors();

    /**
     * Recupera un elenco specifico di film iconici con i loro poster.
     * La query filtra solo alcuni film predefiniti con i rispettivi anni di uscita.
     *
     * @return Una lista di film specifici con i relativi poster.
     */
    @Query("SELECT DISTINCT m FROM Movies m WHERE (m.name = 'Interstellar' AND m.date = 2014) " +
            "OR (m.name = 'Akira' AND m.date = 1988) " +
            "OR (m.name = 'Perfect Days' AND m.date = 2023) " +
            "OR (m.name = 'Blade Runner 2049' AND m.date = 2017) " +
            "OR (m.name = 'Shrek' AND m.date = 2001)")
    List<Movies> getAllPosters();
}
