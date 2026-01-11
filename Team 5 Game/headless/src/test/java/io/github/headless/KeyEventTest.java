package io.github.headless;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Rectangle;

import io.github.some_example_name.KeyEvent;

public class KeyEventTest {

    KeyEvent keyEvent;

    @BeforeEach
    void setUp() {
        Rectangle hitbox = new Rectangle(30,30,10,10);
        keyEvent = new KeyEvent("Event", hitbox, null, null);
    }
    
    @Test
    @DisplayName("Test collision with overlap")
    void correctCollisionTest() {
        Rectangle player = new Rectangle(30, 30, 10, 10);
        assertTrue(keyEvent.collides(player));
    }

    @Test
    @DisplayName("Test collision on boundary")
    void boundaryCollisionTest() {
        Rectangle player = new Rectangle(20, 30, 10, 10);
        assertFalse(keyEvent.collides(player));
    }

    @Test
    @DisplayName("Test collision with no overlap")
    void noCollisionTest() {
        Rectangle player = new Rectangle(500, 500, 1, 1);
        assertFalse(keyEvent.collides(player));
    }
}
