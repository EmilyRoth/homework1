package q6.Synchronized;

import java.util.ArrayList;

public class PIncrement implements Runnable{

    volatile static Integer count;
    int numInc;

    private PIncrement(Integer count, int numInc){
        this.count = count;
        this.numInc = numInc;
    }

    public static int parallelIncrement(int c, int numThreads){
        // your implementation goes here.
        Integer count = c;

        int total = 1200000;
        int inc = total/numThreads;
        int extra =  total%numThreads;

        ArrayList<Thread> threads = new ArrayList<Thread>();
        for(int i = 0; i < numThreads - 1; i++){
            threads.add(new Thread(new PIncrement(count, inc)));
        }
        threads.add(new Thread(new PIncrement(count, inc + extra)));

        //run
        for(Thread t : threads){
            t.start();
        }
        for(Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return -1;
            }
        }

        return PIncrement.count;
    }

    @Override
    public void run() {
        for(int i = 0; i < numInc; i++){
            yay();
        }
    }

    public synchronized static void yay() {
        count += 1;
    }
}
