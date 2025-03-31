var express = require('express');
var router = express.Router();
const MEMBERS_REVIEW_CONTROLLER = require("../controllers/members_review")
const setupSwagger = require("./swagger");
setupSwagger(router)

/**
 * @swagger
 * /members:
 *   get:
 *     summary: Recupera l'elenco dei membri
 *     description: Restituisce una lista di critici cinematografici, suddivisi tra top critic e altri critici.
 *     tags:
 *       - Members
 *     responses:
 *       "200":
 *         description: Lista dei membri recuperata con successo.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 topCritics:
 *                   type: array
 *                   description: Lista dei top critic (massimo 3).
 *                   items:
 *                     type: string
 *                 allCritics:
 *                   type: array
 *                   description: Lista di altri critici (massimo 10).
 *                   items:
 *                     type: string
 *       "500":
 *         description: Errore nel recupero dei membri.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 error:
 *                   type: string
 */
router.get('/members', async (req, res, next) => {
  try {
    const results = await MEMBERS_REVIEW_CONTROLLER.getMembers(req, res);
    res.json(results);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

/**
 * @swagger
 * /members/{critic_name}:
 *   get:
 *     summary: Recupera i dettagli di un critico
 *     description: Restituisce informazioni dettagliate su un critico specifico, incluse le sue recensioni.
 *     tags:
 *       - Members
 *     parameters:
 *       - name: critic_name
 *         in: path
 *         required: true
 *         description: Nome del critico di cui recuperare i dettagli.
 *         schema:
 *           type: string
 *     responses:
 *       "200":
 *         description: Dettagli del critico recuperati con successo.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 criticName:
 *                   type: string
 *                 reviews:
 *                   type: array
 *                   items:
 *                     type: object
 *                   properties:
 *                       movie_title:
 *                         type: string
 *                       review_type:
 *                         type: string
 *                       review_score:
 *                         type: number
 *                       review_date:
 *                         type: string
 *                         format: date
 *                       review_content:
 *                         type: string
 *       "404":
 *         description: Critico non trovato.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 error:
 *                   type: string
 *       "500":
 *         description: Errore nel recupero delle informazioni del critico.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 error:
 *                   type: string
 */
router.get('/members/:critic_name', async (req, res, next) => {
  try {
    const results = await MEMBERS_REVIEW_CONTROLLER.getCriticDetails(req, res);
    res.json(results);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

/**
 * @swagger
 * /reviews/{movieName}/{releaseDate}:
 *   get:
 *     summary: Recupera le recensioni di un film
 *     description: Restituisce le recensioni associate a un film specifico basato sul titolo e sulla data di uscita.
 *     tags:
 *       - Reviews
 *     parameters:
 *       - name: movieName
 *         in: path
 *         required: true
 *         description: Il nome del film da cercare.
 *         schema:
 *           type: string
 *       - name: releaseDate
 *         in: path
 *         required: true
 *         schema:
 *           type: string
 *           format: date
 *     responses:
 *       "200":
 *         description: Recensioni del film recuperate con successo.
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   movie_title:
 *                     type: string
 *                   critic_name:
 *                     type: string
 *                   review_type:
 *                     type: string
 *                   review_score:
 *                     type: number
 *                   review_date:
 *                     type: string
 *                   review_content:
 *                     type: string
 *       "500":
 *         description: Errore nel recupero delle recensioni.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 error:
 *                   type: string
 */
router.get('/reviews/:movieName/:releaseDate', async (req, res) => {
  const { movieName, releaseDate } = req.params;
  const result = await MEMBERS_REVIEW_CONTROLLER.getReviewsByMovie(movieName, releaseDate);
  res.status(result.status).json(result.data);
});

/**
 * @swagger
 * /reviews/add:
 *   post:
 *     summary: Aggiungi una recensione
 *     description: Permette di aggiungere una nuova recensione per un film.
 *     tags:
 *       - Reviews
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               movie_title:
 *                 type: string
 *               critic_name:
 *                 type: string
 *               top_critic:
 *                 type: boolean
 *                 example: true
 *               publisher_name:
 *                 type: string
 *               review_type:
 *                 type: string
 *               review_score:
 *                 type: number
 *               review_date:
 *                 type: string
 *                 format: date
 *               review_content:
 *                 type: string
 *               rotten_tomatoes_link:
 *                 type: string
 *     responses:
 *       "201":
 *         description: Recensione aggiunta con successo.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *       "500":
 *         description: Errore durante l'aggiunta della recensione.
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 error:
 *                   type: string
 */
router.post('/reviews/add', MEMBERS_REVIEW_CONTROLLER.addReview);

module.exports = router;
