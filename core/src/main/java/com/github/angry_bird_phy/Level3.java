package com.github.angry_bird_phy;

import com.badlogic.gdx.physics.box2d.World;

public class Level3 implements Level {

    @Override
    public void createlevel(LevelScreen levelScreen, World world) {

        // birdQueue.add(new BlueBird(world)); // A new type of bird for variety
        levelScreen.birdQueue.add(new RedBird(world));
        levelScreen.birdQueue.add(new YellowBird(world));

        // backgroundTexture = new Texture("level2backdrop.png"); // Updated background
        // for Level 2
        // catapultTexture = new Texture("catapult.png");

        // Adding more pigs to make the level harder
        levelScreen.pigList.add(new NormalPig(world, levelScreen, 12.5f, 1.5f));
        levelScreen.pigList.add(new NormalPig(world, levelScreen, 13.0f, 3.0f));
        levelScreen.pigList.add(new NormalPig(world, levelScreen, 14.0f, 2.0f));
        levelScreen.pigList.add(new FatPig(world, levelScreen, 10.5f, 3.5f)); // A stronger pig for added difficulty

        // Adding more blocks with varied transformations
        levelScreen.blockList.add(new WoodenBlock(world, levelScreen, 12.0f, 1.5f));
        levelScreen.blockList.add(new WoodenBlock(world, levelScreen, 13.2f, 1.5f));
        levelScreen.blockList.add(new StoneBlock(world, levelScreen, 14.0f, 1.5f)); // A new type of block for variety
        WoodenBlock block1 = new WoodenBlock(world, levelScreen, 11.5f, 2.5f);
        block1.transform(13.0f, 2.5f, 45); // Rotated block for structural complexity
        levelScreen.blockList.add(block1);

        levelScreen.blockList.add(new StoneBlock(world, levelScreen, 13f, 3.0f));

    }
}
