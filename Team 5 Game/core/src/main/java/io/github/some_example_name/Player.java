package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.List;

/* This class implements the player character
 * It is responsible for the position, movement, speed
 * and collision detection of the player character.
 */
public class Player {
    protected Sprite sprite;
    protected Vector2 position;
    private float playerSpeed = 140f;
    private Rectangle playerCollision;
    protected int sizeX;
    protected int sizeY;
    private boolean inputInverted = false;

    Array<TiledMapTileLayer> nonWalkable;
    MapLayer walls_layer;
    TiledMapTileLayer object_layer;

    /* Player constructor

    * @param playerTexture - The visual representation of the player character
      @param startXposition - Starting player position on x-axis
      @param startYposition - Starting player position on y-axis
      @param nonWalkableLayers - An array of map layers the player can't move on.
    */
    public Player(Texture playerTexture, float startXPosition, float startYPosition, 
                MapLayer wallLayer, int sizeX, int sizeY) {
        sprite = new Sprite(playerTexture);
        position = new Vector2(startXPosition, startYPosition);
        sprite.setPosition(position.x, position.y);
        sprite.setSize(sizeX, sizeY);

        this.walls_layer = wallLayer;

        playerCollision = new Rectangle(position.x, position.y, (float) (sizeX), (float) (sizeY));
    }

    /* Called by the main class every frame, responsible for player movement and collisions.
    * Player moves using arrow keys or WASD */
    public void update(List<Door> doors, TripwireEvent tripWire) {
        float delta = Gdx.graphics.getDeltaTime();

        float moveX = 0;
        float moveY = 0;

        // Input inversion: if inverted, swap the key mappings
        if (inputInverted) {
            // Inverted: RIGHT/D -> LEFT, LEFT/A -> RIGHT, UP/W -> DOWN, DOWN/S -> UP
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                moveX -= playerSpeed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) {
                moveX += playerSpeed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)    || Gdx.input.isKeyPressed(Input.Keys.W)) {
                moveY -= playerSpeed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
                moveY += playerSpeed * delta;
            }
        } else {
            // Normal input
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                moveX += playerSpeed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) {
                moveX -= playerSpeed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)    || Gdx.input.isKeyPressed(Input.Keys.W)) {
                moveY += playerSpeed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
                moveY -= playerSpeed * delta;
            }
        }

        float newXPosition = position.x + moveX;
        float newYPosition = position.y + moveY;

        // door collision
        boolean blocked = false;
        Rectangle nextPosition = new Rectangle(playerCollision);
        nextPosition.x = newXPosition;
        nextPosition.y = newYPosition;

        for (Door door : doors) {
            if (door.isLocked() && door.collides(nextPosition)) {
                blocked = true;
                break;
            }
        }

        tripWire.checkTrigger(playerCollision);

        // all other collisions
        if (!blocked) {
            Rectangle nextPosX = new Rectangle(playerCollision);
            nextPosX.x = newXPosition;
            if (isWalkable(nextPosX)) {
                position.x = newXPosition;
            }
            Rectangle nextPosY = new Rectangle(playerCollision);
            nextPosY.y = newYPosition;
            if (isWalkable(nextPosY)) {
                position.y = newYPosition;
            }
        }
        sprite.setPosition(position.x, position.y);
        playerCollision.setPosition(position.x, position.y);
    }

    /* This function checks that the position the player is moving to is walkable.
    * This prevents the player from moving through walls or obstacles.
      @param hitbox - The Rectangle hitbox to check collisions against.
      @return bool - True if walkable, false if not
    */
    public boolean isWalkable(Rectangle hitbox) {
        MapObjects walls = walls_layer.getObjects();
        for (int i = 0; i < walls.getCount(); i++){
            RectangleMapObject mapRect = (RectangleMapObject) walls.get(i);
            Rectangle actualRect = mapRect.getRectangle();
            if (actualRect.overlaps(hitbox)){
                return false;
            }
        }
        return true;
    }

    /* Draws player character on map
     * @param SpriteBatch - The spritebatch for the game
     */
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    /*
     * Clamps the player character, stopping them from moving outside map bounds.
     * @param worldWidth
     * @param worldHeight
     */
    public void clamp(float worldWidth, float worldHeight) {
        float width = sprite.getWidth();
        float height = sprite.getHeight();

        float clampX = MathUtils.clamp(sprite.getX(), 0, worldWidth - width);
        float clampY = MathUtils.clamp(sprite.getY(), 0, worldHeight - height);

        sprite.setPosition(clampX, clampY);
        playerCollision.setPosition(clampX, clampY);
    }

    /* Getter for player collision rectangle
     * @return Rectangle - Collision bounds
     */
    public Rectangle getCollision() {
        return playerCollision;
    }

    public void dispose() {
        // Dispose if needed
    }
    /* Getter for player position
     * @return Vector2 - Player position
     */
    public Vector2 getPosition() {
        return this.position;
    }

    /* Getter for player speed
     * @return float - Player speed
     */
    public float getPlayerSpeed() {
        return this.playerSpeed;
    }

    /* Setter for player speed
     * @param float - New value for player speed
     */
    public void setPlayerSpeed(float newSpeed) {
        this.playerSpeed = newSpeed;
    }

    /* Setter for input inversion
     * @param inverted - True to invert input, false for normal input
     */
    public void setInputInverted(boolean inverted) {
        this.inputInverted = inverted;
    }

    /* Getter for input inversion state
     * @return boolean - True if input is inverted, false otherwise
     */
    public boolean isInputInverted() {
        return this.inputInverted;
    }
}
