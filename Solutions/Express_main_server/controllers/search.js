// Importazione della libreria Axios per effettuare richieste HTTP
const AXIOS = require('axios');

/**
 * Funzione per gestire la ricerca di film o attori nel database.
 *
 * @param {Object} req - L'oggetto della richiesta HTTP, che contiene i parametri di ricerca
 * @param {Object} res - L'oggetto della risposta HTTP, che viene utilizzato per inviare il risultato della ricerca
 */
async function search(req, res) {
    // Ottiene il valore della query di ricerca dall'URL
    const query = req.query.query;

    // Ottiene il tipo di ricerca specificato dall'utente (può essere 'movie' o 'actor')
    const type = req.query.type;

    try {
        // Inizializza gli array per film e attori
        let movies = [];
        let actors = [];

        // Controlla se la ricerca riguarda i film
        if (type === 'movie') {
            // Effettua una richiesta GET al backend per cercare i film corrispondenti alla query
            const response = await AXIOS.get(`http://localhost:8080/search-movies?query=${encodeURIComponent(query)}`);

            // Estrae i risultati della ricerca dai dati ricevuti, assicurandosi che non sia undefined
            movies = response.data.movies || [];

            // Renderizza la pagina 'search_movies' passando i risultati della ricerca
            return res.render('pages/search_movies', { movies, query });
        }

        // Controlla se la ricerca riguarda gli attori
        if (type === 'actor') {
            // Effettua una richiesta GET per cercare gli attori corrispondenti alla query
            const first_response = await AXIOS.get(`http://localhost:8080/search-actors?query=${encodeURIComponent(query)}`);

            // Verifica se la risposta contiene una lista di attori valida
            if (Array.isArray(first_response.data.actors)) {
                actors = first_response.data.actors; // Memorizza gli attori trovati

                // Estrai gli ID degli attori dalla risposta
                const actorIds = actors.map(actor => actor.id);

                // Per ogni attore trovato, effettua una richiesta per ottenere i film associati
                const actorDetailsPromises = actorIds.map(async id => {
                    try {
                        console.log('Richiesta per film dell\'attore con ID:', id);

                        // Effettua la richiesta per ottenere i film in cui ha recitato l'attore
                        const res = await AXIOS.get(`http://localhost:8080/movies/get-movie-by-id?movieId=${id}`);

                        return res.data; // Restituisce i dati dei film trovati
                    } catch (error) {
                        // Se il server restituisce un errore 404, ignora l'errore e continua
                        if (error.response && error.response.status === 404) {
                            return null;
                        } else {
                            console.error('Errore inaspettato:', error);
                            return null;
                        }
                    }
                });

                // Attende il completamento di tutte le richieste per ottenere i film associati agli attori
                const moviesResults = await Promise.all(actorDetailsPromises);

                // Filtra i risultati rimuovendo eventuali valori null (cioè film non trovati)
                movies = moviesResults.filter(item => item !== null);
            } else {
                console.error("La proprietà 'actors' non è un array:", first_response.data.actors);
            }

            // Stampa a console l'elenco degli attori trovati per debugging
            console.log(actors);

            // Renderizza la pagina 'search_actors' passando attori e film trovati
            return res.render('pages/search_actors', { actors, movies, query });
        }

        // Se il tipo di ricerca non è valido, genera un errore
        throw new Error('Tipo di ricerca non valido');

    } catch (error) {
        // In caso di errore, stampa un messaggio nella console
        console.error('Errore durante la ricerca:', error);

        // Renderizza la pagina di errore con un messaggio appropriato
        res.render('pages/error', { query, error: 'Dati non disponibili.' });
    }
}

// Esporta la funzione per poterla utilizzare in altre parti dell'applicazione
module.exports = { search };
