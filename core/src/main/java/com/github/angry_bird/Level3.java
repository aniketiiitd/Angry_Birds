package com.github.angry_bird;

import com.badlogic.gdx.physics.box2d.World;

public class Level3 implements Level {

    @Override
    public void createlevel(LevelScreen levelScreen, World world) {

        // birdQueue.add(new BlueBird(world)); // A new type of bird for variety
        levelScreen.birdQueue.add(new RedBird(world));
        levelScreen.birdQueue.add(new YellowBird(world));
        levelScreen.birdQueue.add(new RedBird(world));
        levelScreen.birdQueue.add(new YellowBird(world));
        levelScreen.birdQueue.add(new BlackBird(world));
        levelScreen.birdQueue.add(new YellowBird(world));

        // backgroundTexture = new Texture("level2backdrop.png"); // Updated background
        // for Level 2
        // catapultTexture = new Texture("catapult.png");

        // Adding more pigs to make the level harder
        levelScreen.pigList.add(new NormalPig(world, levelScreen, 12.5f, 1.5f));
        levelScreen.pigList.add(new KingPig(world, levelScreen, 12.55f, 3.0f));
        levelScreen.pigList.add(new KingPig(world, levelScreen, 13.6f, 3.0f));
        levelScreen.pigList.add(new NormalPig(world, levelScreen, 13.85f, 1.5f));
        levelScreen.pigList.add(new FatPig(world, levelScreen, 8.6f, 3.5f)); // A stronger pig for added difficulty
        levelScreen.pigList.add(new FatPig(world, levelScreen, 8.6f, 1.5f));

        // Adding more blocks with varied transformations
        levelScreen.blockList.add(new StoneBlock(world, levelScreen, 11.9f, 1.5f));
        levelScreen.blockList.add(new WoodenBlock(world, levelScreen, 13.2f, 1.5f));
        levelScreen.blockList.add(new StoneBlock(world, levelScreen, 14.4f, 1.5f)); // A new type of block for variety
        levelScreen.blockList.add(new WoodenBlock(world, levelScreen, 12.0f, 3.2f));
        levelScreen.blockList.add(new GlassBlock(world, levelScreen, 13.2f, 3.5f));
        levelScreen.blockList.add(new WoodenBlock(world, levelScreen, 14.4f, 3.2f));

        GlassBlock block1 = new GlassBlock(world, levelScreen, 13.5f, 3.0f);
        block1.transform(12.5f, 2.5f, 90); // Rotated block for structural complexity
        levelScreen.blockList.add(block1);

        GlassBlock block2 = new GlassBlock(world, levelScreen, 13.5f, 3.0f);
        block2.transform(13.8f, 2.5f, 90); // Rotated block for structural complexity
        levelScreen.blockList.add(block2);

        levelScreen.blockList.add(new StoneBlock(world, levelScreen, 9.3f, 1.5f));
        levelScreen.blockList.add(new StoneBlock(world, levelScreen, 8.0f, 1.5f));

        StoneBlock block3 = new StoneBlock(world, levelScreen, 13.5f, 3.0f);
        block3.transform(8.65f, 2.5f, 90); // Rotated block for structural complexity
        levelScreen.blockList.add(block3);
    }
}
