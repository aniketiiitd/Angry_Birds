package com.github.angry_bird;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;

public class WoodenBlock extends Block {
    public WoodenBlock(World world, LevelScreen lvlscreen, float x, float y) {
        super(world, 15, new Texture("woodplank.png"), 1.0f, x, y); // Wooden blocks may have 50 health
        this.levelScreen = lvlscreen;
        this.Block_Scale = 0.85f;
        this.name = "woodplank";
        this.Width = 0.12f;
    }
}
