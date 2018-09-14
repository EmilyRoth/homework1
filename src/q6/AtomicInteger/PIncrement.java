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
        long time = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(c);

        int total = 1200000;
        int inc = total/numThreads;
        int extra =  total%numThreads;

        //populate list of numThreads
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for(int i = 0; i < numThreads - 1; i++){
            threads.add(new Thread(new PIncrement(count, inc)));
        }
        threads.add(new Thread(new PIncrement(count, inc + extra)));

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

        long timelapsed = System.currentTimeMillis() - time;
        System.out.println("Time for AtomicInteger: " + timelapsed);
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
