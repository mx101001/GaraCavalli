import java.util.List;
import java.math.*;

public class Cavallo extends Thread {
    private final String nome;
    private final int distanza;
    private int metriPercorsi = 0;
    private final int passo = 5; 
    private final List<String> classifica;

    public Cavallo(String nome, int distanza, List<String> classifica) {
        this.nome = nome;
        this.distanza = distanza;
        this.classifica = classifica;
    }

    @Override
    public void run() {
        setName(nome); 

        while (metriPercorsi < distanza) {
            metriPercorsi += passo;
            System.out.println(nome + " ha percorso " + metriPercorsi + " metri");

            if (metriPercorsi >= distanza) {
                synchronized (classifica) {
                    classifica.add(nome);
                }
                System.out.println(nome + " ha tagliato il traguardo!");
            }
            azzoppa();
        }
    }

    public void azzoppa(){
        double rand = Math.random();
        int rundem = (int)rand;
        if(rundem % 3 == 1){
            this.interrupt();
        }
    }
}
