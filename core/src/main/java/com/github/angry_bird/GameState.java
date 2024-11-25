package com.github.angry_bird;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    // Game progress
    public int score;
    public boolean isPaused;

    // Bird state
    public Queue<BirdState> birdQueue = new LinkedList<>();
    public BirdState currentBird;

    // Pigs and blocks
    public ArrayList<HittableState> pigList = new ArrayList<>();
    public ArrayList<HittableState> blockList = new ArrayList<>();

    // // World properties (optional)
    // public ArrayList<BodyState> bodies = new ArrayList<>();

}
