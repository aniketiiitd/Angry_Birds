package com.github.angry_bird_phy;

// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Screen;
// import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.graphics.g2d.TextureRegion;
// import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
// import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
// import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class BlackBird extends Bird {
    public BlackBird(World world) {
        super(world, new Texture("blackbird.png"), "Black", new CircleShape(), 1.5f, 0.4f);
        BIRD_SCALE = 0.5f;
        spped_scale = -7f;
    }

    @Override
    public void launch(Vector2 velocity) {
        // body.setLinearVelocity(velocity);
        // Black bird specific launch behavior (e.g., explosive after impact)
    }

    @Override
    public void update(float delta) {
        // Black bird specific updates
    }
}
