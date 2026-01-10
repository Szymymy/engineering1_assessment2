package io.github.headless;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.github.some_example_name.Points;

@DisplayName("Points Tests")
public class PointsTest {
    private Points points;
    
    @BeforeEach
    void setUp() {
        points = new Points();
    }

    @Test
    @DisplayName("Adding positive integer to points")
    void testAddPosPoints() {
        points.addPoints(100);
        assertEquals(100, points.getBaseScore());
    }

    @Test
    @DisplayName("Adding negative integer to points")
    void testAddNegPoints() {
        points.addPoints(-100);
        assertEquals(0, points.getBaseScore());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000, 33, 26})
    @DisplayName("Subracting positive integer from points")
    void testSubPosPoints(int score) {
        points.subtractPoints(score);
        assertEquals(score, points.getPenalties());
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -100, -1000, -33, -26})
    @DisplayName("Subtracting negative integer from points")
    void testSubNegPoints(int score) {
        points.subtractPoints(score);
        assertEquals(0, points.getPenalties());
    }

    @Test
    @DisplayName("Testing 0 time value for calculating final score")
    void testZeroFinalScoreCalculation() {
        points.calcPoints(0);
        assertEquals(0, points.getScore());
    }

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 25.0, 100.0, 250.0})
    @DisplayName("Testing positive time values for calculating final score")
    void testPosFinalScoreCalculation(double time) {
        points.calcPoints(time);
        assertEquals(time*50, points.getScore());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10.0, -25.0, -100.0, -250.0})
    @DisplayName("Testing negative time values for calculating final score")
    void testNegFinalScoreCalculation(double time) {
        points.calcPoints(time);
        assertEquals(0, points.getScore());
    }

        @ParameterizedTest
    @ValueSource(doubles = {10.0, 25.0, 100.0, 250.0})
    @DisplayName("Testing positive time values for calculating final score with multiplier")
    void testPosMultFinalScoreCalculation(double time) {
        points.setScoreMultiplied(true);
        points.calcPoints(time);
        assertEquals(time*50*1.5, points.getScore());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10.0, -25.0, -100.0, -250.0})
    @DisplayName("Testing negative time values for calculating final score with multiplier")
    void testNegMultFinalScoreCalculation(double time) {
        points.setScoreMultiplied(true);
        points.calcPoints(time);
        assertEquals(0, points.getScore());
    }

    @Test
    @DisplayName("Testing tests")
    void testTest() {
        assertTrue(false);
    }



}
