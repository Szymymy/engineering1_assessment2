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
    private Stage stage;
    private BitmapFont buttonsFont;
    private Texture background;
    private Texture exitButtonTexture, hoverExitButtonTexture;

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
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        TextButton exitButton = new TextButton("", buttonStyle);
    }

    private ClickListener makeExitButtonListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {main.mainMenu();}
        };
    }

    @Override
    public void render(float delta) {
        
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
        
        
        
        Gdx.input.setInputProcessor(stage);



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
