package io.github.headless;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.math.Rectangle;

import io.github.some_example_name.Door;
import io.github.some_example_name.EventCounter;
import io.github.some_example_name.TripwireEvent;


public class TripwireEventTest {
    private Door door;
    private EventCounter counter;
    private TripwireEvent tripwire;

    @BeforeEach
    void setUp() {
        door = mock(Door.class);
        counter = mock(EventCounter.class);
        Rectangle zone = new Rectangle(0, 0, 10, 10);
        tripwire = new TripwireEvent("Tripwire", zone, door, counter);
    }

    @Test
    @DisplayName("Tripwire triggers when player overlaps and locks door")
    void triggersOnOverlap() {
        Rectangle player = new Rectangle(0, 0, 5, 10);
        boolean result = tripwire.checkTrigger(player);
        assertTrue(result);
        assertTrue(tripwire.isTriggered());
    }

    @Test
    @DisplayName("Tripwire does not trigger when the player doesn't overlap")
    void doesNotTriggerWhenNoOverlap() {
        Rectangle player = new Rectangle(50, 50, 5, 10);
        boolean result = tripwire.checkTrigger(player);
        assertFalse(tripwire.isTriggered());
        assertFalse(result);
    }

    @Test
    @DisplayName("Calling trigger() directly locks the door and increments the counter")
    void triggerLocksDoorAndIncrementsCounter() {
        tripwire.trigger();
        assertTrue(tripwire.isTriggered());
        verify(door, times(1)).lock();
        verify(counter, times(1)).incrementNegCounter();
    }

    @Test
    @DisplayName("Corner touching does not count as overlap")
    void cornerTouchDoesNotTrigger() {
        Rectangle player = new Rectangle(10, 10, 5, 10);
        boolean result = tripwire.checkTrigger(player);
        assertFalse(result);
        assertFalse(tripwire.isTriggered());
        verify(door, never()).lock();
        verify(counter, never()).incrementNegCounter();
    }

    @Test
    @DisplayName("Tripwire only triggers once even if the player overlaps multiple times")
    void overlapMultipleTimesTriggersOnlyOnce() {
        Rectangle player = new Rectangle(0, 0, 5, 10);
        assertTrue(tripwire.checkTrigger(player));
        assertTrue(tripwire.checkTrigger(player));
        verify(door, times(1)).lock();
        verify(counter, times(1)).incrementNegCounter();
    }
}

