package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/* A class for the AlarmClock item.
 *
 * This item is an alarm clock that can be collected
 * by the player. When collected, it adds 30 seconds
 * to the game timer, giving the player extra time.
 * It disappears once activated.
 *
 */
public class AlarmClockEvent implements Event {
    private String name;
    private boolean isTriggered;
    private Texture texture;
    private Sprite alarmClockSprite;
    private Vector2 position;
    private float collisionRadius;
    private EventCounter eventCounter;

    /* Constructor for the AlarmClock
     * @param name - Event name
     * @param texture - The texture for the item (alarm clock)
     * @param xPosition - The item's position on the x-axis (placeholder for now)
     * @param yPosition - The item's position on the y-axis (placeholder for now)
     * @param eventCounter - Event counter to track triggered events
     */
    public AlarmClockEvent(String name, Texture texture, float xPosition, float yPosition, EventCounter eventCounter) {
        this.name = name;
        this.isTriggered = false;
        this.texture = texture;
        this.alarmClockSprite = new Sprite(texture);
        this.alarmClockSprite.setSize(80f, 50f);
        this.position = new Vector2(xPosition, yPosition);
        this.collisionRadius = 20f;
        this.eventCounter = eventCounter;
    }

    // Checks for player collision
    public boolean checkCollision(Player player) {
        Vector2 playerPosition = player.getPosition();
        if (Math.abs(playerPosition.x - position.x) <= collisionRadius && Math.abs(playerPosition.y - position.y) <= collisionRadius) {
            return true;
        }
        return false;
    }

    public void draw(SpriteBatch batch) {
        if (!isTriggered) {
            alarmClockSprite.setPosition(position.x, position.y);
            alarmClockSprite.draw(batch);
        }
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
            eventCounter.incrementPosCounter();
        }
    }

    // Interface methods
    @Override public String getName() { return name; }
    @Override public void setName(String name) { this.name = name; }
    @Override public boolean isTriggered() { return isTriggered; }
    @Override public void setTriggered(boolean triggered) { this.isTriggered = triggered; }
}

