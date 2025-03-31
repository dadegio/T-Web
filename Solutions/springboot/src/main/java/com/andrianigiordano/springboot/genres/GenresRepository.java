package com.andrianigiordano.springboot.genres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository per la gestione dei generi cinematografici.
 * Chiamata in movies per recuperare tutte le info dei film
 */
@Repository
public interface GenresRepository extends JpaRepository<Genres, Long> {

    /**
     * Recupera tutti i generi associati a un determinato film e li concatena in una stringa separata da virgole.
     *
     * @param movieId l'ID del film di cui si vogliono ottenere i generi
     * @return una stringa contenente i nomi dei generi separati da virgole
     */
    @Query("SELECT STRING_AGG(g.genre, ', ') FROM Genres g WHERE g.movie.id = :movieId")
    String findByMovieId(Long movieId);
}
