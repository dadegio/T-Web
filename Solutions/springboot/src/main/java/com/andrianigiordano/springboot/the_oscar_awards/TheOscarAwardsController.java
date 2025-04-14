package com.andrianigiordano.springboot.the_oscar_awards;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller per gestire le richieste relative agli Oscar.
 */
@RestController
@RequestMapping("/oscar")
@Tag(name = "Oscar Awards", description = "Operazioni relative agli Oscar (film, attori, premi)")
public class TheOscarAwardsController {

    private final TheOscarAwardsService theOscarAwardsService;

    @Autowired
    public TheOscarAwardsController(TheOscarAwardsService theOscarAwardsService) {
        this.theOscarAwardsService = theOscarAwardsService;
    }

    /**
     * Restituisce i primi 100 premi Oscar.
     *
     * @return Lista dei premi Oscar (max 100) ordinati per rilevanza o data.
     */
    @Operation(summary = "Top 100 premi Oscar", description = "Restituisce una lista dei primi 100 premi Oscar")
    @GetMapping("/top100")
    public List<TheOscarAwards> getTop100Oscars() {
        return theOscarAwardsService.getTop100Oscars();
    }
}
