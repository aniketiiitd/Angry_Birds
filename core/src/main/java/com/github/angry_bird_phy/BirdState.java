package com.github.angry_bird_phy;

import java.io.Serializable;
import com.badlogic.gdx.graphics.Texture;

public class BirdState implements Serializable {
    private static final long serialVersionUID = 1L;

    public float posX, posY; // Position
    public float velX, velY; // Velocity
    public float health;
    public boolean isLaunched;
    public String name;
    public Texture birdTexture;

    public BirdState(String name, float posX, float posY, float velX, float velY, float health, boolean isLaunched) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.health = health;
        this.isLaunched = isLaunched;
        // this.birdTexture = texture;
    }
}
