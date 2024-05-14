package com.ceat.game;

import com.badlogic.gdx.graphics.g3d.ModelBatch;

public class GridTile {
    private SimpleModelInstance model;
    private int x;
    private int y;
    private float yOffset;
    private boolean active;
    public GridTile(int x, int y) {
        model = new SimpleModelInstance(SimpleModelInstance.planeModel)
                .setScale(5, 5, 5)
                .setColor(0, 0, 0)
                .setOpacity(0.2f);
        this.x = x;
        this.y = y;
        active = true;
        yOffset = 20;
        new Loop(0.3f) {
            public void run(float dt, float elapsed) {
                yOffset = Lerp.lerp(20, 0, elapsed/0.3f);
            }
            public void onEnd() {
                yOffset = 0;
            }
        };
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean getActive() {
        return active;
    }

    public void render(float lifetime, ModelBatch batch) {
        model.setPosition(x*6, (float)Math.sin(lifetime*2 + x + y)*0.7f + yOffset, y*6);
        model.render(batch);
    }

    public void exit() {
        active = false;
        new Loop(0.3f) {
            public void run(float dt, float elapsed) {
                yOffset = Lerp.lerp(0, 20, elapsed/0.3f);
            }
            public void onEnd() {
                onDispose();
                dispose();
            }
        };
    }

    public void onDispose() {}
    public void dispose() {
        model.dispose();
    }
}
