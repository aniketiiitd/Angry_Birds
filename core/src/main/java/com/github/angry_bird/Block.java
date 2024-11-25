package com.github.angry_bird;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
//import java.io.Serializable;

public class Block extends Hittable {
    protected float density;
    protected TextureRegion texture_region;
    protected LevelScreen levelScreen;
    protected float torque = 0;
    protected float Block_Scale;
    // private float VANISH_TIME = 1f;

    public Block(World world, int health, Texture texture, float density, float x, float y) {
        createBlock(world, x, y);
        this.health = health;
        this.texture = texture;
        this.texture_region = new TextureRegion(this.texture);
        this.density = density;
        // this.body.setLinearVelocity(0, 0); // Set linear speed to zero
        // this.body.setAngularVelocity(0.0f);

    }

    public void takeDamage(float damage, Block block) {
        this.health -= damage;
        if (this.levelScreen.currbird != null && this.levelScreen.currbird.isLaunched) {
            this.levelScreen.score += damage * 10;
        }

    }

    public void destroyBlock() {
        // Add logic to remove the block from the world and clean up
        // body.getWorld().destroyBody(body);
        levelScreen.markBodyForRemoval(this.body, this.texture);
        // this.alpha = 0;
    }

    public int getHealth() {
        return health;
    }

    public void transform(float x, float y, float angle) {
        this.body.setTransform(x, y, angle * MathUtils.degreesToRadians);
    }

    private void createBlock(World world, float x, float y) {
        // Define the body definition for the block
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Make the block dynamic to enable rotation
        bodyDef.position.set(x, y); // Set the initial position

        // Create the body in the world
        Body blockBody = world.createBody(bodyDef);

        // Define the shape for the block (plank-like shape)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.12f, 0.75f); // Set dimensions of the box (half-width, half-height in meters)

        // Define the fixture and its properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = this.density; // Set density for realistic rotation/movement
        fixtureDef.friction = 0.2f; // Adjust friction to prevent excessive sliding
        // fixtureDef.restitution = 0.3f; // Set bounce if required

        // Attach the fixture to the body
        blockBody.createFixture(fixtureDef);

        // Set user data for easy identification in contact handling
        blockBody.setUserData(this);

        // Assign the body to this instance variable
        this.body = blockBody;
        // blockBody.applyTorque(5.0f, true); // Applies a rotational force

        // Dispose of the shape after use
        shape.dispose();
    }

    // You can have different block types by extending this class
}
