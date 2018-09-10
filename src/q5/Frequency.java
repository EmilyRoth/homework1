package q5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;

public class Frequency implements Callable<Integer> {
    // variables
    int[] subArray;
    int input;
    private Frequency(int[] arr, int x){
        subArray = arr;
        input = x;
    }

    // method to compute the number of times x appears in A
    public static int parallelFreq(int x, int[] A, int numThreads){
        //your implementation goes here, return -1 if the input is not valid.
        ExecutorService threads = Executors.newFixedThreadPool(numThreads);
        ArrayList<Future<Integer>> futures = new ArrayList<Future<Integer>>();

        // create the threads
        int sizes[] = new int[numThreads];
        int num = A.length/numThreads;
        int rem = A.length%numThreads;

        for(int i = 0; i < rem; i++){
            sizes[i] = num +1;
        }
        for(int i = rem; i < numThreads; i++) {
            sizes[i] = num;
        }

        int elements = 0;
        for(int i = 0; i<numThreads; i++){
            int[] subArr = new int[sizes[i]];
            subArr = Arrays.copyOfRange(A, elements, elements += sizes[i]);
            Future<Integer> future = threads.submit(new Frequency(subArr, x));
            futures.add(future);
        }

        int sum = 0;
        for(Future<Integer>  fut : futures) {
            try {
                sum += fut.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return -1;
            } catch (ExecutionException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return sum;
    }

    @Override
    public Integer call() throws Exception {
        // look at the subarray
        int counter = 0;
        for(int i = 0; i < subArray.length; i++){
            if(subArray[i] == input){
                counter++;
            }
        }
        return counter;
    }
}