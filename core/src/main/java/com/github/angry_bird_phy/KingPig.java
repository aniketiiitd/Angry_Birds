package com.github.angry_bird_phy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class KingPig extends Pig {
    public KingPig(World world, LevelScreen lvlscreen, float x, float y) {
        super(70, new Texture("kingpig.png"), world, 1.2f, x, y, 0.5f); // Type A pig with 100 health
        this.Pig_Scale = 0.5f;
        this.levelScreen = lvlscreen;

    }
}