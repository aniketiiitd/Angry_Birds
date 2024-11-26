package com.github.angry_bird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class FatPig extends Pig {
    public FatPig(World world, LevelScreen lvlscreen, float x, float y) {
        super(55, new Texture("fatpig.png"), world, 1.2f, x, y, 0.25f); // Type A pig with 100 health
        this.Pig_Scale = 0.35f;
        this.levelScreen = lvlscreen;
        this.name = "fatpig";

    }
}