package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class EduroamEvent implements Event{

    private String name;
    private boolean isTriggered;
    private Texture texture;
    private Sprite wifiSprite;
    private Vector2 position;
    private float collisionRadius;
    private EventCounter eventCounter;

    public EduroamEvent (String name, Texture texture, float xPosition, float yPosition, EventCounter eventCounter) {
        this.name = name;
        this.isTriggered = false;
        this.texture = texture;
        this.wifiSprite = new Sprite(texture);
        this.wifiSprite.setSize(70f, 70f);
        this.position = new Vector2(xPosition, yPosition);
        this.eventCounter = eventCounter;
        this.collisionRadius = 20f;
    }

    public boolean checkCollision(Player player) {
        Vector2 playerPosition = player.getPosition();
        if (Math.abs(playerPosition.x - position.x) <= collisionRadius && Math.abs(playerPosition.y - position.y) <= collisionRadius) {
            return true;
        }
        return false;
    }

    public void draw(SpriteBatch batch) {
        wifiSprite.setPosition(position.x, position.y);
        wifiSprite.draw(batch);
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    @Override
    public void trigger() {
        if (!isTriggered) {
            setTriggered(true);
            eventCounter.incrementNegCounter();
        }
    }

    // Interface methods
    @Override public String getName() { return name; }
    @Override public void setName(String name) { this.name = name; }
    @Override public boolean isTriggered() { return isTriggered; }
    @Override public void setTriggered(boolean triggered) { this.isTriggered = triggered; }
}
