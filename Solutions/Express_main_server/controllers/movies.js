// Importazione della libreria Axios per effettuare richieste HTTP
const AXIOS = require('axios');

// URL del server Java Spring che gestisce i dati dei film
const JAVA_SPRING_SERVER_URL = 'http://localhost:8080/movies';

/**
 * Funzione per ottenere tutti i film dal server Java Spring.
 * Effettua una richiesta GET al backend e passa i dati ottenuti alla vista Handlebars.
 *
 * @param {Object} req - L'oggetto della richiesta HTTP
 * @param {Object} res - L'oggetto della risposta HTTP
 */
async function getAllMovies(req, res) {
    try {
        // Effettua una richiesta GET per ottenere tutti i film
        const response = await AXIOS.get(`${JAVA_SPRING_SERVER_URL}/get-all`);

        // Estrae i dati dei film dalla risposta del server
        const movies = response.data;

        // Renderizza la pagina 'movies' passando i dati dei film alla vista Handlebars
        res.render('pages/movies', { movies });
    } catch (error) {
        // Log dell'errore nel caso in cui la richiesta fallisca
        console.error('Errore nel recupero dei film dal server Spring:', error);

        // Restituisce una pagina di errore con un messaggio appropriato
        res.status(500).render('pages/error', { message: 'Dati non disponibili' });
    }
}

/**
 * Funzione per ottenere tutte le informazioni dettagliate su un film specifico.
 * Recupera i dati del film e le informazioni correlate (cast, paesi, lingue, generi, studi, temi)
 * dal server Java Spring e le recensioni da un altro server Node.js.
 *
 * @param {Object} req - L'oggetto della richiesta HTTP
 * @param {Object} res - L'oggetto della risposta HTTP
 */
async function getAllInfo(req, res) {
    try {
        // Ottiene l'ID del film dai parametri della richiesta
        const movieId = req.params.id;

        // Effettua richieste parallele per recuperare tutte le informazioni del film
        const endpoints = await Promise.all([
            AXIOS.get(`${JAVA_SPRING_SERVER_URL}/get-movie-by-id?movieId=` + movieId), // Dati principali del film
            AXIOS.get(`${JAVA_SPRING_SERVER_URL}/get-crew-by-id?movieId=` + movieId), // Crew (registi, attori, ecc.)
            AXIOS.get(`${JAVA_SPRING_SERVER_URL}/get-countries-by-id?movieId=` + movieId), // Paesi di produzione
            AXIOS.get(`${JAVA_SPRING_SERVER_URL}/get-languages-by-id?movieId=` + movieId), // Lingue disponibili
            AXIOS.get(`${JAVA_SPRING_SERVER_URL}/get-genres-by-id?movieId=` + movieId), // Generi del film
            AXIOS.get(`${JAVA_SPRING_SERVER_URL}/get-studios-by-id?movieId=` + movieId), // Studi cinematografici
            AXIOS.get(`${JAVA_SPRING_SERVER_URL}/get-themes-by-id?movieId=` + movieId) // Temi del film
        ]);

        // Estrae i dati principali del film dalla prima richiesta
        const movieData = endpoints[0].data;

        // Recupera il titolo del film
        const movieName = movieData.title;

        // Recupera la data di uscita del film (si assume che la data sia memorizzata nel campo 'date')
        const releaseDate = movieData.date;

        // Effettua una richiesta GET per ottenere le recensioni da un altro server
        const reviewsResponse = await AXIOS.get(`http://localhost:3001/reviews/${encodeURIComponent(movieName)}/${encodeURIComponent(releaseDate)}`);

        // Struttura l'oggetto con tutti i dati raccolti
        const data = {
            movie: movieData, // Dati principali del film
            crew: endpoints[1].data, // Informazioni sul cast e crew
            countries: endpoints[2].data, // Paesi di produzione
            languages: endpoints[3].data, // Lingue disponibili
            genres: endpoints[4].data, // Generi cinematografici
            studios: endpoints[5].data, // Studi cinematografici
            themes: endpoints[6].data, // Temi del film
            reviews: reviewsResponse.data // Recensioni del film
        };

        // Log per debugging: mostra i dati ricevuti e l'ID del film richiesto
        console.log('Dati ricevuti:', data);
        console.log('Movie ID atteso:', movieId);

        // Renderizza la pagina con tutte le informazioni del film
        res.render('pages/movies_info', data);
    } catch (error) {
        // Log dell'errore nel caso in cui una delle richieste fallisca
        console.error('Errore nel recupero dei dati:', error);

        // Restituisce una pagina di errore con un messaggio appropriato
        res.status(500).render("pages/error", { message: 'Dati non disponibili' });
    }
}

// Esporta le funzioni per poterle utilizzare in altre parti dell'applicazione
module.exports = {
    getAllMovies,
    getAllInfo
};
