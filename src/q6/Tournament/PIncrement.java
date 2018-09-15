package q6.Tournament;


import java.util.ArrayList;

public class PIncrement implements Runnable{
    private int numInc;
    private static volatile  Integer count;
    private int pid;

    private PIncrement(int count, int c, int pid) {
        this.numInc = c;
        this.count = count;
        this.pid = pid;
    }

    public static int parallelIncrement(int c, int numThreads){
        // your implementation goes here
        //populate list of numThreads
        long time = System.currentTimeMillis();
        ArrayList<Thread> threads = new ArrayList<Thread>();

        int total = 1200000;
        int inc = total/numThreads;
        int extra =  total%numThreads;

        int pid = 0;

        for(int i = 0; i < numThreads - 1; i++){
            threads.add(new Thread(new PIncrement(c, inc, pid++)));
        }
        threads.add(new Thread(new PIncrement(c, inc + extra, pid++)));

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
        System.out.println("Time for Tournament: " + timelapsed);
        return c;
    }

    @Override
    public void run() {
        TournamentLock tLock = new TournamentLock(pid);
        int loopCounter = 0;
        while (loopCounter < numInc) {
            tLock.lock(pid);
            count = count + 1;
            tLock.unlock(pid);
        }

    }
}
