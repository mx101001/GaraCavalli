import java.io.*;
import java.util.*;

public class Cavaex extends Thread {
    private final String nome;
    private final int distanza;
    private int metriPercorsi = 0;
    private final ArrayList<String> classifica;

    public Cavaex(String nome, int distanza, ArrayList<String> classifica) {
        this.nome = nome;
        this.distanza = distanza;
        this.classifica = classifica;
    }

    @Override
    public void run() {
        String Buffer = new String();
        setName(nome);
        while (metriPercorsi < distanza) {
            if(this.isInterrupted()){
                Buffer += nome+" è stato azzoppato";
                break;
            }
            metriPercorsi += (int)(Math.random() * 10 ) + 1;
            Buffer += nome + " ha percorso " + metriPercorsi + " metri"+ "\n";
            System.out.println(nome + " ha percorso " + metriPercorsi + " metri");

            if (metriPercorsi >= distanza) {
                synchronized (classifica) {
                    classifica.add(nome);
                }
                Buffer += nome +"ha tagliato il traguardo"+ "\n";
                System.out.println(nome + " ha tagliato il traguardo! in ");
            }
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        WriteHistoryToFile(Buffer);
    }

    public void WriteHistoryToFile(String S){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt"));
            writer.write(S);
            writer.close();
        }catch (IOException e ){
            System.out.println("Errore Input Output");
            e.printStackTrace();
        }
    }
}
