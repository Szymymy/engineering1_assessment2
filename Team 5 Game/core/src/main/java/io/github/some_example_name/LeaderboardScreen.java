package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LeaderboardScreen implements Screen {

    private final Main main;
    private Stage leaderboardStage;
    private BitmapFont buttonsFont;
    private Texture leaderboardBackground;
    private Texture menuButtonTexture, hoverMenuButtonTexture;

    public LeaderboardScreen(final Main game) {
        this.main = game;
        MakeFont();
        generateAssets();
        ShowScreen();
    }

    private void MakeFont() {
        buttonsFont = new BitmapFont();
        buttonsFont.getData().setScale(1.0f); //Set it at random
    }

    private void generateAssets() {

    }

    private void ShowScreen() {

    }

    @Override
    public void render(float delta) {
        
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
        Gdx.input.setInputProcessor(leaderboardStage);
    }

    @Override
    public void dispose() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

}
