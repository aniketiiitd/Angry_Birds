package com.github.angry_bird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class LevelCleared implements com.badlogic.gdx.Screen {

    private SpriteBatch batch;
    private Sprite cleareddisplay;
    private BitmapFont font;
    // private Circle nextlevel;
    private Circle restartbutton;
    private Circle nextbutton;
    private Circle exitbutton;
    private int score;
    OrthographicCamera camera;
    private final Game game;
    private ShapeRenderer shapeRenderer;
    private Level level;

    public LevelCleared(Game game, BitmapFont font, int Score, Level lvl) {
        this.batch = new SpriteBatch();
        this.game = game;
        this.font = font;
        this.score = Score;
        this.level = lvl;
        LevelScreen.ClearGameState();
    }

    @Override
    public void show() {
        Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cleareddisplay = new Sprite(new Texture("levelcleared.png"));

        restartbutton = new Circle(-190, -310, 55);
        nextbutton = new Circle(48, -310, 55);
        exitbutton = new Circle(265, -310, 55);
        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void render(float delta) {
        // Gdx.gl.glClearColor(0, 0, 0, 0);
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // level.update(delta); // Update the level's game logic
        batch.begin();
        batch.draw(cleareddisplay, 550, 75);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        // Define the rectangle coordinates and size
        float rectX = 680; // X position of the rectangle
        float rectY = 500; // Y position of the rectangle
        float rectWidth = 300; // Width of the rectangle
        float rectHeight = 100; // Height of the rectangle

        // Draw the black rectangle
        shapeRenderer.rect(rectX, rectY, rectWidth, rectHeight);
        // End shape rendering
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, "Score: " + score, 720, 550);
        batch.end();

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        // // Check if the mouse is over the play button
        if (restartbutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                game.setScreen(new LevelScreen(game, level));
            }

        } else if (nextbutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                if (this.level instanceof Level1) {
                    LevelScreen screen = new LevelScreen(game, new Level2());
                    screen.pause();
                    try {
                        game.setScreen(screen);
                    } catch (NullPointerException e) {
                        screen.resume();
                        game.setScreen(screen);
                    }
                }

                else if (this.level instanceof Level2) {
                    LevelScreen screen = new LevelScreen(game, new Level3());
                    screen.pause();
                    try {
                        game.setScreen(screen);
                    } catch (NullPointerException e) {
                        screen.resume();
                        game.setScreen(screen);
                    }
                }
            }
        } else if (exitbutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                game.setScreen(new SelectLevel(game, new HomeScreen(game)));
            }
        } else {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        cleareddisplay.getTexture().dispose();
    }

    // Other Screen methods like resize, pause, etc., would go here

}
