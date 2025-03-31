const axios = require('axios'); // Importa la libreria Axios per effettuare richieste HTTP

// URL del backend Spring Boot
const JAVA_SPRING_SERVER_URL = 'http://localhost:8080';

/**
 * Funzione per ottenere i primi 100 film premiati agli Oscar e arricchirli con i poster.
 * @param {Object} req - Oggetto della richiesta HTTP
 * @param {Object} res - Oggetto della risposta HTTP
 */
async function getTop100Oscars(req, res) {
    try {
        // Esegue in parallelo le richieste per ottenere i vincitori degli Oscar e i film
        const [oscarResponse, moviesResponse] = await Promise.all([
            axios.get(`${JAVA_SPRING_SERVER_URL}/oscar/top100`), // Recupera i vincitori degli Oscar
            axios.get(`${JAVA_SPRING_SERVER_URL}/movies/top100`) // Recupera la lista dei film disponibili
        ]);

        // Estrae i dati dalle risposte
        const oscarAwards = oscarResponse.data;
        const movies = moviesResponse.data;

        // Arricchisce gli Oscar con i dettagli del film, inclusi i poster
        const enrichedAwards = oscarAwards
            .map(award => {
                // Trova il film corrispondente per titolo e anno
                const movieDetails = movies.find(movie => movie.title === award.film && movie.date === award.yearFilm);
                return movieDetails && movieDetails.posterUrl ? {
                    ...award,
                    posterUrl: movieDetails.posterUrl, // Aggiunge il poster del film
                    filmId: movieDetails.id // Aggiunge l'ID del film
                } : null; // Se il film non ha un poster, restituisce null
            })
            .filter(award => award !== null); // Rimuove i film senza poster

        // Raggruppa i premi Oscar per categoria
        const groupedAwards = enrichedAwards.reduce((acc, award) => {
            const category = award.category;
            if (!acc[category]) {
                acc[category] = [];
            }
            acc[category].push(award);
            return acc;
        }, {});

        // Renderizza la pagina Oscar con i premi raggruppati
        res.render('pages/oscar', { groupedAwards });
    } catch (error) {
        // Logga l'errore in console
        console.error("Errore nel recupero dei Top 100 Oscar:", error.message);

        // In caso di errore, mostra un messaggio di errore nella pagina
        res.render("pages/oscar", {
            oscarAwards: [],
            error: "Errore nel caricamento dei Top 100 Oscar."
        });
    }
}

// Esporta la funzione per essere utilizzata in altri moduli
module.exports = { getTop100Oscars };
