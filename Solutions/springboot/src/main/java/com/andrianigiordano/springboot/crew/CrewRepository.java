package com.andrianigiordano.springboot.crew;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per la gestione della crew (cast tecnico di un film).
 * Chiamata in movies per recuperare tutte le info dei film
 */
@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {
    /**
     * Recupera i nomi e i ruoli dei membri della crew di un film,
     * concatenandoli in una stringa con formato "Nome (Ruolo), Nome (Ruolo), ...".
     *
     * @param movieId l'ID del film di cui si vogliono ottenere i membri della crew
     * @return una stringa contenente i nomi e i ruoli dei membri della crew separati da virgole
     */
    @Query("SELECT STRING_AGG(CONCAT(c.name, ' (', c.role, ')'), ', ') FROM Crew c WHERE c.movie.id = :movieId")
    String findByMovieId(Long movieId);
}
