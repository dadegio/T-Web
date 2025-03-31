var express = require('express');
var router = express.Router();
var homeController = require('../controllers/home');

/**
 * @swagger
 * /:
 *   get:
 *     summary: Ottieni i film della home
 *     description: Recupera l'elenco dei film dalla home page del server Spring Boot.
 *     tags:
 *       - Home
 *     responses:
 *       '200':
 *         description: Lista dei film ottenuta con successo
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   id:
 *                     type: integer
 *                   name:
 *                     type: string
 *                   poster:
 *                     type: string
 *                   date:
 *                     type: integer
 *                   tagline:
 *                     type: string
 *                   description:
 *                     type: string
 *                   rating:
 *                     type: number
 *                     format: float
 */

router.get('/', homeController.getMoviesHome);

module.exports = router;
