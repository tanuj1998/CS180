import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tyadav on 11/18/16.
 */
public class Divider implements Runnable {
   private static AtomicInteger counter;

    private int start;
    private int end;

    public Divider(int start, int end){
        counter = new AtomicInteger();
        this.start = start;
        this.end = end;
    }

    @Override
    public void run(){
        for(int i = start; i <= end ; i++){
            if(i%7 == 0){
                counter.incrementAndGet();
            }
        }
    }

    public int getCounter(){
        return counter.get();
    }

    public static void main(String[] args){
        try{
            Divider d1 = new Divider(1,1000);
            Thread t1 = new Thread(d1);
            Thread t2 = new Thread(new Divider(1001,2000));
            Thread t3 = new Thread(new Divider(2001,3000));
            t1.start();
            t2.start();
            t3.start();
            t1.join();
            t2.join();
            t3.join();

            System.out.println(d1.getCounter());
        }
        catch (InterruptedException e){
            System.out.println("BIG Problem");
        }
    }
}
