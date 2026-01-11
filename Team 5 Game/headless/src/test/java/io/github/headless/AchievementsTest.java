package io.github.headless;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.some_example_name.Achievements;
import io.github.some_example_name.EventCounter;
import io.github.some_example_name.Points;

public class AchievementsTest {
    private EventCounter eventCounter;
    private Points points;
    private Achievements achievements;

    @BeforeEach
    void setUp() {
        eventCounter = mock(EventCounter.class);
        points = mock(Points.class);
        achievements = new Achievements(eventCounter, points);
    }
    
    @Test
    @DisplayName("No unlock occurs when all counters are below thresholds")
    void noUnlockWhenBelowThresholds() {
        when(eventCounter.getEventsCounter()).thenReturn(new int[] {2, 2, 4});
        achievements.checkForUnlocks();
        verify(points, never()).addPoints(anyInt());
        verify(points, never()).subtractPoints(anyInt());
        assertNull(achievements.popLastUnlockedMessage());
    }

    @Test
    @DisplayName("Positive achievement unlocks at threshold and gives 200 points")
    void positiveUnlocksAtThreshold() {
        when(eventCounter.getEventsCounter()).thenReturn(new int[] {3, 0, 0});
        achievements.checkForUnlocks();
        verify(points, times(1)).addPoints(200);
        String msg = achievements.popLastUnlockedMessage();
        assertEquals("Achievement unlocked: Winning Streak! +200 points", msg);
    }

    @Test
    @DisplayName("Hidden achievement unlocks at threshold and gives 200 points")
    void hiddenUnlocksAtThreshold() {
        when(eventCounter.getEventsCounter()).thenReturn(new int[] {0, 3, 0});
        achievements.checkForUnlocks();
        verify(points, times(1)).addPoints(200);
        String msg = achievements.popLastUnlockedMessage();
        assertEquals("Achievement unlocked: Secret Finder! +200 points", msg);
    }
    
    @Test
    @DisplayName("Negative achievement unlocks at threshold and subtracts 200 points")
    void negativeUnlocksAtThreshold() {
        when(eventCounter.getEventsCounter()).thenReturn(new int[] {0, 0, 5});
        achievements.checkForUnlocks();
        verify(points, times(1)).subtractPoints(200);
        String msg = achievements.popLastUnlockedMessage();
        assertEquals("Achievement unlocked: Bad Luck! -200 points", msg);
    }

    @Test
    @DisplayName("Custom thresholds: unlock uses provided targets")
    void customThresholdsWorks() {
        Achievements custom = new Achievements(eventCounter, points, 1, 2, 1);
        when(eventCounter.getEventsCounter()).thenReturn(new int[] {1, 2, 1});
        custom.checkForUnlocks();
        verify(points, times(2)).addPoints(200);
        verify(points, times(1)).subtractPoints(200);
    }

    @Test
    @DisplayName("Positive unlock only applies once even if called multiple times")
    void positiveUnlockOnlyOnce() {
        when(eventCounter.getEventsCounter()).thenReturn(new int[] {3, 0, 0});
        achievements.checkForUnlocks();
        achievements.checkForUnlocks();
        verify(points, times(1)).addPoints(200);
    }

    @Test
    @DisplayName("Negative unlock only applies once even if called multiple times")
    void negativeUnlockOnlyOnce() {
        when(eventCounter.getEventsCounter()).thenReturn(new int[] {0, 0, 5});
        achievements.checkForUnlocks();
        achievements.checkForUnlocks();
        verify(points, times(1)).subtractPoints(200);
    }

    @Test
    @DisplayName("Hidden unlock only applies once even if called multiple times")
    void hiddenUnlockOnlyOnce() {
        when(eventCounter.getEventsCounter()).thenReturn(new int[] {0, 3, 0});
        achievements.checkForUnlocks();
        achievements.checkForUnlocks();
        verify(points, times(1)).addPoints(200);
    }

}
