package com.github.angry_bird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements com.badlogic.gdx.Screen {

    private final Game game; 
    // private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sprite sprite;
    private float loadingTime = 0f; // A simple timer

    public LoadingScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture("loading.png"));

    }

    @Override
    public void render(float delta) {
        // Clear the screen with black color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch); 
        batch.end();

        loadingTime += delta;
        if (loadingTime > 3.0f) {
            game.setScreen(new HomeScreen(game)); 
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
        sprite.getTexture().dispose();
    }
}
