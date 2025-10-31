import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner tastiera = new Scanner(System.in);
        String BufferFile = new String();
        System.out.println("Inserisci la lunghezza del percorso (in metri): ");
        int percorso = tastiera.nextInt();

        
        ArrayList<String> classifica = new ArrayList<String>();
        ArrayList<Cavallo> cavalli = new ArrayList<Cavallo>();
        int j = 0 ;
        for(int i = 0;i<5;i++){
            j = i+1;
            cavalli.add(new Cavallo("cavallo"+j,percorso,classifica));
        }

        cavalli.forEach(cavallo -> {
            cavallo.run();
        });


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
