/**
 * Created by tyadav on 11/18/16.
 */
public class CounterDriver
{
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter0();
        Thread inc = new Thread(new IncThread(counter));
        Thread dec = new Thread(new DecThread(counter));
        inc.start();
        dec.start();
        inc.join();
        dec.join();
        System.out.println(counter.get());
    }
}

