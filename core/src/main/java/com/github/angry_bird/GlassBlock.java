package com.github.angry_bird;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;

public class GlassBlock extends Block {
    public GlassBlock(World world, LevelScreen lvlscreen, float x, float y) {
        super(world, 15, new Texture("glassplank.png"), 0.8f, x, y); // Wooden blocks may have 50 health
        this.levelScreen = lvlscreen;
        this.Block_Scale = 1.1f;
        this.name = "glassplank";
    }
}
