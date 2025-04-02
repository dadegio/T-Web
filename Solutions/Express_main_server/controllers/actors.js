const AXIOS = require('axios'); // Importa la libreria Axios per effettuare richieste HTTP
const JAVA_SPRING_SERVER_URL = 'http://localhost:8080'; // URL del server Java Spring per la gestione degli attori

/**
 * Funzione che recupera tutti gli attori e li arricchisce con i poster dei film, se disponibili.
 * @param {Object} req - Oggetto della richiesta HTTP
 * @param {Object} res - Oggetto della risposta HTTP
 */
async function getAllActors(req, res) {
    try {
        // Esegue entrambe le richieste in parallelo per ridurre il tempo di attesa
        const [actorsResponse, moviesResponse] = await Promise.all([
            AXIOS.get(`${JAVA_SPRING_SERVER_URL}/actors/get-all`), // Recupera la lista degli attori
            AXIOS.get(`${JAVA_SPRING_SERVER_URL}/movies/actors-home`) // Recupera la lista dei film con i relativi poster
        ]);

        const actors = actorsResponse.data; // Lista degli attori
        const movies = moviesResponse.data; // Lista dei film con i poster

        // Creazione di una mappa filmId
        const moviesMap = new Map(movies.map(movie => [movie.id, movie]));

        // Arricchisce gli attori solo se il film ha un poster
        const enrichedActors = actors
            .map(actor => {
                const movieDetails = moviesMap.get(actor.id); // Recupera i dettagli del film associato all'attore
                return movieDetails?.posterUrl ? { // Verifica se il film ha un poster
                    ...actor,
                    posterUrl: movieDetails.posterUrl, // Aggiunge il poster al dettaglio dell'attore
                    filmId: movieDetails.id
                } : null; // Se non c'è un poster, restituisce null
            })
            .filter(Boolean); // Rimuove i valori null dall'array

        // Rimuove i duplicati di poster
        const uniqueActors = [];
        const seenPosters = new Set();

        enrichedActors.forEach(actor => {
            if (!seenPosters.has(actor.posterUrl)) { // Controlla se il poster è già stato aggiunto
                uniqueActors.push(actor);
                seenPosters.add(actor.posterUrl); // Aggiunge il poster alla lista dei già visti
            }
        });

        // Raggruppa gli attori per nome e limita a 5 per ciascun attore
        const groupedActors = uniqueActors.reduce((acc, actor) => {
            (acc[actor.name] ||= []).push(actor);
            acc[actor.name] = acc[actor.name].slice(0, 5); // Mantiene al massimo 5 attori per nome
            return acc;
        }, {});

        // Renderizza la pagina degli attori
        res.render('pages/actors', {
            layout: 'main', // Specifica il layout da utilizzare
            groupedActors
        });

    } catch (error) {
        res.status(500).send('Errore nel recupero dei dati'); // Gestisce eventuali errori durante la richiesta
    }
}

/**
 * Funzione che recupera le informazioni dettagliate di un attore specifico.
 * @param {Object} req - Oggetto della richiesta HTTP
 * @param {Object} res - Oggetto della risposta HTTP
 */
async function getAllInfo(req, res) {
    try {
        const name = req.params.name; // Recupera il nome dell'attore dai parametri della richiesta

        // Esegue entrambe le richieste in parallelo per ridurre il tempo di attesa
        const [actorsResponse, moviesResponse] = await Promise.all([
            AXIOS.get(`${JAVA_SPRING_SERVER_URL}/actors/get-actor-by-name?name=` + encodeURIComponent(name)),
            AXIOS.get(`${JAVA_SPRING_SERVER_URL}/movies/actors-home`) // Recupera la lista dei film con i relativi poster
        ]);

        const actorsFilms = actorsResponse.data; // Lista dei film in cui ha recitato l'attore
        const moviesData = moviesResponse.data; // Lista dei film con poster

        // Creazione di una mappa filmId
        const moviesMap = new Map(moviesData.map(movie => [movie.id, movie]));

        // Arricchisce gli attori solo se il film ha un poster
        const enrichedActors = actorsFilms
            .map(actor => {
                const movieDetails = moviesMap.get(actor.id); // Recupera i dettagli del film
                return movieDetails?.posterUrl ? {
                    ...actor,
                    posterUrl: movieDetails.posterUrl, // Aggiunge il poster all'attore
                    filmId: movieDetails.id
                } : null;
            })
            .filter(Boolean); // Rimuove i valori null

        // Renderizza la pagina con i dettagli dell'attore
        res.render('pages/actors_info', {
            actor: enrichedActors,
            name: name
        });
    } catch (error) {
        res.status(500).json({ error: "Impossibile recuperare i dettagli dell'attore" }); // Gestisce eventuali errori
    }
}

// Esporta le funzioni per essere utilizzate in altri moduli
module.exports = {getAllActors, getAllInfo};
