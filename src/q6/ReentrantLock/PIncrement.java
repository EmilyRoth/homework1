package q6.ReentrantLock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class PIncrement implements Runnable{
    // threads aren't touching the same count variable
    // threads not releasing lock

    private static ReentrantLock reentrantLock = new ReentrantLock();
    int numInc;
    private static volatile Integer count;

    private PIncrement(int count, int c) {
        this.numInc = c;
        this.count = count;
    }

    public static int parallelIncrement(int c, int numThreads){
        //populate list of numThreads
        ArrayList<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < numThreads; i++) {
            threads.add(new Thread(new PIncrement(c, 1200000/numThreads)));
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
        int loopCounter = 0;
        while (loopCounter < numInc) {
            boolean ans = reentrantLock.tryLock();
            if (ans) {
                loopCounter++;
                try{
                    count = count + 1;
                }finally{
                    reentrantLock.unlock();
                }
            }
        }
    }
}