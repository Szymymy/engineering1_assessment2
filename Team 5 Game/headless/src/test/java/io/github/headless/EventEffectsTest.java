package io.github.headless;
import io.github.some_example_name.DoomScrollEvent;
import io.github.some_example_name.EventCounter;
import io.github.some_example_name.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventEffectsTest {
    EventCounter eventCounter = new EventCounter();
    @Test
    public void testDoomScrollEvent() {
        DoomScrollEvent doomScrollEvent = new DoomScrollEvent("testclass", null, 1, 1, eventCounter);

        doomScrollEvent.trigger();

        assertEquals(1, eventCounter.getEventsCounter()[2]);
        assertTrue(doomScrollEvent.isTriggered());
    }

    @Test
    public void testEventCollision() {
        Player player = new Player(null,1,1,null,1,1);
        DoomScrollEvent doomScrollEvent = new DoomScrollEvent("testclass", null, 1, 1, eventCounter);
        assertTrue(doomScrollEvent.checkCollision(player));

    }
}
