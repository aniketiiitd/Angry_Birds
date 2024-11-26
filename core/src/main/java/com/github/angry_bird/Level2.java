package com.github.angry_bird;

import com.badlogic.gdx.physics.box2d.World;

public class Level2 implements Level {

    @Override
    public void createlevel(LevelScreen levelScreen, World world) {

        // Adding more birds to the queue for Level 2
        LevelScreen.ClearGameState();
        levelScreen.birdQueue.add(new RedBird(world));
        levelScreen.birdQueue.add(new BlackBird(world));
        levelScreen.birdQueue.add(new YellowBird(world));
        // birdQueue.add(new BlueBird(world)); // A new type of bird for variety
        levelScreen.birdQueue.add(new RedBird(world));
        levelScreen.birdQueue.add(new BlackBird(world));

        // backgroundTexture = new Texture("level2backdrop.png"); // Updated background
        // for Level 2
        // catapultTexture = new Texture("catapult.png");

        // Adding more pigs to make the level harder
        levelScreen.pigList.add(new NormalPig(world, levelScreen, 12.6f, 1.5f));
        levelScreen.pigList.add(new NormalPig(world, levelScreen, 13.8f, 1.5f));
        //levelScreen.pigList.add(new NormalPig(world, levelScreen, 14.0f, 2.0f));
        levelScreen.pigList.add(new FatPig(world, levelScreen, 12.6f, 3.1f)); // A stronger pig for added difficulty
        levelScreen.pigList.add(new KingPig(world, levelScreen, 13.8f, 3.1f));

        // Adding more blocks with varied transformations
        levelScreen.blockList.add(new WoodenBlock(world, levelScreen, 12.0f, 1.5f));
        levelScreen.blockList.add(new WoodenBlock(world, levelScreen, 13.2f, 1.5f));
        levelScreen.blockList.add(new WoodenBlock(world, levelScreen, 14.4f, 1.5f));
        //levelScreen.blockList.add(new StoneBlock(world, levelScreen, 12.0f, 1.5f)); // A new type of block for variety
        StoneBlock block1 = new StoneBlock(world, levelScreen, 12.0f, 3.0f);
        block1.transform(12.4f, 3.0f, 90); // Rotated block for structural complexity
        levelScreen.blockList.add(block1);

        StoneBlock block2 = new StoneBlock(world, levelScreen, 13.2f, 3.0f);
        block2.transform(14.0f, 3.0f, 90); // Rotated block for structural complexity
        levelScreen.blockList.add(block2);

        /*block1 = new WoodenBlock(world, levelScreen, 0, 0);
        block1.transform(14.0f, 3.5f, 30);
        levelScreen.blockList.add(block1);

        levelScreen.blockList.add(new WoodenBlock(world, levelScreen, 12.5f, 3.0f));
        levelScreen.blockList.add(new StoneBlock(world, levelScreen, 13f, 3.0f));
        levelScreen.blockList.add(new WoodenBlock(world, levelScreen, 14.5f, 4.0f));
*/
    }
}
