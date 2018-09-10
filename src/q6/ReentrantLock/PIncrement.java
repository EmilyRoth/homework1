package q6.ReentrantLock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class PIncrement implements Runnable{

    ReentrantLock reentrantLock;
    int numInc;
    Integer count;

    private PIncrement(int count, int c, ReentrantLock rl) {
        this.reentrantLock = rl;
        this.numInc = c;
        this.count = count;
    }

    public static int parallelIncrement(int c, int numThreads){
        // your implementation goes here.

        ReentrantLock rl = new ReentrantLock();

        //covert to AtomicInt
        Integer count = c;

        //populate list of numThreads
        ArrayList<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < numThreads; i++) {
            threads.add(new Thread(new PIncrement(count, 1200000/numThreads, rl)));
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

        return count;
    }

    @Override
    public void run() {
        int c = 0;
        while (c < numInc) {
            if (reentrantLock.tryLock()) {
                reentrantLock.lock();
                c++;
                count = count + 1;
                reentrantLock.unlock();
            }
        }
    }
}
