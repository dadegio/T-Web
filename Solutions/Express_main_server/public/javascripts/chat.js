// Variabili globali per memorizzare il nome dell'utente e il numero della stanza della chat
let name = null;
let roomNo = null;

// Connessione al server della chat utilizzando Socket.IO
let chat = io.connect('/chat');

/**
 * Inizializza l'interfaccia della chat nascondendo l'area della chat
 * e mostrando il modulo di accesso iniziale.
 */
function init() {
    document.getElementById('initial_form').classList.remove('d-none'); // Mostra il modulo iniziale
    document.getElementById('chat_interface').classList.add('d-none'); // Nasconde l'interfaccia della chat
    initChatSocket(); // Avvia la gestione degli eventi della chat
}

/**
 * Configura gli eventi WebSocket per la comunicazione con il server.
 */
function initChatSocket() {
    // Evento che viene attivato quando un utente entra in una stanza della chat
    chat.on('joined', function (room, userId) {
        if (userId === name) {
            // Se l'utente che si è unito è lo stesso che ha effettuato l'accesso, nasconde il modulo di login
            hideLoginInterface(room, userId);
        } else {
            // Se è un altro utente, mostra un messaggio di notifica nella chat
            writeOnChatHistory(`<b>${userId}</b> è entrato nella stanza ${room}`);
        }
    });

    // Evento che riceve i messaggi della chat e li mostra nella cronologia
    chat.on('chat', function (room, userId, chatText) {
        let who = userId === name ? 'Me' : userId; // Se il messaggio è dell'utente corrente, visualizza "Me"
        writeOnChatHistory(`<b>${who}:</b> ${chatText}`); // Aggiunge il messaggio alla cronologia della chat
    });
}

/**
 * Invia un messaggio alla chat.
 */
function sendChatText() {
    let chatText = document.getElementById('chat_input').value; // Ottiene il testo dal campo di input
    if (chatText.trim() !== "") { // Controlla che il messaggio non sia vuoto
        chat.emit('chat', roomNo, name, chatText); // Invia il messaggio al server
        document.getElementById('chat_input').value = ""; // Pulisce il campo di input dopo l'invio
    }
}

/**
 * Connetti l'utente a una stanza della chat.
 */
function connectToRoom() {
    roomNo = document.getElementById('roomNo').value; // Ottiene il numero della stanza dalla form
    name = document.getElementById('name').value || 'Guest-' + Math.floor(Math.random() * 1000); // Imposta il nome dell'utente (se non specificato, assegna un nome casuale)
    chat.emit('create or join', roomNo, name); // Richiede al server di creare o unirsi alla stanza specificata
}

/**
 * Aggiunge un messaggio alla cronologia della chat.
 *
 * @param {string} text - Il testo da aggiungere alla cronologia della chat.
 */
function writeOnChatHistory(text) {
    let history = document.getElementById('chat_history'); // Seleziona l'elemento della cronologia chat
    let paragraph = document.createElement('p'); // Crea un nuovo elemento <p> per il messaggio
    paragraph.innerHTML = text; // Imposta il contenuto del messaggio
    history.appendChild(paragraph); // Aggiunge il messaggio alla cronologia
    history.scrollTop = history.scrollHeight; // Scorre automaticamente in basso per mostrare l'ultimo messaggio
}

/**
 * Nasconde l'interfaccia di login e mostra la schermata della chat con i dettagli della stanza.
 *
 * @param {string} room - Il numero della stanza a cui si è connessi.
 * @param {string} userId - L'ID dell'utente connesso.
 */
function hideLoginInterface(room, userId) {
    document.getElementById('initial_form').classList.add('d-none'); // Nasconde il modulo iniziale
    document.getElementById('chat_interface').classList.remove('d-none'); // Mostra l'interfaccia della chat
    document.getElementById('who_you_are').innerHTML = userId; // Mostra il nome dell'utente
    document.getElementById('in_room').innerHTML = room; // Mostra il numero della stanza
}
