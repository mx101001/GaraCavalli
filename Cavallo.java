import java.util.concurrent.CountDownLatch;

public class Cavallo extends Thread {
    private final String Nome;
    private final int Passo;
    private int distanza;
    private final CountDownLatch startSignal;

    public Cavallo(String nome, int PercorsoLength, CountDownLatch startSignal) {
        this.Nome = nome;
        this.Passo = (int) (Math.random() * 10) + 1;
        this.distanza = PercorsoLength;
        this.startSignal = startSignal;
        System.out.println(this.Nome + " corre con un passo di:" + this.Passo + "m");
    }

    @Override
    public void run() {
        setName(Nome);

        try {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(this.Nome + " è stato azzoppato prima della partenza.");
                return;
            }

            startSignal.await();

            while (distanza > 0 && !Thread.currentThread().isInterrupted()) {
                Thread.sleep(100);
                distanza -= Passo;
            }

            if (Thread.currentThread().isInterrupted()) {
                System.out.println(this.Nome + " è stato azzoppato durante la corsa.");
            } else if (distanza <= 0) {
                Main.AddToClassifica(this.Nome);
            }
        } catch (InterruptedException e) {
            System.out.println(this.Nome + " è stato azzoppato (interrotto durante l'attesa o la corsa).");
            Thread.currentThread().interrupt();
        }
    }
}