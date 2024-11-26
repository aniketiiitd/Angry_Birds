package com.github.angry_bird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class NormalPig extends Pig {
    public NormalPig(World world, LevelScreen lvlscreen, float x, float y) {
        super(25, new Texture("plainpig.png"), world, 1.0f, x, y, 0.33f); // Type A pig with 100 health
        this.Pig_Scale = 0.65f;
        this.levelScreen = lvlscreen;
        this.name="normalpig";

    }
}