# 🐎 GaraCavalli - Simulazione di Corsa di Cavalli

Questo repository contiene un'applicazione in Java che simula una corsa di cavalli utilizzando il multithreading. Ogni cavallo è gestito da un thread separato e il vincitore è determinato in base alla velocità (passo) casuale e alla gestione delle interruzioni.

## ✨ Caratteristiche del Progetto

Il progetto implementa una simulazione con le seguenti funzionalità chiave:

* **Multithreading:** Ogni cavallo (`Cavallo.java`) è un thread che simula la sua corsa in modo indipendente.
* **Velocità Casuale:** Ogni cavallo riceve un "passo" (velocità) casuale all'avvio.
* **Sincronizzazione della Partenza:** Viene utilizzato un `CountDownLatch` (`startSignal`) per assicurare che tutti i cavalli partano simultaneamente.
* **Gestione di un Infortunio:** Un cavallo viene interrotto casualmente poco dopo la partenza per simularne l'infortunio (`cavalloAzzoppato.interrupt()`).
* **Classifica Concorrente:** La classifica finale è gestita da una `CopyOnWriteArrayList` per la sicurezza del thread.
* **Logging e Salvataggio:** Tutti gli output della simulazione (partenza, infortunio, arrivi) vengono loggati e salvati nel file `classifica.txt`.

## 🛠️ Struttura del Progetto

Il progetto è composto da due classi principali:

1.  ### `GaraCavalli.java` (Classe Principale)
    * Contiene il metodo `main()`, la logica per l'input utente (lunghezza del percorso), l'inizializzazione dei cavalli (5 istanze), la gestione della partenza e della classifica.
    * Gestisce la simulazione dell'infortunio (`interrupt()`) di un cavallo casuale.
    * Definisce e salva i risultati finali nel file `classifica.txt`.
    
2.  ### `Cavallo.java` (Thread)
    * Estende `Thread` e implementa la logica di corsa.
    * Ogni cavallo attende il segnale di partenza (`startSignal.await()`).
    * Simula il movimento decrementando la distanza in base al suo "passo" fino a raggiungere il traguardo (`distanza <= 0`).
    * Gestisce l'interruzione (l'azzoppamento) del thread.

## 🚀 Requisiti e Esecuzione

### Requisiti

* **Java Development Kit (JDK):** Versione 8 o successiva.

### Compilazione ed Esecuzione

1.  **Clona il repository:**
    ```bash
    git clone [https://github.com/mx101001/GaraCavalli.git](https://github.com/mx101001/GaraCavalli.git)
    cd GaraCavalli
    ```

2.  **Compila i file Java:**
    ```bash
    javac Cavallo.java GaraCavalli.java
    ```

3.  **Esegui l'applicazione:**
    ```bash
    java GaraCavalli
    ```
    L'applicazione ti chiederà di inserire la lunghezza del percorso.

### Output

Al termine della corsa, troverai un file chiamato **`classifica.txt`** nella directory del progetto contenente tutti i log della simulazione e la classifica finale.

## 🤝 Contributi

Sentiti libero di aprire issue o inviare Pull Request per migliorare la simulazione, ad esempio aggiungendo:
* Una GUI per la visualizzazione in tempo reale.
* Una logica di "passo" più complessa.
* Gestione dei thread tramite `ExecutorService`.

## 📧 Contatti

Per qualsiasi domanda o suggerimento sul progetto, puoi contattare l'autore su GitHub:
* **Autore:** [Filippo Berti] (il nome è preso dai commenti in `Cavallo.java`)
* **Profilo GitHub:** [https://github.com/mx101001](https://github.com/mx101001)

---

Licenza: Se non specificato diversamente, si assume un approccio standard di **Open Source**. Si consiglia di aggiungere un file `LICENSE` per specificare la licenza esatta (ad esempio MIT o GPL).
