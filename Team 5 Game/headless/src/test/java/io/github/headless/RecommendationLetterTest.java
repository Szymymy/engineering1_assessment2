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
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.EventCounter;
import io.github.some_example_name.Player;
import io.github.some_example_name.RecommendationLetterEvent;

public class RecommendationLetterTest {
    private EventCounter counter;
    private Player player;
    private RecommendationLetterEvent event;

    @BeforeEach
    void setUp() {
        counter = mock(EventCounter.class);
        player = mock(Player.class);
        Texture texture = mock(Texture.class);
        event = new RecommendationLetterEvent("teacher",texture,100f,100f,counter);
    }

    @Test
    @DisplayName("Collision returns true when player is inside collision radius")
    void collisionWithinRadius() {
        when(player.getPosition()).thenReturn(new Vector2(105f, 95f));
        boolean result = event.checkCollision(player);
        assertTrue(result);
    }

    @Test
    @DisplayName("Collision returns false when player is outside collision radius")
    void collisionOutsideRadius() {
        when(player.getPosition()).thenReturn(new Vector2(200f, 200f));
        boolean result = event.checkCollision(player);
        assertFalse(result);
    }

    @Test
    @DisplayName("Corner touching does not count as overlap")
    void cornerTouchDoesNotTrigger() {
        when(player.getPosition()).thenReturn(new Vector2(121f, 121f));
        boolean result = event.checkCollision(player);
        assertFalse(result);
        assertFalse(event.isTriggered());
        verify(counter, never()).incrementPosCounter();
}

    @Test
    @DisplayName("Trigger sets event as triggered and increments positive counter")
    void triggerIncrementsPositiveCounter() {
        event.trigger();
        assertTrue(event.isTriggered());
        verify(counter, times(1)).incrementPosCounter();
    }

    @Test
    @DisplayName("Trigger occurs only once")
    void triggerOnlyOnce() {
        event.trigger();
        event.trigger();
        verify(counter, times(1)).incrementPosCounter();
    }

    @Test
    @DisplayName("Responded flag can be set and read")
    void respondedCanBeSet() {
        event.setResponded(true);
        assertTrue(event.hasResponded());
    }
}

