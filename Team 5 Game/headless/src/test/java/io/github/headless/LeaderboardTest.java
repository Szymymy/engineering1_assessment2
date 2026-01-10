package io.github.headless;

import io.github.some_example_name.Leaderboard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LeaderboardTest {
    @Test
    public void inputTest() {
        Leaderboard l = new Leaderboard();
        String[] expectedL = {"Szymy&100","null&0","null&0","null&0","null&0"};
        l.updateLeaderboard(100, "Szymy");
        assertEquals(expectedL, l.getLeaderboard());
    }
}
