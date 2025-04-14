package com.andrianigiordano.springboot.actors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST per la gestione degli attori.
 */
@RestController
@RequestMapping("/actors")
@Tag(name = "Actors", description = "Operazioni per la gestione degli attori")
public class ActorsController {

    private final ActorsService actorsService;

    @Autowired
    public ActorsController(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

    /**
     * Endpoint per ottenere la lista completa di tutti gli attori disponibili.
     *
     * @return una lista di oggetti Actors contenente tutti gli attori presenti nel database
     */
    @Operation(summary = "Ottieni tutti gli attori", description = "Restituisce una lista di tutti gli attori nel database")
    @GetMapping("/get-all")
    public List<Actors> getAllActors() {
        return actorsService.getAllActors();
    }

    /**
     * Endpoint per ottenere i dettagli di un attore filtrando per nome.
     *
     * @param name il nome dell'attore da cercare
     * @return una lista di oggetti Actors che corrispondono al nome fornito
     */
    @Operation(summary = "Cerca attore per nome", description = "Restituisce gli attori che corrispondono al nome specificato")
    @GetMapping("/get-actor-by-name")
    public List<Actors> getMovieDetails(
            @Parameter(description = "Nome dell'attore da cercare") @RequestParam String name) {
        return actorsService.getActorByName(name);
    }
}
