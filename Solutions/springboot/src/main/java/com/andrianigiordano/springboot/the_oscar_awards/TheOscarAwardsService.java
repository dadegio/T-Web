package com.andrianigiordano.springboot.the_oscar_awards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servizio che gestisce la logica di business relativa agli Oscar.
 * Fornisce metodi per ottenere i dati degli Oscar tramite il repository.
 */
@Service // L'annotazione @Service indica che questa classe è un servizio, ossia una classe di logica di business.
public class TheOscarAwardsService {

    // Variabile che rappresenta il repository per l'accesso ai dati sugli Oscar.
    private final TheOscarAwardsRepository theOscarAwardsRepository;

    /**
     * Costruttore del servizio, che inietta il repository per l'accesso ai dati.
     *
     * @param theOscarAwardsRepository Il repository per l'accesso ai dati degli Oscar.
     */
    @Autowired // Inietta automaticamente il repository tramite il costruttore
    public TheOscarAwardsService(TheOscarAwardsRepository theOscarAwardsRepository) {
        this.theOscarAwardsRepository = theOscarAwardsRepository;
    }

    /**
     * Metodo che recupera la lista dei Top 100 Oscar dal repository.
     * Questo metodo si basa sul repository per ottenere i vincitori degli Oscar
     * nelle cerimonie posteriori al numero 90.
     *
     * @return Una lista di oggetti "TheOscarAwards" che rappresentano i top 100 Oscar.
     */
    public List<TheOscarAwards> getTop100Oscars() {
        // Chiama il metodo del repository per ottenere la lista degli Oscar
        return theOscarAwardsRepository.findTheOscarAwards();
    }
}
