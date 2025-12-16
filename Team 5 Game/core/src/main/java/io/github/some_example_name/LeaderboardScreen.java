package io.github.some_example_name;

import io.github.some_example_name.Leaderboard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.particles.values.GradientColorValue;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

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
        exitButtonTexture =  new Texture(Gdx.files.internal("ExitButton.png"));
        hoverExitButtonTexture =  new Texture(Gdx.files.internal("HoverExitButton.png"));

    }

    private TextButtonStyle createExitButtonStyle() {
        TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(exitButtonTexture);
        buttonStyle.over = new TextureRegionDrawable(hoverExitButtonTexture);
        buttonStyle.down = new TextureRegionDrawable(exitButtonTexture);
        buttonStyle.font = buttonsFont; 
        return buttonStyle;
    }

    private void drawLeaderboard(Table table) {
        Leaderboard leaderboard = new Leaderboard();
        String[] scores = leaderboard.getLeaderboard();
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = buttonsFont;
        Label title = new Label("LEADERBOARD", labelStyle);
        Label score1 = new Label(scores[0], labelStyle);
        Label score2 = new Label(scores[1], labelStyle);
        Label score3 = new Label(scores[2], labelStyle);
        Label score4 = new Label(scores[3], labelStyle);
        Label score5 = new Label(scores[4], labelStyle);
        table.add(title).pad(10); table.row();
        table.add(score1).pad(10);; table.row();
        table.add(score2).pad(10);; table.row();
        table.add(score3).pad(10);; table.row();
        table.add(score4).pad(10);; table.row();
        table.add(score5).pad(10);; table.row();
    }

    private void ShowScreen() {
        TextButtonStyle exitButtonStyle = createExitButtonStyle();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        drawLeaderboard(table);

        TextButton exitButton = new TextButton("", exitButtonStyle);
        exitButton.setStyle(exitButtonStyle);
        exitButton.setBounds(0, 0, 400, 120);
        exitButton.addListener(makeExitButtonListener());
        stage.addActor(exitButton);
    }

    private ClickListener makeExitButtonListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {main.mainMenu();}
        };
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1, true);
        stage.act(delta);
        stage.draw();

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
