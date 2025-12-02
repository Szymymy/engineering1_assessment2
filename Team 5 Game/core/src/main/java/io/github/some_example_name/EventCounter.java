package io.github.some_example_name;

/**
 * Class that is used for tracking the number of triggered events in the game.
 */
public class EventCounter {
    // eventsCounter array holds the count for each event being triggered in the
    // format [pos, hid, neg]
    private static int[] eventsCounter = new int[]{0,0,0};

    public void incrementPosCounter() {
        eventsCounter[0]++;
    }

    public void incrementHidCounter() {
        eventsCounter[1]++;
    }

    public void incrementNegCounter() {
        eventsCounter[2]++;
    }

    public void resetEventsCounter() {
        eventsCounter[0] = 0;
        eventsCounter[1] = 0;
        eventsCounter[2] = 0;
        
    }

    public int[] getEventsCounter() {
        return eventsCounter;
    }
}
