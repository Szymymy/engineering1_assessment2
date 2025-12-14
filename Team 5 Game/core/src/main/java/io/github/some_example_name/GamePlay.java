package io.github.some_example_name;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

/* This class handles the main game screen.
 * This class creates the map, player, dean
 * and any objects as well as a timer and pause menu
 * that can be activated by player input.
 */
public class GamePlay implements Screen {
	// Textures
    Texture playerTexture;
    Texture speedBoostTexture;
    Texture doorTexture;
    Texture keyTexture;
    Texture deanTexture;
    Texture deanAreaDebug;
    Texture alarmClockTexture;
    Texture dodgyTakeawayTexture;
    Texture teacherTexture;
    Texture friendTexture;

    SpriteBatch spriteBatch;
    BitmapFont font;
    Player player;
    SpeedBoostEvent speedBoost;
    AlarmClockEvent alarmClock;
    DodgyTakeawayEvent dodgyTakeaway;
    RecommendationLetterEvent recommendationLetter;
    AnnoyingFriendEvent annoyingFriend;

    // map
    FitViewport viewport;
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Rectangle finishZone;
    // map collision
    Array<TiledMapTileLayer> nonWalkableLayers;
    MapLayer walls;
    TiledMapTileLayer corners;

    // UI
    boolean isPaused;
    boolean speedBoostActive = false;
    boolean inputInvertedActive = false;
    boolean examActive = false;
    boolean recommendationLetterActive = false;
    boolean annoyingFriendActive = false;
    Stage stage;
    Skin skin;
    Label label;
    Label boostLabel;
    Label pausedLabel;
    Label eventCountLabel;
    Label scoreMultipliedLabel;
    Label annoyingFriendLabel;

    // Label styles (red, yellow and green)
    Label.LabelStyle redStyle;
    Label.LabelStyle yellowStyle;
    Label.LabelStyle greenStyle;

    // Timer
    double timer = 300.0;
    double boostTimer = 30f; // For speed boost
    double inputInvertedTimer = 20f; // For dodgy takeaway input inversion
    double annoyingFriendTimer = 15f; // For annoying friend effect

    // Doors
    private List<Door> doors = new ArrayList<>();
    private TripwireEvent tripWire;
    private KeyEvent key;
    private boolean hasKey = false;
    private ExamEvent exam;

    // Dean
    private Dean dean;

    private final Main main;

    // Points
    Points points = new Points();

    // Event Counter
    EventCounter eventCounter = new EventCounter();

    // constructor
    public GamePlay(final Main game) {
        this.main = game;
    }

    @Override
    public void show() {
        System.out.println("GamePlay screen loading...");

        // Initialize viewport and camera FIRST
        viewport = new FitViewport(1600, 1120);
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        camera.position.set(960, 540, 0);
        camera.update();

        spriteBatch = new SpriteBatch();

        // Load textures
        playerTexture = new Texture(Gdx.files.internal("player1.png"));
        speedBoostTexture = new Texture(Gdx.files.internal("speed_boost_sprite.png"));
        doorTexture = new Texture(Gdx.files.internal("door1.png"));
        keyTexture = new Texture(Gdx.files.internal("keycard1.png"));
        deanTexture = new Texture(Gdx.files.internal("dean.png"));
        // TODO: Replace with actual alarm clock texture when available
        alarmClockTexture = new Texture(Gdx.files.internal("alarm.png")); // Placeholder texture
        // TODO: Replace with actual dodgy takeaway texture when available
        dodgyTakeawayTexture = new Texture(Gdx.files.internal("dodgy_takeaway.png")); // Placeholder texture
        teacherTexture = new Texture(Gdx.files.internal("teacher.png"));
        friendTexture = new Texture(Gdx.files.internal("friend.png"));

        System.out.println("Textures loaded successfully");

        // Load map
        map = new TmxMapLoader().load(Gdx.files.internal("maps/ENG.tmx").file().getPath());
        System.out.println("Map loaded successfully");

        // Set up collision layer
        walls = map.getLayers().get("Wall_Collisions");

        // Set up map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // Initialize game objects
        // player
        player = new Player(playerTexture, 775, 100, walls, 40, 40);

        // speedboost
        speedBoost = new SpeedBoostEvent("SpeedBoost", speedBoostTexture, 680, 490, eventCounter);

        // alarm clock (placeholder coordinates - TODO: set actual position)
        alarmClock = new AlarmClockEvent("AlarmClock", alarmClockTexture, 950, 650, eventCounter);

        // dodgy takeaway (TODO: placeholder coordinates - set actual position)
        dodgyTakeaway = new DodgyTakeawayEvent("DodgyTakeaway", dodgyTakeawayTexture, 420, 820, eventCounter);

        // recommendation letter (teacher) - TODO: placeholder coordinates
        recommendationLetter = new RecommendationLetterEvent("RecommendationLetter", teacherTexture, 1070, 200, eventCounter);

        // annoying friend - TODO: placeholder coordinates
        annoyingFriend = new AnnoyingFriendEvent("AnnoyingFriend", friendTexture, 800, 300, eventCounter);

        // dean
        dean = new Dean(deanTexture, 550f, 480f, nonWalkableLayers, walls, 425f, 425f, 180f, 145f, 50, 50);

        // doors
        Door door = new Door(485, 580, 52, 52, doorTexture);
        door.unlock();
        doors.add(door);

        // key
        Rectangle keyZone = new Rectangle(1472, 480, 35, 35);
        key = new KeyEvent("Keycard", keyZone, keyTexture, eventCounter);

        // tripwire
        Rectangle tripWireZone = new Rectangle(384, 480, 32, 64);
        tripWire = new TripwireEvent("tripwire", tripWireZone, door, eventCounter);

        // exam
        Rectangle examZone = new Rectangle(900, 800, 50, 50);
        exam = new ExamEvent("Exam", examZone, eventCounter);

        finishZone = new Rectangle(0, 864, 32, 128);

        // Set up UI (only game UI, no menu)
        // Use FitViewport to match the game viewport for consistent coordinates
        stage = new Stage(new FitViewport(1600, 1120));

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        font = new BitmapFont();
        font.getData().setScale(2.5f);

        redStyle = new Label.LabelStyle(font, Color.RED);
        yellowStyle = new Label.LabelStyle(font, Color.YELLOW);
        greenStyle = new Label.LabelStyle(font, Color.GREEN);
        label = new Label(String.format("%.1f", timer), greenStyle);
        boostLabel = new Label(String.format("%.1f", boostTimer), greenStyle);
        eventCountLabel = new Label("POS: 0 / HID: 0 / NEG: 0", greenStyle);
        pausedLabel = new Label("PAUSED", greenStyle);
        scoreMultipliedLabel = new Label("Score is permanently multiplied!", greenStyle);
        annoyingFriendLabel = new Label("Annoying friend following you!", redStyle);

        // Position labels within the 1600x1120 coordinate system
        label.setPosition(700, 1050); // Timer at the top center
        boostLabel.setPosition(300, 1050); // Boost timer on the left
        eventCountLabel.setPosition(1000, 1050); // Event counter on the right
        pausedLabel.setPosition(700, 560); // Paused label at center (1120/2 = 560)
        scoreMultipliedLabel.setPosition(50, 1050); // Left of timer, same height
        annoyingFriendLabel.setPosition(50, 1000); // Below score multiplied label

        // Add all labels to stage
        stage.addActor(label);
        stage.addActor(boostLabel);
        stage.addActor(eventCountLabel);
        stage.addActor(pausedLabel);
        stage.addActor(scoreMultipliedLabel);
        stage.addActor(annoyingFriendLabel);
        
        // Set initial visibility
        boostLabel.setVisible(false);
        pausedLabel.setVisible(false);
        scoreMultipliedLabel.setVisible(false);
        annoyingFriendLabel.setVisible(false);

        // Set input processor for UI interactions (needed for exam dialog)
        Gdx.input.setInputProcessor(stage);

        System.out.println("GamePlay screen loaded successfully");
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // No menu check - just show the game directly
        togglePause();
        if (!isPaused && !examActive && !recommendationLetterActive && !annoyingFriendActive) {
            input();
            logic();
            updateTimer(delta);
            updateEventCount();
            speedBoost();
            alarmClock();
            dodgyTakeaway();
            checkExam();
            checkRecommendationLetter();
            checkAnnoyingFriend();
            dean.update();
            // Update annoying friend position (follows player when active)
            if (annoyingFriend.isFollowing()) {
                annoyingFriend.update(player, delta);
            }
        }
        draw();
    }

    public void speedBoost() {
        if (!speedBoost.isTriggered() && speedBoost.checkCollision(player)) {
            speedBoost.trigger();
            boostLabel.setVisible(true);
            speedBoostActive = true;
            modifySpeed(2); // Doubles player speed
        }
    }

    /* Handles alarm clock event collision and triggering
     * When the player collects the alarm clock, it adds 30 seconds to the timer
     */
    public void alarmClock() {
        if (!alarmClock.isTriggered() && alarmClock.checkCollision(player)) {
            alarmClock.trigger();
            timer += 30.0; // Add 30 seconds to the timer
        }
    }

    /* Handles annoying friend event collision and triggering
     * When the player collides with the annoying friend, they follow and slow them down for 15 seconds
     */
    public void checkAnnoyingFriend() {
        if (!annoyingFriend.isTriggered() && annoyingFriend.checkCollision(player)) {
            annoyingFriend.trigger();
            annoyingFriendActive = true;
            annoyingFriendTimer = 15f; // Reset timer to 15 seconds
            annoyingFriendLabel.setVisible(true);
            // Slow down the player by half
            modifySpeed(0.5f);
            showAnnoyingFriendDialog();
        }
    }

    /* Shows the annoying friend dialog popup
     * Displays a message that the friend will be annoying for 15 seconds
     */
    private void showAnnoyingFriendDialog() {
        // Create window with white background
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.titleFontColor = Color.BLACK;
        // Create white background drawable
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        TextureRegion textureRegion = new TextureRegion(new com.badlogic.gdx.graphics.Texture(pixmap));
        pixmap.dispose();
        NinePatch ninePatch = new NinePatch(textureRegion, 0, 0, 0, 0);
        windowStyle.background = new NinePatchDrawable(ninePatch);
        
        Window friendWindow = new Window("Annoying Friend", windowStyle);
        friendWindow.setModal(true);
        friendWindow.setMovable(false);
        friendWindow.setResizable(false);
        
        // Set window size and position (centered using viewport coordinates)
        float windowWidth = 600;
        float windowHeight = 250;
        float windowX = (viewport.getWorldWidth() - windowWidth) / 2;
        float windowY = (viewport.getWorldHeight() - windowHeight) / 2;
        friendWindow.setSize(windowWidth, windowHeight);
        friendWindow.setPosition(windowX, windowY);
        
        // Adjust padding to prevent title from being cut off
        friendWindow.padTop(40); // Extra padding at top for title

        // Create table for layout - center everything
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // Message label
        BitmapFont messageFont = new BitmapFont();
        messageFont.getData().setScale(1.8f);
        Label messageLabel = new Label("I will be annoying you for 15 seconds!", new Label.LabelStyle(messageFont, Color.BLACK));
        table.add(messageLabel).padBottom(20).padTop(20).padRight(80);
        table.row();

        // OK button
        TextButton okButton = new TextButton("OK", skin);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                friendWindow.remove();
                stage.getRoot().removeActor(friendWindow);
                annoyingFriendActive = false;
            }
        });
        table.add(okButton).center().padRight(80);
        friendWindow.add(table);

        stage.addActor(friendWindow);
    }

    /* Handles dodgy takeaway event collision and triggering
     * When the player collects the dodgy takeaway, it inverts input for 20 seconds
     */
    public void dodgyTakeaway() {
        if (!dodgyTakeaway.isTriggered() && dodgyTakeaway.checkCollision(player)) {
            dodgyTakeaway.trigger();
            inputInvertedActive = true;
            inputInvertedTimer = 20f; // Reset timer to 20 seconds
            player.setInputInverted(true);
        }
    }

    /* Handles exam event collision and triggering
     * When the player enters the exam zone, shows the exam popup
     */
    public void checkExam() {
        if (!exam.isTriggered() && exam.checkTrigger(player.getCollision())) {
            examActive = true;
            isPaused = true; // Pause game during exam
            showExamDialog();
        }
    }

    /* Handles recommendation letter event collision and triggering
     * When the player collides with the teacher, shows a popup asking for reference
     */
    public void checkRecommendationLetter() {
        if (!recommendationLetter.hasResponded() && recommendationLetter.checkCollision(player)) {
            recommendationLetterActive = true;
            isPaused = true;
            showRecommendationLetterDialog();
        }
    }

    /* Shows the recommendation letter dialog popup
     * Asks the player if they want to get a reference
     */
    private void showRecommendationLetterDialog() {
        // Create window with white background
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.titleFontColor = Color.BLACK;
        // Create white background drawable
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        TextureRegion textureRegion = new TextureRegion(new com.badlogic.gdx.graphics.Texture(pixmap));
        pixmap.dispose();
        NinePatch ninePatch = new NinePatch(textureRegion, 0, 0, 0, 0);
        windowStyle.background = new NinePatchDrawable(ninePatch);
        
        Window recommendationWindow = new Window("Recommendation Letter", windowStyle);
        recommendationWindow.setModal(true);
        recommendationWindow.setMovable(false);
        recommendationWindow.setResizable(false);
        
        // Set window size and position (centered using viewport coordinates)
        float windowWidth = 600;
        float windowHeight = 300;
        float windowX = (viewport.getWorldWidth() - windowWidth) / 2;
        float windowY = (viewport.getWorldHeight() - windowHeight) / 2;
        recommendationWindow.setSize(windowWidth, windowHeight);
        recommendationWindow.setPosition(windowX, windowY);
        
        // Adjust padding to prevent title from being cut off
        recommendationWindow.padTop(40); // Extra padding at top for title

        // Create table for layout - center everything
        Table table = new Table();
        table.setFillParent(true);
        table.center(); // Center the table content

        // Question label
        BitmapFont questionFont = new BitmapFont();
        questionFont.getData().setScale(2f);
        Label questionLabel = new Label("Do you want to get my reference?", new Label.LabelStyle(questionFont, Color.BLACK));
        table.add(questionLabel).padBottom(30).padTop(20).padRight(80);
        table.row();

        // Yes button
        TextButton yesButton = new TextButton("Yes", skin);
        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                recommendationLetter.trigger();
                recommendationLetter.setResponded(true);
                points.setScoreMultiplied(true);
                scoreMultipliedLabel.setVisible(true);
                recommendationWindow.remove();
                stage.getRoot().removeActor(recommendationWindow);
                recommendationLetterActive = false;
                isPaused = false;
            }
        });

        // No button
        TextButton noButton = new TextButton("No", skin);
        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                recommendationLetter.setResponded(true);
                recommendationWindow.remove();
                stage.getRoot().removeActor(recommendationWindow);
                recommendationLetterActive = false;
                isPaused = false;
            }
        });

        // Create a horizontal table for buttons and center it
        Table buttonRow = new Table();
        buttonRow.add(yesButton).padRight(30);
        buttonRow.add(noButton).padRight(80);
        table.add(buttonRow).center();
        recommendationWindow.add(table);

        stage.addActor(recommendationWindow);
    }

    /* Shows the exam dialog popup with questions
     * Displays one question at a time with 4 answer options
     */
    private void showExamDialog() {
        List<Question> questions = exam.getQuestions();
        final int[] currentQuestionIndex = {0};
        final int[] correctAnswers = {0};
        
        showQuestionDialog(questions, currentQuestionIndex, correctAnswers);
    }

    /* Displays a single question dialog
     * @param questions - List of all questions
     * @param currentQuestionIndex - Array with current question index (mutable)
     * @param correctAnswers - Array with count of correct answers (mutable)
     */
    private void showQuestionDialog(List<Question> questions, final int[] currentQuestionIndex, final int[] correctAnswers) {
        if (currentQuestionIndex[0] >= questions.size()) {
            // All questions answered, calculate score
            finishExam(correctAnswers[0]);
            return;
        }

        Question question = questions.get(currentQuestionIndex[0]);
        String[] options = question.getOptions();

        // Create window with white background
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.titleFontColor = Color.BLACK;
        // Create white background drawable
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture whiteTexture = new Texture(pixmap);
        pixmap.dispose();
        NinePatch whitePatch = new NinePatch(new TextureRegion(whiteTexture), 0, 0, 0, 0);
        windowStyle.background = new NinePatchDrawable(whitePatch);
        
        Window examWindow = new Window("Exam Question " + (currentQuestionIndex[0] + 1) + "/5", windowStyle);
        examWindow.setModal(true);
        examWindow.setMovable(false);

        // Set window size and position (centered)
        float windowWidth = 900;
        float windowHeight = 600;
        float windowX = (viewport.getWorldWidth() - windowWidth) / 2;
        float windowY = (viewport.getWorldHeight() - windowHeight) / 2;
        examWindow.setSize(windowWidth, windowHeight);
        examWindow.setPosition(windowX, windowY);
        
        // Adjust padding to prevent title from being cut off
        examWindow.padTop(40); // Extra padding at top for title

        // Create table for layout
        Table table = new Table();
        table.setFillParent(true);
        table.pad(20);
        table.padTop(50); // Extra top padding to account for title

        // Question label (use smaller font for better readability, black text on white background)
        BitmapFont questionFont = new BitmapFont();
        questionFont.getData().setScale(1.8f);
        Label questionLabel = new Label(question.questionText, new Label.LabelStyle(questionFont, Color.BLACK));
        questionLabel.setWrap(true);
        table.add(questionLabel).colspan(2).padBottom(40).width(windowWidth - 80).height(100);
        table.row();

        // Create 4 answer buttons
        for (int i = 0; i < 4; i++) {
            final int answerIndex = i;
            TextButton answerButton = new TextButton(options[i], skin);
            answerButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Check if answer is correct
                    if (question.isCorrect(answerIndex)) {
                        correctAnswers[0]++;
                    }
                    
                    // Remove current window
                    examWindow.remove();
                    stage.getRoot().removeActor(examWindow);
                    
                    // Show next question or finish
                    currentQuestionIndex[0]++;
                    showQuestionDialog(questions, currentQuestionIndex, correctAnswers);
                }
            });
            table.add(answerButton).width(350).height(60).pad(10);
            if (i % 2 == 1) {
                table.row();
            }
        }

        examWindow.add(table);
        stage.addActor(examWindow);
    }

    /* Finishes the exam and applies points based on score
     * Shows result dialog with pass/fail message
     * @param correctCount - Number of correct answers (out of 5)
     */
    private void finishExam(int correctCount) {
        boolean passed = correctCount >= 3;
        
        // Apply points
        if (passed) {
            points.addPoints(100); // Bonus points for passing
        } else {
            points.subtractPoints(50); // Penalty for failing
        }
        
        // Show result dialog
        showExamResultDialog(correctCount, passed);
    }
    
    /* Shows the exam result dialog with pass/fail message
     * @param correctCount - Number of correct answers
     * @param passed - Whether the player passed (>=3 correct)
     */
    private void showExamResultDialog(int correctCount, boolean passed) {
        // Create window with white background
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.titleFontColor = Color.BLACK;
        // Create white background drawable
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture whiteTexture = new Texture(pixmap);
        pixmap.dispose();
        NinePatch whitePatch = new NinePatch(new TextureRegion(whiteTexture), 0, 0, 0, 0);
        windowStyle.background = new NinePatchDrawable(whitePatch);
        
        Window resultWindow = new Window("Exam Results", windowStyle);
        resultWindow.setModal(true);
        resultWindow.setMovable(false);
        
        // Set window size and position (centered)
        float windowWidth = 700;
        float windowHeight = 400;
        float windowX = (viewport.getWorldWidth() - windowWidth) / 2;
        float windowY = (viewport.getWorldHeight() - windowHeight) / 2;
        resultWindow.setSize(windowWidth, windowHeight);
        resultWindow.setPosition(windowX, windowY);
        
        // Adjust padding to prevent title from being cut off
        resultWindow.padTop(40); // Extra padding at top for title
        
        // Create table for layout
        Table table = new Table();
        table.setFillParent(true);
        table.pad(30);
        table.padTop(50); // Extra top padding to account for title
        
        // Result message
        BitmapFont resultFont = new BitmapFont();
        resultFont.getData().setScale(2.0f);
        Color resultColor = passed ? Color.GREEN : Color.RED;
        String resultMessage = passed ? "PASSED!" : "FAILED!";
        String scoreMessage = "You got " + correctCount + " out of 5 questions correct.";
        String pointsMessage = passed ? "+100 points" : "-50 points";
        
        Label resultLabel = new Label(resultMessage, new Label.LabelStyle(resultFont, resultColor));
        table.add(resultLabel).padBottom(20);
        table.row();
        
        // Score message
        BitmapFont scoreFont = new BitmapFont();
        scoreFont.getData().setScale(1.5f);
        Label scoreLabel = new Label(scoreMessage, new Label.LabelStyle(scoreFont, Color.BLACK));
        table.add(scoreLabel).padBottom(15);
        table.row();
        
        // Points message
        Label pointsLabel = new Label(pointsMessage, new Label.LabelStyle(scoreFont, Color.BLACK));
        table.add(pointsLabel).padBottom(30);
        table.row();
        
        // Close button
        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resultWindow.remove();
                stage.getRoot().removeActor(resultWindow);
                examActive = false;
                isPaused = false;
                exam.setExamActive(false);
            }
        });
        table.add(closeButton).width(200).height(50);
        
        resultWindow.add(table);
        stage.addActor(resultWindow);
    }

    /* Changes the player speed by a modifier given as parameter
    * @param modifier - 2 or 0.5 */
    public void modifySpeed(float modifier) {
        player.setPlayerSpeed((player.getPlayerSpeed()) * modifier);
    }

    private void input() {
        player.update(doors, tripWire);
    }

    private void logic() {
    	//clamp player movement to world
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        player.clamp(worldWidth, worldHeight);

        if (finishZone.overlaps(player.getCollision())) {
            gameOver(true);
        }

        // Key collection
        if (!key.isTriggered() && key.collides(player.getCollision()) && tripWire.isTriggered()) {
            key.trigger();
            hasKey = true;
        }


        // Unlock doors
        if (hasKey) {
            for (Door door : doors) {
                door.unlock();
            }
        }


        // Dean collision
        if (dean.checkCollision(player.getCollision())) {
            points.deanCaughtYou();
            gameOver(false); // Triggers bad ending
        }

    }

    private void draw() {
        viewport.apply();

        // Draw map
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Draw game objects
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        player.draw(spriteBatch);

        //only draw when the player has not collected yet
        if (!speedBoost.isTriggered()) {
            speedBoost.draw(spriteBatch);
        }

        //draw alarm clock if not collected
        if (!alarmClock.isTriggered()) {
            alarmClock.draw(spriteBatch);
        }

        //draw dodgy takeaway if not collected
        if (!dodgyTakeaway.isTriggered()) {
            dodgyTakeaway.draw(spriteBatch);
        }

        //draw recommendation letter (teacher) - always visible
        recommendationLetter.draw(spriteBatch);

        //draw annoying friend - always visible
        annoyingFriend.draw(spriteBatch);

        //draw list of doors
        for (Door door : doors) {
            door.draw(spriteBatch);
        }

        //draw key
        if (tripWire.isTriggered()) {
            key.draw(spriteBatch);
        }

        //draw dean
        dean.draw(spriteBatch);

        spriteBatch.end();

        // Draw UI
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    /* Called when the game is over.
    * Responsible for displaying end screen and calculating final point score
    * @param hasWon - If true, the good ending screen is shown.
    * The bad ending screen is shown if false.*/
    public void gameOver(boolean hasWon) {
        eventCounter.resetEventsCounter();
        System.out.println("Game Over!");
        if (hasWon) {
            points.calcPoints(timer);
            main.winGame(points.getScore());
        }
        else {
            main.endGame();
        }
    }

    /* Updates timers in the game
     * Called each frame to reduce the game timer.
     * Changes the colour of the game timer based on time remaining
     * Ends game if timer reaches 0
     * Removes speed boost if active and speed boost timer reaches 0
     * @param delta - Delta time
     */
    private void updateTimer(float delta) {
        timer -= delta;
        if (timer <= 150 && timer >= 60) {
            label.setStyle(yellowStyle);
        }
        if (timer <= 60) {
            label.setStyle(redStyle);
        }
        label.setText(String.format("%.1f", timer));
        if (timer <= 0) {
            gameOver(false);
        }
        // Updates speed boost timer
        if (speedBoostActive) {
            boostTimer -= delta;
            boostLabel.setText(String.format("%.1f", boostTimer));
            if (boostTimer <= 0) {
                modifySpeed(0.5f);
                boostLabel.setVisible(false);
                speedBoostActive = false;
            }
        }
        // Updates input inversion timer
        if (inputInvertedActive) {
            inputInvertedTimer -= delta;
            if (inputInvertedTimer <= 0) {
                player.setInputInverted(false);
                inputInvertedActive = false;
            }
        }
        // Updates annoying friend timer
        if (annoyingFriend.isFollowing()) {
            annoyingFriendTimer -= delta;
            if (annoyingFriendTimer <= 0) {
                annoyingFriend.stopFollowing();
                annoyingFriendLabel.setVisible(false);
                // Restore player speed (multiply by 2 to undo the 0.5 slow)
                modifySpeed(2f);
            }
        }
    }

    // Updates the event counter in game.
    private void updateEventCount() {
        int[] eventCount = eventCounter.getEventsCounter();
        eventCountLabel.setText("POS:"+eventCount[0]+
                                " / HID:"+eventCount[1]+
                                " / NEG:"+eventCount[2]);
    }

    public void togglePause() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
            pausedLabel.setVisible(isPaused);
        }
    }

    /* Dispose function to remove UI elements from memory */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        spriteBatch.dispose();
        playerTexture.dispose();
        doorTexture.dispose();
        speedBoost.dispose();
        alarmClock.dispose();
        dodgyTakeaway.dispose();
        recommendationLetter.dispose();
        annoyingFriend.dispose();
        keyTexture.dispose();
        deanTexture.dispose();
        map.dispose();
        mapRenderer.dispose();
    }
}
