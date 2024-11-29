package com.github.angry_bird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
public class BlackBird extends Bird {
    public BlackBird(World world) {
        super(world, new Texture("blackbird.png"), "Black", new CircleShape(), 1.5f, 0.4f);
        BIRD_SCALE = 0.5f;
        spped_scale = -7f;
    }
}
