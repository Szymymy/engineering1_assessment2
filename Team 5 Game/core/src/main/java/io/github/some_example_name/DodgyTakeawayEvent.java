package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/* A class for the DodgyTakeaway item.
 *
 * This item is a dodgy takeaway that can be collected
 * by the player. When collected, it inverts player input
 * for 20 seconds (up goes down, left goes right, etc.).
 * It disappears once activated.
 *
 */
public class DodgyTakeawayEvent implements Event {
    private String name;
    private boolean isTriggered;
    private Texture texture;
    private Sprite dodgyTakeawaySprite;
    private Vector2 position;
    private float collisionRadius;
    private EventCounter eventCounter;

    /* Constructor for the DodgyTakeaway
     * @param name - Event name
     * @param texture - The texture for the item (dodgy takeaway)
     * @param xPosition - The item's position on the x-axis (TODO: placeholder for now)
     * @param yPosition - The item's position on the y-axis (TODO: placeholder for now)
     * @param eventCounter - Event counter to track triggered events
     */
    public DodgyTakeawayEvent(String name, Texture texture, float xPosition, float yPosition, EventCounter eventCounter) {
        this.name = name;
        this.isTriggered = false;
        this.texture = texture;
        this.dodgyTakeawaySprite = new Sprite(texture);
        // Scale sprite to slightly bigger than standard 32x32 textures
        this.dodgyTakeawaySprite.setSize(48f, 48f);
        this.position = new Vector2(xPosition, yPosition);
        this.collisionRadius = 24f; // Adjusted to match larger sprite size
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
            dodgyTakeawaySprite.setPosition(position.x, position.y);
            dodgyTakeawaySprite.draw(batch);
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
            eventCounter.incrementNegCounter();
        }
    }

    // Interface methods
    @Override public String getName() { return name; }
    @Override public void setName(String name) { this.name = name; }
    @Override public boolean isTriggered() { return isTriggered; }
    @Override public void setTriggered(boolean triggered) { this.isTriggered = triggered; }
}

