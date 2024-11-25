package com.github.angry_bird;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;

public class StoneBlock extends Block {
    public StoneBlock(World world, LevelScreen lvlscreen, float x, float y) {
        super(world, 40, new Texture("steelrod.png"), 1.2f, x, y); // Wooden blocks may have 50 health
        this.levelScreen = lvlscreen;
        this.Block_Scale = 0.85f;
        this.name = "stoneplank";
    }
}
