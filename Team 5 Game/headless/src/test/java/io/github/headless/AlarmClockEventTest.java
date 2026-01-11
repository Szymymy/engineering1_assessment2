package io.github.headless;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.AlarmClockEvent;
import io.github.some_example_name.EventCounter;
import io.github.some_example_name.Player;


@ExtendWith(MockitoExtension.class)
public class AlarmClockEventTest {
    
    @Mock
    EventCounter eventCounter; 
    @Mock
    Player player;

    @Test
    @DisplayName("Testing player position that should trigger a collision")
    void correctCollisionTest() {
        when(player.getPosition()).thenReturn(new Vector2(30, 30));
        AlarmClockEvent alarmClockEvent = new AlarmClockEvent("Event", null, 30, 30, eventCounter);
        Boolean test = alarmClockEvent.checkCollision(player);
        assertTrue(test);
    }

    @Test
    @DisplayName("Testing player posiiton on the edge of the collision radius")
    void edgeCaseCollisionTest() {
        when(player.getPosition()).thenReturn(new Vector2(10,10));
        AlarmClockEvent alarmClockEvent = new AlarmClockEvent("Event", null, 30, 30, eventCounter);
        Boolean test = alarmClockEvent.checkCollision(player);
        assertTrue(test);
    }

    @Test
    @DisplayName("Testing player positiion out of the collision radius")
    void beyondRadiusCollisionTest() {
        when(player.getPosition()).thenReturn(new Vector2(100,100));
        AlarmClockEvent alarmClockEvent = new AlarmClockEvent("Event", null, 30, 30, eventCounter);
        Boolean test = alarmClockEvent.checkCollision(player);
        assertFalse(test);
    }
}


