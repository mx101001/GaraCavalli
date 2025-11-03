import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Main {
    private static final ArrayList<String> classifica = new ArrayList<String>();

    public static synchronized void AddToClassifica(String nome){
        classifica.add(nome);
    }

    public static void main(String[] args)  {
        Scanner tastiera = new Scanner(System.in);
        String BufferFile = new String();
        System.out.println("Inserisci la lunghezza del percorso (in metri): ");
        int percorso = tastiera.nextInt();


        ArrayList<Cavallo> cavalli = new ArrayList<Cavallo>();
        CountDownLatch startSignal = new CountDownLatch(1);
        int j = 0 ;
        for(int i = 0;i<5;i++){
            j = i+1;
            cavalli.add(new Cavallo("cavallo"+j,percorso,startSignal));
        }

        cavalli.forEach(cavallo -> {
            cavallo.start();
        });
        startSignal.countDown();



        cavalli.get((int)(Math.random() * cavalli.size())).interrupt();

        cavalli.forEach(cavallo -> {
            try {
                cavallo.join();
            } catch (InterruptedException e) {
                System.out.println("stacktrace:\n");
                        e.printStackTrace();
            }
        });

       
        System.out.println("\n CLASSIFICA ");
        for (int i = 0; i < classifica.size(); i++) {
            System.out.println((i + 1) + "° posto: " + classifica.get(i));
        }


    }


}
