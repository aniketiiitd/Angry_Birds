package com.github.angry_bird_phy;

import com.badlogic.gdx.physics.box2d.World;

public interface Level {

    public void createlevel(LevelScreen levelScreen, World world);
}
