package com.github.angry_bird_phy;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.physics.box2d.*;

public class Hittable {
    protected Body body;
    protected int health;
    protected float vanishTimer = 0;
    protected float alpha = 1f;
    protected Texture texture;
}
