import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tyadav on 11/18/16.
 */
public class Counter2 implements Counter {
    private static AtomicInteger counter;
    private int value = 0;
    @Override
    public void inc() {
        counter.getAndIncrement();
    }

    @Override
    public void dec() {
        counter.getAndDecrement();
    }

    @Override
    public int get(){
        return counter.get();

        }


}


