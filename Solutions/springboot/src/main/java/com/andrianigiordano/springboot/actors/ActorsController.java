package com.andrianigiordano.springboot.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST per la gestione degli attori.
 */
@RestController
@RequestMapping("/actors") // Definisce il path base per tutti gli endpoint di questo controller
public class ActorsController {

    private final ActorsService actorsService;

    /**
     * Costruttore del controller con iniezione delle dipendenze.
     * L'oggetto ActorsService viene iniettato automaticamente da Spring.
     *
     * @param actorsService il servizio per la gestione degli attori
     */
    @Autowired
    public ActorsController(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

    /**
     * Endpoint per ottenere la lista completa di tutti gli attori disponibili.
     *
     * @return una lista di oggetti Actors contenente tutti gli attori presenti nel database
     */
    @GetMapping("/get-all")
    public List<Actors> getAllActors() {
        return actorsService.getAllActors();
    }

    /**
     * Endpoint per ottenere i dettagli di un attore filtrando per nome.
     *
     * @param name il nome dell'attore da cercare, passato come parametro della query
     * @return una lista di oggetti Actors che corrispondono al nome fornito
     */
    @GetMapping("/get-actor-by-name")
    public List<Actors> getMovieDetails(@RequestParam String name) {
        return actorsService.getActorByName(name);
    }
}
