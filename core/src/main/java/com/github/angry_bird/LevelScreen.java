package com.github.angry_bird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LevelScreen implements com.badlogic.gdx.Screen{

        private Level level;
        private SpriteBatch batch;
        private Sprite pauseicon;
        OrthographicCamera camera;
        private Circle pausebutton;
        private final Game game;

        public LevelScreen(Level level, Game game) {
            this.level = level;
            this.batch = new SpriteBatch();
            this.game=game;
        }

    @Override
    public void show() {
        Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
        camera= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        pauseicon=new Sprite(new Texture("pauseicon.png"));
        pauseicon.setSize(90,90);
        pauseicon.setPosition(1487,780);

        pausebutton=new Circle(735,380,45);

    }

    @Override
        public void render(float delta) {
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            //level.update(delta); // Update the level's game logic
            batch.begin();
            level.render(batch); // Render the level's entities
            pauseicon.draw(batch);
            batch.end();

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        // Check if the mouse is over the play button
        if (pausebutton.contains(mousePos.x, mousePos.y)) {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Hand);
            if (Gdx.input.isTouched()) {
                game.setScreen(new PauseScreen(game,this));
            }

        } else {
            Gdx.graphics.setSystemCursor(com.badlogic.gdx.graphics.Cursor.SystemCursor.Arrow);
        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
        @Override
        public void dispose() {
            batch.dispose();
            level.dispose();
        }

        // Other Screen methods like resize, pause, etc., would go here
    }


