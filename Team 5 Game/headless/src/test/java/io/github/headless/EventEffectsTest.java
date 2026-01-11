package io.github.headless;
import com.badlogic.gdx.Game;
import io.github.some_example_name.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class used to test 3 different events
 * 1. doomscroll event
 * 2. dodgytakeaway event
 * 3. speedboost event
 */
public class EventEffectsTest {
    EventCounter eventCounter;
    @BeforeEach
    public void setUp() {
        eventCounter = new EventCounter();
        eventCounter.resetEventsCounter();
    }

    //testing doomscroll event
    @Test
    public void testDoomScrollEvent() {
        DoomScrollEvent doomScrollEvent = new DoomScrollEvent("testclass", null, 1, 1, eventCounter);

        doomScrollEvent.trigger();

        assertEquals(1, eventCounter.getEventsCounter()[2]);
        assertTrue(doomScrollEvent.isTriggered());
    }

    @Test
    public void testdoomscrollventCollision() {
        Player player = new Player(null,1,1,null,1,1);
        DoomScrollEvent doomScrollEvent = new DoomScrollEvent("testclass", null, 1, 1, eventCounter);
        assertTrue(doomScrollEvent.checkCollision(player));

    }

    //test dodgytakeawayevent
    @Test
    void testDodgyTakeawayEvent() {
        DodgyTakeawayEvent dodgyTakeawayEvent = new DodgyTakeawayEvent("testclass", null, 1, 1, eventCounter);

        dodgyTakeawayEvent.trigger();
        assertEquals(1, eventCounter.getEventsCounter()[2]);
        assertTrue(dodgyTakeawayEvent.isTriggered());
    }

    @Test
    public void testDodgyTakeawayCollision() {
        Player player = new Player(null,1,1,null,1,1);
        DodgyTakeawayEvent  dodgyTakeawayEvent = new DodgyTakeawayEvent("testclass", null, 1, 1, eventCounter);
        assertTrue(dodgyTakeawayEvent.checkCollision(player));
    }

    @Test
    public void testDodgyTakeawayTriggerOnlyOnce() {
        DodgyTakeawayEvent dodgyTakeawayEvent = new DodgyTakeawayEvent("testclass", null, 1, 1, eventCounter);

        dodgyTakeawayEvent.trigger();
        dodgyTakeawayEvent.trigger();

        assertEquals(1, eventCounter.getEventsCounter()[2]);
    }

    //testing speed boost event
    @Test
    void testSpeedBoostTrigger() {
        EventCounter counter = new EventCounter();
        SpeedBoostEvent speedBoost =
            new SpeedBoostEvent("SpeedBoost", null, 1, 1, counter);

        speedBoost.trigger();

        assertTrue(speedBoost.isTriggered());
    }

    @Test
    void testSpeedBoostIncrementsPositiveCounter() {
        EventCounter counter = new EventCounter();
        SpeedBoostEvent speedBoost =
            new SpeedBoostEvent("SpeedBoost", null, 1, 1, counter);

        speedBoost.trigger();

        assertEquals(1, counter.getEventsCounter()[0]); // POSITIVE index
    }

    @Test
    void testSpeedBoostCollision() {
        Player player = new Player(null, 1, 1, null, 1, 1);
        SpeedBoostEvent speedBoost =
            new SpeedBoostEvent("SpeedBoost", null, 1, 1, new EventCounter());

        assertTrue(speedBoost.checkCollision(player));
    }
    @Test
    void testSpeedBoostInitialState() {
        SpeedBoostEvent speedBoost =
            new SpeedBoostEvent("SpeedBoost", null, 1, 1, new EventCounter());

        assertFalse(speedBoost.isTriggered());
    }

    @Test
    void testSpeedBoostEffectIncreasesSpeed() {
        Player player = new Player(null, 1, 1, null, 1, 1);

        float originalSpeed = player.getPlayerSpeed();

        player.setPlayerSpeed(originalSpeed * 2);

        assertEquals(originalSpeed * 2, player.getPlayerSpeed());
    }

    @Test
    void testSpeedBoostEffectDecreasesSpeed() {
        Player player = new Player(null, 1, 1, null, 1, 1);

        float originalSpeed = player.getPlayerSpeed();

        player.setPlayerSpeed(originalSpeed / 2);

        assertEquals(originalSpeed / 2, player.getPlayerSpeed());
    }
}
