package io.github.headless;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class to test different behaviours of the player class
 */
public class PlayerTest {

    //helper method
    private Player createPlayer(float x, float y, int sizeX, int sizeY) {
        MapLayer wallLayer = new MapLayer();
        return new Player(null, x, y, wallLayer, sizeX, sizeY);
    }

    @Test
    public void testPlayerInitialPosition() {
        Player player = createPlayer(100, 200, 32, 32);

        assertEquals(100, player.getPosition().x);
        assertEquals(200, player.getPosition().y);


    }

    @Test
    public void testPlayerCollisionBoxSize() {
        Player player = createPlayer(0,0,32,48);
        Rectangle Collision = player.getCollision();
        assertEquals(32, Collision.width);
        assertEquals(48, Collision.height);
    }

    @Test
    public void testDefaultPlayerSpeed() {
        Player player = createPlayer(0,0,32,32);
        assertEquals(140f,player.getPlayerSpeed());
    }

    @Test
    public void testUpdatePlayerSpeed() {
        Player player = createPlayer(0,0,32,32);
        player.setPlayerSpeed(200f);
        assertEquals(200f,player.getPlayerSpeed());
    }

    @Test
    public void TestPlayerInputInversion() {
        Player player = createPlayer(0,0,32,32);

        player.setInputInverted(true);
        assertTrue(player.isInputInverted());

        player.setInputInverted(false);
        assertFalse(player.isInputInverted());
    }

    @Test
    public void testIsWalkableReturnsFalseOnCollision() {
        MapLayer wallLayer = new MapLayer();

        RectangleMapObject wallObject = new RectangleMapObject(0, 0, 32, 32);

        wallLayer.getObjects().add(wallObject);

        Player player = new Player(null, 0, 0, wallLayer, 32, 32);

        Rectangle testHitbox = new Rectangle(0, 0, 32, 32);

        assertFalse(player.isWalkable(testHitbox));
    }
}
