package com.github.angry_bird;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Level1 extends Level{

    public Level1(){
        this.addBird(new Redbird(190, 250,"redbird.png"));
        this.addBird(new Blackbird(175, 125,"blackbird.png"));
        this.addBird(new Yellowbird(105, 125,"yellowbird.png"));
        this.addBird(new Redbird(32, 125,"redbird.png"));
        this.addBlock(new Woodblock(1137,125,"woodplank.png",0));
        this.addBlock(new Woodblock(1175,125,"woodframe.png",0));
        this.addPig(new Plainpig(1175,220,"plainpig.png"));
        this.addBlock(new Woodblock(1275,125,"woodplank.png",0));
        this.addBlock(new Woodblock(1200,240,"woodplank.png",90));
        this.addPig(new Plainpig(1160,355,"plainpig.png"));
        this.addPig(new Plainpig(1050,125,"plainpig.png"));
        this.addBlock(new Woodblock(1045,115,"woodplank.png",-40));
        this.addPig(new Plainpig(1295,125,"plainpig.png"));
        this.addBlock(new Woodblock(1370,115,"woodplank.png",40));
    }
}
