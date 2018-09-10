package q6.Tournament;


public class PIncrement implements Runnable{
    public static int parallelIncrement(int c, int numThreads){
        // your implementation goes here

        int total = 1200000;
        int inc = total/numThreads;
        int extra =  total%numThreads;

        return c;
    }

    @Override
    public void run() {

    }
}
