package com.github.angry_bird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class FailedScreen implements com.badlogic.gdx.Screen {

    private SpriteBatch batch;
    private Sprite faileddisplay;

    // private Circle r;
    private Circle restartbutton;
    private Circle exitbutton;

    OrthographicCamera camera;
    private final Game game;
    private Level level;
    // private LevelScreen lvlscreen;

    public FailedScreen(Game game, Level lvl) {
        this.batch = new SpriteBatch();
        this.game = game;
        this.level = lvl;
        LevelScreen.ClearGameState();
    }

    @Override
    public void show() {
        Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        faileddisplay = new Sprite(new Texture("failedscreen.png"));

        exitbutton = new Circle(-140, -160, 48);
        restartbutton = new Circle(110, -160, 48);

    }

    @Override
    public void render(float delta) {

        batch.begin();
        batch.draw(faileddisplay, 550, 160);
        batch.end();

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        if (restartbutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                game.setScreen(new LevelScreen(game, level));
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
        faileddisplay.getTexture().dispose();
    }

}
