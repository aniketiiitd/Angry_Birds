package com.github.angry_bird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Block extends Hittable{

    private Sprite blockicon;
    private float x, y;
    protected int hardness;
    protected int material;

    public Block(float x, float y, String str,int val) {
        this.x = x;
        this.y = y;
        this.blockicon=new Sprite(new Texture(str));
        this.blockicon.setPosition(x,y);
        this.blockicon.setRotation(val);
    }

    public void draw(SpriteBatch batch) {
        blockicon.draw(batch);
    }
    public void setSize(int w,int h){
        this.blockicon.setSize(w,h);
    }
    public void dispose() {
        blockicon.getTexture().dispose();
    }
}
