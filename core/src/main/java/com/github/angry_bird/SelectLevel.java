package com.github.angry_bird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;

public class SelectLevel implements com.badlogic.gdx.Screen {

    private final Game game; // Reference to the main game class
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sprite title, levels, backicon;
    private Sprite background;
    private Rectangle l1button, l2button, l3button;
    private Pixmap cursorPixmap;
    private Circle backbutton;
    private com.badlogic.gdx.graphics.Cursor cursor;
    private HomeScreen home;

    public SelectLevel(Game game, HomeScreen home) {
        this.game = game;
        this.home = home;
    }

    @Override
    public void show() {
        // Initialize camera and viewport
        Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        background = new Sprite(new Texture("b2.png"));
        title = new Sprite(new Texture("selectlevel.png"));
        levels = new Sprite(new Texture("levels.png"));
        backicon = new Sprite(new Texture("backicon.png"));
        l1button = new Rectangle(-400, -210, 160, 270);
        l2button = new Rectangle(-100, -210, 160, 270);
        l3button = new Rectangle(200, -210, 160, 270);
        backbutton = new Circle(-735, 380, 45);
        // back=new Circle();

        cursorPixmap = new Pixmap(Gdx.files.internal("cursor.png"));
        cursor = Gdx.graphics.newCursor(cursorPixmap, 16, 0);
        Gdx.graphics.setCursor(cursor);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.draw(title, 400, 700);
        batch.draw(levels, 400, 250);
        batch.draw(backicon, 30, 780);
        batch.end();

        // Get the mouse position in world coordinates
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        // Check if the mouse is over the play button
        if (l1button.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.justTouched()) {
                LevelScreen screen = new LevelScreen(game, new Level1());
                screen.pause();
                try {
                    game.setScreen(screen);
                } catch (NullPointerException e) {
                    screen.resume();
                    game.setScreen(screen);
                }
            }
        } else if (l2button.contains(mousePos.x, mousePos.y) && LevelStatusManager.readLevelStatus("Level2")) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.justTouched()) {
                LevelScreen screen = new LevelScreen(game, new Level2());
                screen.pause();
                try {
                    game.setScreen(screen);
                } catch (NullPointerException e) {
                    screen.resume();
                    game.setScreen(screen);
                }
            }
        } else if (l3button.contains(mousePos.x, mousePos.y) && LevelStatusManager.readLevelStatus("Level3")) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.justTouched()) {
                LevelScreen screen = new LevelScreen(game, new Level3());
                screen.pause();
                try {
                    game.setScreen(screen);
                } catch (NullPointerException e) {
                    screen.resume();
                    game.setScreen(screen);
                }
            }
        } else if (backbutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                game.setScreen(home); // Ensure PlayScreen is properly implemented
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
        title.getTexture().dispose();
        background.getTexture().dispose();
        levels.getTexture().dispose();
        backicon.getTexture().dispose();
        cursorPixmap.dispose();
        if (cursor != null) {
            cursor.dispose();
        }
    }
}
