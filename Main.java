import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    private static final List<String> classifica = new CopyOnWriteArrayList<>();

    public static void AddToClassifica(String nome) {
        classifica.add(nome);
    }

    public static void main(String[] args) {
        Scanner tastiera = new Scanner(System.in);
        String BufferFile = new String();
        System.out.println("Inserisci la lunghezza del percorso (in metri): ");
        int percorso = tastiera.nextInt();

        List<Cavallo> cavalli = new ArrayList<>();
        CountDownLatch startSignal = new CountDownLatch(1);
        int j;
        for (int i = 0; i < 5; i++) {
            j = i + 1;
            cavalli.add(new Cavallo("cavallo" + j, percorso, startSignal));
        }

        cavalli.forEach(Cavallo::start);

        startSignal.countDown();

        cavalli.get((int) (Math.random() * cavalli.size())).interrupt();

        for (Cavallo cavallo : cavalli) {
            try {
                cavallo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Il thread principale è stato interrotto.");
            }
        }

        BufferFile += "\n CLASSIFICA ";
        System.out.println("\n CLASSIFICA ");
        for (int i = 0; i < classifica.size(); i++) {
            BufferFile += "\n" + (i + 1) + "° posto: " + classifica.get(i);
            System.out.println((i + 1) + "° posto: " + classifica.get(i));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("classifica.txt"))) {
            writer.write(BufferFile);
        } catch (IOException e) {
            System.err.println("Errore nella scrittura del file: " + e.getMessage());
        }
    }
}