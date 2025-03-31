const axios = require('axios');
const express = require('express');
const router = require("./home");

/**
 * @swagger
 * /members:
 *     get:
 *       summary: Ottiene i primi tre top critici e altri dieci critici normali
 *       description: Recupera la lista dei critici e dei top critici dal server secondario
 *       tags:
 *         - Members
 *       responses:
 *         '200':
 *           description: Lista dei critici recuperata con successo
 *           content:
 *             application/json:
 *               schema:
 *                 type: object
 *                 properties:
 *                   topCritics:
 *                     type: array
 *                     items:
 *                       type: string
 *                   allCritics:
 *                     type: array
 *                     items:
 *                       type: string
 *         '500':
 *           description: Errore interno del server
 * */

router.get('/members', async (req, res) => {
    try {

        // Chiamata al secondo server per ottenere i dati dei membri
        const response = await axios.get('http://localhost:3001/members');

        // I dati ricevuti dal server
        const { topCritics, allCritics } = response.data;

        // Render della pagina con i dati ricevuti
        res.render('pages/members', {
            topCritics,  // Passa l' array dei top critics alla view
            allCritics   // Passa l' array degli altri critics alla view
        });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

/**
 * @swagger
 * /members/{critic_name}:
 *   get:
 *     summary: Ottiene i dettagli di un critico
 *     description: Recupera i film recensiti da un critico specifico
 *     tags:
 *       - Members
 *     parameters:
 *       - in: path
 *         name: critic_name
 *         required: true
 *         schema:
 *           type: string
 *         description: Nome del critico di cui ottenere le recensioni
 *     responses:
 *       '200':
 *         description: Dati del critico recuperati con successo
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 criticName:
 *                   type: string
 *                   example: "Roger Ebert"
 *                 reviews:
 *                   type: array
 *                   items:
 *                     type: object
 *                     properties:
 *                       rotten_tomatoes_link:
 *                         type: string
 *                       movie_title:
 *                         type: string
 *                       critic_name:
 *                         type: string
 *                       top_critic:
 *                         type: boolean
 *                       publisher_name:
 *                         type: string
 *                       review_type:
 *                         type: string
 *                       review_score:
 *                         type: string
 *                       review_date:
 *                         type: string
 *                         format: date
 *                       review_content:
 *                         type: string
 *       '500':
 *         description: Errore interno del server
 */

// Rotta per ottenere i dettagli di un critico specifico
router.get('/members/:critic_name', async (req, res) => {
    try {
        const criticName = decodeURIComponent(req.params.critic_name);

        // Fai una richiesta al secondo server (localhost:3001) per ottenere le recensioni del critico
        const response = await axios.get(`http://localhost:3001/members/${criticName}`);

        // Passa i dati ottenuti dal secondo server alla vista HBS
        res.render('pages/critic', { criticName, movies: response.data });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

module.exports = router;
