package com.ceat.game.entity;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.ceat.game.Lerp;
import com.ceat.game.Loop;
import com.ceat.game.SimpleModelInstance;

public class GridTile {
    private SimpleModelInstance model;
    private int x;
    private int y;
    private float yOffset;
    private boolean active;
    private float lifetime;
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
    public Vector3 getAbsolutePosition() {
        return new Vector3(x*6, (float)Math.sin(lifetime*2 + x + y)*0.7f + yOffset, y*6);
    }
    public void render(ModelBatch batch) {
        model.setPosition(getAbsolutePosition());
        model.render(batch);
    }

    public void render(float lifetime, ModelBatch batch) {
        this.lifetime = lifetime;
        model.setPosition(getAbsolutePosition());
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
