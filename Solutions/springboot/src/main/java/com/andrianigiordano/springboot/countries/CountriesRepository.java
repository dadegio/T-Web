package com.andrianigiordano.springboot.countries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository per la gestione dei paesi (Countries).
 * Chiamata in movies per recuperare tutte le info dei film
 */
@Repository // Indica che questa interfaccia è un componente Spring per l'accesso ai dati
public interface CountriesRepository extends JpaRepository<Countries, Long> {

    /**
     * Recupera tutti i paesi associati a un determinato film e li concatena in una stringa separata da virgole.
     *
     * @param movieId l'ID del film di cui si vogliono ottenere i paesi
     * @return una stringa contenente i nomi dei paesi separati da virgole
     */
    @Query("SELECT STRING_AGG(c.country, ', ') FROM Countries c WHERE c.movie.id = :movieId")
    String findByMovieId(Long movieId);
}
