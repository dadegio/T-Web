package com.andrianigiordano.springboot.studios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'accesso ai dati relativi agli studi cinematografici.
 */
@Repository // Indica che questa interfaccia è un repository Spring per l'accesso ai dati.
public interface StudiosRepository extends JpaRepository<Studios, Long> {

    /**
     * Query per ottenere una lista di studi cinematografici associati a un film specifico.
     * La query restituisce un'unica stringa contenente gli studi separati da una virgola.
     *
     * @param movieId ID del film per cui si vogliono ottenere gli studi cinematografici.
     * @return Una stringa contenente gli studi associati al film, separati da virgola.
     */
    @Query("SELECT STRING_AGG(s.studio, ', ') FROM Studios s WHERE s.movie.id = :movieId")
    String findByMovieId(Long movieId); // Restituisce gli studi di un film, separati da virgola.
}
