package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/* A class for the AnnoyingFriend event (negative event).
 *
 * This event represents an annoying friend that follows the player
 * for 15 seconds after collision, slowing them down.
 * The friend remains visible and follows the player during the effect.
 *
 */
public class AnnoyingFriendEvent implements Event {
    private String name;
    private boolean isTriggered;
    private Texture texture;
    private Sprite friendSprite;
    private Vector2 position;
    private float collisionRadius;
    private EventCounter eventCounter;
    private boolean isFollowing = false; // Track if friend is currently following player
    private float followSpeed = 80f; // Speed at which friend follows player

    /* Constructor for the AnnoyingFriend
     * @param name - Event name
     * @param texture - The texture for the friend
     * @param xPosition - The friend's starting position on the x-axis
     * @param yPosition - The friend's starting position on the y-axis
     * @param eventCounter - Event counter to track triggered events
     */
    public AnnoyingFriendEvent(String name, Texture texture, float xPosition, float yPosition, EventCounter eventCounter) {
        this.name = name;
        this.isTriggered = false;
        this.texture = texture;
        this.friendSprite = new Sprite(texture);
        // Same size as teacher (48x58)
        this.friendSprite.setSize(48f, 58f);
        this.position = new Vector2(xPosition, yPosition);
        this.collisionRadius = 20f;
        this.eventCounter = eventCounter;
    }

    // Checks for player collision (only before triggered)
    public boolean checkCollision(Player player) {
        if (isTriggered) return false;
        Vector2 playerPosition = player.getPosition();
        if (Math.abs(playerPosition.x - position.x) <= collisionRadius && Math.abs(playerPosition.y - position.y) <= collisionRadius) {
            return true;
        }
        return false;
    }

    // Updates the friend's position to follow the player
    public void update(Player player, float delta) {
        if (isFollowing) {
            Vector2 playerPosition = player.getPosition();
            // Calculate direction to player
            float dx = playerPosition.x - position.x;
            float dy = playerPosition.y - position.y;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            
            // Only move if not too close to player
            if (distance > 30f) {
                // Normalize and apply speed
                float moveX = (dx / distance) * followSpeed * delta;
                float moveY = (dy / distance) * followSpeed * delta;
                position.x += moveX;
                position.y += moveY;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        // Friend is always visible (before and during following)
        friendSprite.setPosition(position.x, position.y);
        friendSprite.draw(batch);
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
            isFollowing = true;
            eventCounter.incrementNegCounter();
        }
    }

    // Called when the effect ends
    public void stopFollowing() {
        isFollowing = false;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        this.isFollowing = following;
    }

    public Vector2 getPosition() {
        return position;
    }

    // Interface methods
    @Override public String getName() { return name; }
    @Override public void setName(String name) { this.name = name; }
    @Override public boolean isTriggered() { return isTriggered; }
    @Override public void setTriggered(boolean triggered) { this.isTriggered = triggered; }
}

