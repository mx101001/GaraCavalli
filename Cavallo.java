import java.util.concurrent.CountDownLatch;

/**
 * Rappresenta un cavallo partecipante alla gara.
 * <p>
 * La classe estende {@link Thread} e simula il comportamento di un cavallo che avanza
 * a passi casuali finché non raggiunge il traguardo o viene interrotto (azzoppato).
 * </p>
 *
 * @author Filippo Berti
 * @version 1.0
 */

public class Cavallo extends Thread {
    /** Nome del cavallo (es. "Cavallo1"). */
    private final String nome;
    /** Dimensione del passo in metri (valore casuale tra 1 e 10). */
    private final int passo;
    /** Distanza rimanente al traguardo (in metri). */
    private int distanza;
    /** Segnale usato per avviare simultaneamente tutti i thread dei cavalli. */
    private final CountDownLatch startSignal;

    /**
     * Costruisce un nuovo cavallo.
     *
     * @param nome nome del cavallo (es: "Cavallo1")
     * @param percorsoLength lunghezza del percorso di gara in metri
     * @param startSignal CountDownLatch usato per avviare tutti i thread simultaneamente
     */
    public Cavallo(String nome, int percorsoLength, CountDownLatch startSignal) {
        this.nome = nome;
        this.passo = (int) (Math.random() * 10) + 1;
        this.distanza = percorsoLength;
        this.startSignal = startSignal;
        System.out.println(this.nome + " corre con un passo di: " + this.passo + "m");
    }

    /**
     * Esecuzione del thread del cavallo.
     * <p>
     * Il cavallo attende il segnale di partenza, quindi avanza di un valore pari a {@code passo}
     * ogni 100ms finché la distanza rimanente non è minore o uguale a zero (taglia il traguardo)
     * oppure finché il thread non viene interrotto (cavallo azzoppato).
     * </p>
     * <p>
     * Se il cavallo completa la gara, viene registrato nella classifica tramite
     * {@link GaraCavalli#addToClassifica(String)}.
     * </p>
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        setName(nome);
        try {
            startSignal.await();
            while (distanza > 0 && !Thread.currentThread().isInterrupted()) {
                Thread.sleep(100);
                distanza -= passo;
            }
            if (!Thread.currentThread().isInterrupted() && distanza <= 0) {
                System.out.println(nome + " ha tagliato il traguardo!");
                GaraCavalli.addToClassifica(nome);
            } else {
                System.out.println(nome + " è stato azzoppato durante la corsa.");
            }
        } catch (InterruptedException e) {
            System.out.println(nome + " è stato azzoppato (interrotto).");
            Thread.currentThread().interrupt(); 
        }
    }
}
