package q6.Synchronized;

import java.util.ArrayList;

public class PIncrement implements Runnable{

    Integer count;
    int numInc;

    private PIncrement(Integer count, int numInc){
        this.count = count;
        this.numInc = numInc;
    }

    public static int parallelIncrement(int c, int numThreads){
        // your implementation goes here.
        Integer count = c;

        ArrayList<Thread> threads = new ArrayList<Thread>();
        for(int i = 0; i < numThreads; i++){
            threads.add(new Thread(new PIncrement(count, 1200000/numThreads)));
        }

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

        // TODO why are we getting 0?
        return count.intValue();
    }

    @Override
    public void run() {
        for(int i = 0; i < numInc; i++){
            synchronized (count){
                count = count + 1;
            }
        }
    }

    public static void main(String[] args) {
        System.out.print(parallelIncrement(0, 8));
    }
}
