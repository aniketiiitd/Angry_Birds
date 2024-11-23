package com.github.angry_bird_phy;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;

public class Level1 implements Level {

    private static final float FIXED_ROTATION_TIMEOUT = 2.0f;

    @Override
    public void createlevel(LevelScreen levelScreen, World world) {
        levelScreen.birdQueue.add(new RedBird(world));
        levelScreen.birdQueue.add(new YellowBird(world));
        levelScreen.birdQueue.add(new YellowBird(world));
        levelScreen.birdQueue.add(new RedBird(world));

        /*
        levelScreen.pigList.add(new NormalPig(world, levelScreen, 12.35f, 1.7f));
        levelScreen.pigList.add(new KingPig(world, levelScreen, 12.3f, 3.0f));
        levelScreen.pigList.add(new FatPig(world, levelScreen, 11.1f, 1.7f));

         */


        NormalPig normalPig = new NormalPig(world, levelScreen, 12.35f, 1.7f);
        normalPig.getBody().setFixedRotation(true);
        normalPig.getBody().setType(BodyDef.BodyType.StaticBody); // Temporarily static
        levelScreen.pigList.add(normalPig);

        KingPig kingPig = new KingPig(world, levelScreen, 12.3f, 3.0f);
        kingPig.getBody().setFixedRotation(true);
        kingPig.getBody().setType(BodyDef.BodyType.StaticBody);
        levelScreen.pigList.add(kingPig);

        FatPig fatPig = new FatPig(world, levelScreen, 11.1f, 1.7f);
        fatPig.getBody().setFixedRotation(true);
        fatPig.getBody().setType(BodyDef.BodyType.StaticBody);
        levelScreen.pigList.add(fatPig);


        /*
        levelScreen.blockList.add(new GlassBlock(world, levelScreen, 11.8f, 1.7f));
        levelScreen.blockList.add(new StoneBlock(world, levelScreen, 12.93f, 1.7f));

        GlassBlock block1 = new GlassBlock(world, levelScreen, 11.5f, 2.7f);
        block1.transform(12.3f, 2.45f, 90);
        levelScreen.blockList.add(block1);
        levelScreen.blockList.add(new StoneBlock(world, levelScreen, 11.8f, 2.95f));

         */

        GlassBlock glassBlock1 = new GlassBlock(world, levelScreen, 11.8f, 1.7f);
        glassBlock1.getBody().getFixtureList().get(0).setFriction(0.8f);
        glassBlock1.getBody().getFixtureList().get(0).setDensity(2.0f);
        glassBlock1.getBody().resetMassData();
        levelScreen.blockList.add(glassBlock1);

        StoneBlock stoneBlock1 = new StoneBlock(world, levelScreen, 12.93f, 1.7f);
        stoneBlock1.getBody().getFixtureList().get(0).setFriction(0.9f);
        stoneBlock1.getBody().getFixtureList().get(0).setDensity(3.0f);
        stoneBlock1.getBody().resetMassData();
        levelScreen.blockList.add(stoneBlock1);

        // Create and transform block with stabilization
        GlassBlock block1 = new GlassBlock(world, levelScreen, 11.5f, 2.7f);
        block1.getBody().getFixtureList().get(0).setFriction(0.8f);
        block1.getBody().getFixtureList().get(0).setDensity(2.0f);
        block1.getBody().setFixedRotation(true);
        block1.transform(12.3f, 2.45f, 90);
        levelScreen.blockList.add(block1);

        StoneBlock stoneBlock2 = new StoneBlock(world, levelScreen, 11.8f, 2.95f);
        stoneBlock2.getBody().getFixtureList().get(0).setFriction(0.9f);
        stoneBlock2.getBody().getFixtureList().get(0).setDensity(3.0f);
        stoneBlock2.getBody().resetMassData();
        levelScreen.blockList.add(stoneBlock2);

        // Schedule physics state changes after initialization
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (Pig pig : levelScreen.pigList) {
                    pig.getBody().setType(BodyDef.BodyType.DynamicBody);
                    pig.getBody().setFixedRotation(false);
                    pig.getBody().setAngularDamping(0.8f);
                }

                // Handle blocks if they have similar getBody() method
                // ... (block physics update code)
            }
        }, FIXED_ROTATION_TIMEOUT);
    }
}
