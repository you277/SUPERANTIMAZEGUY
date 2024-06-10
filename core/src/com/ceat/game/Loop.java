package com.ceat.game;

import java.util.ArrayList;

public class Loop {
    public enum LoopType {
        SYNCED,
        ABSOLUTE
    }
    private static final ArrayList<Loop> loops = new ArrayList<>();
    public static void runLoops(float deltaTime, float rawDeltaTime) {
        for (int i = 0; i < loops.size(); i++) {
            Loop loop = loops.get(i);
            float dt = loop.type == LoopType.SYNCED ? deltaTime : rawDeltaTime;
            loop.lifetime += dt;
            loop.run(dt, loop.lifetime);
            if (loop.lifetime > loop.duration) {
                loop.onEnd();
                loops.remove(i);
                i--;
            }
        }
    }
    private float duration;
    private float lifetime;
    private LoopType type;
    public Loop(float duration, LoopType type) {
        this.duration = duration;
        this.type = type;
        loops.add(this);
    }
    public Loop(float duration) {
        Loop hi = this;
        new Loop(duration, LoopType.SYNCED) {
            public void run(float deltaTime, float elapsed) {
                hi.run(deltaTime, elapsed);
            }
            public void onEnd() {
                hi.onEnd();
            }
        };
    }

    public void run(float deltaTime, float elapsed) {}
    public void onEnd() {}
}
