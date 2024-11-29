package com.github.angry_bird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class Settings implements com.badlogic.gdx.Screen {

    private SpriteBatch batch;
    private Sprite settingsdisplay, muteicon, unmuteicon;
    private boolean val;
    // private Circle resetgamebutton;
    private Circle volumebutton;
    private Circle resetbutton;
    private Circle exitbutton;

    OrthographicCamera camera;
    private final Game game;
    private HomeScreen home;

    public Settings(Game game, HomeScreen home) {
        this.home = home;
        this.batch = new SpriteBatch();
        this.game = game;
        val = false;
    }

    @Override
    public void show() {
        Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        settingsdisplay = new Sprite(new Texture("settings_screen.png"));
        settingsdisplay.setSize(831, 508);
        volumebutton = new Circle(-138, -85, 70);
        resetbutton = new Circle(85, -85, 70);
        exitbutton = new Circle(380, 190, 60);
        muteicon = new Sprite(new Texture("muted.png"));
        muteicon.setPosition(590, 290);
        unmuteicon = new Sprite(new Texture("unmuted.png"));
        unmuteicon.setPosition(590, 290);

    }

    @Override
    public void render(float delta) {
        
        batch.begin();
        batch.draw(settingsdisplay, 370, 180);
        if (val) {
            muteicon.draw(batch);
        } else if (!val) {
            unmuteicon.draw(batch);
        }
        batch.end();

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        // // Check if the mouse is over the play button
        if (volumebutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.justTouched()) {
                
                if (!val) {
                    val = true;
                } else if (val) {
                    val = false;
                }
            }

        } else if (resetbutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.justTouched()) {
                LevelScreen.ResetGame();
            }
        } else if (exitbutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.justTouched()) {
                game.setScreen(this.home);
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
        settingsdisplay.getTexture().dispose();
        muteicon.getTexture().dispose();
        unmuteicon.getTexture().dispose();
    }


}
