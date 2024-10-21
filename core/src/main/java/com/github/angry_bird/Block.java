package com.github.angry_bird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Block extends Hittable{

    private Sprite block;
    private float x, y;
    protected int hardness;
    protected int material;

    public Block(float x, float y, String str) {
        this.x = x;
        this.y = y;
        this.block=new Sprite(new Texture(str));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(block, x, y);
    }
    public void dispose() {
        block.getTexture().dispose();
    }
}
