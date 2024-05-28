package com.ceat.game;

import java.util.ArrayList;

public class Loop {
    private static final ArrayList<Loop> loops = new ArrayList<>();
    public static void runLoops(float deltaTime) {
        for (int i = 0; i < loops.size(); i++) {
            Loop loop = loops.get(i);
            loop.lifetime += deltaTime;
            loop.run(deltaTime, loop.lifetime);
            if (loop.lifetime > loop.duration) {
                loop.onEnd();
                loops.remove(i);
                i--;
            }
        }
    }
    private final float duration;
    private float lifetime;
    public Loop(float duration) {
        this.duration = duration;
        loops.add(this);
    }

    public void run(float deltaTime, float elapsed) {}
    public void onEnd() {}
}
