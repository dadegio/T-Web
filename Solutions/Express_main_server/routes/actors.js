const express = require('express');
const router = express.Router();
const ACTORS_CONTROLLER= require('../controllers/actors');
const setupSwagger = require('./swagger');

setupSwagger(router);

/**
 * @swagger
 * /actors/get-all-actors:
 *   get:
 *     summary: Recupera tutti gli attori con i loro film e poster
 *     tags:
 *         - Actors
 *     responses:
 *       200:
 *         description: Lista di attori recuperata con successo
 *         content:
 *           text/html:
 *       500:
 *         description: Errore nel recupero dei dati
 */
router.get('/get-all-actors', ACTORS_CONTROLLER.getAllActors);

/**
 * @swagger
 * /actors/actors-info/{name}:
 *   get:
 *     summary: Recupera i dettagli di un attore specifico
 *     tags:
 *         - Actors
 *     parameters:
 *       - in: path
 *         name: name
 *         required: true
 *         schema:
 *           type: string
 *         description: Nome dell'attore da cercare
 *     responses:
 *       200:
 *         description: Dettagli dell'attore recuperati con successo
 *         content:
 *           text/html:
 *       500:
 *         description: Impossibile recuperare i dettagli dell'attore
 */
router.get('/actors-info/:name', ACTORS_CONTROLLER.getAllInfo);

module.exports = router;