package com.andrianigiordano.springboot.languages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository per la gestione delle lingue cinematografiche.
 * Chiamata in movies per recuperare tutte le info dei film
 */
@Repository
public interface LanguagesRepository extends JpaRepository<Languages, Long> {

    /**
     * Recupera tutte le lingue associate a un determinato film e le concatena in una stringa separata da virgole.
     *
     * @param movieId l'ID del film di cui si vogliono ottenere le lingue
     * @return una stringa contenente i nomi delle lingue separati da virgole
     */
    @Query("SELECT STRING_AGG(l.language, ', ') FROM Languages l WHERE l.movie.id = :movieId")
    String findByMovieId(Long movieId);
}
