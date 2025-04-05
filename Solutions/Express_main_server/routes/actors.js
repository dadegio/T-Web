const express = require('express');
const router = express.Router();
const ACTORS_CONTROLLER= require('../controllers/actors');
const setupSwagger = require('./swagger');

setupSwagger(router);

/**
 * @swagger
 * /actors/get-all-actors:
 *   get:
 *     summary: Recupera tutti gli attori con i loro film
 *     tags:
 *       - Actors
 *     responses:
 *       200:
 *         description: Lista di attori recuperata con successo
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   name:
 *                     type: string
 *                   role:
 *                     type: string
 *                   id:
 *                     type: integer
 *                   posterUrl:
 *                      type: string
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
 *       - Actors
 *     responses:
 *       200:
 *         description: Lista di attori recuperata con successo
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   name:
 *                     type: string
 *                   role:
 *                     type: string
 *                   id:
 *                     type: integer
 *                   posterUrl:
 *                      type: string
 *       500:
 *         description: Errore nel recupero dei dati
 */
router.get('/actors-info/:name', ACTORS_CONTROLLER.getAllInfo);

module.exports = router;