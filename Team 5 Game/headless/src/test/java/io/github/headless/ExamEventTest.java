package io.github.headless;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.math.Rectangle;

import io.github.some_example_name.EventCounter;
import io.github.some_example_name.ExamEvent;

public class ExamEventTest {
    private EventCounter counter;
    private ExamEvent examEvent;
    private Rectangle zone;

    @BeforeEach
    void setUp() {
        counter = mock(EventCounter.class);
        zone = new Rectangle(0, 0, 10, 10);
        examEvent = new ExamEvent("Exam", zone, counter);
    }

    @Test
    @DisplayName("Exam triggers when player overlaps zone and becomes active")
    void triggersOnOverlapAndBecomesActive() {
        Rectangle player = new Rectangle(0, 0, 5, 5);
        boolean result = examEvent.checkTrigger(player);
        assertTrue(result);
        assertTrue(examEvent.isTriggered());
        assertTrue(examEvent.isExamActive());
        verify(counter, times(1)).incrementHidCounter();
    }

    @Test
    @DisplayName("Exam does not trigger when player does not overlap zone")
    void doesNotTriggerWhenNoOverlap() {
        Rectangle player = new Rectangle(50, 50, 5, 5);
        boolean result = examEvent.checkTrigger(player);
        assertFalse(result);
        assertFalse(examEvent.isTriggered());
        assertFalse(examEvent.isExamActive());
    }

    @Test
    @DisplayName("Exam only triggers once even if player overlaps multiple times")
    void triggersOnlyOnce() {
        Rectangle player = new Rectangle(0, 0, 5, 5);
        assertTrue(examEvent.checkTrigger(player));
        assertFalse(examEvent.checkTrigger(player));
        verify(counter, times(1)).incrementHidCounter();
    }

    @Test
    @DisplayName("getQuestions returns 5 initialized questions")
    void hasFiveQuestions() {
        assertEquals(5, examEvent.getQuestions().size());
    }

}
