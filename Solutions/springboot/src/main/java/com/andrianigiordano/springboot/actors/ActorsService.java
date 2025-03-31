package com.andrianigiordano.springboot.actors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servizio per la gestione degli attori.
 * Questa classe fornisce metodi per recuperare gli attori dal repository
 * e applicare eventuali filtri di ricerca.
 */
@Service
public class ActorsService {

    private final ActorsRepository actorsRepository;

    /**
     * Costruttore del servizio con iniezione delle dipendenze.
     * L'istanza di ActorsRepository viene iniettata automaticamente da Spring.
     *
     * @param actorsRepository il repository per l'accesso ai dati sugli attori
     */
    public ActorsService(ActorsRepository actorsRepository) {
        this.actorsRepository = actorsRepository;
    }

    /**
     * Recupera tutti gli attori destinati alla visualizzazione nella home.
     *
     * @return una lista di oggetti Actors
     */
    public List<Actors> getAllActors() {
        return actorsRepository.findActorsForHome();
    }

    /**
     * Cerca attori il cui nome contiene la stringa fornita, ignorando maiuscole e minuscole.
     * Per limitare il numero di risultati, viene applicato un sistema di paginazione.
     *
     * @param query la stringa da cercare nel nome degli attori
     * @return una lista di attori che corrispondono al criterio di ricerca
     */
    public List<Actors> searchActorsByName(String query) {
        PageRequest pageable = PageRequest.of(0, 100); // Offset 0, massimo 100 risultati
        return actorsRepository.findByNameContainingIgnoreCase(query, pageable);
    }

    /**
     * Recupera gli attori che hanno esattamente il nome specificato.
     *
     * @param name il nome completo dell'attore da cercare
     * @return una lista di attori che corrispondono al nome esatto
     */
    public List<Actors> getActorByName(String name) {
        return actorsRepository.findActorByName(name);
    }
}
