var express = require('express');
var router = express.Router();

/**
 * @swagger
 *  /chat:
 *     get:
 *       summary: Ottiene la pagina principale della chat
 *       description: Restituisce la pagina della chat con il titolo "My Chat"
 *       tags:
 *         - Chat
 *       responses:
 *         "200":
 *           description: Pagina della chat caricata con successo
 *           content:
 *             text/html:
 *               schema:
 *                 type: string
 *         "500":
 *           description: Errore nel caricamento della pagina
 * */
/* GET home page. */
router.get('/', function(req, res, next) {
    res.render('pages/chat', { title: 'My Chat' });
});

module.exports = router;
