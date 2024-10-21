package com.github.angry_bird;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Level1 extends Level{

    public Level1(){
        this.addBird(new Redbird(190, 250,"redbird.png"));
        this.addBird(new Blackbird(175, 125,"blackbird.png"));
        this.addBird(new Yellowbird(105, 125,"yellowbird.png"));
        this.addBird(new Redbird(32, 125,"redbird.png"));
    }
}
