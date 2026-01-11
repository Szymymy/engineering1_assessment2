package io.github.headless;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.github.some_example_name.Dean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
* class to test the behaviours of the dean class
*/
public class DeanTest {

    //helper method
    private Dean createDean(float x, float y,int sizeX, int sizeY) {
        Array<TiledMapTileLayer> testlayers = new Array<>();
        MapLayer testWallLayer = new MapLayer();
        return new Dean(null,x,y,testlayers,testWallLayer,0,0,425f, 425f, sizeX, sizeY);

    }

    @Test
    public void testDeanInitialPosition() {
        Dean dean = createDean(100,100,32,32);

        assertEquals(100,dean.getPosition().x);
        assertEquals(100,dean.getPosition().y);

    }

    @Test
    public void testDeanCollisionBoxSize() {
        Dean dean = createDean(100,100,52,82);
        Rectangle collision = dean.getCollision();
        assertEquals(52,collision.width);
        assertEquals(82,collision.height);
    }

}
