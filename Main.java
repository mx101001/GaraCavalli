import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    private static final List<String> classifica = new CopyOnWriteArrayList<>();
    private static final StringBuilder bufferFile = new StringBuilder();
    
    public static void addToClassifica(String nome) {
        classifica.add(nome);
    }
    
    private static void log(String message) {
        System.out.println(message);
        bufferFile.append(message).append("\n");
    }
    
    public static void main(String[] args) {
        Scanner tastiera = new Scanner(System.in);
        
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
    
    private static void salvaRisultati() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("classifica.txt"))) {
            writer.write(bufferFile.toString());
        } catch (IOException e) {
            System.err.println("Errore nella scrittura del file: " + e.getMessage());
        }
    }
}
