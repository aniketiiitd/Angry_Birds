package com.github.angry_bird;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Pig extends Hittable{

    private Sprite pig;
    private float x, y;
    protected int weight;
    protected int health;

    public Pig(float x, float y, String str) {
        this.x = x;
        this.y = y;
        this.pig=new Sprite(new Texture(str));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(pig, x, y);
    }
    public void dispose() {
        pig.getTexture().dispose();
    }
}
