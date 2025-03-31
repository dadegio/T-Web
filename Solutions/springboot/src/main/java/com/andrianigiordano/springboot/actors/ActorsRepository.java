package com.andrianigiordano.springboot.actors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per la gestione degli attori (Actors).
 */
@Repository // Indica che questa interfaccia è un componente Spring per l'accesso ai dati
public interface ActorsRepository extends JpaRepository<Actors, Long> {

    /**
     * Cerca attori il cui nome contiene la stringa fornita, ignorando maiuscole e minuscole.
     *
     * @param name il nome (o parte di esso) dell'attore da cercare
     * @param pageable parametro per gestire la paginazione dei risultati
     * @return una lista di attori che corrispondono al criterio di ricerca
     */
    @Query("SELECT DISTINCT a FROM Actors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Actors> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    /**
     * Recupera un elenco predefinito di attori famosi da mostrare nella homepage.
     * L'elenco è attualmente statico e include Ryan Gosling, Zendaya e Tom Holland.
     *
     * @return una lista di attori predefiniti
     */
    @Query("SELECT DISTINCT a FROM Actors a WHERE (a.name = 'Ryan Gosling' OR a.name = 'Zendaya' OR a.name = 'Tom Holland')")
    List<Actors> findActorsForHome();

    /**
     * Recupera una lista di attori che hanno esattamente il nome specificato.
     *
     * @param name il nome esatto dell'attore da cercare
     * @return una lista di attori che corrispondono esattamente al nome fornito
     */
    @Query("SELECT a FROM Actors a WHERE a.name = :name")
    List<Actors> findActorByName(@Param("name") String name);
}
