package io.github.headless;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.some_example_name.EventCounter;

public class EventCounterTest {
    EventCounter eventCounter;

    @BeforeEach
    private void setUp() {
        eventCounter = new EventCounter();
    }

    @Test
    @DisplayName("Testing adding to counter")
    void AddTest() {
        eventCounter.incrementPosCounter();
        eventCounter.incrementHidCounter();
        eventCounter.incrementNegCounter();
        int[] expectedArray = {1,1,1};
        assertArrayEquals(expectedArray, eventCounter.getEventsCounter());
    }

    
    @Test
    @DisplayName("Testing resetting counter")
    void ResetTest() {
        eventCounter.incrementPosCounter();
        eventCounter.incrementHidCounter();
        eventCounter.incrementNegCounter();
        eventCounter.resetEventsCounter();
        int[] expectedArray = {0,0,0};
        assertArrayEquals(expectedArray, eventCounter.getEventsCounter());
    }
}
