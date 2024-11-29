package com.github.angry_bird;
//import com.github.angry_bird.WoodenBlock;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Bird {
    protected Body body;
    protected Texture texture;
    protected String name;
    protected boolean isLaunched;
    protected float birdAlpha = 1f;
    protected float BIRD_SCALE;
    protected float spped_scale;
    protected float density;
    private Shape shape;
    protected double moInertia = 1;
    protected boolean hashitonce = false;
    protected float health = 10;
    protected float radius;

    public Bird(World world, Texture texture, String name, Shape shape, float density, float radius) {
        // this.body = body;
        this.texture = texture;
        this.name = name;
        this.shape = shape;
        this.density = density;
        this.createBird(world);
        this.moInertia = (1 / 2) * (this.density);
        this.radius = radius;
        // this.body.setTransform(2, 0.6f + 1.0f, 0);
        // this.body.setLinearVelocity(0, 0); // Set linear speed to zero
        // this.body.setAngularVelocity(0.0f); // Set angular speed to zero

    }

    public void repositionCurrentBird() {
        this.body.setTransform(2, 0.6f + 1.0f, 0);
        this.body.setLinearVelocity(0, 0); // Set linear speed to zero
        this.body.setAngularVelocity(0.0f); // Set angular speed to zero
    }

    private void createBird(World world) {
        // Initially set the bird as Kinematic to keep it still on the catapult
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody; // Keep it kinematic until the launch
        bodydef.position.set(1.9f, 0.6f + 1.0f); // Position the bird at the catapult

        Body birdbody = world.createBody(bodydef);
        Shape shape = this.shape;
        shape.setRadius(0.25f); // Default size (radius 0.25 meters)

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = this.density;
        fixtureDef.friction = 0.5f; // Ensure low friction so that the bird doesn't slide prematurely
        // fixtureDef.restitution = 0.5f;

        birdbody.createFixture(fixtureDef);
        this.body = birdbody;
        birdbody.setUserData(this);
        birdbody.setLinearVelocity(0, 0);
        this.repositionCurrentBird();
        shape.dispose();
    }
}
