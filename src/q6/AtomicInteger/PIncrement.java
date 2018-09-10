package q6.AtomicInteger;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PIncrement implements Runnable{

    AtomicInteger count;
    int numInc;

    private PIncrement(AtomicInteger count, int numInc) {
        this.count = count;
        this.numInc = numInc;
    }

    public static int parallelIncrement(int c, int numThreads){
        // your implementation goes here.
        //covert to AtomicInt
        AtomicInteger count = new AtomicInteger(c);

        //populate list of numThreads
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < numThreads; i++) {
            threads.add(new Thread(new PIncrement(count, 1200000/numThreads)));
        }

        //run
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return count.intValue();
    }

    @Override
    public void run() {
        for (int i = 0; i < numInc; i++) {
            synchronized (count) { // TODO is this ok?
                count.compareAndSet(count.get(), count.get() + 1);
            }
        }
    }
}
