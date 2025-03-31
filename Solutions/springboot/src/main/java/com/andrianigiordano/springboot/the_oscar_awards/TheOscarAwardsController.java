package com.andrianigiordano.springboot.the_oscar_awards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per gestire le richieste relative agli Oscar.
 * Gestisce le richieste HTTP per ottenere informazioni sugli Oscar.
 */
@RestController // Indica che questa classe gestisce le richieste HTTP e restituisce risposte in formato JSON
@RequestMapping("/oscar") // Mappa le richieste alla route "/oscar"
public class TheOscarAwardsController {

    // Iniezione del servizio che gestisce la logica degli Oscar
    private final TheOscarAwardsService theOscarAwardsService;

    /**
     * Costruttore per iniettare il servizio TheOscarAwardsService.
     *
     * @param theOscarAwardsService Il servizio che gestisce la logica delle operazioni sugli Oscar.
     */
    @Autowired // Indica che il costruttore sarà utilizzato da Spring per l'iniezione delle dipendenze
    public TheOscarAwardsController(TheOscarAwardsService theOscarAwardsService) {
        this.theOscarAwardsService = theOscarAwardsService;
    }

    /**
     * Metodo per ottenere la lista dei primi 100 Oscar.
     *
     * @return Una lista degli oggetti "TheOscarAwards" contenenti i dettagli dei primi 100 Oscar.
     */
    @GetMapping("/top100") // Gestisce la richiesta GET alla rotta "/oscar/top100"
    public List<TheOscarAwards> getTop100Oscars() {
        // Recupera la lista dei Top 100 Oscar dal servizio
        return theOscarAwardsService.getTop100Oscars(); // Restituisce la lista degli Oscar
    }

}
