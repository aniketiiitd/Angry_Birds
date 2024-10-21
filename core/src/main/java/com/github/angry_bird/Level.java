package com.github.angry_bird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Level {
    protected ArrayList<Bird> birds;
    protected ArrayList<Pig> pigs;
    protected ArrayList<Block> blocks;
    protected Catapult catapult;
    private int score;
    private Sprite background;

    public Level() {
        birds = new ArrayList<>();
        pigs = new ArrayList<>();
        blocks = new ArrayList<>();
        catapult = new Catapult();
        background=new Sprite(new Texture("levelbackdrop.png"));
    }

    public void render(SpriteBatch batch) {
        // Render all entities
        background.draw(batch);

        for (Bird bird : birds) {
            bird.draw(batch);
        }
        for (Pig pig : pigs) {
            pig.draw(batch);
        }
        for (Block block : blocks) {
            block.draw(batch);
        }
        catapult.draw(batch);
    }


    public void addBird(Bird bird) {
        birds.add(bird);
    }

    public void addPig(Pig pig) {
        pigs.add(pig);
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void dispose() {
        // Dispose of resources for all entities
        for (Bird bird : birds) {
            bird.dispose();
        }
        for (Pig pig : pigs) {
            pig.dispose();
        }
        for (Block block : blocks) {
            block.dispose();
        }
        catapult.dispose();
    }
}
