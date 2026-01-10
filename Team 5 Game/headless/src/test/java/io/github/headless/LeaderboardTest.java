package io.github.headless;

import io.github.some_example_name.Leaderboard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

public class LeaderboardTest {
    private Leaderboard leaderboard;

    @Test
    @DisplayName("Reading from correct file")
    void loadTestCorrectFile() {
        leaderboard = new Leaderboard("src/test/java/io/github/Test data/leaderboardCorrect.txt");
        String[] expectedLeaderboard = {"Bart&10000", "Lisa&9000", "Maggie&6000", "Marge&5000", "Homer&1000"};
        assertArrayEquals(expectedLeaderboard, leaderboard.getLeaderboard());
    }

    @Test
    @DisplayName("Reading from file with too little elements")
    void loadTestTooLittle() {
        leaderboard = new Leaderboard("src/test/java/io/github/Test data/leaderboardTooLittle.txt");
        assertThrows(IllegalArgumentException.class, () -> { leaderboard.getLeaderboard();});
    }

    @Test
    @DisplayName("Reading from file with too many elements")
    void loadTestTooMany() {
        leaderboard = new Leaderboard("src/test/java/io/github/Test data/leaderboardTooMany.txt");
        assertThrows(IllegalArgumentException.class, () -> { leaderboard.getLeaderboard();});
    }

    @Test
    @DisplayName("Reading from empty file")
    void loadTestEmpty() {
        leaderboard = new Leaderboard("src/test/java/io/github/Test data/leaderboardEmpty.txt");
        assertThrows(IllegalArgumentException.class, () -> { leaderboard.getLeaderboard();});
    }

    @Test
    @DisplayName("Clearing a leaderboard after usage")
    void clearTest() {
        leaderboard = new Leaderboard("src/test/java/io/github/Test data/leaderboardClear.txt");
        leaderboard.updateLeaderboard(1000, "Test", "src/test/java/io/github/Test data/leaderboardClear.txt");
        leaderboard.clearLeaderboard("src/test/java/io/github/Test data/leaderboardClear.txt");
        String[] test = new String[] {"null&0", "null&0", "null&0", "null&0", "null&0"};
        assertArrayEquals(test, leaderboard.getLeaderboard());
    }


}