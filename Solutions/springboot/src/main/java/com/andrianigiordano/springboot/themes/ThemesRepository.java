package com.andrianigiordano.springboot.themes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository per gestire l'accesso ai dati relativi ai temi dei film nel database.
 */
@Repository // L'annotazione @Repository indica che questa interfaccia è un componente di accesso ai dati.
public interface ThemesRepository extends JpaRepository<Themes, Long> {

    /**
     * Metodo personalizzato per ottenere i temi di un film a partire dall'ID del film.
     * Utilizza una query JPQL per concatenare i temi in una stringa separata da virgole.
     *
     * @param movieId L'ID del film per cui recuperare i temi.
     * @return Una stringa con i temi del film separati da virgola.
     */
    @Query("SELECT STRING_AGG(t.theme, ', ') FROM Themes t WHERE t.movie.id = :movieId")
    String findByMovieId(Long movieId); // Restituisce una stringa con tutti i temi del film separati da virgola.
}
