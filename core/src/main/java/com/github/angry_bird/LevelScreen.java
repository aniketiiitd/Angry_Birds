package com.github.angry_bird;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Timer;
//import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import com.badlogic.gdx.graphics.Cursor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LevelScreen implements Screen, ContactListener {
    private Vector2 releaseVelocity;
    private Vector2 initialTouchPosition;
    private static final float WORLD_WIDTH = 16.0f; // Adjust for Box2D units (meters)
    private static final float WORLD_HEIGHT = 9.0f;
    private static final float GROUND_Y = 0.6f; // Y position for ground surface
    // private static final float currbird.BIRD_SCALE = 0.25f; // Scale factor for
    // the bird texture
    private static final float VANISH_TIME = 0.9f; // Time before bird vanishes in seconds
    private static final float hittable_VANISH_TIME = 0.5f;
    private static final int TRAJECTORY_POINTS = 20; // Number of points in the trajectory
    private static final float TIME_STEP = 0.1f; // Time between points in seconds
    ArrayList<Body> bodiesToRemove = new ArrayList<>();
    ArrayList<Texture> texturesToRemove = new ArrayList<>();
    protected Queue<Bird> birdQueue = new LinkedList<>();
    protected ArrayList<Pig> pigList = new ArrayList<>();
    protected ArrayList<Block> blockList = new ArrayList<>();
    private ArrayList<Hittable> blocktoremove = new ArrayList<>();
    private ArrayList<Hittable> pigtoremove = new ArrayList<>();

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private SpriteBatch batch;

    protected Bird currbird;
    private Texture birdTexture;
    private Texture backgroundTexture;
    private Texture catapultTexture;

    private MouseJoint mouseJoint;
    private boolean isDragging = false;
    // private boolean isLaunched = false; // Flag to prevent re-launching
    private float vanishTimer = 0f; // Timer for bird disappearance
    // private float birdAlpha = 1f;
    private TextureRegion birdRegion;
    protected int score = 0;
    private BitmapFont font;
    private BitmapFont birdcount;
    private Game game;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    // private ShapeRenderer sr1;
    private Sprite pauseicon;
    // private Circle pausebutton;
    OrthographicCamera camera;
    protected Level level;
    private boolean ispaused = false;

    private final Vector2 catapultAnchorLeft = new Vector2(220, GROUND_Y + 200); // Example coordinates
    private final Vector2 catapultAnchorRight = new Vector2(280, GROUND_Y + 200);
    private static String filename;

    public LevelScreen(Game game, Level lvl) {
        this.game = game;
        this.level = lvl;
        if (lvl instanceof Level1) {
            filename = "gamestate1.sav";
        } else if (lvl instanceof Level2) {
            filename = "gamestate2.sav";
        } else if (lvl instanceof Level3) {
            filename = "gamestate3.sav";
        }
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true); // Gravity
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        world.setContactListener(this); // Registers the current class as the listener

        initialTouchPosition = new Vector2();
        releaseVelocity = new Vector2();

        // Create ground with friction to stop the bird from sliding
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0, GROUND_Y); // Correct Y position of the ground
        Body groundBody = world.createBody(groundBodyDef);

        EdgeShape groundEdge = new EdgeShape();
        groundEdge.set(-WORLD_WIDTH / 4, GROUND_Y, WORLD_WIDTH + 1f, GROUND_Y); // Ground spans full screen width
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundEdge;
        groundFixtureDef.friction = 0.5f; // Friction to prevent sliding
        groundBody.createFixture(groundFixtureDef);
        groundBody.setUserData("ground");
        groundEdge.dispose();

        // Create screen boundaries (left and right)
        BodyDef boundaryBodyDef = new BodyDef();
        Body boundaryBody = world.createBody(boundaryBodyDef);

        // Left edge boundary
        EdgeShape leftEdge = new EdgeShape();
        leftEdge.set(-WORLD_WIDTH / 4, 0, -WORLD_WIDTH / 4, WORLD_HEIGHT);
        boundaryBody.createFixture(leftEdge, 0);
        leftEdge.dispose();

        // Right edge boundary
        EdgeShape rightEdge = new EdgeShape();
        rightEdge.set(WORLD_WIDTH * 2, 0, WORLD_WIDTH * 2, WORLD_HEIGHT);
        boundaryBody.createFixture(rightEdge, 0);
        rightEdge.dispose();

        // Load textures
        backgroundTexture = new Texture("levelbackdrop.png");
        catapultTexture = new Texture("catapult.png");

        if (!ispaused) {

            level.createlevel(this, world);
            currbird = birdQueue.poll();
            birdTexture = currbird.texture;
            birdRegion = new TextureRegion(birdTexture);

        }

        font = new BitmapFont(); // Use default font; you can also load custom fonts
        font.getData().setScale(2); // Scale up the font if needed

        birdcount = new BitmapFont();
        birdcount.getData().setScale(2f);

        pauseicon = new Sprite(new Texture("pauseicon.png"));
        pauseicon.setSize(90, 90);
        pauseicon.setPosition(1487, 780);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (ispaused) {
            this.loadGameState(LevelScreen.loadFromFile());
        }
    }

    private void pauseinput() {
        Vector2 touchPos = new Vector2(Gdx.input.getX() / 100f, (Gdx.graphics.getHeight() - Gdx.input.getY()) / 70f);

        // Pause button clickable area (in world units)
        float pauseButtonX = 1530f / 100f; // Convert screen coordinates to world units
        float pauseButtonY = 830f / 70f; // Convert screen coordinates to world units
        float pauseButtonRadius = 45f / 100f; // Convert radius to world units

        // Check if touch is within the pause button area
        if (touchPos.dst(pauseButtonX, pauseButtonY) <= pauseButtonRadius) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);

            // Handle click event on pause button
            if (Gdx.input.isTouched()) {
                // System.out.println("Pause button clicked!");
                // if (currbird.isLaunched ) {

                // }
                pause();
                saveToFile(saveGameState());
                game.setScreen(new PauseScreen(game, level));
                // Add your pause logic here, e.g., switch to a pause menu or stop the game loop
            }
        } else {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }
    }

    private void handleInput() {
        Vector2 touchPos = new Vector2(Gdx.input.getX() / 100f, (Gdx.graphics.getHeight() - Gdx.input.getY()) / 70f);

        // Check if touch is within the catapult area
        if (isTouchInCatapultArea(touchPos)) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);

            if (Gdx.input.isTouched() && !currbird.isLaunched) {
                if (!isDragging) {
                    initialTouchPosition.set(touchPos);

                    // Create a static body with a slightly higher Y position as anchor for the
                    // MouseJoint
                    BodyDef bodyDef = new BodyDef();
                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set(currbird.body.getPosition().x, currbird.body.getPosition().y + 0.1f);
                    Body anchorBody = world.createBody(bodyDef);

                    // Create a MouseJoint to attach the bird to the touch position
                    MouseJointDef jointDef = new MouseJointDef();
                    jointDef.bodyA = anchorBody;
                    jointDef.bodyB = currbird.body;
                    jointDef.target.set(currbird.body.getPosition());
                    jointDef.maxForce = 500f * currbird.body.getMass();
                    mouseJoint = (MouseJoint) world.createJoint(jointDef);
                    isDragging = true;
                } else {
                    // Update joint target only if touch moved significantly
                    if (touchPos.dst(mouseJoint.getTarget()) > 0.1f) {
                        mouseJoint.setTarget(touchPos);
                        releaseVelocity.set(currbird.body.getPosition()).sub(initialTouchPosition)
                                .scl(currbird.spped_scale);

                    }
                }
            }
        } else {
            // Reset cursor to default when not over catapult area
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }

        // Launching logic
        if (isDragging && !Gdx.input.isTouched()) {
            // System.out.println(currbird);
            // Calculate velocity for launch based on the dragging distance
            Vector2 releasePosition = currbird.body.getPosition();
            releaseVelocity.set(releasePosition).sub(initialTouchPosition).scl(currbird.spped_scale);

            currbird.body.setLinearVelocity(releaseVelocity);

            world.destroyJoint(mouseJoint); // Destroy the joint after launch
            isDragging = false;
            currbird.isLaunched = true; // Mark as launched to prevent re-launching
        }
    }

     private void special_move_yellow() {
         Vector2 currvelocity = currbird.body.getLinearVelocity();
         currbird.body.setLinearVelocity(currvelocity.x * 1.25f, currvelocity.y * 1.25f);
     }

    @Override
    public void render(float delta) {
        if (ispaused) {
            // System.out.println("is paused");
            return;
        }
        ScreenUtils.clear(1, 1, 1, 1);

        // Update the Box2D world
        world.step(1 / 60f, 6, 2);

        for (Body bodys : bodiesToRemove) {
            // System.out.println(body);
            world.destroyBody(bodys);
        }
        bodiesToRemove.clear();
        for (Texture textures : texturesToRemove) {
            // System.out.println(texture);
            textures.dispose(); // Dispose of the texture when you're done with it
        }
        texturesToRemove.clear();

         if (currbird != null && currbird.name.equals("Yellow") && !currbird.hashitonce && currbird.isLaunched
                 && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
             special_move_yellow();
         }

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(catapultTexture, 170, -30, catapultTexture.getWidth() / 2.2f, catapultTexture.getHeight() / 2.2f);

        if (!pigList.isEmpty()) {
            for (Pig pig : pigList) {
                if (pig != null) {
                    batch.setColor(1f, 1f, 1f, pig.alpha);
                    Vector2 pigPosition = pig.body.getPosition();
                    float pigscale = pig.Pig_Scale;
                    batch.draw(pig.texture_region,
                            pigPosition.x * 100 - pig.texture_region.getRegionWidth() / 2f * pigscale,
                            pigPosition.y * 100 - pig.texture_region.getRegionHeight() / 2f * pigscale,
                            pig.texture_region.getRegionWidth() * pigscale / 2f,
                            pig.texture_region.getRegionHeight() * pigscale / 2f, // Origin
                            pig.texture_region.getRegionWidth() * pigscale,
                            pig.texture_region.getRegionHeight() * pigscale,
                            1f, 1f,
                            pig.body.getAngle() * MathUtils.radiansToDegrees);
                }
                if (pig != null) {
                    check_outofscreen(pig);
                }
            }
            pigList.removeAll(pigtoremove);
            pigtoremove.clear();

            if (pigList.isEmpty()) {

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (currbird != null) {
                            score += 500;
                        }
                        if (!birdQueue.isEmpty()) {
                            score -= 500;
                        }
                        while (!birdQueue.isEmpty()) {
                            birdQueue.poll();
                            score += 500;
                        }
                        if (level instanceof Level1) {
                            LevelStatusManager.writeLevelStatus("Level2", true);
                        } else if (level instanceof Level2) {
                            LevelStatusManager.writeLevelStatus("Level3", true);
                        }
                        game.setScreen(new LevelCleared(game, font, score, level));
                    }

                }, 1f);

            }
        }
        if (!blockList.isEmpty()) {
            for (Block block : blockList) {
                if (block != null) {
                    batch.setColor(1f, 1f, 1f, block.alpha);
                    Vector2 blockPosition = block.body.getPosition();
                    float blkscale = block.Block_Scale;
                    batch.draw(block.texture_region,
                            blockPosition.x * 100 - block.texture_region.getRegionWidth() / 2 * blkscale,
                            blockPosition.y * 100 - block.texture_region.getRegionHeight() / 2 * blkscale,
                            block.texture_region.getRegionWidth() * blkscale / 2f,
                            block.texture_region.getRegionHeight() * blkscale / 2f, // Origin
                            block.texture_region.getRegionWidth() * blkscale,
                            block.texture_region.getRegionHeight() * blkscale,
                            1f, 1f,
                            block.body.getAngle() * MathUtils.radiansToDegrees);
                }
                if (block != null) {
                    check_outofscreen(block);
                }

            }
            blockList.removeAll(blocktoremove);
            blocktoremove.clear();

        }
        if (currbird != null && !pigList.isEmpty()) { // Check if currbird is not null
            // Set color with alpha to control bird opacity

            batch.setColor(1f, 1f, 1f, currbird.birdAlpha);
            batch.draw(
                    birdRegion,
                    currbird.body.getPosition().x * 100 - birdTexture.getWidth() / 2 * currbird.BIRD_SCALE,
                    currbird.body.getPosition().y * 100 - birdTexture.getHeight() / 2 * currbird.BIRD_SCALE,
                    birdTexture.getWidth() * currbird.BIRD_SCALE / 2, // originX: Half width for center rotation
                    birdTexture.getHeight() * currbird.BIRD_SCALE / 2, // originY: Half height for center rotation
                    birdTexture.getWidth() * currbird.BIRD_SCALE,
                    birdTexture.getHeight() * currbird.BIRD_SCALE,
                    1f, 1f, // scaleX and scaleY (1 means no scaling)
                    currbird.body.getAngle() * MathUtils.radiansToDegrees // rotation angle
            );
        }

        font.draw(batch, "Score: " + score, 720, 870);
        if (birdQueue.isEmpty() && currbird == null) {
            birdcount.draw(batch, "Birds Remaining: " + (birdQueue.size()), 100, 870);
        } else {
            birdcount.draw(batch, "Birds Remaining: " + (birdQueue.size() + 1), 100, 870);
        }

        batch.setColor(1f, 1f, 1f, 1f); // Reset color back to opaque for other
        // textures
        pauseicon.draw(batch);
        batch.end();

        debugRenderer.render(world, batch.getProjectionMatrix());

        pauseinput();
        if (currbird != null) {
            handleInput();

            if (!currbird.isLaunched && isDragging) {
                // System.out.println("in condition");
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(139 / 255f, 69 / 255f, 19 / 255f, 1); // Brown color (RGB)

                // Get bird's current position
                Vector2 birdPosition = new Vector2(currbird.body.getPosition().x * 100,
                        currbird.body.getPosition().y * 100);
                // birdPosition.y += 100;
                // Draw left elastic
                drawThickLine(catapultAnchorLeft, birdPosition, 6f);

                // Draw right elastic
                drawThickLine(catapultAnchorRight, birdPosition, 6f);

                shapeRenderer.end();
            }
        }

        if (isDragging && currbird != null) {
            Vector2 trajectoryVelocity = releaseVelocity.set(currbird.body.getPosition()).sub(initialTouchPosition)
                    .scl(currbird.spped_scale);
            renderTrajectory(currbird.body.getPosition(), trajectoryVelocity);
        }

        if (currbird != null && currbird.isLaunched == true) {
            checkGroundCollision(); // Check if the bird hits the ground
        }

        // birdQueue.poll(); // Remove the bird from the queue after launching
        if (currbird == null && !birdQueue.isEmpty()) {
            // System.out.println("getting new bird...");
            currbird = birdQueue.poll(); // Get the next bird in the queue
            // System.out.println(currbird);
            currbird.body.setLinearVelocity(0, 0);
            currbird.body.setAngularVelocity(0);
            birdTexture = currbird.texture; // Update texture for the new bird
            birdRegion = new TextureRegion(birdTexture);

            currbird.repositionCurrentBird(); // Position the next bird for launching
        }

        if (currbird == null && birdQueue.isEmpty() && !pigList.isEmpty()) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    // Recheck the condition after a short delay
                    if (currbird == null && birdQueue.isEmpty() && !pigList.isEmpty()) {
                        // pigList.removeAll(pigList);
                        game.setScreen(new FailedScreen(game, level)); // Show the failed screen only if condition is
                                                                       // still
                        // true
                    }
                }
            }, 2f); // Delay of 0.1 seconds
        }

    }

    // Helper method to draw a thick line as a rectangle
    private void drawThickLine(Vector2 start, Vector2 end, float thickness) {
        Vector2 direction = end.cpy().sub(start); // Direction vector from start to end
        float length = direction.len(); // Length of the line
        float angle = direction.angleDeg(); // Angle of the line

        // Draw a rectangle as the thick line
        shapeRenderer.rect(start.x, start.y - thickness / 2, 0, thickness / 2, length, thickness, 1, 1, angle);
    }

    private void checkGroundCollision() {
        // Bird has landed on the ground and has minimal vertical velocity (not in
        // motion)
        // body.
        if (currbird.body.getPosition().y <= GROUND_Y + 0.9f && Math.abs(currbird.body.getLinearVelocity().y) < 0.1f
                || (currbird.health <= 0)) {
            vanishTimer += Gdx.graphics.getDeltaTime(); // Increment vanish timer
            // currbird.body.setAngularVelocity(0);
            currbird.body.setAngularDamping(2.0f);
            // Bird has been still on the ground for enough time to vanish
            if (vanishTimer >= VANISH_TIME) {
                // Start fading out the bird
                currbird.birdAlpha -= Gdx.graphics.getDeltaTime() / VANISH_TIME; // Decrease alpha over time

                if (currbird.birdAlpha <= 0f) {
                    // Bird is completely transparent, mark for removal
                    markBodyForRemoval(currbird.body, birdTexture); // Mark for removal
                    currbird = null; // Set to null to stop rendering and avoid further updates
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);

                }
            }
        }
        // Bird has gone off-screen, either below the visible ground or out of the
        // horizontal bounds
        else if (currbird.body.getPosition().y <= GROUND_Y || currbird.body.getPosition().x < -WORLD_WIDTH / 6
                || currbird.body.getPosition().x > WORLD_WIDTH) {

            markBodyForRemoval(currbird.body, birdTexture); // Mark for removal
            currbird = null; // Set to null to stop rendering and avoid further updates
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        } else {
            // Reset vanish timer and opacity if airborne
            // System.out.println(Math.abs(currbird.getLinearVelocity().y));
            vanishTimer = 0f;
            currbird.birdAlpha = 1f; // Reset opacity
            // currbird.setAngularVelocity(0); // Stop rotation if airborne
        }
    }

    private void check_outofscreen(Hittable obj) {

        if (obj.health <= 0) {
            obj.vanishTimer += Gdx.graphics.getDeltaTime();

            if (obj.vanishTimer >= hittable_VANISH_TIME) {
                obj.alpha -= Gdx.graphics.getDeltaTime() / hittable_VANISH_TIME; // Decrease alpha over time
                if (obj.alpha <= 0f) {
                    markBodyForRemoval(obj.body, obj.texture);
                    if (obj instanceof Block) {
                        blocktoremove.add(obj);
                    } else {
                        pigtoremove.add(obj);
                    }
                }
            }
        }

        else if (obj.body.getPosition().y <= GROUND_Y || obj.body.getPosition().x < 7
                || obj.body.getPosition().x > WORLD_WIDTH - 0.3f) {
            if (obj instanceof Pig) {
                if (obj.body.getPosition().x > WORLD_WIDTH - 0.5f && obj.body.getPosition().y >= GROUND_Y - 0.5f) {
                    obj.body.setAngularVelocity(8f);
                    obj.body.setAngularDamping(2);
                    return;

                } else if (obj.body.getPosition().x < 7 && obj.body.getPosition().x > 4) {
                    obj.body.setAngularVelocity(-8f);
                    obj.body.setAngularDamping(2);
                    return;
                } else if (obj.body.getPosition().x < 4) {
                    obj.health = 0;
                    return;
                }

            }
            // System.out.println(obj);
            obj.health = 0;
        }
    }

    // Helper method to check if the touch position is in the catapult area
    private boolean isTouchInCatapultArea(Vector2 touchPos) {
        float catapultX = 1.5f;
        float catapultY = GROUND_Y + 1f;
        float catapultWidth = 1.75f;
        float catapultHeight = 2.0f;

        return touchPos.x >= catapultX - catapultWidth / 2 && touchPos.x <= catapultX + catapultWidth / 2
                && touchPos.y >= catapultY && touchPos.y <= catapultY + catapultHeight;
    }

    private void renderTrajectory(Vector2 startPosition, Vector2 initialVelocity) {
        float time = 0;
        float gravity = world.getGravity().y;

        batch.begin();
        for (int i = 0; i < TRAJECTORY_POINTS; i++) {
            // Calculate the x and y positions using the projectile motion formula
            float x = startPosition.x + initialVelocity.x * time;
            float y = startPosition.y + initialVelocity.y * time + 0.5f * gravity * time * time;

            // Convert to pixel coordinates for rendering
            float pixelX = x * 100 - 5; // Adjust to center the dot (5 is half of the dot size)
            float pixelY = y * 100 - 5;

            // Draw a small circle or dot for each point in the trajectory
            batch.draw(birdTexture, pixelX, pixelY, 8, 8); // Scale down the texture for trajectory points
            time += TIME_STEP;
        }
        batch.end();
    }

    public void markBodyForRemoval(Body body, Texture texture) {
        bodiesToRemove.add(body);
        texturesToRemove.add(texture);

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        // System.out.println("game paused");
        ispaused = true;
    }

    @Override
    public void resume() {
        // System.out.println("game resumed");
        ispaused = false;
    }

    @Override
    public void hide() {
    }

    @Override
    public void beginContact(Contact contact) {
        if (ispaused) {
            return;
        }
        // System.out.println("In contact");
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        Object userDataA = bodyA.getUserData();
        Object userDataB = bodyB.getUserData();
        if ((userDataA instanceof Bird && userDataB instanceof Block)
                || (userDataA instanceof Block && userDataB instanceof Bird) && !currbird.hashitonce
                        && currbird.isLaunched) {
            Bird bird = (userDataA instanceof Bird) ? (Bird) userDataA : (Bird) userDataB;
            Block block = (userDataA instanceof Block) ? (Block) userDataA : (Block) userDataB;

            // Handle the bird-block collision
            handleBirdBlockCollision(bird, block);
            bird.health = 0;
            bird.hashitonce = true;
        }
        if ("ground".equals(bodyA.getUserData()) || "ground".equals(bodyB.getUserData()) && (currbird.isLaunched)) {
            Body otherBody = "ground".equals(bodyA.getUserData()) ? bodyB : bodyA;

            // Check if the other body is a plank (you might need a similar user data or
            // type check)
            if (otherBody.getUserData() instanceof Block) {
                Block plank = (Block) otherBody.getUserData();
                plank.body.setAngularDamping(0.075f);

                Vector2 impactVelocity = plank.body.getLinearVelocity();
                float speed = impactVelocity.len();
                // System.out.println(speed);
                // Calculate damage based on speed (adjust multiplier as needed)
                int damage = (int) (speed * 2.5); // Example: 10 damage per unit of speed
                plank.takeDamage(damage, plank);

            }
        }
        if (userDataA instanceof Block && userDataB instanceof Block) {
            Block blockA = (Block) userDataA;
            Block blockB = (Block) userDataB;

            // Handle the block-block collision
            handleBlockBlockCollision(blockA, blockB, contact);
        }

        // PIG
        // Bird-Pig collision
        if ((userDataA instanceof Bird && userDataB instanceof Pig) ||
                (userDataA instanceof Pig && userDataB instanceof Bird)) {
            Bird bird = (userDataA instanceof Bird) ? (Bird) userDataA : (Bird) userDataB;
            Pig pig = (userDataA instanceof Pig) ? (Pig) userDataA : (Pig) userDataB;
            bird.health = 0;
            bird.hashitonce = true;
            if (bird.isLaunched) {
                float velocity = bird.body.getLinearVelocity().len();
                int damage = (int) (velocity * bird.density * 2); // Example damage calculation
                pig.takeDamage(damage);
            }
        }

        // Block-Pig collision
        if ((userDataA instanceof Block && userDataB instanceof Pig) ||
                (userDataA instanceof Pig && userDataB instanceof Block)) {
            Block block = (userDataA instanceof Block) ? (Block) userDataA : (Block) userDataB;
            Pig pig = (userDataA instanceof Pig) ? (Pig) userDataA : (Pig) userDataB;

            float velocity = block.body.getLinearVelocity().len();
            if (velocity > 1.5f) { // Threshold velocity for damage
                int damage = (int) (velocity * block.density * 2); // Example damage calculation
                pig.takeDamage(damage);
            }
        }

        // Ground-Pig collision
        if ((userDataA instanceof Pig && "ground".equals(userDataB)) ||
                (userDataB instanceof Pig && "ground".equals(userDataA))) {
            Pig pig = (userDataA instanceof Pig) ? (Pig) userDataA : (Pig) userDataB;

            float velocity = pig.body.getLinearVelocity().len();
            if (velocity > 1.5f) { // Threshold velocity for ground impact
                int damage = (int) (velocity * pig.density * 2); // Example damage calculation
                pig.takeDamage(damage);
            }
        }
    }

    private void handleBirdBlockCollision(Bird bird, Block block) {

        // Calculate the point of impact relative to the block's center
        Vector2 impactPoint = new Vector2();
        impactPoint.set(bird.body.getPosition()).sub(block.body.getPosition());

        // Calculate the distance from the center of the block to the impact point (r)
        float distance = impactPoint.len();
        // System.out.println(impactPoint);
        // If the bird hits the block off-center, apply torque
        float torque = 1;
        if (distance > 0) {
            // System.out.println("distance >0");
            // Calculate the direction of the collision impact (velocity vector of the bird)
            Vector2 velocity = bird.body.getLinearVelocity().cpy();

            // Apply torque proportional to the distance of impact and the bird's velocity
            // The farther from the center, the more torque applied
            torque = velocity.x * distance; // Simple torque calculation; can be modified to make it more
            block.torque = torque;
            torque = (float) (torque * 0.8);
            if (impactPoint.y > 0) {
                block.body.setAngularVelocity(-torque);
            } else {
                block.body.setAngularVelocity(torque);
            }

            bird.moInertia = 1;
            // block.torque = 0;
        }

        block.takeDamage(bird.density * 10 + (Math.abs(torque / 5)), block);
        // System.out.println(block.getHealth());
    }

    private void handleBlockBlockCollision(Block blockA, Block blockB, Contact contact) {
        // Get the collision points
        WorldManifold worldManifold = contact.getWorldManifold();
        Vector2 collisionPoint = worldManifold.getPoints()[0];

        // Calculate the relative velocity at the collision point
        Vector2 velocityA = blockA.body.getLinearVelocityFromWorldPoint(collisionPoint);
        Vector2 velocityB = blockB.body.getLinearVelocityFromWorldPoint(collisionPoint);
        Vector2 relativeVelocity = velocityA.sub(velocityB);

        // Calculate impulse based on relative velocity and masses of blocks
        float massA = blockA.body.getMass();
        float massB = blockB.body.getMass();
        float impulseStrength = relativeVelocity.len() * (massA + massB) / 2;
        if (impulseStrength < 3) {
            return;
        }
        // System.out.println(impulseStrength);
        // Apply forces based on impact angle and impulse strength
        Vector2 impactDirection = relativeVelocity.nor().scl(impulseStrength);
        blockA.body.applyLinearImpulse(impactDirection, collisionPoint, true);
        blockB.body.applyLinearImpulse(impactDirection.scl(-1), collisionPoint, true);

        // System.out.println(impulseStrength);
        // Calculate angular velocity instead of torque for rotation
        float distanceA = collisionPoint.dst(blockA.body.getPosition());
        float distanceB = collisionPoint.dst(blockB.body.getPosition());
        float angularVelocityA = impulseStrength * distanceA * 0.1f; // Adjust 0.1f as needed for rotation sensitivity
        float angularVelocityB = impulseStrength * distanceB * 0.1f;

        // Apply angular velocities based on impact point location
        if (collisionPoint.y > blockA.body.getPosition().y) {
            blockA.body.setAngularVelocity(angularVelocityA);
        } else {
            blockA.body.setAngularVelocity(-angularVelocityA);
        }

        if (collisionPoint.y > blockB.body.getPosition().y) {
            blockB.body.setAngularVelocity(-angularVelocityB);
        } else {
            blockB.body.setAngularVelocity(angularVelocityB);
        }

        // Damage both blocks on impact
        blockA.takeDamage(5 + (impulseStrength / 2), blockA);
        blockB.takeDamage(5 + impulseStrength / 2, blockB);
    }

    @Override
    public void endContact(Contact contact) {
        // Handle collision end
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Optionally handle before the collision resolution
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Optionally handle after the collision resolution
    }

    public float givedeltatime() {
        return Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dispose() {
        birdTexture.dispose();
        backgroundTexture.dispose();
        catapultTexture.dispose();
        // blockList.getFirst().texture.dispose();
        batch.dispose();
        world.dispose();
        debugRenderer.dispose();
        font.dispose();
    }

    public GameState saveGameState() {
        GameState gameState = new GameState();
        gameState.score = score;
        gameState.isPaused = ispaused;

        // Save current bird
        if (currbird != null) {
            // System.out.println("saving currbird");
            gameState.currentBird = new BirdState(
                    currbird.name,
                    currbird.body.getPosition().x,
                    currbird.body.getPosition().y,
                    currbird.body.getLinearVelocity().x,
                    currbird.body.getLinearVelocity().y,
                    currbird.health,
                    currbird.isLaunched);
        }
        // Save birds
        for (Bird bird : birdQueue) {
            gameState.birdQueue.add(new BirdState(
                    bird.name,
                    bird.body.getPosition().x,
                    bird.body.getPosition().y,
                    bird.body.getLinearVelocity().x,
                    bird.body.getLinearVelocity().y,
                    bird.health,
                    bird.isLaunched));
        }

        // Save pigs
        for (Pig pig : pigList) {
            // System.out.println(pig.body.getPosition().y);
            gameState.pigList.add(new HittableState(
                    pig.body.getPosition().x,
                    pig.body.getPosition().y,
                    pig.getHealth(), pig.body.getAngle() * MathUtils.radiansToDegrees, pig.name));
        }

        // Save blocks
        for (Block block : blockList) {
            gameState.blockList.add(new HittableState(
                    block.body.getPosition().x,
                    block.body.getPosition().y,
                    block.getHealth(), block.body.getAngle() * MathUtils.radiansToDegrees, block.name));
        }

        return gameState;
    }

    public void loadGameState(GameState gameState) {
        // Restore score and pause state
        score = gameState.score;
        ispaused = gameState.isPaused;

        // Restore current bird
        if (gameState.currentBird != null) {

            if (gameState.currentBird.name.equals("Red")) {
                currbird = new RedBird(world); // Example for creating a bird

            } else if (gameState.currentBird.name.equals("Black")) {
                currbird = new BlackBird(world);
            } else if (gameState.currentBird.name.equals("Yellow")) {
                currbird = new YellowBird(world);
                // System.out.println("CREATING yellowbird");
            }
            currbird.body.setTransform(
                    gameState.currentBird.posX,
                    gameState.currentBird.posY,
                    0);
            currbird.body.setLinearVelocity(
                    gameState.currentBird.velX,
                    gameState.currentBird.velY);
            currbird.health = gameState.currentBird.health;
            currbird.isLaunched = gameState.currentBird.isLaunched;
            this.birdTexture = currbird.texture;
            this.birdRegion = new TextureRegion(birdTexture);
        }

        for (BirdState birdState : gameState.birdQueue) {
            Bird bird = null;
            if (birdState.name.equals("Red")) {
                bird = new RedBird(world); // Example for creating a bird
                // System.out.println("created red bird");

            } else if (birdState.name.equals("Black")) {
                bird = new BlackBird(world);
            } else if (birdState.name.equals("Yellow")) {
                bird = new YellowBird(world);
            }

            bird.body.setTransform(birdState.posX, birdState.posY, 0);
            bird.body.setLinearVelocity(birdState.velX, birdState.velY);
            bird.health = birdState.health;
            bird.isLaunched = birdState.isLaunched;
            birdQueue.add(bird);
        }

        for (HittableState pigState : gameState.pigList) {
            Pig pig = null;
            if (pigState.name.equals("normalpig")) {
                pig = new NormalPig(world, this, 0, 0);
            } else if (pigState.name.equals("fatpig")) {
                pig = new FatPig(world, this, 0, 0);
            } else if (pigState.name.equals("kingpig")) {
                pig = new KingPig(world, this, 0, 0);
            }
            pig.transform(pigState.posX, pigState.posY, pigState.angle);
            pig.health = (pigState.health);
            pigList.add(pig);
        }

        for (HittableState blockState : gameState.blockList) {
            Block block = null;
            if (blockState.name.equals("woodplank")) {
                block = new WoodenBlock(world, this, 0, 0);
            } else if (blockState.name.equals("glassplank")) {
                block = new GlassBlock(world, this, 0, 0);
            } else if (blockState.name.equals("stoneplank")) {
                block = new StoneBlock(world, this, 0, 0);
            }
            block.transform(blockState.posX, blockState.posY, blockState.angle);
            block.health = (blockState.health);
            blockList.add(block);
        }
        this.resume();
    }

    public static void saveToFile(GameState gameState) {

        ClearGameState();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            // System.out.println("state saved!");
            out.writeObject(gameState);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static GameState loadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            // System.out.println("state loaded!");
            return (GameState) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void ClearGameState() {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            // Writing a null or default state to the file to clear it
            out.writeObject(null); // Clears by writing 'null' (or use a default game state object)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ResetGame() {

        for (int i = 1; i < 4; i++) {
            try (FileOutputStream fileOut = new FileOutputStream("gamestate" + i + ".sav");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                // Writing a null or default state to the file to clear it
                out.writeObject(null); // Clears by writing 'null' (or use a default game state object)
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        LevelStatusManager.writeLevelStatus("Level2", false);
        LevelStatusManager.writeLevelStatus("Level3", false);

    }
}
