package com.github.angry_bird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class LevelCleared implements com.badlogic.gdx.Screen {

    private SpriteBatch batch;
    private Sprite cleareddisplay;


    private Circle nextlevel;
    private Circle restartbutton;
    private Circle exitbutton;

    OrthographicCamera camera;
    private final Game game;

    public LevelCleared(Game game) {
        this.batch = new SpriteBatch();
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cleareddisplay = new Sprite(new Texture("levelcleared.png"));

        restartbutton = new Circle(-190, -310, 55);
        //nextlevel = new Circle(-10, -200, 55);
        exitbutton=new Circle(265,-310,55);

    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0, 0);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //level.update(delta); // Update the level's game logic
        batch.begin();
        batch.draw(cleareddisplay, 550, 75);
        batch.end();

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

//        // Check if the mouse is over the play button
        if (restartbutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                game.setScreen(new LevelScreen(new Level1(), game));
            }

        }  else if (exitbutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                game.setScreen(new SelectLevel(game,new HomeScreen(game)));
            }
        }else {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
        }

    }
    @Override
    public void resize ( int width, int height){
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void hide () {
    }
    @Override
    public void dispose () {
        batch.dispose();
        cleareddisplay.getTexture().dispose();
    }

    // Other Screen methods like resize, pause, etc., would go here


}
