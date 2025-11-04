import java.util.concurrent.CountDownLatch;

/**Classe Cavallo che estende la Classe thread per permettere la simulazione di una gara di cavalli
 * @author Filippo Berti
 * */

public class Cavallo extends Thread {
    private final String nome;
    private final int passo;
    private int distanza;
    private final CountDownLatch startSignal;

    /**
     * @param nome nome del cavallo es: "Cavallo1"
     * @param percorsoLength lunghezza del percorso di gara
     * @param startSignal CountDownLatch usato per avviare tutti i thread simultaneamente
     */
    public Cavallo(String nome, int percorsoLength, CountDownLatch startSignal) {
        this.nome = nome;
        this.passo = (int) (Math.random() * 10) + 1;
        this.distanza = percorsoLength;
        this.startSignal = startSignal;
        System.out.println(this.nome + " corre con un passo di: " + this.passo + "m");
    }

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
