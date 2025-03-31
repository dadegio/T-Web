const mongoose = require('mongoose'); // Importa la libreria mongoose per connettersi a MongoDB

// Definizione dell'URL del database MongoDB specifico che vogliamo utilizzare
const mongoDB = 'mongodb://localhost:27017/ium_database';

// Imposta la Promise di mongoose alla Promise globale di JavaScript
mongoose.Promise = global.Promise;

// Tentativo di connessione al database
connection = mongoose.connect(mongoDB, {
    checkServerIdentity: false, // Opzione per evitare controlli sull'identità del server
})
    .then(() => {
        // Se la connessione ha successo, stampa un messaggio nella console
        console.log('Connessione a MongoDB riuscita!');
    })
    .catch((error) => {
        // Se la connessione fallisce, stampa un messaggio di errore nella console
        console.log('Connessione a MongoDB non riuscita! ' + JSON.stringify(error));
    });
