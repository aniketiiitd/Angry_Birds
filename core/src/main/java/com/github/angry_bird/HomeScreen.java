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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HomeScreen implements com.badlogic.gdx.Screen {

    private final Game game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sprite background;
    private Sprite playicon, settingsicon, exiticon;
    private Sprite title;
    private Rectangle playButton;
    private Circle settingsbutton, exitbutton;
    private Pixmap cursorPixmap;
    private com.badlogic.gdx.graphics.Cursor cursor;

    public HomeScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        background = new Sprite(new Texture("b1.png"));
        playicon = new Sprite(new Texture("playicon.png"));
        settingsicon = new Sprite(new Texture("settingsicon.png"));
        exiticon = new Sprite(new Texture("exitbutton.png"));
        title = new Sprite(new Texture("title.png"));
     
        playButton = new Rectangle(-170, -130, 335, 170);
        settingsbutton = new Circle(-720, -380, 50);
        exitbutton = new Circle(720, -380, 45);

        // Load the custom cursor texture
        cursorPixmap = new Pixmap(Gdx.files.internal("cursor.png"));
        cursor = Gdx.graphics.newCursor(cursorPixmap, 16, 0);
        Gdx.graphics.setCursor(cursor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        batch.draw(playicon, 620, 330);
        batch.draw(settingsicon, 30, 10);
        batch.draw(title, 400, 700);
        batch.draw(exiticon, 1470, 10);
        batch.end();

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        if (playButton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                game.setScreen(new SelectLevel(game, this)); 
            }
        } else if (settingsbutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                game.setScreen(new Settings(game, this)); 
            }
        } else if (exitbutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                // game.setScreen(new Settings(game)); 
                Gdx.app.exit();
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
        background.getTexture().dispose();
        playicon.getTexture().dispose();
        settingsicon.getTexture().dispose();
        title.getTexture().dispose();
        exiticon.getTexture().dispose();
        cursorPixmap.dispose();
        if (cursor != null) {
            cursor.dispose();
        }
    }
}
