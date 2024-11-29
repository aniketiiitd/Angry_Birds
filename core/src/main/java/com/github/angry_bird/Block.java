package com.github.angry_bird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;

public class Block extends Hittable {
    protected float density;
    protected TextureRegion texture_region;
    protected LevelScreen levelScreen;
    protected float torque = 0;
    protected float Block_Scale;
    protected float Width;

    public Block(World world, int health, Texture texture, float density, float x, float y) {
        createBlock(world, x, y);
        this.health = health;
        this.texture = texture;
        this.texture_region = new TextureRegion(this.texture);
        this.density = density;

    }

    public void takeDamage(float damage, Block block) {
        this.health -= damage;
        if (this.levelScreen.currbird != null && this.levelScreen.currbird.isLaunched) {
            this.levelScreen.score += damage * 10;
        }

    }

    public void destroyBlock() {
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
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        Body blockBody = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.12f, 0.75f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = this.density;
        fixtureDef.friction = 0.2f;
        blockBody.createFixture(fixtureDef);

        blockBody.setUserData(this);
        this.body = blockBody;
        shape.dispose();
    }

}
