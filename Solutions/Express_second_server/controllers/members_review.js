// Importa il modello MembersReview, che rappresenta la collezione di recensioni dei membri nel database
const MembersReview = require('../models/members_review');

/**
 * Recupera i critici cinematografici dal database, separando i top critic dagli altri.
 *
 * @param {Object} req - L'oggetto della richiesta HTTP.
 * @param {Object} res - L'oggetto della risposta HTTP.
 */
exports.getMembers = async (req, res) => {
    try {
        // Esegue due query in parallelo:
        // - Trova i primi 3 critici che sono top critic
        // - Trova i primi 10 critici che non sono top critic
        const [topCritics, allCritics] = await Promise.all([
            MembersReview.find({ top_critic: true }).limit(3).select('critic_name'),
            MembersReview.find({ top_critic: false }).limit(10).select('critic_name')
        ]);

        // Restituisce una risposta JSON con i critici separati in due categorie
        res.status(200).json({
            topCritics: topCritics.map(review => review.critic_name),
            allCritics: allCritics.map(review => review.critic_name)
        });

    } catch (error) {
        // Gestisce eventuali errori durante il recupero dei critici
        res.status(500).json({ error: error.message });
    }
};

/**
 * Recupera i dettagli delle recensioni di un critico specifico.
 *
 * @param {Object} req - L'oggetto della richiesta HTTP contenente il nome del critico nei parametri URL.
 * @param {Object} res - L'oggetto della risposta HTTP.
 */
exports.getCriticDetails = async (req, res) => {
    try {
        // Decodifica il nome del critico dall'URL e sostituisce eventuali "%20" con spazi
        let criticName = decodeURIComponent(req.params.critic_name);
        criticName = criticName.replace(/%20/g, ' ').trim();  // Pulisce il nome eliminando eventuali spazi in eccesso

        console.log('Decoded Critic Name:', criticName);  // Debug: stampa il nome decodificato

        // Cerca tutte le recensioni associate al critico specificato
        const reviews = await MembersReview.find({ critic_name: criticName })
            .select('movie_title review_type review_score review_date review_content');

        // Se il critico non ha recensioni, restituisce un errore 404
        if (reviews.length === 0) {
            return res.status(404).json({ error: 'Critico non trovato' });
        }

        // Restituisce i dettagli delle recensioni del critico in formato JSON
        res.status(200).json({
            criticName,
            reviews: reviews.map(review => ({
                movie_title: review.movie_title,
                review_type: review.review_type,
                review_score: review.review_score,
                review_date: review.review_date,
                review_content: review.review_content
            }))
        });

    } catch (error) {
        console.error('Error fetching critic details:', error);
        res.status(500).json({ error: error.message });
    }
};

/**
 * Recupera tutte le recensioni di un film specifico sulla base del suo nome e della data di uscita.
 *
 * @param {string} movieName - Il titolo del film per cui recuperare le recensioni.
 * @param {string} releaseDate - La data di uscita del film nel formato "YYYY-MM-DD".
 * @returns {Object} Un oggetto contenente lo stato HTTP e i dati delle recensioni.
 */
exports.getReviewsByMovie = async (movieName, releaseDate) => {
    try {
        // Converte la stringa della data di uscita in un oggetto di tipo Date
        const releaseDateObj = new Date(releaseDate);
        const releaseDateFormatted = releaseDateObj.toISOString().split('T')[0]; // Formatta la data come "YYYY-MM-DD"

        // Cerca le recensioni del film basandosi sul titolo e sulla data di uscita
        const reviews = await MembersReview.find({
            movie_title: { $regex: new RegExp(`^${movieName}$`, 'i') }, // Ignora maiuscole/minuscole
            review_date: {
                $gte: releaseDateFormatted, // Considera solo recensioni dalla data di uscita in poi
                $lte: '2025-12-31' // Imposta un limite massimo arbitrario (può essere modificato)
            }
        }).select('movie_title critic_name review_type review_score review_date review_content');

        // Restituisce le recensioni in un oggetto con lo stato HTTP
        return { status: 200, data: reviews || [] };

    } catch (error) {
        return { status: 500, data: { message: 'Errore nel recupero delle recensioni.', error } };
    }
};

/**
 * Aggiunge una nuova recensione al database basandosi sui dati ricevuti da una richiesta HTTP.
 *
 * @param {Object} req - L'oggetto della richiesta HTTP contenente i dettagli della recensione nel corpo.
 * @param {Object} res - L'oggetto della risposta HTTP.
 */
exports.addReview = async (req, res) => {
    // Estrae i dati della recensione dal corpo della richiesta
    const {
        movie_title,
        critic_name,
        top_critic,
        publisher_name,
        review_type,
        review_score,
        review_date,
        review_content,
        rotten_tomatoes_link
    } = req.body;

    // Crea un nuovo oggetto recensione da salvare nel database
    const newReview = new MembersReview({
        movie_title,
        critic_name,
        top_critic: top_critic === 'on',  // I checkbox HTML inviano "on" quando selezionati, quindi lo convertiamo in booleano
        publisher_name,
        review_type,
        review_score,
        review_date,
        review_content,
        rotten_tomatoes_link
    });

    try {
        // Salva la nuova recensione nel database
        await newReview.save();
        // Dopo il salvataggio, reindirizza l'utente alla pagina dei film
        res.redirect("http://localhost:3000/movies/get-all-movies");

    } catch (err) {
        console.error(err);
        // Se si verifica un errore, restituisce un errore HTTP 500 con un messaggio appropriato
        res.status(500).json({ message: 'Errore nel salvataggio della recensione' });
    }
};
