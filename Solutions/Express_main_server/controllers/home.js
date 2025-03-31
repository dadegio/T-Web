const axios = require('axios'); // Importa la libreria Axios per effettuare richieste HTTP

// URL del backend Spring Boot che fornisce i film per la home page
const JAVA_SPRING_SERVER_URL = 'http://localhost:8080/movies/get-home-movies';

/**
 * Funzione per ottenere i film con poster e renderizzarli nella home page.
 * @param {Object} req - Oggetto della richiesta HTTP
 * @param {Object} res - Oggetto della risposta HTTP
 */
exports.getMoviesHome = async (req, res) => {
    try {
        // Effettua una richiesta GET al server Java Spring Boot per ottenere i film
        const response = await axios.get(JAVA_SPRING_SERVER_URL);
        const movies = response.data; // Estrae i dati dei film dalla risposta

        // Renderizza la pagina home con la lista dei film
        res.render('partials/home', { layout: 'main', movies });
    } catch (error) {
        // Logga l'errore nel recupero dei dati dal backend
        console.error('Errore nel recupero dei film:', error);

        // In caso di errore, renderizza comunque la pagina home ma senza film
        res.render('partials/home', { layout: 'main', movies: [] });
    }
};
