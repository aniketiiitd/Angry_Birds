package com.github.angry_bird;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Pig extends Hittable {

    protected TextureRegion texture_region;
    protected float Pig_Scale;
    protected LevelScreen levelScreen;
    protected float density;
    protected float radius;

    public Pig(int health, Texture texture, World world, Float density, Float x, Float y, float radius) {
        // this.body = body;
        this.health = health;
        this.texture = texture;
        this.texture_region = new TextureRegion(this.texture);
        this.density = density;
        this.radius = radius;
        createPig(world, x, y);
    }

    public void takeDamage(float damage) {
        this.health -= damage;
        // System.out.printf("health: %d\n", health);
        if (this.levelScreen.currbird != null && this.levelScreen.currbird.isLaunched) {
            this.levelScreen.score += damage * 10;
        }

    }

    public void destroyPig() {
        body.getWorld().destroyBody(body);
    }

    public int getHealth() {
        return health;
    }

    public void transform(float x, float y, float angle) {
        this.body.setTransform(x, y, angle * MathUtils.degreesToRadians);
    }

    private void createPig(World world, Float x, Float y) {

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(x, y);

        Body pigbody = world.createBody(bodydef);
        Shape shape = new CircleShape();
        shape.setRadius(this.radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = this.density;
        fixtureDef.friction = 0.5f;

        pigbody.createFixture(fixtureDef);
        this.body = pigbody;
        pigbody.setUserData(this);

        shape.dispose();
    }

}
