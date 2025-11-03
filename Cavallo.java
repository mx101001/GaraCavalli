import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Cavallo extends Thread{
    private final String Nome;
    private final int Passo;
    private int distanza;
    private final CountDownLatch startSignal;
    //----------------------------//
    private int metriPercorsi = 0;
    //----------------------------//
    public Cavallo(String nome, int PercorsoLength,CountDownLatch startSignal){
        this.Nome = nome;
        this.Passo = (int)(Math.random() * 10) +1 ;
        this.distanza = PercorsoLength;
        this.startSignal = startSignal;
        System.out.println(this.Nome+" corre con un passo di:"+this.Passo+"m");
    }

    @Override
    public void run(){
        setName(Nome);
        if(this.isInterrupted()){
            return;
        }
        try {
            startSignal.await();
        while (!this.isInterrupted()){
            if(distanza <= 0){
                Main.AddToClassifica(this.Nome);
                return;
            }else{
                distanza -= Passo;
            }
        }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


















    public String GetNome(){
        return this.Nome;
    }

}
