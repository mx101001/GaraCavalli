import java.util.*;

public class Cavallo extends Thread {
    private final String nome;
    private final int distanza;
    private int metriPercorsi = 0;
    private final ArrayList<String> classifica;

    public Cavallo(String nome, int distanza, ArrayList<String> classifica) {
        this.nome = nome;
        this.distanza = distanza;
        this.classifica = classifica;
    }

    @Override
    public void run() {
        setName(nome);
        while (metriPercorsi < distanza) {
            if(this.isInterrupted()){
                break;
            }
            metriPercorsi += (int)(Math.random() * 10 ) + 1;
            System.out.println(nome + " ha percorso " + metriPercorsi + " metri");

            if (metriPercorsi >= distanza) {
                synchronized (classifica) {
                    classifica.add(nome);
                }
                System.out.println(nome + " ha tagliato il traguardo! in ");
            }
        }
    }
}
