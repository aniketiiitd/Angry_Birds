package com.github.angry_bird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bird {

    protected Sprite birdicon;
    private float x, y;
    protected int weight;
    protected int velocity;

    public Bird(float x, float y, String str) {
        this.x = x;
        this.y = y;
        this.birdicon=new Sprite(new Texture(str));
        birdicon.setPosition(x, y);


    }

    public void draw(SpriteBatch batch) {
        birdicon.draw(batch);
    }

    public void dispose() {
        birdicon.getTexture().dispose();
    }

    public void setSize(int h, int w) {
        this.birdicon.setSize(h,w);
    }
}
