# Progetto IUM - TWEB

## 1. Introduzione

Questo progetto nasce dall'integrazione delle competenze acquisite nei moduli **IUM (Interazione Uomo-Macchina)** e **TWEB (Tecnologie Web)**. L'obiettivo principale è stato lo sviluppo di una piattaforma web dedicata alla consultazione di dati relativi a film e serie TV, basata su dataset forniti e gestiti attraverso un'infrastruttura server strutturata.

Il progetto si articola in due parti principali:

* **Elaborazione e analisi dati (IUM)**: pulizia, trasformazione e studio dei dataset.
* **Sviluppo di applicazione web (TWEB)**: realizzazione di client e backend multipli per la presentazione e gestione dei dati.

Il lavoro è stato svolto in due componenti equilibrate, con una suddivisione delle attività pari a:

* *Agnese Andriani: 50%*
* *Davide Giordano: 50%*

---

## 2. Struttura della Soluzione

La piattaforma è composta da:

| Componente                | Tecnologia                         | Funzione                                              |
| ------------------------- | ---------------------------------- | ----------------------------------------------------- |
| Server Express Principale | Node.js + Express                  | Gestione routing e composizione dati per il frontend  |
| Server Express Secondario | Node.js + Express + MongoDB        | Gestione dati dinamici (recensioni, aggiornamenti)    |
| Server Backend            | Spring Boot + PostgreSQL           | Gestione dati statici e complessa modellazione entità |
| Frontend Web              | Handlebars + Axios + CSS/Bootstrap | Visualizzazione e interazione lato utente             |

### 2.1 Data Cleaning (IUM)

L'elaborazione dati è stata effettuata tramite **Jupyter Notebook** e libreria **Pandas**.

Fasi principali:

* Importazione e comprensione dei dataset
* Gestione valori mancanti e duplicati
* Uniformazione dei formati (es. scala punteggi recensioni → 0-10)
* Esportazione dei dati pronti per i database

**Problematiche:**

* Conversione scale di valutazione eterogenee
* Pulizia film inconsistenti o non distribuiti

**Output:**
Dataset utilizzabili senza ulteriori trasformazioni durante l'uso in backend.

---

### 2.2 Data Analysis (IUM)

Sono stati creati due notebook di analisi tematiche:

* `cartoon.ipynb`
* `nolan.ipynb`

Utilizzate: Pandas, Matplotlib, Seaborn, Numpy, GeoPandas.

Requisiti soddisfatti:

* Ampia varietà di visualizzazioni
* Utilizzo di grafici geografici
* Notebook consegnati in formato eseguito

---

### 2.3 Server Express Principale (TWEB)

Responsabile dell'instradamento richieste e assemblaggio dati provenienti da backend multipli.

**Challenge:** sincronizzazione tempi di risposta e aggregazione dati.

**Soluzione:** utilizzo di `async/await` e `Promise.all`.

---

### 2.4 Server Express Secondario (Dati Dinamici)

Gestione di recensioni e contenuti aggiornabili.

Richiede configurazione di **MongoDB** con modelli associati alle tabelle interessate.

---

### 2.5 Server Spring Boot (Dati Statici)

Gestione film, attori e metadati tramite **PostgreSQL** + **JPA**.

Architettura a livelli:

* Entity
* Repository
* Service
* Controller

Utilizzo di **DTO** per aggregazione trasparente dei dati (es. film + poster).

---

### 2.6 Frontend Web

Sviluppato con **Handlebars** per il templating e **Axios** per le chiamate API.

Ottimizzazioni:

* Minimizzazione dati richiesti nelle pagine principali
* Gestione pagina errore dedicata

---

## 3. Conclusioni

Il progetto ha permesso di integrare competenze trasversali su:

* pulizia e analisi dati
* modellazione entità backend
* gestione comunicazione tra microservizi
* sviluppo frontend dinamico basato su API

La divisione delle responsabilità tra server ha consentito un design pulito, ma ha richiesto particolare cura nell'orchestrazione delle comunicazioni.

---

## 4. Bibliografia e Strumenti Utilizzati

* Slide dei corsi IUM e Tweb
* Documentazione ufficiale: Pandas, Express.js, Spring Boot, PostgreSQL, MongoDB
* Bootstrap, W3School, JavaDoc
* ChatGPT utilizzato per supporto alla scrittura e ristrutturazione codice, con verifica manuale successiva

---

## 5. Stato del Progetto

| Componente     | Stato           |
| -------------- | --------------- |
| Data Cleaning  | ✅ Completato    |
| Data Analysis  | ✅ Completato    |
| Backend Spring | ✅ Funzionante   |
| Server Express | ✅ Funzionante   |
| Frontend       | ✅ Funzionante   |

---

Fine del documento.
