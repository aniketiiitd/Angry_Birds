package com.github.angry_bird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Catapult {
    private Sprite sprite;
    Bird currentbird;

    public Catapult() {
        sprite = new Sprite(new Texture("pulledcatapult.png"));

        // Set the initial position and size
        sprite.setPosition(185, 125);
        sprite.setSize(125, 200); // Set initial size (width and height)
    }

    // Set the position of the catapult
    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }

    // Stretch the catapult by scaling it
    public void stretch(float scaleX, float scaleY) {
        sprite.setScale(scaleX, scaleY);
    }

    // Draw the sprite using the SpriteBatch
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    // Update method to handle any animation or logic
    public void update(float deltaTime) {
        // Add logic here for animations or interactions if needed
    }

    // Dispose the texture when done
    public void dispose() {
        sprite.getTexture().dispose();
    }
    public void pull(Bird bird){}
    public void release(Bird bird){}
    public void load(Bird bird){}
    public void givetrajectory(){}
}
