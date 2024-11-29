package com.github.angry_bird;
//import com.github.angry_bird.WoodenBlock;

import com.badlogic.gdx.graphics.Texture;
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

    }

    public void repositionCurrentBird() {
        this.body.setTransform(2, 0.6f + 1.0f, 0);
        this.body.setLinearVelocity(0, 0); 
        this.body.setAngularVelocity(0.0f); 
    }

    private void createBird(World world) {
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(1.9f, 0.6f + 1.0f); 

        Body birdbody = world.createBody(bodydef);
        Shape shape = this.shape;
        shape.setRadius(0.25f); 

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = this.density;
        fixtureDef.friction = 0.5f; 

        birdbody.createFixture(fixtureDef);
        this.body = birdbody;
        birdbody.setUserData(this);
        birdbody.setLinearVelocity(0, 0);
        this.repositionCurrentBird();
        shape.dispose();
    }
}
