import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner tastiera = new Scanner(System.in);

        System.out.println("Inserisci la lunghezza del percorso (in metri): ");
        int percorso = tastiera.nextInt();

        
        List<String> classifica = new ArrayList<>();

       
        Cavallo c1 = new Cavallo("cavallo1", percorso, classifica);
        Cavallo c2 = new Cavallo("cavallo2", percorso, classifica);
        Cavallo c3 = new Cavallo("cavallo3", percorso, classifica);
        Cavallo c4 = new Cavallo("cavallo4", percorso, classifica);
        Cavallo c5 = new Cavallo("cavallo5", percorso, classifica);

       
        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();

       
        try {
            c1.join();
            c2.join();
            c3.join();
            c4.join();
            c5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       
        System.out.println("\n CLASSIFICA ");
        for (int i = 0; i < classifica.size(); i++) {
            System.out.println((i + 1) + "° posto: " + classifica.get(i));
        }
    }
}
