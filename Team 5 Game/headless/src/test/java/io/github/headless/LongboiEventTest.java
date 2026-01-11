package io.github.headless;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.EventCounter;
import io.github.some_example_name.LongboiCaptureEvent;
import io.github.some_example_name.Player;

@ExtendWith(MockitoExtension.class)
public class LongboiEventTest {
    LongboiCaptureEvent longboiEvent;

    @Mock
    Player player;

    @Test
    @DisplayName("Testing collision for longboi 1")
    void correctCollisionTestOne() {
        when(player.getPosition()).thenReturn(new Vector2(70, 300));
        longboiEvent = new LongboiCaptureEvent("Event", null, null);
        int test = longboiEvent.checkCollision(player);
        assertEquals(0, test);
    }

    @Test
    @DisplayName("Testing collision for longboi 2")
    void correctCollisionTestTwo() {
        when(player.getPosition()).thenReturn(new Vector2(1450, 600));
        longboiEvent = new LongboiCaptureEvent("Event", null, null);
        int test = longboiEvent.checkCollision(player);
        assertEquals(1, test);
    }

    @Test
    @DisplayName("Testing collision for longboi 3")
    void correctCollisionTestThree() {
        when(player.getPosition()).thenReturn(new Vector2(120, 800));
        longboiEvent = new LongboiCaptureEvent("Event", null, null);
        int test = longboiEvent.checkCollision(player);
        assertEquals(2, test);
    }

    @Test
    @DisplayName("Testing double collision for longboi 1")
    void noCollisionTest() {
        when(player.getPosition()).thenReturn(new Vector2(0, 0));
        longboiEvent = new LongboiCaptureEvent("Event", null, null);
        int test = longboiEvent.checkCollision(player);
        test = longboiEvent.checkCollision(player);
        assertEquals(-1, test);
    }

    @Test
    @DisplayName("Testing collecting 1 longboi")
    void collectLongboiOneTest() {
        longboiEvent = new LongboiCaptureEvent("Event", null, null);
        assertFalse(longboiEvent.collectLongboi(0));
    }

    @Test
    @DisplayName("Testing double collecting longboi 1")
    void doubleCollectLongboiOneTest() {
        longboiEvent = new LongboiCaptureEvent("Event", null, null);
        longboiEvent.collectLongboi(0);
        assertFalse(longboiEvent.collectLongboi(0));
    }

    @Test
    @DisplayName("Testing collecting non-existent longboi")
    void nonExistingCollectLongboiTest() {
        longboiEvent = new LongboiCaptureEvent("Event", null, null);
        assertFalse(longboiEvent.collectLongboi(10));
    }
    
    @Test
    @DisplayName("Testing collecting every longboi")
    void collectAllLongboisTest() {
        EventCounter eventCounter = new EventCounter();
        longboiEvent = new LongboiCaptureEvent("Event", null, eventCounter);
        longboiEvent.collectLongboi(0);
        longboiEvent.collectLongboi(1);
        assertTrue(longboiEvent.collectLongboi(2));
    }


}
