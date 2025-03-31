package com.andrianigiordano.springboot.the_oscar_awards;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaccia del repository per l'accesso ai dati relativi agli Oscar.
 */
@Repository // Indica che questa è una classe di repository, che gestisce l'accesso ai dati nel database
public interface TheOscarAwardsRepository extends JpaRepository<TheOscarAwards, Long> {

    /**
     * Metodo per ottenere gli Oscar relativi alla cerimonia con numero superiore a 90
     * dove il vincitore è indicato come "true".
     *
     * @return Una lista di oggetti "TheOscarAwards" che rappresentano gli Oscar dei vincitori.
     */
    @Query("SELECT o FROM TheOscarAwards o  where o.ceremony > 90 and o.winner = true")
    List<TheOscarAwards> findTheOscarAwards();  // Metodo per ottenere i top 100 Oscar
}
