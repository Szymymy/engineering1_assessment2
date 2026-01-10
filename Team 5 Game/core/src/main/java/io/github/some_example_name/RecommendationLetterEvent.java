package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/* A class for the RecommendationLetter event (Teacher).
 *
 * This event represents a teacher that can give a recommendation letter.
 * When the player collides with the teacher, a popup appears asking
 * if they want to get a reference. If yes, the score is permanently multiplied.
 * The event can only be triggered once.
 *
 */
public class RecommendationLetterEvent implements Event {
    private String name;
    private boolean isTriggered;
    private Texture texture;
    private Sprite teacherSprite;
    private Vector2 position;
    private float collisionRadius;
    private EventCounter eventCounter;
    private boolean hasResponded = false; // Track if player has responded to popup

    /* Constructor for the RecommendationLetter
     * @param name - Event name
     * @param texture - The texture for the teacher
     * @param xPosition - The teacher's position on the x-axis
     * @param yPosition - The teacher's position on the y-axis
     * @param eventCounter - Event counter to track triggered events
     */
    public RecommendationLetterEvent(String name, Texture texture, float xPosition, float yPosition, EventCounter eventCounter) {
        this.name = name;
        this.isTriggered = false;
        this.texture = texture;
        if (texture != null) {
            this.teacherSprite = new Sprite(texture);
            // Scale to slightly bigger than standard 32x32 textures
            this.teacherSprite.setSize(48f, 58f);
        }
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
        // Teacher always remains visible even after giving recommendation letter
        if (teacherSprite != null) {
            teacherSprite.setPosition(position.x, position.y);
            teacherSprite.draw(batch);
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

    public boolean hasResponded() {
        return hasResponded;
    }

    public void setResponded(boolean responded) {
        this.hasResponded = responded;
    }

    // Interface methods
    @Override public String getName() { return name; }
    @Override public void setName(String name) { this.name = name; }
    @Override public boolean isTriggered() { return isTriggered; }
    @Override public void setTriggered(boolean triggered) { this.isTriggered = triggered; }
}
