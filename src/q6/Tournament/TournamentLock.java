package q6.Tournament;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class TournamentLock implements Lock {

    static boolean wantCS[] = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    static int pid_tracker[] = {8,8,8,8,8,8,8,0,1,2,3,4,5,6,7};
    static int turn[] = {0, 0, 0, 0, 0, 0, 0};

    int originalPlace;
    int pid;
    int place;


    public TournamentLock(int numThreads){
        switch (numThreads) {
            case 0: this.originalPlace = 7; this.place = 7; pid = numThreads;
                break;
            case 1: this.originalPlace = 8; this.place = 8; pid = numThreads;
                break;
            case 2: this.originalPlace = 9; this.place = 9; pid = numThreads;
                break;
            case 3: this.originalPlace = 10; this.place = 10; pid = numThreads;
                break;
            case 4: this.originalPlace = 11; this.place = 11; pid = numThreads;
                break;
            case 5: this.originalPlace = 12; this.place = 12; pid = numThreads;
                break;
            case 6: this.originalPlace = 13; this.place = 13; pid = numThreads;
                break;
            case 7: this.originalPlace = 14; this.place = 14; pid = numThreads;
                break;
        }
    }

    @Override
    public void lock(int pid) {
        // level 0
        int counterpart_place = toCounterpart(place);
        wantCS[place] = true;
        turn[toDown(place)] = pid_tracker[counterpart_place];
        while(wantCS[counterpart_place] && (turn[toDown(place)] == pid_tracker[counterpart_place]));

        pid_tracker[place] = 8;
        place = toDown(place);
        pid_tracker[place] = pid;

        // level 1
        counterpart_place = toCounterpart(place);
        wantCS[place] = true;
        turn[toDown(place)] = pid_tracker[counterpart_place];
        while(wantCS[counterpart_place] && (turn[toDown(place)] == pid_tracker[counterpart_place]));

        pid_tracker[place] = 8;
        place = toDown(place);
        pid_tracker[place] = pid;

        // level 2
        counterpart_place = toCounterpart(place);
        wantCS[place] = true;
        turn[toDown(place)] = pid_tracker[counterpart_place];
        while(wantCS[counterpart_place] && (turn[toDown(place)] == pid_tracker[counterpart_place]));

        pid_tracker[place] = 8;
        place = toDown(place);
        pid_tracker[place] = pid;

        // win
        System.out.println("won!" + pid);
    }

    private static int toCounterpart(int i) {
        return (i%2 == 1) ? i + 1 : i - 1;
    }

    private static int toDown(int i) {
        return (i - 1) / 2;
    }

    @Override
    public void unlock(int pid) {
        pid_tracker[originalPlace] = pid;
        pid_tracker[place] = 8;
    }
}
