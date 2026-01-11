package io.github.headless;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.some_example_name.Question;

public class QuestionTest {
    private Question question;

    @BeforeEach
    void setUp() {
        question = new Question("What is 2 + 2?", "3", "4", "5", "6", 1);
    }

    @Test
    @DisplayName("getOptions returns all four options in correct order")
    void getOptionsReturnCorrectArray() {
        String[] options = question.getOptions();
        assertArrayEquals(new String[]{"3", "4", "5","6"}, options);
    }

    @Test
    @DisplayName("isCorrect returns false for incorrect answer")
    void isCorrectReturnsFalseForIncorrectAnswer() {
        assertFalse(question.isCorrect(0));
        assertFalse(question.isCorrect(2));
        assertFalse(question.isCorrect(3));
    }

}