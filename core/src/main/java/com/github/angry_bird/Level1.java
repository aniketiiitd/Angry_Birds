package com.github.angry_bird;

import com.badlogic.gdx.physics.box2d.World;

public class Level1 implements Level {

    @Override
    public void createlevel(LevelScreen levelScreen, World world) {
        levelScreen.birdQueue.add(new RedBird(world));
        levelScreen.birdQueue.add(new YellowBird(world));
        levelScreen.birdQueue.add(new YellowBird(world));
        levelScreen.birdQueue.add(new RedBird(world));

        levelScreen.pigList.add(new NormalPig(world, levelScreen, 12.35f, 1.7f));
        levelScreen.pigList.add(new KingPig(world, levelScreen, 12.3f, 3.0f));
        // levelScreen.pigList.add(new FatPig(world, levelScreen, 11.1f, 1.7f));

        levelScreen.blockList.add(new GlassBlock(world, levelScreen, 11.8f, 1.7f));
        levelScreen.blockList.add(new StoneBlock(world, levelScreen, 12.93f, 1.7f));

        GlassBlock block1 = new GlassBlock(world, levelScreen, 11.5f, 2.7f);
        block1.transform(12.3f, 2.45f, 90);
        levelScreen.blockList.add(block1);
        levelScreen.blockList.add(new StoneBlock(world, levelScreen, 11.8f, 2.95f));
    }
}
