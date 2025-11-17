import components.FileChooser;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Simula una semplice gara di cavalli utilizzando thread.
 * <p>
 * La classe crea più istanze di {@link Cavallo}, le avvia simultaneamente tramite
 * un {@link CountDownLatch} e registra la classifica nell'ordine di arrivo.
 * I risultati vengono stampati a video e salvati in un file di testo (classifica.txt).
 *
 * <p>Comportamento principale:
 * <ul>
 *   <li>Crea 5 cavalli con nomi "Cavallo1".."Cavallo5"</li>
 *   <li>Attende l'input da tastiera per la lunghezza del percorso</li>
 *   <li>Avvia i cavalli, interrompe casualmente uno di essi (azzoppamento)</li>
 *   <li>Raccoglie la classifica e la salva su file</li>
 * </ul>
 *
 */
public class GaraCavalli {
    /** Lista thread-safe che rappresenta la classifica (ordine di arrivo). */
    private static final List<String> classifica = new CopyOnWriteArrayList<>();
    /** Buffer che accumula l'output destinato anche al file di risultato. */
    private static final StringBuilder bufferFile = new StringBuilder();
    private static File OutputFile;

    /**
     * Metodo per aggiungere un cavallo
     * @param nome nome del cavallo da aggiungere alla Lista classifica
     * Aggiunge un nome alla classifica (usato dai cavalli quando tagliano il traguardo).
     *
     * @param nome nome del cavallo che ha tagliato il traguardo
     */
    public static void addToClassifica(String nome) {
        classifica.add(nome);

    }

    /**
     * Metodo della classe GaraCavalli che serve a stampare e salvare i log sullo String Builder
     * @param message La stringa che deve essere stampata e salvata sul buffer
     * Stampa un messaggio su console e lo memorizza nel buffer destinato al file.
     *
     * @param message messaggio da loggare
     */
    private static void log(String message) {
        System.out.println(message);
        bufferFile.append(message).append("\n");
    }
    
    /**
     * Punto d'ingresso dell'applicazione. Gestisce la creazione e l'esecuzione della gara.
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        Scanner tastiera = new Scanner(System.in);
        createAndShowGUI();
        log("=== GARA DI CAVALLI ===");
        System.out.print("Inserisci la lunghezza del percorso (in metri): ");
        int percorso = tastiera.nextInt();
        log("Percorso: " + percorso + " metri\n");
        
        List<Cavallo> cavalli = new ArrayList<>();
        CountDownLatch startSignal = new CountDownLatch(1);
        
        
        for (int i = 0; i < 5; i++) {
            cavalli.add(new Cavallo("Cavallo" + (i + 1), percorso, startSignal));
        }
        
       
        cavalli.forEach(Cavallo::start);
        
        try {
            
            Thread.sleep(1000);
            log("--- PARTENZA! ---\n");
            startSignal.countDown();

            Thread.sleep(300);
            Cavallo cavalloAzzoppato = cavalli.get((int) (Math.random() * cavalli.size()));
            log(">>> " + cavalloAzzoppato.getName() + " viene azzoppato! <<<\n");
            cavalloAzzoppato.interrupt();
            
          
            for (Cavallo cavallo : cavalli) {
                cavallo.join();
            }
            
            
            log("\n=== CLASSIFICA FINALE ===");
            if (classifica.isEmpty()) {
                log("Nessun cavallo ha completato la gara!");
            } else {
                for (int i = 0; i < classifica.size(); i++) {
                    log((i + 1) + "° posto: " + classifica.get(i));
                }
            }
            
           
            salvaRisultati();
            log("\nRisultati salvati in: classifica.txt");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Il thread principale è stato interrotto.");
        } finally {
            tastiera.close();
        }
    }
    
    /**
     * Salva il contenuto del buffer su file "classifica.txt" nella cartella di lavoro corrente.
     * In caso di errore di I/O, scrive un messaggio su stderr.
     */
    private static void salvaRisultati() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OutputFile))) {
            writer.write(bufferFile.toString());
        } catch (IOException e) {
            System.err.println("Errore nella scrittura del file: " + e.getMessage());
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FileChooser fileChooserComponent = new FileChooser();

        fileChooserComponent.setFileSelectedCallback(selectedFile -> {
            OutputFile = selectedFile;

            // Esempio: chiudo la finestra una volta che ho il file
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(fileChooserComponent);
            if (parent != null) {
                parent.dispose();
            }
        });
        frame.add(fileChooserComponent);
        frame.pack();
        frame.setVisible(true);
    }

}
