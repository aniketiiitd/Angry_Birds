package com.github.angry_bird_phy;

import java.io.Serializable;

public class HittableState implements Serializable {
    private static final long serialVersionUID = 1L;

    public float posX, posY; // Position
    public int health; // Health
    public float angle;

    public HittableState(float posX, float posY, int health, float angle) {
        this.posX = posX;
        this.posY = posY;
        this.health = health;
        this.angle = angle;
    }
}
