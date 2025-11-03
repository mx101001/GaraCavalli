import java.util.concurrent.CountDownLatch;

public class Cavallo extends Thread {
    private final String nome;
    private final int passo;
    private volatile int distanza;
    private final CountDownLatch startSignal;

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
                Main.AddToClassifica(nome);
            } else {
                System.out.println(nome + " è stato azzoppato durante la corsa.");
            }
            
        } catch (InterruptedException e) {
            System.out.println(nome + " è stato azzoppato (interrotto).");
            Thread.currentThread().interrupt(); 
        }
    }
    
    
    public int getDistanza() {
        return distanza;
    }
    
    public String getNomeCavallo() {
        return nome;
    }
}
