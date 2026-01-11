package io.github.headless;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.EventCounter;
import io.github.some_example_name.Player;
import io.github.some_example_name.SpeedBoostEvent;


public class SpeedBoostTest {
    private EventCounter counter;
    private Player player;
    private SpeedBoostEvent event;

    @BeforeEach
    void setUp() {
        counter = mock(EventCounter.class);
        player = mock(Player.class);
        Texture texture = mock(Texture.class);
        event = new SpeedBoostEvent("speedboost", texture, 100f, 100f, counter);
    }

    @Test
    @DisplayName("Collision returns true when player is within collision radius")
    void collisionWithinRadius() {
        when(player.getPosition()).thenReturn(new Vector2(110f, 90f));
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
    @DisplayName("Corner touching just outside radius does not count as collision")
    void cornerTouchDoesNotCollide() {
        when(player.getPosition()).thenReturn(new Vector2(121f, 121f));
        boolean result = event.checkCollision(player);
        assertFalse(result);
        verify(counter, never()).incrementPosCounter();
    }

    @Test
    @DisplayName("Deactivate sets triggered to false")
    void deactivateClearsTriggered() {
        event.trigger();
        assertTrue(event.isTriggered());
        event.deactivate();
        assertFalse(event.isTriggered());
        assertFalse(event.getActive());
    }
}
