package io.github.some_example_name;

/**
 * A class that tracks simple achievements based on 
 * how many events have been triggered.
 *
 * Reads counts from EventCounter
 * Unlocks achievements once
 * Applies score changes via Points
 */
public class Achievements {

    private final EventCounter eventCounter;
    private final Points points;
    // thresholds
    private final int POSITIVE_TARGET;
    private final int HIDDEN_TARGET;
    private final int NEGATIVE_TARGET;
    private boolean positiveUnlocked = false;
    private boolean hiddenUnlocked = false;
    private boolean negativeUnlocked = false;
    // Message for on-screen text
    private String lastUnlockedMessage = null;
    
    // Default constructor using default thresholds
    public Achievements(EventCounter eventCounter, Points points) {
        this(eventCounter, points, 3, 3, 5);
    }

    // Allows custom achievement thresholds if needed in future, for possibly adding modes later 
    public Achievements(EventCounter eventCounter, Points points, int positiveTarget, int hiddenTarget, int negativeTarget) {
        this.eventCounter = eventCounter;
        this.points = points;
        this.POSITIVE_TARGET = positiveTarget;
        this.HIDDEN_TARGET = hiddenTarget;
        this.NEGATIVE_TARGET = negativeTarget;
    }

    /**
     * Call after any event is triggered
     */
    public void checkForUnlocks() {
        int[] counts = eventCounter.getEventsCounter(); // [positive, hidden, negative]
        int pos = counts[0];
        int hid = counts[1];
        int neg = counts[2];

        // Example achievements
        if (!positiveUnlocked && pos >= POSITIVE_TARGET) {
            unlockPositive("Achievement unlocked: Winning Streak! +", 200);
            positiveUnlocked = true;
        }

        if (!hiddenUnlocked && hid >= HIDDEN_TARGET) {
            unlockPositive("Achievement unlocked: Secret Finder! +", 200);
            hiddenUnlocked = true;
        }

        if (!negativeUnlocked && neg >= NEGATIVE_TARGET) {
            unlockNegative("Achievement unlocked: Bad Luck! -", 200);
            negativeUnlocked = true;
        }
    }

    /**
     * Returns last achievement message and clears
     */
    public String popLastUnlockedMessage() {
        String msg = lastUnlockedMessage;
        lastUnlockedMessage = null;
        return msg;
    }
    // helpers
    private void unlockPositive(String message, int pointsToAdd) {
        points.addPoints(pointsToAdd);
        lastUnlockedMessage = message + pointsToAdd + " points";
        System.out.println(lastUnlockedMessage);
    }

    private void unlockNegative(String message, int penalty) {
        points.subtractPoints(penalty);
        lastUnlockedMessage = message + penalty + " points";
        System.out.println(lastUnlockedMessage);
    }
}
