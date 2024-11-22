package com.github.angry_bird_phy;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;

public class WoodenBlock extends Block {
    public WoodenBlock(World world, LevelScreen lvlscreen, float x, float y) {
        super(world, 25, new Texture("woodplank.png"), 1.0f, x, y); // Wooden blocks may have 50 health
        this.levelScreen = lvlscreen;
        this.Block_Scale = 0.75f;
    }
}
