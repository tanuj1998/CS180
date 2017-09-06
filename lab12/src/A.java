/**
 * Created by tyadav on 11/18/16.
 */
public class A extends Thread {
    public void run() {
        while (true) {
            System.out.println("A");
        }
    }
    public static void main(String[] args) {
        Thread t = new A();
        t.start();
    }
}


