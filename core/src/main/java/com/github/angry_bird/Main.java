package com.github.angry_bird;

import com.badlogic.gdx.Game;

public class Main extends Game {

    @Override
    public void create() {
        // Set the initial screen to the Loading screen
        this.setScreen(new loadingscreen(this));
        //this.setScreen(new LevelScreen(new Level1(),this));
    }

    @Override
    public void render() {
        super.render(); // This will call render() on the current screen
    }

    @Override
    public void dispose() {
        // Dispose of any resources if needed
    }
}
