package io.github.some_example_name;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/* Main class of the application.
 * The Main is responsible for starting up the game
 * when it is launched. It initialises the sprite batch
 * and loads the main menu screen.
 * It also loads the ending screen when the game ends.
 */
public class Main extends Game {
	public SpriteBatch batch;

    @Override
    public void create() {
        if (Gdx.app.getType() != Application.ApplicationType.HeadlessDesktop) {
            batch = new SpriteBatch();
            //Calling the new class here
            setScreen(new MainMenu(this)); // Start with the menu
        }
    	

    }

    @Override
    public void render() {
    	super.render();
    }

  //This function disposes of application resources freeing up memory
    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
        if (getScreen() != null) {
        	getScreen().dispose();
        }
    }

    // Method to start the game
    public void startGame() {setScreen(new GamePlay(this)); }

    // Method to return to menu
    public void mainMenu() {setScreen(new MainMenu(this));}

    // Method to go to leaderboard screen
    public void leaderboardScreen() {setScreen(new LeaderboardScreen(this));}

    /* Two methods responsible for ending the game.
     * endGame(): Called when the player loses the game
     * winGame(): Called when the player wind the game
     * @param escaped - True if the player wins, false if they lose
     * @param points - The number of points won by the player.
     * Set to 0 if the player loses.
     */
    public void endGame() {setScreen(new EndGameScreen(this, false, 0));}

    public void winGame(int points) {setScreen(new EndGameScreen(this, true, points));}

}
