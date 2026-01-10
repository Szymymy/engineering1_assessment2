package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/* A hidden quest event where the player must collect 3 longboi items.
 *
 * When all 3 longbois are collected, the quest is completed and
 * the player receives bonus points with a congratulatory message.
 *
 * This is a hidden event (quest is not announced, but items are visible).
 */
public class LongboiCaptureEvent implements Event {
    private String name;
    private boolean isTriggered;
    private Texture texture;
    private EventCounter eventCounter;

    // Three longboi sprites and their positions
    private Sprite[] longboiSprites;
    private Vector2[] positions;
    private boolean[] collected;
    private int collectedCount = 0;

    private float collisionRadius = 25f;
    private static final int TOTAL_LONGBOIS = 3;

    /* Constructor
     * @param name - Name of the event
     * @param texture - The longboi texture
     * @param eventCounter - Event counter to track triggered events
     */
    public LongboiCaptureEvent(String name, Texture texture, EventCounter eventCounter) {
        this.name = name;
        this.isTriggered = false;
        this.texture = texture;
        this.eventCounter = eventCounter;

        // Initialize arrays
        if (texture != null) {
            this.longboiSprites = new Sprite[TOTAL_LONGBOIS];
        }
        this.positions = new Vector2[TOTAL_LONGBOIS];
        this.collected = new boolean[TOTAL_LONGBOIS];

        // Create sprites for all 3 longbois
        for (int i = 0; i < TOTAL_LONGBOIS; i++) {
            if (texture != null) {
                longboiSprites[i] = new Sprite(texture);
                longboiSprites[i].setSize(48f, 48f); // Adjust size as needed
            }

            collected[i] = false;
        }

        // Initialize positions - these are placeholders, edit as needed
        initializePositions();
    }

    /* EDIT HERE: Set the positions for the 3 longboi items
     * Change these coordinates to place them in desired locations
     */
    private void initializePositions() {
        // Longboi 1 position - TODO: Set actual position
        positions[0] = new Vector2(70f, 300f);

        // Longboi 2 position - TODO: Set actual position
        positions[1] = new Vector2(1450f, 600f);

        // Longboi 3 position - TODO: Set actual position
        positions[2] = new Vector2(120f, 800f);
    }

    /* Checks collision with any uncollected longboi
     * @param player - The player to check collision against
     * @return index of collected longboi (0-2), or -1 if none collected
     */
    public int checkCollision(Player player) {
        Vector2 playerPosition = player.getPosition();

        for (int i = 0; i < TOTAL_LONGBOIS; i++) {
            if (!collected[i]) {
                if (Math.abs(playerPosition.x - positions[i].x) <= collisionRadius &&
                    Math.abs(playerPosition.y - positions[i].y) <= collisionRadius) {
                    return i;
                }
            }
        }
        return -1;
    }

    /* Collects a specific longboi
     * @param index - The index of the longboi to collect (0-2)
     * @return true if all longbois are now collected
     */
    public boolean collectLongboi(int index) {
        if (index >= 0 && index < TOTAL_LONGBOIS && !collected[index]) {
            collected[index] = true;
            collectedCount++;

            // Check if all 3 are collected
            if (collectedCount >= TOTAL_LONGBOIS) {
                trigger();
                return true;
            }
        }
        return false;
    }

    /* Gets the number of collected longbois
     * @return Number collected (0-3)
     */
    public int getCollectedCount() {
        return collectedCount;
    }

    /* Checks if all longbois have been collected
     * @return true if all 3 are collected
     */
    public boolean isQuestComplete() {
        return collectedCount >= TOTAL_LONGBOIS;
    }

    /* Draws all uncollected longbois
     * @param batch - SpriteBatch to draw with
     */
    public void draw(SpriteBatch batch) {
        for (int i = 0; i < TOTAL_LONGBOIS; i++) {
            if (!collected[i]) {
                if (longboiSprites[i] != null) {
                    longboiSprites[i].setPosition(positions[i].x, positions[i].y);
                    longboiSprites[i].draw(batch);
                }
            }
        }
    }

    /* Disposes of the texture */
    public void dispose() {
        // Note: Texture is managed by GamePlay, don't dispose here
    }

    @Override
    public void trigger() {
        if (!isTriggered) {
            isTriggered = true;
            eventCounter.incrementHidCounter(); // Hidden event
        }
    }

    // Interface methods
    @Override public String getName() { return name; }
    @Override public void setName(String name) { this.name = name; }
    @Override public boolean isTriggered() { return isTriggered; }
    @Override public void setTriggered(boolean triggered) { this.isTriggered = triggered; }
}

