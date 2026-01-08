package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * DoomScrollEvent represents an in game event where the player
 * encounters a phone and triggers a negative outcome.
 * The event can only be triggered once in the game
 */
public class DoomScrollEvent implements Event{
    private String name;
    private boolean isTriggered;
    private Texture texture;
    private Sprite phoneSprite;
    private Vector2 position;
    private float collisionRadius;
    private EventCounter eventCounter;


    public DoomScrollEvent (String name, Texture texture, float xPosition, float yPosition, EventCounter eventCounter) {
        this.name = name;
        this.isTriggered = false;
        this.texture = texture;
        this.phoneSprite = new Sprite(texture);
        this.phoneSprite.setSize(60f,70f);
        this.position = new Vector2(xPosition, yPosition);
        this.collisionRadius = 20f;
        this.eventCounter = eventCounter;
    }

    public boolean checkCollision(Player player) {
        Vector2 playerPosition = player.getPosition();
        if (Math.abs(playerPosition.x - position.x) <= collisionRadius && Math.abs(playerPosition.y - position.y) <= collisionRadius) {
            return true;
        }
        return false;
    }

    public void draw(SpriteBatch batch) {
        phoneSprite.setPosition(position.x, position.y);
        phoneSprite.draw(batch);
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
