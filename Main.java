import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner tastiera = new Scanner(System.in);

        System.out.println("Inserisci la lunghezza del percorso (in metri): ");
        int percorso = tastiera.nextInt();

        
        ArrayList<String> classifica = new ArrayList<String>();
        ArrayList<Cavallo> cavalli = new ArrayList<Cavallo>();

        for(int i = 0;i<5;i++){
            cavalli.add(new Cavallo("cavallo"+i,percorso,classifica));
        }

        cavalli.forEach(cavallo -> {
            cavallo.run();
        });

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
