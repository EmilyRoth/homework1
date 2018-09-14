package q6.Tournament;


public class PIncrement implements Runnable{
    public static int parallelIncrement(int c, int numThreads){
        // your implementation goes here
        long time = System.currentTimeMillis();

        int total = 1200000;
        int inc = total/numThreads;
        int extra =  total%numThreads;


        long timelapsed = System.currentTimeMillis() - time;
        System.out.println("Time for Tournament: " + timelapsed);
        return c;
    }

    @Override
    public void run() {

    }
}
