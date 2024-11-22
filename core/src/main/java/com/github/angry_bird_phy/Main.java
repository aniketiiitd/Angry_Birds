package com.github.angry_bird_phy;

import com.badlogic.gdx.Game;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
    @Override
    public void create() {
        // setScreen(new LoadingScreen(this));
        setScreen(new HomeScreen(this));
    }
}
