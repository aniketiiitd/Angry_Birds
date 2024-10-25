package com.github.angry_bird;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Pig extends Hittable{

    private Sprite pigicon;
    private float x, y;
    protected int weight;
    protected int health;

    public Pig(float x, float y, String str) {
        this.x = x;
        this.y = y;
        this.pigicon=new Sprite(new Texture(str));
        pigicon.setPosition(x,y);
    }

    public void draw(SpriteBatch batch) {
       pigicon.draw(batch);
    }

    public void setSize(int w,int h){
        this.pigicon.setSize(w,h);
    }
    public void dispose() {
        pigicon.getTexture().dispose();
    }
    public void die(){}

    public boolean isalive(){
        return true;
    }
}
